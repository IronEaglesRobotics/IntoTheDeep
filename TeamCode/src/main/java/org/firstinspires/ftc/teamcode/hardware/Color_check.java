package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

class Color_check {
    ColorSensor c_sensor;
    intake intake;
    Color_check Init(ColorSensor temp_c_sensor, HardwareMap HardwareMap,){
        c_sensor = temp_c_sensor;
        intake = new intake().Init(HardwareMap); // creates paradox
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
    void check (color request) throws InterruptedException {
        if (!(this.getcolor() == request)){
            intake.eject();
        }
    }
}
