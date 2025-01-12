package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "clipAutoCommand", preselectTeleOp = "Main Teleop")
public class commandClipAuto extends CommandOpMode {
    Robot robot;
    Pose2d start = new Pose2d(0, 0, 0);
    Vector2d toBar = new Vector2d(-32, -21);
    Pose2d toPickup = new Pose2d(-17,16,Math.toRadians(135));
    Pose2d toWall = new Pose2d(15,28,Math.toRadians(180));
    Pose2d toBar2 = new Pose2d(-32,-18,Math.toRadians(0));
    Vector2d toPark = new Vector2d(32,25);
    TrajectoryActionBuilder builder;


    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot().init(hardwareMap);
        builder = robot.getDrive().actionBuilder(start);
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        clip().andThen(Clip2()).schedule();
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
        }
    }
    Command clip(){
        return new WaitCommand(20)
                .andThen(robot.getSlides().preclip())
                .andThen(new WaitCommand(500))
                .andThen(robot.runAction(builder.splineToConstantHeading(toBar, Math.toRadians(90)).build()))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command placeBlock1(){
        return robot.getSlides().down()
                .andThen(robot.runAction(builder.splineToLinearHeading(toPickup,0).build()))
                .andThen(robot.getIntakeArm().extendCommand())
                .andThen(robot.getIntake().runIntake())
                .andThen(robot.runAction(builder.setTangent(Math.toRadians(135)).lineToYConstantHeading(5).build()))
                .andThen(robot.runAction(builder.turn(90).build()))
                .andThen(robot.getIntake().reverseIntake())
                .andThen(robot.getIntakeArm().extendCommand());
    }
//    Command grab(){
//        return robot.runAction(builder.splineToLinearHeading(toWall,Math.toRadians(90)).build())
//                .andThen(robot.getClaw().adaptClaw());
//    }
    Command Clip2(){
        return robot.getSlides().down()
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(toBar,0)).splineToLinearHeading(toWall,Math.toRadians(-60)).build()))
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).splineToLinearHeading(toBar2,Math.toRadians(60)).build()))
                .andThen(robot.getSlides().postclip()
                .alongWith(new WaitCommand(1000)
                .andThen(robot.getClaw().openCommand())));
    }
    Command park(){
        return robot.runAction(builder.splineToConstantHeading(toPark,Math.toRadians(90)).build())
                .alongWith(robot.getSlides().down());
    }
}
