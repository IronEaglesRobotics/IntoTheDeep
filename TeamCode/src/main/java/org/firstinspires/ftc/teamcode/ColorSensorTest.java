package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;

@TeleOp(name="ColorSensor Opmode", group="Iterative OpMode")
public class ColorSensorTest extends OpMode{
    public ColorSensor colorSensor;
    private double redValue;
    private double

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class,"cS");
        colorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.update();
    }

}
