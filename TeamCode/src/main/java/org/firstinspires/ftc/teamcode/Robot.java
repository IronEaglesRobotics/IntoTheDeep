package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    Drive drive;
    Lift lift;
    Claw claw;
    Wrist wrist;
    Arm arm;
    public static double test = 0.0;

    public Robot(HardwareMap hardwareMap) {
        this.drive = new Drive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        // this.lift = new Lift(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.arm = new Arm(hardwareMap);
        this.wrist = new Wrist(hardwareMap);
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
//            liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

            liftLeft = hardwareMap.get(DcMotor.class, "liftL");
            liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }

        public void setTargetPosition(int p) {
            this.liftRight.setTargetPosition(p);
            this.liftLeft.setTargetPosition(p);
            this.liftRight.setPower(p);
            this.liftLeft.setPower(p);
            this.liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        public void setPower(double v) {
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

    public static class Drive {
        private DcMotor FL;
        private DcMotor BL;
        private DcMotor FR;
        private DcMotor BR;
        private DcMotor encoderLeft;
        private DcMotor encoderRight;
        private DcMotor encoderAux;

        public Drive(HardwareMap hardwareMap) {
            FL = hardwareMap.dcMotor.get("frontLeft");
            FL.setDirection(DcMotor.Direction.REVERSE);
            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            BL = hardwareMap.dcMotor.get("backLeft");
            BL.setDirection(DcMotor.Direction.REVERSE);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            FR = hardwareMap.dcMotor.get("frontRight");
            FR.setDirection(DcMotor.Direction.REVERSE);
            FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            BR = hardwareMap.dcMotor.get("backRight");
            BR.setDirection(DcMotor.Direction.REVERSE);
            BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            encoderLeft = BL;
            encoderRight = BR;
            encoderAux = FR;









        }

        public void handleInput(Gamepad gamepad1) {
            //defines inputs
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);



            //sets motor power based on input
            double FLPower = ((y + x + rx) / denominator);
            double BLPower = ((y - x + rx) / denominator);
            double FRPower = ((y - x - rx) / denominator);
            double BRPower = ((y + x - rx) / denominator);

//            double maxPower= Math.max(Math.abs(FLPower), Math.abs(FRPower)) Math.abs(BLPower), Math.abs(BRPower));
//
//            if

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);

            FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        }
//
//
    }

}







































