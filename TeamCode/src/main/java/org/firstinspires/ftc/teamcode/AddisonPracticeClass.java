package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 *  ----!please read this!----
 *  This is a practice class I have created for our team. I am going to
 *  base it off BasicOpMode_Iterative in "java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Iterative.java"
 *  Please mess around with the external.samples. That will get us started.
 *  I can help you out during robotics on Thursday,  your having trouble. Good luck :)
 *  ----!please read this!----
 */

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
//@Disabled
public class AddisonPracticeClass extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lOne = null;
    private DcMotor lTwo = null;
    private DcMotor rOne = null;
    private DcMotor rTwo = null;

    @Override
    public void init() {
        //initial message out
        telemetry.addData("status", "just started bro");
        //define motors
        lOne = hardwareMap.get(DcMotor.class, "l1");
        lTwo = hardwareMap.get(DcMotor.class, "l2");
        rOne = hardwareMap.get(DcMotor.class, "r1");
        rTwo = hardwareMap.get(DcMotor.class, "r2");
        //define motor direction
        lOne.setDirection(DcMotor.Direction.FORWARD);
        lTwo.setDirection(DcMotor.Direction.REVERSE);
        rOne.setDirection(DcMotor.Direction.FORWARD);
        rTwo.setDirection(DcMotor.Direction.REVERSE);
        //initialized message out
        telemetry.addData("status", "Initialized");
    }

    @Override
    public void init_loop() {
    }
//ran when driver hits start
    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop() {
        //define motor powers
        double leftPower;
        double rightPower;
        //define binds
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);

        drive = -gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;
//updating motor power
lOne.setPower(leftPower);
lTwo.setPower(leftPower);
rOne.setPower(rightPower);
rTwo.setPower(rightPower);
//log data?
telemetry.addData("status", "Run Time: " + runtime.toString());
telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }
    @Override
    public void stop() {
    }
}
