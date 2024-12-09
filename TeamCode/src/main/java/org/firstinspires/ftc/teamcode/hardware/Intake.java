package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Intake extends SubsystemBase {
    CRServo beatBar;
    Servo wrist;
    boolean wristUp = true;
    public Intake(HardwareMap hardwareMap) {
        beatBar = hardwareMap.get(CRServo.class,"beatbar");
        wrist = hardwareMap.get(Servo.class,"wrist");
    }
    public void startBeatBar (){
        beatBar.setPower(-1);
    }
    public void stopBeatBar (){
        beatBar.setPower(0);
    }
    public void reverseBeatBar(){
        beatBar.setPower(1);
    }
    public void toggleWrist (){
        if (!wristUp){
            wrist.setPosition(1);
        } else {
            wrist.setPosition(0);
        }
    }
    activeIntake activeIntake = new activeIntake(this);
    reverseIntake reverseIntake = new reverseIntake(this);
    offIntake offIntake = new offIntake(this);
    public static class activeIntake extends InstantCommand{
        Intake intake;
        public activeIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }

        @Override
        public void initialize() {
            if (intake.wristUp){
                intake.toggleWrist();
            }
            intake.startBeatBar();
        }
    }
    public static class reverseIntake extends InstantCommand{
        Intake intake;
        public reverseIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }

        @Override
        public void initialize() {
            if (intake.wristUp){
                intake.toggleWrist();
            }
            intake.reverseBeatBar();
        }
    }
    public static class offIntake extends InstantCommand{
        Intake intake;
        public offIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }

        @Override
        public void initialize() {
            if (!intake.wristUp){
                intake.toggleWrist();
            }
            intake.stopBeatBar();
        }
    }
}