package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class pusher extends SubsystemBase {
    Servo pusher;
    public pusher(HardwareMap hardwareMap){
        pusher = hardwareMap.get(Servo.class,"pusher");
        pusher.scaleRange(0,4);
    }
    public void push() throws InterruptedException{
        pusher.setPosition(1);
        wait(500);
        pusher.setPosition(0);
    }
}
