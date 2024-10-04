package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class intake {
    Servo Rot1,Rot2,Eject,Beat_bar;
    ColorSensor c_sensor;
    boolean beatbar_flipped;
    double rot1,rot2,eject,beat_bar;
    Color_check color_check = new Color_check();


    public intake Init(HardwareMap HardwareMap){
        Rot1 = HardwareMap.get(Servo.class,"intake_rot1");
        Rot2 = HardwareMap.get(Servo.class,"intake_rot2");
        Eject = HardwareMap.get(Servo.class,"eject");
        Beat_bar = HardwareMap.get(Servo.class,"beat_bar");
        c_sensor = HardwareMap.get(ColorSensor.class,"c_sensor");


        return this;
    }
    public void toggle_beatbar (){
        beatbar_flipped = !beatbar_flipped;
        beat_bar = beatbar_flipped ? 1 : 0;
    }
    public void pickup() throws InterruptedException {
        this.toggle_beatbar();
        Thread.sleep(1000);
        color_check.getcolor();
    }
    void eject() throws InterruptedException {
        eject = 0;
        sleep(1000);
        eject = 1;
    }
    void update_servo(){
        Rot1.setPosition(rot1);
        Rot2.setPosition(rot2);
        Eject.setPosition(eject);
        Beat_bar.setPosition(beat_bar);
    }
}
