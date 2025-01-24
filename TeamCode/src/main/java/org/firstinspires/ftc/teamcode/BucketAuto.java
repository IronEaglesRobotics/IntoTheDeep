package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.trajectory.constraint.TrajectoryConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//C:\Users\Minh\AppData\Local\Android\Sdk\platform-tools\adb.exe connect  192.168.43.1:5555

@Config
@Autonomous(name = "BucketAuto", group = "Autonomous")
public class BucketAuto extends LinearOpMode {

    MecanumDrive Drive;
    private Servo claw;
    private Servo arm;
    private Servo arm1;
    public DcMotor lift1;
    public DcMotor lift2;
    public static double armInit = 0.3;
    public static double clawOpen = 0.75;
    public static double clawClose = 0.35;
    public static double armFloor = 0;
    public static int SLIDES_DOWN = 0;
    public static int SLIDES_HIGH_BUCKET = 2700;
    public static double armBucket = 0.25;
    public static double armBucketS = 0.4;
    public static double armBucketdown = 0.2;
    public static double armBucketSdown = 0.45;

    Vector2d barPos = new Vector2d(-40, -33);
    Vector2d scorePos = new Vector2d(-45, -30);
    Vector2d sample1 = new Vector2d(-41.5, -43.5);
    Vector2d sample2 = new Vector2d(-30.7, -41.5);
    Vector2d backup = new Vector2d(-45, -20);
    Vector2d wallPos = new Vector2d(-55, -10);
    Vector2d redo = new Vector2d(-42 , -32.2);
    Vector2d forward = new Vector2d(- 39, -31);

    @Override
    public void runOpMode() throws InterruptedException {
        claw = hardwareMap.servo.get("claw");
        arm = hardwareMap.servo.get("arm2");

        lift1 = hardwareMap.get(DcMotor.class, "lift1");
        lift2 = hardwareMap.get(DcMotor.class, "lift2");
        lift1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift1.setDirection(DcMotorSimple.Direction.REVERSE);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift2.setDirection(DcMotorSimple.Direction.FORWARD);

        arm.setDirection(Servo.Direction.REVERSE);

        Drive = new MecanumDrive(hardwareMap, new Pose2d(-63, -20, 0));
        Pose2d pose = Drive.localizer.getPose();
        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0)).build();
        Action ready2 = Drive.actionBuilder(new Pose2d(forward, Math.toRadians(-93))).splineToConstantHeading(barPos, Math.toRadians(-93), null, new ProfileAccelConstraint(-5, 5)).build();
        Action recorrect = Drive.actionBuilder(new Pose2d(barPos, Math.toRadians(46.5))).splineToConstantHeading(redo, Math.toRadians(46.5)).build();
        Action score = Drive.actionBuilder(new Pose2d(barPos, Math.toRadians(46.5))).splineToConstantHeading(scorePos, Math.toRadians(46.5)).build();
        Action Back = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(backup, Math.toRadians(0)).build();
        Action firstsample = Drive.actionBuilder(new Pose2d(forward, Math.toRadians(-93))).splineToConstantHeading(sample1, Math.toRadians(-93), null, new ProfileAccelConstraint(-25, 25)).build();
        Action secondsample = Drive.actionBuilder(new Pose2d(forward, Math.toRadians(-93))).splineToConstantHeading(sample2, Math.toRadians(-93), null, new ProfileAccelConstraint(-25, 25)).build();
        Action turntobucket = Drive.actionBuilder(new Pose2d(barPos, 0)).turnTo(Math.toRadians(46.5)).build();
        Action turntobucket2 = Drive.actionBuilder(new Pose2d(barPos, -93)).turnTo(Math.toRadians(46.5), new TurnConstraints(10, -2, 2)).build();
        Action turntobucket3 = Drive.actionBuilder(new Pose2d(barPos, -93)).turnTo(Math.toRadians(46.5), new TurnConstraints(10, -2, 2)).build();
        Action drivetobucket = Drive.actionBuilder(new Pose2d(sample1, Math.toRadians(-93))).splineToConstantHeading(barPos, Math.toRadians(-93)).build();
        Action drivetobucket2 = Drive.actionBuilder(new Pose2d(sample2, Math.toRadians(-93))).splineToConstantHeading(barPos, Math.toRadians(-93)).build();
        Action turntosample = Drive.actionBuilder(new Pose2d(barPos, Math.toRadians(46.5))).turnTo(Math.toRadians(-93)).build();
        Action inch = Drive.actionBuilder(new Pose2d(barPos, Math.toRadians(46.5))).splineToConstantHeading(forward, Math.toRadians(46.5), null, new ProfileAccelConstraint(-10, 10)).build();
        Action inchAGAIN = Drive.actionBuilder(new Pose2d(barPos, Math.toRadians(46.5))).splineToConstantHeading(forward, Math.toRadians(46.5), null, new ProfileAccelConstraint(-10, 10)).build();
        claw.setPosition(0.35);
        arm.setPosition(armInit);


        waitForStart();

        lift1.setTargetPosition(SLIDES_HIGH_BUCKET);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_HIGH_BUCKET+100);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        Actions.runBlocking(ready);

        sleep(200);

        Actions.runBlocking(turntobucket);

        sleep(250);

        arm.setPosition(armBucketdown);

        sleep(250);

        claw.setPosition(clawOpen);

        sleep(200);

        arm.setPosition(armInit);

        sleep(100);

        lift1.setTargetPosition(SLIDES_DOWN);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_DOWN);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        sleep(500);

        Actions.runBlocking(ready2);

        sleep(500);

        Actions.runBlocking(firstsample);

        sleep(250);

        arm.setPosition(armFloor);

        sleep(350);

        claw.setPosition(clawClose);

        sleep(300);

        arm.setPosition(armInit);

        Actions.runBlocking(drivetobucket);

        sleep(500);

        Actions.runBlocking(turntobucket2);

        sleep(500);



        lift1.setTargetPosition(SLIDES_HIGH_BUCKET);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_HIGH_BUCKET+100);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        sleep(1500);

        Actions.runBlocking(inch);

        sleep(250);

        arm.setPosition(armBucketdown);

        sleep(100);

        claw.setPosition(clawOpen);

        sleep(100);

        arm.setPosition(armInit);

        sleep(100);

        lift1.setTargetPosition(SLIDES_DOWN);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_DOWN);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        sleep(500);

        Actions.runBlocking(ready2);

        sleep(250);

        Actions.runBlocking(secondsample);

        sleep(1000);

        arm.setPosition(armFloor);

        sleep(350);

        claw.setPosition(clawClose);

        sleep(300);

        arm.setPosition(armInit);

        sleep(100);

        Actions.runBlocking(drivetobucket2);

        sleep(1000);

        Actions.runBlocking(turntobucket3);

        lift1.setTargetPosition(SLIDES_HIGH_BUCKET);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_HIGH_BUCKET+100);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        sleep(1500);

        Actions.runBlocking(inchAGAIN);

        sleep(500);

        arm.setPosition(armBucketdown);

        sleep(100);

        claw.setPosition(clawOpen);

        sleep(250);

        arm.setPosition(armInit);

        sleep(500);

        lift1.setTargetPosition(SLIDES_DOWN);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift1.setPower(1);
        lift2.setTargetPosition(SLIDES_DOWN);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setPower(1);

        sleep(1000);

        Actions.runBlocking(ready2);



        while (opModeIsActive()&&!isStopRequested()){
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }


    }


}