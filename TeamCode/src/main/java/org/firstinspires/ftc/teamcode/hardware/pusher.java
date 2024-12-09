package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class pusher extends SubsystemBase {
    Servo Pusher;
    double pusher = 0;
    public pusher(HardwareMap hardwareMap){
        Pusher = hardwareMap.get(Servo.class,"pusher");

        Pusher.scaleRange(.13,.54);
    }
    public void activatePush() {
        pusher = 1;
    }
    public void offPush() {
        pusher = 0;
    }
    @Override
    public void periodic(){
        Pusher.setPosition(pusher);
    }
    public activateCommand activateCommand = new activateCommand(this);
    public offCommand offCommand = new offCommand(this);

    public class activateCommand extends InstantCommand{
        pusher pusher;
        public activateCommand(pusher tempPusher){
            pusher = tempPusher;
        }
        public void initialize() {
            pusher.activatePush();
        }
    }
    public class offCommand extends InstantCommand {
        pusher pusher;
        public offCommand(pusher tempPusher){
            pusher = tempPusher;
        }
        public void initialize() {
            pusher.offPush();
        }
    }
}
