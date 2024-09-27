package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@Config
@TeleOp(name = "servo")
public class servo extends OpMode {
    //define objects
    public Servo test;
    public static double openPos = .15;
    public static double closePos = .3;


    @Override
    public void init() {
        this.test = hardwareMap.servo.get("test");
        this.test.setPosition(.1);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
//            this.test.getController().pwmEnable();
            this.test.setPosition(openPos);
        } else {
            this.test.getController().pwmDisable();
        }

//        if(gamepad1.right_bumper){
//        }

    }
}
