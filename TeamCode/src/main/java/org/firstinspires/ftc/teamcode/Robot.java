package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    Drive drive;
    Extendo extendo;
    Lift lift;
    Claw claw;
    Intake intake;

    public Robot(HardwareMap hardwareMap) {
        this.drive = new Drive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.extendo = new Extendo(hardwareMap);
    }

    public static class Extendo {
        private Servo extendo;

        public Extendo(HardwareMap hardwareMap) {
            extendo = hardwareMap.servo.get("e");
        }
    }

    public static class Lift {
        private DcMotor lift;

        public Lift(HardwareMap hardwareMap) {
            lift = hardwareMap.dcMotor.get("l");

            lift.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public static class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.servo.get("c");
        }
    }

    public static class Intake {
        private DcMotor intake;

        public Intake(HardwareMap hardwareMap) {
            intake = hardwareMap.dcMotor.get("i");

            intake.setDirection(DcMotor.Direction.FORWARD);
        }
    }

    public static class Drive {
        private DcMotor FL;
        private DcMotor BL;
        private DcMotor FR;
        private DcMotor BR;

        public Drive(HardwareMap hardwareMap) {
            FL = hardwareMap.dcMotor.get("frontLeft");
            BL = hardwareMap.dcMotor.get("backLeft");
            FR = hardwareMap.dcMotor.get("frontRight");
            BR = hardwareMap.dcMotor.get("backRight");

            FL.setDirection(DcMotor.Direction.REVERSE);
            BL.setDirection(DcMotor.Direction.REVERSE);
            FR.setDirection(DcMotor.Direction.FORWARD);
            BR.setDirection(DcMotor.Direction.FORWARD);
        }

        public void handleInput(Gamepad gamepad1, Gamepad gamepad2) {
            //defines inputs
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y)+ Math.abs(x) + Math.abs(rx), 1);

            //sets motor power based on input
            double FLPower = (y + x + rx / denominator);
            double BLPower = (y - x + rx / denominator);
            double FRPower = (y - x - rx / denominator);
            double BRPower = (y + x - rx / denominator);

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);
        }
    }
}
