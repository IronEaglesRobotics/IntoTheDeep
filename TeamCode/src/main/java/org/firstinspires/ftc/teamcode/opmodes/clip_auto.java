package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;
import org.firstinspires.ftc.teamcode.roadrunner.ActionCommand;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "clip_auto", preselectTeleOp = "Main Teleop")
public class clip_auto  extends LinearOpMode {
    Robot robot;

//    @Override
//    public void initialize() {
//
//
//    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        Action act1 = robot.getDrive().actionBuilder(new Pose2d(0,0,0))
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.PRECLIP);
                    robot.getSlides().periodic();
                })
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-26,-18),-Math.PI/2)
                .afterDisp(0,()->{robot.getSlides().setTarget(Slides.Position.POSTCLIP);})
                .afterDisp(1,robot.getClaw()::open)
                .waitSeconds(2)
                .build();
        Action act2 = robot.getDrive().actionBuilder(new Pose2d(-26,-18,0))
                .setTangent(0)
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.DOWN);
                    robot.getSlides().periodic();
                    robot.getIntakeArm().toggleExtension();
                    robot.getIntakeArm().periodic();
                })
                .splineToLinearHeading(new Pose2d(-13,0,Math.toRadians(135)),0)
                .afterDisp(0,robot.getIntake()::wristUp)
                .afterDisp(0,robot.getIntake()::startBeatBar)
                .lineToX(-25)
                .waitSeconds(.5)
                .turn(Math.toRadians(90))
                .afterDisp(0,robot.getIntake()::reverseBeatBar)
                .afterTime(1,robot.getIntakeArm()::toggleExtension)
                .afterDisp(0,robot.getIntakeArm()::periodic)
                .splineToLinearHeading(new Pose2d(-20,0,Math.toRadians(135)),0)
                .build();
        waitForStart();

        Actions.runBlocking(act1);
        Actions.runBlocking(act2);
    }
}
