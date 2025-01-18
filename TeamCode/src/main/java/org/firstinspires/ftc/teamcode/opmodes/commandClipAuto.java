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
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "clipAutoCommand", preselectTeleOp = "Main Teleop")
public class commandClipAuto extends CommandOpMode {
    Robot robot;
    Pose2d start = new Pose2d(0, 0, Math.toRadians(180));
    Vector2d toBar = new Vector2d(33, 27);
    Pose2d toPickup = new Pose2d(30,-15,Math.toRadians(0));
    Pose2d toWall = new Pose2d(10,-12,Math.toRadians(-2));
    Pose2d toBar2 = new Pose2d(20,18,Math.toRadians(180));
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
        clip().andThen(placeBlock1()).andThen(Clip2()).andThen(Clip2()).schedule();
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
        }
    }
    Command clip(){
        return new Intake.colorSet(Intake.colors.NULL,robot.getIntake())
                .andThen(robot.getSlides().preclip())
                .andThen(new WaitCommand(200))
                .andThen(robot.runAction(builder.splineToConstantHeading(toBar,Math.toRadians(-135)).build()))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command placeBlock1(){
        return robot.getSlides().down()
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(toBar,Math.toRadians(180))).splineToLinearHeading(new Pose2d(15,0,Math.toRadians(90)),Math.toRadians(150)).splineToLinearHeading(toPickup,Math.toRadians(45)).build()))
                .andThen(robot.getPusher().activateCommand())
                .andThen(new WaitCommand(50))
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toPickup).turn(Math.toRadians(-90)).setTangent(0).lineToX(-10).build()));
    }
//    Command grab(){
//        return robot.runAction(builder.splineToLinearHeading(toWall,Math.toRadians(90)).build())
//                .andThen(robot.getClaw().adaptClaw());
//    }
    Command Clip2(){
        return robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,-15,Math.toRadians(180))).splineToLinearHeading(toWall,Math.toRadians(50)).build())
                .andThen(robot.getPusher().offCommand().alongWith(robot.getSlides().down()))
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).setTangent(Math.toRadians(0)).lineToX(-4).build()))
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(200))
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).splineToLinearHeading(toBar2,Math.toRadians(-110)).build()))
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toBar2).setTangent(Math.toRadians(190)).lineToX(33).build()))
                .andThen(robot.getSlides().postclip()
                .alongWith(new WaitCommand(200)
                .andThen(robot.getClaw().openCommand())));
    }
    Command park(){
        return robot.runAction(builder.splineToConstantHeading(toPark,Math.toRadians(90)).build())
                .alongWith(robot.getSlides().down());
    }
}
