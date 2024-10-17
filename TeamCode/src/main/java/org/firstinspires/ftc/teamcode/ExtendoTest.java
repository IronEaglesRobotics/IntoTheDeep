package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Extendo Opmode", group="Iterative OpMode")
public class ExtendoTest extends LinearOpMode{

    public Servo extendo;
    public double EXTENDED = 2;
    public double RETRACTED = -2;

    @Override
    public void runOpMode() throws InterruptedException{
        extendo = hardwareMap.servo.get("eX");
        extendo.scaleRange(RETRACTED,EXTENDED);
        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.left_stick_y >= 0) {
                extendo.setPosition(extendo.getPosition()+0.1);
            }
            else if (gamepad1.left_stick_y <= 0) {
                extendo.setPosition(extendo.getPosition()-0.1);
            }
            else {
                stop();
            }

        }
    }
}
