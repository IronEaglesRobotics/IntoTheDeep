package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.wristFloorlowscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.wristMedianhighscale1;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Collections;
import java.util.Set;

@Config
public class Intake extends SubsystemBase {
    CRServo beatBar;
    Servo Wrist;
    double wrist = 1;
    boolean wristUp = true;
    public enum colors{
        RED,
        BLUE,
        YELLOW,
        NULL
    }
    public colors target = colors.YELLOW;
    int r;
    int g;
    int b;
    int a;

    ColorSensor colorSensor;
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
    public colors getColor() {
        colors color;
        b = colorSensor.blue();
        g = colorSensor.green();
        r = colorSensor.red();
        a = colorSensor.alpha();

        if (r + b + g < 190) {
            color = colors.NULL;
        } else if (b > g+10 && b > r+10) {
            color = colors.BLUE;
        } else if (g > b+25 && g > r+25) {
            color = colors.YELLOW;
        } else if (r > b+50 && r > g+50) {
            color = colors.RED;
        } else {
            color = colors.NULL;
        }
        return color;
    }
    public void periodic() {
        Wrist.setPosition(wrist);
        this.colorCheck.schedule();
    }

    public activeIntake activeIntake = new activeIntake(this);
    public reverseIntake reverseIntake = new reverseIntake(this);
    public offIntake offIntake = new offIntake(this);
    public colorSet setRed = new colorSet(colors.RED,this);
    public colorSet setBlue = new colorSet(colors.BLUE,this);
    public colorCheck colorCheck = new colorCheck(this);
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
    public static class colorSet extends InstantCommand{
        colors color;

        Intake intake;
        public colorSet(colors tColor, Intake tIntake){
            color = tColor;
            intake = tIntake;
        }
        @Override
        public void initialize() {
            intake.target = color;
        }
    }
    public static class colorCheck extends CommandBase {
        Intake intake;
        public colorCheck(Intake tIntake){
            intake = tIntake;
            addRequirements(intake);
        }
        @Override
        public void initialize() {
            if (intake.target == intake.getColor()){
                intake.offIntake.andThen(intake.reverseIntake).andThen(new WaitCommand(1000)).andThen(intake.activeIntake);
            }
        }
        @Override
        public boolean isFinished(){return (intake.wrist == 0);}
    }
}