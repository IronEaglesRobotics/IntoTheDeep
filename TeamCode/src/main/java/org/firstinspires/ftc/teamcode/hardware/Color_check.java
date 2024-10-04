package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

class Color_check {
    enum color {red,blue,yellow}
    ColorSensor c_sensor;
    color request;
    intake intake;
    Color_check Init(ColorSensor temp_c_sensor, HardwareMap HardwareMap, color i_request){
        c_sensor = temp_c_sensor;
        request = i_request;
        intake = new intake().Init(HardwareMap);
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
    void check () throws InterruptedException {
        if (!(this.getcolor() == request)){
            intake.eject();
        }
    }
}
