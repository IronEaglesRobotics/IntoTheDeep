package org.firstinspires.ftc.teamcode.hardware;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class intake {
    Servo Rot1,Rot2,Eject,Beat_bar;
    ColorSensor c_sensor;
    boolean beatbar_flipped;
    double rot1,rot2,eject,beat_bar;
    color_check color_check = new color_check();


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
    public void pickup(){
        this.toggle_beatbar();
        wait(1000);
        color_check.getcolor();
    }
    void eject(){
        eject = 0;
        sleep(1000);
        eject = 1;
    }
}
class color_check {
    enum color {red,blue,yellow}
    ColorSensor c_sensor;
    color request;
    color_check Init(ColorSensor temp_c_sensor,HardwareMap HardwareMap,color i_request){
        c_sensor = temp_c_sensor;
        request = i_request;
        intake intake = new intake.Init(HardwareMap);
        return this;
    }
    color getcolor(){
        int sensitivity = 30;
        color temp_color;
        if (c_sensor.red()>c_sensor.blue()+sensitivity) temp_color = color.red;
        else if (c_sensor.blue()>c_sensor.red()+sensitivity) temp_color = color.blue;
        else temp_color = color.yellow;

        return temp_color;
    }
    void check (){
        if (!(this.getcolor() == request)){

        }
    }
}