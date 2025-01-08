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
    Pose2d start = new Pose2d(0, 0, 180);
    Vector2d toBar = new Vector2d(-32, -15);
    Pose2d toPickup = new Pose2d(-13,0,Math.toRadians(-135));
    Pose2d reset1 = new Pose2d(-20,0,Math.toRadians(-135));

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        Action act1 = robot.getDrive().actionBuilder(start)
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.PRECLIP);
                    robot.update();
                })
                .setTangent(0)
                .splineToConstantHeading(toBar,-Math.PI/2)
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.POSTCLIP);
                    robot.update();
                })
                .afterDisp(1,robot.getClaw()::open)
                .waitSeconds(2)
                .build();
        Action act2 = robot.getDrive().actionBuilder(new Pose2d(toBar,180))
                .setTangent(0)
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.DOWN);
                    robot.getIntakeArm().toggleExtension();
                    robot.update();
                })
                .splineToLinearHeading(toPickup,Math.PI/2)
                .afterDisp(0,robot.getIntake()::wristUp)
                .afterDisp(0,robot.getIntake()::startBeatBar)
                .afterDisp(0,robot::update)
                .lineToX(-25)
                .splineToLinearHeading(toPickup,0)
                .afterDisp(0,robot.getIntake()::reverseBeatBar)
                .afterTime(1,robot.getIntakeArm()::toggleExtension)
                .afterDisp(0,robot::update)
                .splineToLinearHeading(new Pose2d(-20,0,Math.toRadians(135)),Math.PI/2)
                .build();
        Action act3 = robot.getDrive().actionBuilder(reset1)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(0,5,0),Math.toRadians(90))
                .afterDisp(0,robot.getClaw()::close)
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.PRECLIP);
                    robot.update();
                })
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(toBar,180),Math.toRadians(90))
                .afterDisp(0,()->{
                    robot.getSlides().setTarget(Slides.Position.POSTCLIP);
                    robot.update();
                })
                .afterDisp(1,robot.getClaw()::open)
                .waitSeconds(2)
                .build();
        Action act4 = robot.getDrive().actionBuilder(new Pose2d(toBar,180))
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(0,10),Math.toRadians(90))
                .build();
        waitForStart();

        Actions.runBlocking(act1);
        Actions.runBlocking(act2);
        Actions.runBlocking(act3);
        Actions.runBlocking(act4);
    }
}
