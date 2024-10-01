package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.SampleMecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends OpMode {
    private Robot robot;
    @Override
    public void init() {
        this.robot = new Robot().init(hardwareMap);
    }

    @Override
    public void loop() {
        //drive
        robot.getDrive().setInput(gamepad1);
        robot.getDrive().update();

        //extendo

        if (gamepad1.right_bumper){
            this.robot.getArm().extend();
        } else {
            this.robot.getArm().retract();
        }

    }
}
