package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Intake extends SubsystemBase {
    public Intake(HardwareMap hardwareMap) {

    }
    public void startBeatBar (){
        //sets CRservo to power 1
    }
    public void stopBeatBar (){
        //sets CRservo to power 0
    }
    public void reversBeatBar (){
        //sets CRservo to -1
    }
    public void toggleWrist (){
        // lowers intake to floor and lifts it up
    }

}