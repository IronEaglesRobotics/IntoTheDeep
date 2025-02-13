package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.IntegralScanResult;
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
    private Servo arm1;
    public Servo wrist;
    private Servo arm2;
    private Servo elbow;
    public DcMotor lift1;
    public DcMotor lift2;


    public static int SLIDES_DOWN = 0;
    public static int SLIDES_PICKUP = 450   ;
    public static int SLIDES_SCORE1 = 1900;
    public static int SLIDES_SCORE2 = 1300;
    public static double armPickUp = 0.57;
    public static double armFloor = 0;
    public static double armInit = 0.25;
    public static double clawOpen = 0.4;
    public static double clawClose = 0;
    public static double armBucket = 0.25;
    public static double armScore2 = 0.03;
    public static double armScore = 0.07;
    public static double clawInit = 0.4;
    public static double elbowdown = 0.4;
    public static double elbowscore = 0.8;
    public static double elbowSpec = 0.8;
    public static double elbowSpec2 = 0.15;
    public static double elbowpickup = 0.31;
    public static double wristFlipped = 0.54;
    public static double wristNotFlipped = 0;
    public static double elbowInit = 0.9;
    public static int SLIDES1 = 1075;

    Vector2d barPos = new Vector2d(-37.5, -5);
    Vector2d barPos2 = new Vector2d(-56.5, -6.5);
    Vector2d barPos3 = new Vector2d(-56.5, -7.5);
    Vector2d barPos4 = new Vector2d(-37.5, -8);
    Vector2d scorePos = new Vector2d(-28.5, -5);
    Vector2d scorePos2 = new Vector2d(-41.5, -8);
    Vector2d groundPick1 = new Vector2d(-46, -57.7);
    Vector2d groundPick2 = new Vector2d(-46, -65.4);
    Vector2d groundPick3 = new Vector2d(-47.5, -51);
    Vector2d toWall = new Vector2d(-50, -46);
    Vector2d drop = new Vector2d(-50, -59);
    Vector2d backup = new Vector2d(-62, -43);
    Vector2d back2 = new Vector2d(-45.5, -5);
    Vector2d wallPos = new Vector2d(-55.5, -10);
    Vector2d parked = new Vector2d(-62, -60);
    Vector2d back3 = new Vector2d(-46.5, -47);

    Vector2d spec3hang = new Vector2d(-41.5, -5);


    protected void Spec1Score() {
        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0), null, new ProfileAccelConstraint(-40, 60)).build();
        Action score = Drive.actionBuilder(new Pose2d(barPos, 0)).lineToXConstantHeading(-36, null, new ProfileAccelConstraint(-40, 80)).build();
        Actions.runBlocking(
                new SequentialAction(
                    new InstantAction(() -> elbow.setPosition(elbowscore)),
                    new InstantAction(() -> arm1.setPosition(0)),
                    new InstantAction(() -> arm2.setPosition(0)),
                    new InstantAction(() -> wrist.setPosition(0)),
                    new ParallelAction(
                            ready,
                            ready,
                            new InstantAction(() -> lift1.setTargetPosition(SLIDES1)),
                            new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift2.setTargetPosition(SLIDES1)),
                            new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift1.setPower(1)),
                            new InstantAction(() -> lift2.setPower(1))
                    ),
                    new ParallelAction(
                            score,
                            new InstantAction(() -> sleep(250)),
                            new InstantAction(()-> claw.setPosition(clawOpen))
                    )


                )
        );
    }
    protected void SamplePicks(){
        Action back1 = Drive.actionBuilder(new Pose2d(scorePos, 0)).lineToXConstantHeading(-46).build();
        Action sample1 = Drive.actionBuilder(new Pose2d(-46.5, -5, 0)).strafeToConstantHeading(groundPick1, null, new ProfileAccelConstraint(-40, 60)).build();
        Action sample2 = Drive.actionBuilder(new Pose2d(groundPick1, 0)).strafeToConstantHeading(groundPick2, null, new ProfileAccelConstraint(-15, 80)).build();
        Action sample3 = Drive.actionBuilder(new Pose2d(groundPick2, 0)).strafeToConstantHeading(groundPick3, null, new ProfileAccelConstraint(-30, 70)).build();
        Action backToWall1 = Drive.actionBuilder(new Pose2d(groundPick2, 0)).lineToXConstantHeading(-57, null, new ProfileAccelConstraint(-10, 30)).build();
        Action backToBar = Drive.actionBuilder(new Pose2d(-57.5, -64.5, 0)).strafeToConstantHeading(barPos2, null, new ProfileAccelConstraint(-30, 60)).build();

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                back1,
                                back1,
                                new InstantAction(() -> lift1.setTargetPosition(1100)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(1100)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> elbow.setPosition(0.5))
                        ),
                        new ParallelAction(
                                sample1,
                                sample1,
                                new InstantAction(() -> lift1.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> arm1.setPosition(0.1)),
                                new InstantAction(() -> arm2.setPosition(0.1)),
                                new InstantAction(() -> elbow.setPosition(0.5))
                        ),
                        new InstantAction(() -> arm1.setPosition(armFloor)),
                        new InstantAction(() -> arm2.setPosition(armFloor)),
                        new InstantAction(() -> elbow.setPosition(elbowdown)),
                        new InstantAction(() -> sleep(250)),
                        new InstantAction(() -> claw.setPosition(clawClose)),
                        new InstantAction(() -> sleep(150)),
                        new ParallelAction(
                                new InstantAction(() -> lift1.setTargetPosition(SLIDES_PICKUP)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(SLIDES_PICKUP)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> elbow.setPosition(.6)),
                                new InstantAction(() -> sleep(500)),
                                new InstantAction(() -> arm1.setPosition(armPickUp)),
                                new InstantAction(() -> arm2.setPosition(armPickUp)),
                                new InstantAction(() -> sleep(750))

                        ),
                        sample2,
                        sample2,
                        new InstantAction(() -> claw.setPosition(clawOpen)),
                        new InstantAction(() -> sleep(150)),
                        new ParallelAction(
                                new InstantAction(() -> elbow.setPosition(0.6)),
                                new InstantAction(() -> lift1.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> arm1.setPosition(armFloor)),
                                new InstantAction(() -> arm2.setPosition(armFloor))

                        ),
                        new InstantAction(() -> sleep(400)),
                        new InstantAction(() -> elbow.setPosition(elbowdown)),
                        new InstantAction(() -> sleep(350)),
                        new InstantAction(() -> claw.setPosition(clawClose)),
                        new InstantAction(() -> sleep(150)),
                        new ParallelAction(
                                new InstantAction(() -> lift1.setTargetPosition(640)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(640)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> elbow.setPosition(.7)),
                                new InstantAction(() -> sleep(500)),
                                new InstantAction(() -> arm1.setPosition(armPickUp)),
                                new InstantAction(() -> arm2.setPosition(armPickUp)),
                                new InstantAction(() -> sleep(900)),
                                new InstantAction(() -> claw.setPosition(clawOpen))
                        ),
                        new ParallelAction(
                                new InstantAction(() -> lift1.setTargetPosition(585)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(575)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1))
                        ),
                        new InstantAction(() -> sleep(100)),
                        new InstantAction(() -> elbow.setPosition(elbowpickup)),
                        new InstantAction(() -> wrist.setPosition(wristFlipped)),
                        new ParallelAction(
                                backToWall1,
                                new InstantAction(() -> sleep(900)),
                                new InstantAction(() -> claw.setPosition(clawClose))
                        )

                )
        );
    }

    protected void SpecHangs() {
        Action backToBar = Drive.actionBuilder(new Pose2d(-56.5, -64.5, 0)).strafeToConstantHeading(barPos2, null, new ProfileAccelConstraint(-50, 80)).build();
        Action backToBarAGAIN = Drive.actionBuilder(new Pose2d(-56.5, -47, 0)).strafeToConstantHeading(barPos3, null, new ProfileAccelConstraint(-50, 80)).build();
        Action score2 = Drive.actionBuilder(new Pose2d(barPos2, 0)).lineToXConstantHeading(-32, null, new ProfileAccelConstraint(-40, 80)).build();
        Action score3 = Drive.actionBuilder(new Pose2d(barPos3, 0)).lineToXConstantHeading(-31, null, new ProfileAccelConstraint(-40, 80)).build();
        Action back2 = Drive.actionBuilder(new Pose2d(-34, -6.5, 0)).lineToX(-46.5).build();
        Action Back3 = Drive.actionBuilder(new Pose2d(-34, -7.5, 0)).lineToX(-46.5).build();
        Action Strafe = Drive.actionBuilder(new Pose2d(-46.5, -6.5, 0)).strafeToConstantHeading(back3).build();
        Action Wall = Drive.actionBuilder(new Pose2d(back3, 0 )).lineToXConstantHeading(-56.5, null, new ProfileAccelConstraint(-15, 30)).build();

        Actions.runBlocking(
                new SequentialAction(
                        new InstantAction(() -> elbow.setPosition(0.85)),
                        new InstantAction(() -> arm1.setPosition(0)),
                        new InstantAction(() -> arm2.setPosition(0)),
                        new InstantAction(() -> wrist.setPosition(0)),
                        new InstantAction(() -> lift1.setTargetPosition(1075)),
                        new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift2.setTargetPosition(1075)),
                        new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift1.setPower(1)),
                        new InstantAction(() -> lift2.setPower(1)),

                        new ParallelAction(
                                backToBar,
                                backToBar
                        ),

                        new ParallelAction(
                                score2,
                                new InstantAction(() -> sleep(950)),
                                new InstantAction(()-> claw.setPosition(clawOpen))
                        ),
                        new ParallelAction(
                                new InstantAction(() -> lift1.setTargetPosition(1100)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(1100)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> elbow.setPosition(0.5))
                        ),
                        back2,
                        new InstantAction(() -> sleep(100)),
                        new ParallelAction(
                                new InstantAction(() -> lift1.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift2.setTargetPosition(SLIDES_DOWN)),
                                new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                                new InstantAction(() -> lift1.setPower(1)),
                                new InstantAction(() -> lift2.setPower(1)),
                                new InstantAction(() -> elbow.setPosition(0.6))
                        ),
                        new ParallelAction(
                            Strafe,
                            new InstantAction(() -> lift1.setTargetPosition(SLIDES_PICKUP)),
                            new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift2.setTargetPosition(SLIDES_PICKUP)),
                            new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift1.setPower(1)),
                            new InstantAction(() -> lift2.setPower(1)),
                            new InstantAction(() -> arm1.setPosition(armPickUp)),
                            new InstantAction(() -> arm2.setPosition(armPickUp)),
                            new InstantAction(() -> elbow.setPosition(0.6)),
                            new InstantAction(() -> wrist.setPosition(wristFlipped))
                        ),
                        new ParallelAction(
                            new InstantAction(() -> elbow.setPosition(0.35)),
                            new InstantAction(() -> lift1.setTargetPosition(575)),
                            new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift2.setTargetPosition(575)),
                            new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                            new InstantAction(() -> lift1.setPower(1)),
                            new InstantAction(() -> lift2.setPower(1)),
                            Wall
                        ),
                        new InstantAction(() -> claw.setPosition(clawClose)),
                        new InstantAction(() -> sleep(250)),
                        new InstantAction(() -> elbow.setPosition(0.85)),
                        new InstantAction(() -> arm1.setPosition(0)),
                        new InstantAction(() -> arm2.setPosition(0)),
                        new InstantAction(() -> wrist.setPosition(0)),
                        new InstantAction(() -> lift1.setTargetPosition(1075)),
                        new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift2.setTargetPosition(1075)),
                        new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift1.setPower(1)),
                        new InstantAction(() -> lift2.setPower(1)),

                        new ParallelAction(
                        backToBarAGAIN,
                        backToBarAGAIN,

                        new InstantAction(() -> sleep(150))
                ),

                        new ParallelAction(
                                score3,
                                new InstantAction(() -> sleep(950)),
                                new InstantAction(()-> claw.setPosition(clawOpen))
                        ),
                new ParallelAction(
                        Back3,
                        new InstantAction(() -> elbow.setPosition(0.7)),
                        new InstantAction(() -> lift1.setTargetPosition(SLIDES_DOWN)),
                        new InstantAction(() -> lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift2.setTargetPosition(SLIDES_DOWN)),
                        new InstantAction(() -> lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION)),
                        new InstantAction(() -> lift1.setPower(1)),
                        new InstantAction(() -> lift2.setPower(1))
                )
                )


        );
    }




    @Override
    public void runOpMode() throws InterruptedException {
        claw = hardwareMap.servo.get("claw");
        arm1 = hardwareMap.servo.get("arm1");
        arm2 = hardwareMap.servo.get("arm2");
        elbow = hardwareMap.servo.get("elbow");
        wrist = hardwareMap.servo.get("wrist");

        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");
        lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift1.setDirection(DcMotorSimple.Direction.REVERSE);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lift1.setDirection(DcMotorSimple.Direction.REVERSE);
        lift2.setDirection(DcMotorSimple.Direction.REVERSE);


        arm1.setDirection(Servo.Direction.REVERSE);
        arm2.setDirection(Servo.Direction.FORWARD);

        Drive = new MecanumDrive(hardwareMap, new Pose2d(-63, -20, 0));
        Pose2d pose = Drive.localizer.getPose();

//        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
//        Action park = Drive.actionBuilder(new Pose2d(groundPick1, 0)).splineToConstantHeading(parked, 0).build();
//        Action score = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0), null, new ProfileAccelConstraint(-20, 20)).build();
//        Action score2 = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0)).build();
//        Action score3 = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0)).build();
//        Action wall = Drive.actionBuilder(new Pose2d(scorePos, 0)).strafeToConstantHeading(toWall).build();
//        Action sample1 = Drive.actionBuilder(new Pose2d(scorePos, 0)).strafeToConstantHeading(groundPick1).build();
//        Action back = Drive.actionBuilder(new Pose2d(toWall, 0)).strafeToConstantHeading(backup, null, new ProfileAccelConstraint(-10,10)).build();
//        Action back2 = Drive.actionBuilder(new Pose2d(toWall, 0)).strafeToConstantHeading(backup, null, new ProfileAccelConstraint(-10,10)).build();
//        Action dropoff = Drive.actionBuilder(new Pose2d(groundPick1, 0)).splineToConstantHeading(drop, Math.toRadians(0)).build();
//        Action ready2 = Drive.actionBuilder(new Pose2d(backup, 0)).strafeToConstantHeading(barPos).build();
//        Action ready3 = Drive.actionBuilder(new Pose2d(backup, 0)).strafeToConstantHeading(scorePos2).build();
//        Action pickfromWitt = Drive.actionBuilder(new Pose2d(drop, 0)).strafeToConstantHeading(toWall).build();

        claw.setPosition(clawClose);
        elbow.setPosition(elbowInit);
        arm1.setPosition(armInit);
        arm2.setPosition(armInit);
        wrist.setPosition(wristNotFlipped);



        waitForStart();

        Spec1Score();

        SamplePicks();
//
        SpecHangs();

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