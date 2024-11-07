
package org.firstinspires.ftc.teamcode;

import static androidx.core.content.ContextCompat.getColor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "ColorSensor_Test", group = "Sensors")
public class colorSensor extends LinearOpMode{

    public ColorSensor yeah;

    public void runOpMode() {

        yeah = hardwareMap.get(ColorSensor.class, "test");

        telemetry.addData("Status:", "Hi, this is functioning");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            int b = yeah.blue();
            int r = yeah.red();
            int g = yeah.green();
            telemetry.addData("Blue:", b);
            telemetry.addData("\nRed:", r);
            telemetry.addData("\nGreen:", g);
            telemetry.update();
        }
    }
}

    