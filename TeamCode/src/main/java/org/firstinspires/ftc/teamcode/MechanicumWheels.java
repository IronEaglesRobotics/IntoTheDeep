package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "mechanicumOpMode", group = "movement")
public class MechanicumWheels extends LinearOpMode {

    //defines motors
    public DcMotor FL;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor BR;
    public Servo intake1;
    public Servo intake2;

    public MechanicumWheels(HardwareMap hardwareMap, Pose2d startPose) {

    }

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.dcMotor.get("fl");
        BL = hardwareMap.dcMotor.get("bl");
        FR = hardwareMap.dcMotor.get("fr");
        BR = hardwareMap.dcMotor.get("br");
        intake1 = hardwareMap.servo.get("i1");
        intake2 = hardwareMap.servo.get("i2");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            //defines inputs
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;


            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) +Math.abs(rx), 1);

            //sets motor power based on input
            double FLPower = (y + x + rx / denominator);
            double BLPower = (y + x + rx / denominator);
            double FRPower = (y - x - rx / denominator);
            double BRPower = (y - x - rx / denominator);

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);

            if(gamepad1.left_bumper) {
                intake1.setPosition(.5);
                intake2.setPosition(-.5);
            }
            else {
                intake1.setPosition(0);
                intake2.setPosition(0);
            }
        }
    }

    public void setDrivePower(double x, double y, double rx) {
        double denominator = Math.abs(Math.abs(y) + Math.abs(x) + Math.abs(rx));

        double FLPower = (y + x + rx) / denominator;
        double BLPower = (y - x + rx) / denominator;
        double FRPower = (y - x - rx) / denominator;
        double BRPower = (y + x - rx) / denominator;

        FL.setPower(FLPower);
        BL.setPower(BLPower);
        FR.setPower(FRPower);
        BR.setPower(BRPower);
    }

    public void setDrivePower(Pose2d newPose) {
        double x = newPose.getX();
        double y = newPose.getY();
        double rx = newPose.getHeading();

        double denominator = Math.abs(Math.abs(y) + Math.abs(x) + Math.abs(rx));

        double FLPower = (y + x + rx) / denominator;
        double BLPower = (y - x + rx) / denominator;
        double FRPower = (y - x - rx) / denominator;
        double BRPower = (y + x - rx) / denominator;

        FL.setPower(FLPower);
        BL.setPower(BLPower);
        FR.setPower(FRPower);
        BR.setPower(BRPower);
    }
}
