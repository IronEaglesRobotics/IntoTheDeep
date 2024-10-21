package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "setO")
public class set0 extends OpMode {
    Servo Servo1;
    Servo Servo2;
    double servo1 = 0;
    double servo2 = 0;
    @Override
    public void init(){
        Servo1 = hardwareMap.get(Servo.class,"rot1");
        Servo2 = hardwareMap.get(Servo.class,"rot2");
    }
    @Override
    public void loop()  {

        Servo2.setDirection(Servo.Direction.REVERSE);
        servo1 += gamepad1.left_stick_x;
        servo2 += gamepad1.left_stick_x;
        servo1 = Math.max(Math.min(servo1,1),0);
        servo2 = Math.max(Math.min(servo2,1),0);
        Servo1.setPosition(servo1);
        Servo2.setPosition(servo2);
        telemetry.addData("servo1",servo1);
        telemetry.addData("servo2",servo2);
    }
}