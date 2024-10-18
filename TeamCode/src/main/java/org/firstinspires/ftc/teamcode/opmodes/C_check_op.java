package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Color_check;

@TeleOp(name = "color_op")
public class C_check_op extends LinearOpMode {
    ColorSensor c_sensor;

    @Override
    public void runOpMode() throws InterruptedException{
        c_sensor = hardwareMap.get(ColorSensor.class, "c_sensor");
        Color_check color_check = new Color_check().Init(c_sensor,hardwareMap);

        while (opModeIsActive()){
            telemetry.addData("red",c_sensor.red());
            telemetry.addData("blue",c_sensor.blue());
            telemetry.addData("green",c_sensor.green());
            telemetry.addData("red",color_check.getcolor());
            telemetry.update();
        }
    }
}
