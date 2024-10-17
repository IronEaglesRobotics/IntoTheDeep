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
    public ColorSensor frontColorSensor;


    @Override
    public void init() {
        frontColorSensor = hardwareMap.get(ColorSensor.class,"cS");
        frontColorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        telemetry.addData("Red", frontColorSensor.red());
        telemetry.addData("Green", frontColorSensor.green());
        telemetry.addData("Blue", frontColorSensor.blue());
        telemetry.update();
    }

}
