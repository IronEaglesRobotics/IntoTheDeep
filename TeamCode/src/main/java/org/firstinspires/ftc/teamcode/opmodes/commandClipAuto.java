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
    Vector2d toBar = new Vector2d(-32, -15);
    Pose2d toPickup = new Pose2d(0,-13,Math.toRadians(135));
    Pose2d reset1 = new Pose2d(-20,0,Math.toRadians(135));
    TrajectoryActionBuilder builder;


    @Override
    public void initialize() {
        robot = new Robot().init(hardwareMap);
        builder = robot.getDrive().actionBuilder(start);
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        schedule(clip().andThen(placeBlock1()));
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
        }
    }
    Command clip(){
        return new WaitCommand(20)
                .andThen(robot.getSlides().preclip())
                .alongWith(robot.runAction(builder.splineToConstantHeading(toBar, Math.toRadians(45)).build()))
                .andThen(robot.getSlides().postclip())
                .alongWith(new WaitCommand(2000).andThen(new Claw.ClawCommand(robot.getClaw(), true)));
    }
    Command placeBlock1(){
        return robot.getSlides().down()
                .alongWith(robot.getIntakeArm().extendCommand())
                .andThen(robot.runAction(builder.splineToLinearHeading(toPickup,0).build()))
                .alongWith(robot.getIntake().runIntake())
                .andThen(robot.runAction(builder.setTangent(Math.toRadians(135)).lineToYConstantHeading(5).build()))
                .andThen(robot.runAction(builder.turn(90).build()))
                .andThen(robot.getIntake().reverseIntake())
                .andThen(robot.getIntakeArm().extendCommand())
                .andThen(robot.runAction(builder.splineToLinearHeading(reset1,0).build()));
    }
}
