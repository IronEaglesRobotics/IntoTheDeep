package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servo")
public class servo extends OpMode {
    //define objects
    public Servo test;

    @Override
    public void init() {
        test = hardwareMap.servo.get("test");
        test.setPosition(.5);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            test.setPosition(1);
        } else {
            test.setPosition(0);
        }

    }
}
