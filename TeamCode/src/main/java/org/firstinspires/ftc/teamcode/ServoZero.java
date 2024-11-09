package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ZeroServo", group = "Zeroing")
public class ServoZero extends LinearOpMode {

    public Servo servo1;
    public Servo servo2;

    @Override
    public void runOpMode() throws InterruptedException{

        servo1 = hardwareMap.servo.get("s1");

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.left_bumper) {
                servo1.setPosition(1);
            }
            else {
                servo1.setPosition(0);
            }
        }
    }
}
