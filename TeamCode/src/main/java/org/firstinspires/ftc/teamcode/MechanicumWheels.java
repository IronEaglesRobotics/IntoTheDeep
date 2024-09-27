package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "mechanicumOpMode", group = "movement")
public class MechanicumWheels extends LinearOpMode {

    //defines motors
    public DcMotor FL;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor BR;
    public Servo leftHand;
    public Servo extendo;
    public double OPEN = 2;
    public double CLOSE =0;
    public double CLAW_MIN = 0;
    public double CLAW_MAX = 2;
    public double EXTEND = 2;
    public double RETRACT = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.dcMotor.get("frontLeft");
        BL = hardwareMap.dcMotor.get("backLeft");
        FR = hardwareMap.dcMotor.get("frontRight");
        BR = hardwareMap.dcMotor.get("backRight");
        leftHand = hardwareMap.servo.get("aS");
        extendo = hardwareMap.servo.get("eX");
        leftHand.scaleRange(CLAW_MIN, CLAW_MAX);
        extendo.scaleRange(EXTEND, RETRACT);

        FL.setDirection(DcMotor.Direction.FORWARD);
        BL.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            //defines inputs
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;

            double turn = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) +Math.abs(turn), 1);

            //sets motor power based on input
            FL.setPower((y - x - turn) / denominator);
            BL.setPower((y + x - turn) / denominator);
            FR.setPower((y + x + turn) / denominator);
            BR.setPower((y - x + turn) / denominator);

            if (gamepad1.right_bumper) {
                leftHand.setPosition(OPEN);
            }
            else {
                leftHand.setPosition(CLOSE);
            }
            telemetry.update();
            telemetry.addData("open",OPEN);
            telemetry.addData("closed",CLOSE);

            if (gamepad1.left_bumper) {
                extendo.setPosition(EXTEND);
            }
            else {
                extendo.setPosition(RETRACT);
            }
        }   telemetry.update();
            telemetry.addData("extended",EXTEND);
            telemetry.addData("retracted",RETRACT);

        }

    }

