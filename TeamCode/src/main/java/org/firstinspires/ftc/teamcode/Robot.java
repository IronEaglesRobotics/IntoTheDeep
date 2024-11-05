package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    Drive drive;
    ExtendoRight extendoRight;
    ExtendoLeft extendoLeft;
    Lift lift;
    Claw claw;
    Intake intake;
    TiltRight tiltRight;
    TiltLeft tiltLeft;
    Cjoint1 cjoint1;
    Cjoint2 cjoint2;
    Cjoint3 cjoint3;

    public Robot(HardwareMap hardwareMap) {
        this.drive = new Drive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.tiltRight = new TiltRight(hardwareMap);
        this.tiltLeft = new TiltLeft(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.extendoRight = new ExtendoRight(hardwareMap);
        this.extendoLeft = new ExtendoLeft(hardwareMap);
    }

    public static class ExtendoRight {
        private Servo extendoRight;

        public void setPosition(double position) {
            this.extendoRight.setPosition(position);
        }
        public ExtendoRight(HardwareMap hardwareMap) {

            extendoRight = hardwareMap.servo.get("eR");
        }
    }

    public static class ExtendoLeft {
        private Servo extendoLeft;

        public void setPosition(double position) {
            this.extendoLeft.setPosition(position);
        }
        public ExtendoLeft(HardwareMap hardwareMap) {
            extendoLeft = hardwareMap.servo.get("eL");
        }

    }

    public static class Lift {
        private DcMotor lift;

        public Lift(HardwareMap hardwareMap) {
            lift = hardwareMap.get(DcMotor.class, "l");

            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public void setTargetPosition(int i) {
            this.lift.setTargetPosition(i);
        }

        public void setPower(double v) {
            this.lift.setPower(v);
        }

        public void setMode(DcMotor.RunMode runMode) {
            this.lift.setMode(runMode);
        }
    }

    public static class Claw {
        private Servo claw;
        public void setPosition(double position) {
            this.claw.setPosition(position);
        }

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.servo.get("c");
        }
    }

    public static class Cjoint1 {
        private Servo joint1;
        public void setPosition(double position){
            this.joint1.setPosition(position);
        }
        public Cjoint1(HardwareMap hardwareMap) {
            joint1 = hardwareMap.servo.get("j1");
        }
    }

    public static class Cjoint2 {
        private Servo joint2;
        public void setPosition(double position){
            this.joint2.setPosition(position);
        }
        public Cjoint2(HardwareMap hardwareMap){
            joint2 = hardwareMap.servo.get("j2");
        }
    }

    public static class Cjoint3 {
        private Servo joint3;
        public void setPosition(double position){
            this.joint3.setPosition(position);
        }
        public Cjoint3(HardwareMap hardwareMap){
            joint3 = hardwareMap.servo.get("j3");
        }
    }

    public static class Intake {
        private DcMotor spin;
        public void setSpin(double power) {
            this.spin.setPower(power);
        }

        public Intake(HardwareMap hardwareMap) {
            spin = hardwareMap.dcMotor.get("s");

            spin.setDirection(DcMotor.Direction.FORWARD);
        }
    }

    public static class TiltRight {
        private Servo tiltRight;
        public void setPosition(double position) {
            this.tiltRight.setPosition(position);
        }
        public TiltRight(HardwareMap hardwareMap) {
            tiltRight = hardwareMap.servo.get("tR");
        }
    }

    public static class TiltLeft {
        private Servo tiltLeft;
        public void setPosition(double position) {
            this.tiltLeft.setPosition(position);
        }
        public TiltLeft(HardwareMap hardwareMap) {
            tiltLeft = hardwareMap.servo.get("tL");
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
            double lsy = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(lsy)+ Math.abs(x) + Math.abs(rx), 1);

            //sets motor power based on input
            double FLPower = (lsy + x + rx / denominator);
            double BLPower = (lsy - x + rx / denominator);
            double FRPower = (lsy - x - rx / denominator);
            double BRPower = (lsy + x - rx / denominator);

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);

            }
        }
    }
