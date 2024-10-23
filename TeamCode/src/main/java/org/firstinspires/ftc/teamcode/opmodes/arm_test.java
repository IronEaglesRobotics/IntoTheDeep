package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "arm_test")
public class arm_test extends OpMode {
    DcMotor motor1;
    DcMotor motor2;
    @Override
    public void init(){
        motor1 = hardwareMap.get(DcMotor.class,"front_left");
        motor2 = hardwareMap.get(DcMotor.class,"back_left");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop(){
        if (motor2.getCurrentPosition() > 0 && motor2.getCurrentPosition() < 60000) {
            motor1.setPower(gamepad1.left_stick_y);
            motor2.setPower(gamepad1.left_stick_y);
        }
        telemetry.addData("encoder",motor2.getCurrentPosition());
    }
}
