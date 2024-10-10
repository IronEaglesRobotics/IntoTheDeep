package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ColorSensor Opmode", group="Iterative OpMode")
public class ColorSensorTest extends LinearOpMode{

    public ModernRoboticsI2cColorSensor frontColorSensor = null;

    @Override
    public void runOpMode() {
        frontColorSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class,"cS");

        waitForStart();

        frontColorSensor.enableLed(true);

        while (opModeIsActive()); {
            telemetry.addData("Color Number", frontColorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            telemetry.update();

        }
    }

}
