package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.wristFloorlowscale;
import static org.firstinspires.ftc.teamcode.lib.Config.wristFullLowscale;
import static org.firstinspires.ftc.teamcode.lib.Config.wristFullhighscale;
import static org.firstinspires.ftc.teamcode.lib.Config.wristMedianhighscale;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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
    double highScale = wristMedianhighscale;

    ColorSensor colorSensor;
    public Intake(HardwareMap hardwareMap) {
        beatBar = hardwareMap.get(CRServo.class,"beatbar");
        Wrist = hardwareMap.get(Servo.class,"wrist");
        Wrist.scaleRange(wristFloorlowscale,wristFullhighscale);
        colorSensor = hardwareMap.get(ColorSensor.class,"c_sensor");
    }
    public void startBeatBar (){
        beatBar.setPower(1);
    }
    public void stopBeatBar (){
        beatBar.setPower(0);
    }
    public void reverseBeatBar(){
        beatBar.setPower(-.3);
    }
    public void wristUp(){
        Wrist.scaleRange(wristFloorlowscale, wristMedianhighscale);
        wrist = 0;
    }
    public void wristLow(){
        Wrist.scaleRange(wristFullLowscale, wristMedianhighscale);
        wrist = 0;
    }
    public void wristDown(){
        Wrist.scaleRange(wristFloorlowscale, wristMedianhighscale);
        wrist = 1;
    }
    public void wristStore(){
        Wrist.scaleRange(wristFloorlowscale,wristFullhighscale);
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

    public onIntake onIntake(){return new onIntake(this);}
    public reverseIntake reverseIntake(){return new reverseIntake(this);}
    public offIntake offIntake(){return new offIntake(this);}
    public ejectIntake ejectIntake(){return new ejectIntake(this);}
    public storeIntake storeIntake(){return new storeIntake(this);}
    public colorSet setRed = new colorSet(colors.RED,this);
    public colorSet setBlue = new colorSet(colors.BLUE,this);
    public runIntake runIntake() {return new runIntake(this);}
    public offEject offEject() {return new offEject(this);}
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
    public static class storeIntake extends InstantCommand{
        Intake intake;
        public storeIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }

        @Override
        public void initialize() {
            intake.wristStore();
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
    public static class ejectIntake extends InstantCommand{
        Intake intake;
        public ejectIntake(Intake tempIntake){
            intake = tempIntake;
            addRequirements(intake);
        }
        @Override
        public void initialize(){
            intake.wristLow();
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
        if (this.getColor() == target){
            output = intake.offIntake()
                    .andThen(intake.reverseIntake())
                    .andThen(new WaitCommand(750))
                    .whenFinished(this::stopBeatBar);
        } else {
            output = new offIntake(this).andThen(storeIntake());
        }
        return output;
    }
    public class runIntake extends CommandBase {
        Intake intake;
        public runIntake(Intake tIntake){
            intake = tIntake;
            addRequirements(intake);
        }
        @Override
        public void initialize(){
            intake.onIntake().schedule();
        }
        @Override
        public boolean isFinished(){
            return (intake.getColor() != colors.NULL);
        }
        @Override
        public void end(boolean i){
            intake.actionChoice().schedule();
        }
    }
    public class offEject extends CommandBase{
        Intake intake;
        public offEject(Intake tintake){
            intake = tintake;
            addRequirements(intake);
        }
        @Override
        public void initialize() {
            intake.stopBeatBar();
        }
    }
}
