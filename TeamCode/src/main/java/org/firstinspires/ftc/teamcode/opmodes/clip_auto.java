package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;

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
                .afterDisp(1,()->{ robot.getSlides().setTarget(Slides.Position.PRECLIP);})
                .splineToSplineHeading(new Pose2d(-25,-30,0),0)
                .afterDisp(0,()->{robot.getSlides().setTarget(Slides.Position.POSTCLIP);})
                .build();
        Action act2 = robot.getDrive().actionBuilder(new Pose2d(-25,-30,0))
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(20,0,Math.toRadians(135)),0)
                .afterDisp(0,robot::activateIntake)
                .lineToX(25)
                .waitSeconds(.5)
                .turn(90)
                .afterDisp(0,robot.getIntake()::reverseBeatBar)
                .afterTime(1,robot.getIntakeArm()::toggleExtension)
                .splineToLinearHeading(new Pose2d(20,0,Math.toRadians(135)),0)
                .build();

        Actions.runBlocking(act1);
        wait(500);
        Actions.runBlocking(act2);
    }
}
