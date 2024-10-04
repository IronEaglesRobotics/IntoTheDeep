package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.Config;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class Hang_arm {
    public DcMotor motor;
    public Servo servo;

    public Hang_arm Init(HardwareMap hmap){
        motor = hmap.get(DcMotor.class,"hang_motor");
        servo = hmap.get(Servo.class,"hand_servo");

        return this;
    }
    public void lift_hook(double pos){
        servo.setPosition(pos);
    }
    public void pullup(double power){
            motor.setPower(power);
    }

}
