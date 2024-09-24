package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "mechanicumOpMode", group = "movement")
public class MechanicumWheels extends LinearOpMode {

    //defines motors
    public DcMotor FL;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor BR;

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.dcMotor.get("frontLeft");
        BL = hardwareMap.dcMotor.get("backLeft");
        FR = hardwareMap.dcMotor.get("frontRight");
        BR = hardwareMap.dcMotor.get("backRight");

        FL.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            //defines inputs
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;

            double turn = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) +Math.abs(turn), 1);

            //sets motor power based on input
            FL.setPower((x + y + turn) / denominator);
            BL.setPower((y - x + turn) / denominator);
            FR.setPower((y - x - turn) / denominator);
            BR.setPower((y + x - turn) / denominator);

        }

    }
}
