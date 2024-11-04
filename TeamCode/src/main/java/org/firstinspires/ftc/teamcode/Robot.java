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
    Tilt tilt;
    Cjoint1 cjoint1;
    Cjoint2 cjoint2;
    Cjoint3 cjoint3;

    public Robot(HardwareMap hardwareMap) {
        this.drive = new Drive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.tilt = new Tilt(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.extendo = new Extendo(hardwareMap);
    }

    public static class Extendo {
        private Servo extendo;
        public void setPosition(double position) {
            this.extendo.setPosition(position);
        }
        public Extendo(HardwareMap hardwareMap) {

            extendo = hardwareMap.servo.get("e");
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

    public static class Tilt {
        private Servo tilt1;
        private Servo tilt2;
        public void setPosition(double position) {
            this.tilt1.setPosition(position);
            this.tilt2.setPosition(position);
        }
        public Tilt(HardwareMap hardwareMap) {
            tilt1 = hardwareMap.servo.get("t1");
            tilt2 = hardwareMap.servo.get("t2");
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
