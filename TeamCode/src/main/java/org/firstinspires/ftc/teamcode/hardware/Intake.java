package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.wristFloorlowscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.wristMedianhighscale1;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

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
        colorSensor = hardwareMap.get(ColorSensor.class,"c_sensor");
    }
    public void startBeatBar (){
        beatBar.setPower(1);
    }
    public void stopBeatBar (){
        beatBar.setPower(0);
    }
    public void reverseBeatBar(){
        beatBar.setPower(-1);
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
    }

    public onIntake onIntake = new onIntake(this);
    public reverseIntake reverseIntake = new reverseIntake(this);
    public offIntake offIntake = new offIntake(this);
    public colorSet setRed = new colorSet(colors.RED,this);
    public colorSet setBlue = new colorSet(colors.BLUE,this);
    public runIntake runIntake = new runIntake(this);
    public static class onIntake extends InstantCommand{
        Intake intake;
        public onIntake(Intake tempIntake){
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
    public Command actionChoice() {
        Command output;
        Intake intake = this;
        BooleanSupplier booleanSupplier = new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return intake.getColor() != colors.NULL;
            }
        };
        if (this.getColor() == target){
            output = intake.onIntake.andThen(new WaitCommand(1000000000)).interruptOn(booleanSupplier)
                    .andThen(intake.offIntake)
                    .andThen(intake.reverseIntake)
                    .andThen(new WaitCommand(750))
                    .andThen(intake.onIntake);
        } else {
            output = new onIntake(this).andThen(new WaitCommand(100000000)).interruptOn(booleanSupplier).andThen(new offIntake(this));
        }
        return output;
    }
    public static class runIntake extends CommandBase {
        Intake intake;
        public runIntake(Intake tIntake){
            intake = tIntake;
            addRequirements(intake);
        }
        @Override
        public void initialize() {
            intake.actionChoice().schedule();
        }
        @Override
        public boolean isFinished(){
            return (true);
        }
    }
}
