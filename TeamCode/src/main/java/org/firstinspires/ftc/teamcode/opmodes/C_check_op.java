package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Color_check;

@TeleOp(name = "color_op")
public class C_check_op extends OpMode {
    ColorSensor c_sensor;
    Color_check color_check;
    @Override
    public void init() {
        c_sensor = hardwareMap.get(ColorSensor.class, "c_sensor");
        color_check = new Color_check().Init(c_sensor, hardwareMap);
    }
    public void loop() {
            telemetry.addData("red",c_sensor.red());
            telemetry.addData("blue",c_sensor.blue());
            telemetry.addData("green",c_sensor.green());
            telemetry.addData("red",color_check.getcolor());
            telemetry.update();
    }
}
