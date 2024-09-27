package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp (name = "motor")
public class motor extends OpMode {
    //define motor
    public DcMotor test;
    public int power = 1;

    @Override
    public void init() {
        this.test = hardwareMap.get(DcMotor.class,"test");
        this.test.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.test.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.test.setPower(0);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
           this.test.setPower(power);
        } else {
           this.test.setPower(0);
        }

        if (gamepad1.right_bumper) {
            this.test.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            this.test.setDirection(DcMotorSimple.Direction.FORWARD);
        }

    }
}
