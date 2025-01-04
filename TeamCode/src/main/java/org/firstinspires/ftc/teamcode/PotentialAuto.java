package org.firstinspires.ftc.teamcode;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Meet2", group= "")
public class PotentialAuto extends LinearOpMode {
    Robot robot;

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fld= null;
    private DcMotor bld = null;
    private DcMotor frd = null;
    private DcMotor brd = null;
    private DcMotor lift = null;
    public Servo claw;

    public Servo arm;


    @Override

    public void runOpMode() {
        fld  = hardwareMap.get(DcMotor.class, "fld");
        bld  = hardwareMap.get(DcMotor.class, "bld");
       frd = hardwareMap.get(DcMotor.class, "frd");
        brd = hardwareMap.get(DcMotor.class, "brd");
        lift = hardwareMap.dcMotor.get("lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        arm = hardwareMap.servo.get("arm");
        claw = hardwareMap.servo.get("claw");
            arm.setPosition( 0.25);
         claw.setPosition(0.05);

        waitForStart();

        robot.lift.setTargetPosition(1700, 0.5);
        robot.arm.setPosition(0.7);

        sleep(1000);

        brd.setDirection(DcMotor.Direction.REVERSE);
        fld.setDirection(DcMotor.Direction.REVERSE);
        frd.setDirection(DcMotor.Direction.REVERSE);
        bld.setDirection(DcMotor.Direction.REVERSE);

        bld.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bld.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (bld.getCurrentPosition()<5000){

            brd.setPower(0.5);
            bld.setPower(0.5);
            fld.setPower(0.5);
            frd.setPower(0.5);

        }

        brd.setPower(0);
        bld.setPower(0);
        fld.setPower(0);
        frd.setPower(0);






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