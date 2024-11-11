package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="PotentialAuto", group= "")
public class PotentialAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor intake = null;
    public Servo leftHand;

    public Servo extendo;
    public Servo arm1;
    public Servo arm2;
    public Servo arm3;
    private Servo tiltRight;
    private Servo tiltLeft;
    private Servo extendoRight;
    private Servo extendoLeft;


    @Override
    public void runOpMode() {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "backLeft");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRight");
        intake = hardwareMap.dcMotor.get("s");
        extendoRight = hardwareMap.servo.get("eR");
        extendoLeft = hardwareMap.servo.get("eL");

        tiltRight = hardwareMap.servo.get("tR");
        tiltLeft = hardwareMap.servo.get("tL");



        //leftHand = hardwareMap.servo.get("aS");
        //leftHand.scaleRange(0,2);
        //extendo = hardwareMap.servo.get("eX");
        //arm1 = hardwareMap.servo.get("a1");
        //arm2 = hardwareMap.servo.get("a2");
        //arm3 = hardwareMap.servo.get("a3");

        waitForStart();



        extendoRight.setPosition(0);
        extendoLeft.setPosition(0);


        tiltRight.setPosition(.395); //0
        tiltLeft.setPosition(.395);
        intake.setPower(-1);

        sleep(1000);

        tiltRight.setPosition(0.2); //0
        tiltLeft.setPosition(0.2);
        intake.setPower(0);
        extendoRight.setPosition(0.26);
        extendoLeft.setPosition(0.26);
//
//        leftFrontDrive.setPower(-.50);
//       leftBackDrive.setPower(.50);
//       rightFrontDrive.setPower(.50);
//    rightBackDrive.setPower(-.50);
//
//    sleep(2000);
//
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//sleep(250);
//        leftFrontDrive.setPower(-.50);
//        leftBackDrive.setPower(-.50);
//        rightFrontDrive.setPower(-.50);
//        rightBackDrive.setPower(-.50);
//sleep (100);
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);

//
//        leftFrontDrive.setPower(.4);
//        leftBackDrive.setPower(.4);
//        rightFrontDrive.setPower(.4);
//        rightBackDrive.setPower(.4);
//
//        sleep(2000);
//
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//
//        leftFrontDrive.setPower(-.50);
//        leftBackDrive.setPower(.50);
//        rightFrontDrive.setPower(.50);
//        rightBackDrive.setPower(-.50);
//
//        sleep(1000);
//
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//
//        leftFrontDrive.setPower(-.4);
//        leftBackDrive.setPower(-.4);
//        rightFrontDrive.setPower(-.45);
//        rightBackDrive.setPower(-.45);
//
//        sleep(2500);
//
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//
//        leftFrontDrive.setPower(.4);
//        leftBackDrive.setPower(.4);
//        rightFrontDrive.setPower(.4);
//        rightBackDrive.setPower(.4);
//
//        sleep(250);
//
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//
//            /*leftFrontDrive.setPower(.5);
//            leftBackDrive.setPower(.5);
//            rightFrontDrive.setPower(.5);
//            rightBackDrive.setPower(.5);
//            */
        //sleep(250)
    }
}