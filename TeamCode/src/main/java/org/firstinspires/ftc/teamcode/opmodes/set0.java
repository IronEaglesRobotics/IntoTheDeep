package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "setO")
public class set0 extends LinearOpMode {
    Servo servo1 = hardwareMap.get(Servo.class,"rot1");
    Servo servo2 = hardwareMap.get(Servo.class,"rot2");
    @Override
    public void runOpMode() throws InterruptedException {
        while (opModeIsActive()) {
            servo1.setPosition(0);
            servo2.setPosition(0);
        }
    }
}
