package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    Drive drive;
    Extendo extendo;
    TopExtendo topExtendo;
    Lift lift;
    Claw claw;
    Intake intake;
    TiltRight tiltRight;
    TiltLeft tiltLeft;
    Wrist wrist;
    Elbow elbow;
    Arm arm;
    public static double test = 0.0;

    public Robot(HardwareMap hardwareMap) {
        this.drive = new Drive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.tiltRight = new TiltRight(hardwareMap);
        this.tiltLeft = new TiltLeft(hardwareMap);
       // this.lift = new Lift(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.extendo = new Extendo(hardwareMap);
        this.topExtendo = new TopExtendo(hardwareMap);
        this.arm = new Arm(hardwareMap);
        this.elbow = new Elbow(hardwareMap);
        this.wrist = new Wrist(hardwareMap);
    }

    public static class Extendo {
        private Servo extendoRight;
        private Servo extendoLeft;

        public Extendo(HardwareMap hardwareMap) {

            extendoRight = hardwareMap.servo.get("eR");
            extendoLeft = hardwareMap.servo.get("eL");
            extendoRight.setDirection(Servo.Direction.REVERSE);
        }

        public void moveTo(double position) {
            this.extendoRight.setPosition(position);
            this.extendoLeft.setPosition(position);
        }
    }

    public static class TopExtendoLeft {

        public TopExtendoLeft(HardwareMap hardwareMap) {

        }

    }

    public static class TopExtendo {
        private Servo topExtendoRight;
        private Servo topExtendoLeft;

        public void setPosition(double position) {
            this.topExtendoRight.setPosition(position);
            this.topExtendoLeft.setPosition(position);
        }
        public TopExtendo(HardwareMap hardwareMap) {
            topExtendoRight = hardwareMap.servo.get("TeR");
            topExtendoLeft = hardwareMap.servo.get("TeL");
        }
    }

    public static class Lift {
        private DcMotor liftRight;
        private DcMotor liftLeft;
       // double ticks = 384.5;
       // double newTarget;

        public Lift(HardwareMap hardwareMap) {
            liftRight = hardwareMap.get(DcMotor.class, "liftR");
            liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

            liftLeft = hardwareMap.get(DcMotor.class, "liftL");
            liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }

        public void setTargetPosition(int i) {
            this.liftRight.setTargetPosition(i);
            this.liftLeft.setTargetPosition(i);

        }

        public void setPower(double v) {
            this.liftRight.setPower(v);
            this.liftLeft.setPower(v);
        }
        public void setMode(DcMotor.RunMode mode){
            this.liftRight.setMode(mode);
            this.liftLeft.setMode(mode);
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

    public static class Wrist {
        private Servo wrist;
        public void setPosition(double position){
            this.wrist.setPosition(position);
        }
        public Wrist(HardwareMap hardwareMap) {
            wrist = hardwareMap.servo.get("j1");
        }
    }

    public static class Elbow {
        private Servo elbow;
        public void setPosition(double position){
            this.elbow.setPosition(position);
        }
        public Elbow(HardwareMap hardwareMap){
            elbow = hardwareMap.servo.get("j2");
        }
    }

    public static class Arm {
        private Servo leftArm;
        private Servo rightArm;

        public void setPosition(double position){
            this.leftArm.setPosition(position);
           // this.rightArm.setPosition(position);
        }
        public Arm(HardwareMap hardwareMap){
            leftArm = hardwareMap.servo.get("j3L");
          //  rightArm = hardwareMap.servo.get("j3R");
            leftArm.setDirection(Servo.Direction.REVERSE);
        }
    }

    public static class Intake {
        private DcMotor intake;
        public void setSpin(double power) {
            this.intake.setPower(power);
        }

        public Intake(HardwareMap hardwareMap) {
            intake = hardwareMap.dcMotor.get("s");

            intake.setDirection(DcMotor.Direction.FORWARD);
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
            tiltLeft.setDirection(Servo.Direction.REVERSE);
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

            FR.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public void handleInput(Gamepad gamepad1) {
            //defines inputs
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y)+ Math.abs(x) + Math.abs(rx), 1);

            //sets motor power based on input
            double FLPower = (y - x + rx / denominator);
            double BLPower = (y - x + rx / denominator);
            double FRPower = (y - x - rx / denominator);
            double BRPower = (y - x - rx / denominator);

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);

            }


        }
    }










































