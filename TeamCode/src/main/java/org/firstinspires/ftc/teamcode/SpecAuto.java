package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.time.Instant;


//C:\Users\Minh\AppData\Local\Android\Sdk\platform-tools\adb.exe connect  192.168.43.1:5555


@Config
@Autonomous(name = "SpecAuto", group = "Autonomous")
public class SpecAuto extends LinearOpMode {

    MecanumDrive Drive;
    private Servo claw;
    private Servo arm;
    private Servo elbow;
    public DcMotor lift1;
    public DcMotor lift2;

    public static int SLIDES_DOWN = 0;
    public static int SLIDES_PICKUP = 400;
    public static int SLIDES_SCORE1 = 2150;
    public static int SLIDES_SCORE2 = 1300;
    public static double armPickUp = 0.57;
    public static double armFloor = 0;
    public static double armInit = 0.3;
    public static double clawOpen = 0.75;
    public static double clawClose = 0.34;
    public static double armBucket = 0.25;
    public static double armScore2 = 0.03;
    public static double armScore = 0.03;
    public static double clawInit = 0.4;
    public static double elbowdown;
    public static double elbowscore;
    public static double elbowpickup;

    Vector2d barPos = new Vector2d(-45.5, -5);
    Vector2d scorePos = new Vector2d(-43.7, -5);
    Vector2d scorePos2 = new Vector2d(-43.7, -8);
    Vector2d groundPick = new Vector2d(-45.5, -59);
    Vector2d toWall = new Vector2d(-50, -46);
    Vector2d drop = new Vector2d(-50, -59);
    Vector2d backup = new Vector2d(-62, -43);
    Vector2d wallPos = new Vector2d(-55, -10);
    Vector2d parked = new Vector2d(-62, -60);

    Vector2d spec3hang = new Vector2d(-41.5, -5);


    protected void SpecScore() {
        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
        Action score = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
        Actions.runBlocking(
                new SequentialAction(
                    new ParallelAction(
                            ready,
                            ready,
                            new InstantAction(() -> lift1.setTargetPosition(SLIDES_SCORE1)),
                            new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift2.setTargetPosition(SLIDES_SCORE1)),
                            new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift1.setPower(1)),
                            new InstantAction(() -> lift2.setPower(1)),
                            new InstantAction(() -> arm.setPosition(armScore))
                    )//,
//                    score,
//                    score,
//                    new InstantAction(() -> sleep(100)),
//                    new ParallelAction(
//                        new InstantAction(() -> lift1.setTargetPosition(SLIDES_SCORE2)),
//                        new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
//                        new InstantAction(() -> lift1.setPower(1)),
//                        new InstantAction(() -> lift2.setTargetPosition(SLIDES_SCORE2+50)),
//                        new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
//                        new InstantAction(() -> lift2.setPower(1))
//                    ),
//                    new InstantAction(() -> sleep(750)),
//                    new InstantAction(()-> claw.setPosition(clawOpen))

                )
        );
    }


    @Override
    public void runOpMode() throws InterruptedException {
        claw = hardwareMap.servo.get("claw");
        arm = hardwareMap.servo.get("arm2");
        elbow = hardwareMap.servo.get("elbow");

        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");
        lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift1.setDirection(DcMotorSimple.Direction.REVERSE);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift2.setDirection(DcMotorSimple.Direction.FORWARD);



        arm.setDirection(Servo.Direction.FORWARD);

        Drive = new MecanumDrive(hardwareMap, new Pose2d(-63, -20, 0));
        Pose2d pose = Drive.localizer.getPose();

        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
        Action park = Drive.actionBuilder(new Pose2d(groundPick, 0)).splineToConstantHeading(parked, 0).build();
        Action score = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
        Action score2 = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0)).build();
        Action score3 = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0)).build();
        Action wall = Drive.actionBuilder(new Pose2d(scorePos, 0)).strafeToConstantHeading(toWall).build();
        Action sample1 = Drive.actionBuilder(new Pose2d(scorePos, 0)).strafeTo(groundPick).build();
        Action back = Drive.actionBuilder(new Pose2d(toWall, 0)).strafeToConstantHeading(backup, null, new ProfileAccelConstraint(-10,10)).build();
        Action back2 = Drive.actionBuilder(new Pose2d(toWall, 0)).strafeToConstantHeading(backup, null, new ProfileAccelConstraint(-10,10)).build();
        Action dropoff = Drive.actionBuilder(new Pose2d(groundPick, 0)).splineToConstantHeading(drop, Math.toRadians(0)).build();
        Action ready2 = Drive.actionBuilder(new Pose2d(backup, 0)).strafeToConstantHeading(barPos).build();
        Action ready3 = Drive.actionBuilder(new Pose2d(backup, 0)).strafeToConstantHeading(scorePos2).build();
        Action pickfromWitt = Drive.actionBuilder(new Pose2d(drop, 0)).strafeToConstantHeading(toWall).build();

        claw.setPosition(clawClose);
        arm.setPosition(armInit);


        waitForStart();

        SpecScore();

//        Actions.runBlocking(wall);
//
//        lift1.setTargetPosition(SLIDES_DOWN);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift1.setPower(1);
//
//        lift2.setTargetPosition(SLIDES_DOWN+50);
//        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift2.setPower(1);
//
//        sleep(500);
//
//        Actions.runBlocking(back);



//        Actions.runBlocking(sample1);
//
//        sleep(500);
//
//        arm.setPosition(armFloor);
//
//        sleep(500);
//
//        claw.setPosition(clawClose);
//
//        sleep(500);
//
//        arm.setPosition(armPickUp);
//
//        lift1.setTargetPosition(SLIDES_PICKUP);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift1.setPower(1);
//
//        lift2.setTargetPosition(SLIDES_PICKUP+50);
//        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift2.setPower(1);
//
//        Actions.runBlocking(dropoff);
//
//        sleep(500);
//
//        claw.setPosition(clawOpen);
//
//        sleep(500);
//
//        Actions.runBlocking(pickfromWitt);
//
//        sleep(500);
//
//        Actions.runBlocking(back2);
//
//        sleep(500);
//
//        claw.setPosition(clawClose);
//
//        sleep(250);
//
//        lift1.setTargetPosition(SLIDES_SCORE1);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift1.setPower(0.5);
//
//        lift2.setTargetPosition(SLIDES_SCORE1);
//        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift2.setPower(0.5);
//
//        arm.setPosition(armScore);
//
//        Actions.runBlocking(ready3);
//
//        sleep(250);
//
//        Actions.runBlocking(score3);
//
//        sleep(500);
//
//        lift1.setTargetPosition(SLIDES_SCORE2);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift1.setPower(1);
//
//        lift2.setTargetPosition(SLIDES_SCORE2+50);
//        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift2.setPower(1);
//
//
//
////        arm.setPosition(armScore2);
//
//        sleep(250);
//
//        claw.setPosition(clawOpen);
//
//        sleep(100);
//
//        lift1.setTargetPosition(SLIDES_DOWN);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift1.setPower(1);
//
//        lift2.setTargetPosition(SLIDES_DOWN+50);
//        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift2.setPower(1);
//
//        arm.setPosition(armInit);


//        arm.setPosition(armFloor);
//        lift.setTargetPosition(SLIDES_DOWN);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.setPower(0.3);
//
//        sleep(1000);
//
//        claw.setPosition(clawClose);
//
//        sleep(500);
//        Actions.runBlocking(park);


//        sleep(100);
//
//        lift.setTargetPosition(SLIDES_DOWN);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.setPower(0.5);
//
//        arm.setPosition(armFloor);


        while (opModeIsActive() && !isStopRequested()) {
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }


    }

    public TrajectoryActionBuilder getTrajectoryActionBuilder() {

        return Drive.actionBuilder(Drive.localizer.getPose());
    }


}