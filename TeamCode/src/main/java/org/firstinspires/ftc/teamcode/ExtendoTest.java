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
    public double RETRACTED = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        extendo = hardwareMap.servo.get("eX");
        extendo.scaleRange(EXTENDED,RETRACTED);
        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
