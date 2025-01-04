package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;
import org.firstinspires.ftc.teamcode.roadrunner.ActionCommand;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "clip_auto", preselectTeleOp = "Main Teleop")
public class clip_auto  extends CommandOpMode {
    Robot robot;
    Pose2d start = new Pose2d(0, 0, 0);
    Vector2d toBar = new Vector2d(-26, -18);
    Pose2d toPickup = new Pose2d(-13,0,Math.toRadians(135));
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
                .alongWith(robot.runAction(builder.splineToConstantHeading(toBar, -Math.PI / 2).build()))
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
