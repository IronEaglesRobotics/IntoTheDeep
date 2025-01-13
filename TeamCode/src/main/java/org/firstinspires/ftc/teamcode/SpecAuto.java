package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


//C:\Users\Minh\AppData\Local\Android\Sdk\platform-tools\adb.exe connect  192.168.43.1:5555


@Config
@Autonomous(name = "SpecAuto", group = "Autonomous")
public class SpecAuto extends LinearOpMode {

    MecanumDrive Drive;
    private Servo claw;
    private Servo arm;
    public DcMotor lift;
    public static double armInit = 0.25;
    public static int SLIDES_SCORE1 = 1750;
    public static int SLIDES_SCORE2 = 1350;
    public static double armScore1 = 0.08;
    public static double armScore2 = 0.015;
    public static double armPickUp = 0.06;
    public static int SLIDES_PICKUP = 475;
    public static double clawOpen = 0.75;
    public static double clawClose = 0.4;
    public static double armFloor = 0.05;
    public static int SLIDES_DOWN = 0;

    Vector2d barPos = new Vector2d(-40.5, -5);
    Vector2d scorePos = new Vector2d(-39.5,-5);
    Vector2d groundPick = new Vector2d(-41.5, -57.5);
    Vector2d toWall = new Vector2d(-50, -57.5);
    Vector2d backup = new Vector2d(-45, -5);
    Vector2d wallPos = new Vector2d(-55, -10);
    Vector2d parked = new Vector2d(-62, -60);

    @Override
    public void runOpMode() throws InterruptedException {
        claw = hardwareMap.servo.get("claw");
        arm = hardwareMap.servo.get("arm");

        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);

        arm.setDirection(Servo.Direction.REVERSE);

        Drive = new MecanumDrive(hardwareMap, new Pose2d(-63, -20, 0));
        Pose2d pose = Drive.localizer.getPose();
        Action ready = Drive.actionBuilder(new Pose2d(-63, -20, 0)).splineToConstantHeading(barPos, Math.toRadians(0)).build();
        Action park = Drive.actionBuilder(new Pose2d(groundPick, 0)).splineToConstantHeading(parked, 0).build();
        Action score = Drive.actionBuilder(new Pose2d(barPos, 0)).splineToConstantHeading(scorePos, Math.toRadians(0)).build();
        Action Back = Drive.actionBuilder(new Pose2d(barPos, 0)).lineToX(-45).build();
        Action sample = Drive.actionBuilder(new Pose2d(scorePos, 0)).strafeTo(groundPick).build();
        Action turn = Drive.actionBuilder(new Pose2d(groundPick, 0)).turnTo(Math.toRadians(185)).build();
        claw.setPosition(0.4);
        arm.setPosition(armInit);


        waitForStart();

        sleep(5000);

        lift.setTargetPosition(SLIDES_SCORE1);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);

        arm.setPosition(armScore1);

        Actions.runBlocking(ready);

        sleep(2000);

        Actions.runBlocking(score);

        sleep(1500);

       lift.setTargetPosition(SLIDES_SCORE2);
       lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       lift.setPower(1);

       arm.setPosition(armScore2);

        sleep(200);

        claw.setPosition(clawOpen);

        sleep(500);

        arm.setPosition(armInit);

        sleep(500);
        lift.setTargetPosition(SLIDES_PICKUP);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);

       // Actions.runBlocking(Back);

        sleep(1000);

        Actions.runBlocking(sample);

        arm.setPosition(armFloor);
        lift.setTargetPosition(SLIDES_DOWN);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.3);

        sleep(1000);

        claw.setPosition(clawClose);

        sleep(500);
        Actions.runBlocking(park);


//        sleep(100);
//
//        lift.setTargetPosition(SLIDES_DOWN);
//        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.setPower(0.5);
//
//        arm.setPosition(armFloor);





        while (opModeIsActive()&&!isStopRequested()){
            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }


    }


}