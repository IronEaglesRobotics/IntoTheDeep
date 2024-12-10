package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.wristFloorlowscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.wristMedianhighscale1;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Intake extends SubsystemBase {
    CRServo beatBar;
    Servo Wrist;
    double wrist = 1;
    boolean wristUp = true;
    public Intake(HardwareMap hardwareMap) {
        beatBar = hardwareMap.get(CRServo.class,"beatbar");
        Wrist = hardwareMap.get(Servo.class,"wrist");
        Wrist.scaleRange(wristFloorlowscale1,wristMedianhighscale1);
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
    public void wristUp(){
        wrist = 0;
    }
    public void wristDown(){
        wrist = 1;
    }
    public void periodic() {
        Wrist.setPosition(wrist);
    }

    public activeIntake activeIntake = new activeIntake(this);
    public reverseIntake reverseIntake = new reverseIntake(this);
    public offIntake offIntake = new offIntake(this);
    public static class activeIntake extends InstantCommand{
        Intake intake;
        public activeIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }

        @Override
        public void initialize() {
            intake.wristUp();
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
            intake.wristDown();
            intake.stopBeatBar();
        }
    }
}