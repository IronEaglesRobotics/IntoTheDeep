package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *  ----!please read this!----
 *  This is a practice class I have created for our team. I am going to
 *  base it off BasicOpMode_Iterative in "java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Iterative.java"
 *  Please mess around with the external.samples. That will get us started.
 *  I can help you out during robotics on Thursday, if your having trouble. Good luck :)
 *  ----!please read this!----
 */
@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class AddisonPracticeClass extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    @Override
    public void init() {
        telemetry.addData("status", "just started bro");
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop() {
double leftPower;
double rightPower;

//Tank Controls
leftDrive.setPower(leftPower);
rightDrive.setPower(rightPower);

telemetry.addData("status", "Run Time: " + runtime.toString());
telemetry.addData("Motors", "left (%.2f), right (%.2f)") leftPower, rightPower;
    }
    @Override
    public void stop() {
    }
}
