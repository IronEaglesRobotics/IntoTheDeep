package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Dev")
public class TeleOp extends LinearOpMode {

    //define DC Motors
    public DcMotor FL;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor BR;

    //define Color Sensor
    public ColorSensor yeah;

    //Mechanicum Drive Function
    public void updateMove() {
        //defines inputs
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) +Math.abs(rx), 1);

        //sets motor power based on input
        double FLPower = ((y + x + rx) / denominator);
        double BLPower = ((y - x + rx) / denominator);
        double FRPower = ((y - x - rx) / denominator);
        double BRPower = ((y + x - rx) / denominator);

        FL.setPower(FLPower);
        BL.setPower(BLPower);
        FR.setPower(FRPower);
        BR.setPower(BRPower);
    }

    //Color Sensor Function
    public void colorGet() {

        telemetry.addData("Status:", "Hi, this is functioning");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            int b = yeah.blue();
            int r = yeah.red();
            int g = yeah.green();
            telemetry.addData("Blue:", b);
            telemetry.addData("\nRed:", r);
            telemetry.addData("\nGreen:", g);
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

        //hardware mapping
        //dcMotor
        FL = hardwareMap.dcMotor.get("frontLeft");
        BL = hardwareMap.dcMotor.get("backLeft");
        FR = hardwareMap.dcMotor.get("frontRight");
        BR = hardwareMap.dcMotor.get("backRight");
        //colorSensor
        yeah = hardwareMap.get(ColorSensor.class, "test");

        //direction setting
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

        //runs when start
        waitForStart();

        //running loop
        while (opModeIsActive()) {
            updateMove();
            colorGet();
        }

    }
}