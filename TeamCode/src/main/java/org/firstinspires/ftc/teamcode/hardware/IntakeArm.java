package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.CLAW_ROT;
import static org.firstinspires.ftc.teamcode.lib.Config.LEFT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.RIGHT_ARM;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeArm extends SubsystemBase {
    Servo rotation1;
    Servo rotation2;
    Servo extension1;
    Servo extension2;


    public IntakeArm(HardwareMap HardwareMap){
        rotation1 = HardwareMap.get(Servo.class,LEFT_ARM);
        rotation2 = HardwareMap.get(Servo.class,RIGHT_ARM);
        rotation2.setDirection(Servo.Direction.REVERSE);

        extension1 = HardwareMap.get(Servo.class,"");
        extension2 = HardwareMap.get(Servo.class,"");
    }
}
