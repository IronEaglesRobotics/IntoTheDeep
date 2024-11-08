package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoZero extends LinearOpMode {

    public Servo servo;

    @Override
    public void runOpMode() throws InterruptedException{

        servo = hardwareMap.servo.get("s");
        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.right_bumper) {
                servo.setPosition(1);
            }
            else {
                servo.setPosition(0);
            }
        }
    }
}
