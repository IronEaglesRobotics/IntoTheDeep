package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Drive;

@TeleOp(name = "presentation_op")
public class presentation_op extends OpMode {
    Drive drive = new Drive().Init(hardwareMap);
    Servo Servo1;
    Servo Servo2;
    double servo1 = 0;
    double servo2 = 0;
    DcMotor motor1;
    DcMotor motor2;
    GamepadEx controller = new GamepadEx(gamepad1);

    @Override
    public void init(){
        motor1 = hardwareMap.get(DcMotor.class,"front_left");
        motor2 = hardwareMap.get(DcMotor.class,"back_left");
        Servo1 = hardwareMap.get(Servo.class,"rot1");
        Servo2 = hardwareMap.get(Servo.class,"rot2");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Servo2.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop(){
        drive.setDrive(controller,System.currentTimeMillis());

        servo1 += gamepad2.left_stick_x;
        servo2 += gamepad2.left_stick_x;
        servo1 = Math.max(Math.min(servo1,1),0);
        servo2 = Math.max(Math.min(servo2,1),0);
        Servo1.setPosition(servo1);
        Servo2.setPosition(servo2);

        if (motor2.getCurrentPosition() > 0 && motor2.getCurrentPosition() < 60000) {
            motor1.setPower(gamepad2.right_stick_y);
            motor2.setPower(gamepad2.right_stick_y);
        }

        telemetry.addData("encoder",motor2.getCurrentPosition());
        telemetry.addData("servo1",servo1);
        telemetry.addData("servo2",servo2);
        telemetry.update();
    }
}
