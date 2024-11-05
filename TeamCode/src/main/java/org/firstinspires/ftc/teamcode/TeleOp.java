package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TeleOp extends OpMode {
    Robot robot;

    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
    }

    @Override
    public void loop() {
        robot.drive.handleInput(this.gamepad1, this.gamepad2);

        if(gamepad1.y) {
            robot.extendoLeft.setPosition(1);
            robot.extendoRight.setPosition(-1);

        }

        if(gamepad1.a) {
            robot.tiltRight.setPosition(0);
            robot.tiltLeft.setPosition(0);

            robot.extendoLeft.setPosition(0);
            robot.extendoRight.setPosition(0);
        }

        if(gamepad1.left_trigger > 0) {
            robot.tiltRight.setPosition(-1);
            robot.tiltLeft.setPosition(1);
            robot.intake.setSpin(1);
        }

        if(gamepad1.right_bumper){
            robot.claw.setPosition(.1);
        }

        if(gamepad1.left_bumper){
            robot.claw.setPosition(0);
        }

        if(gamepad2.y) {
        }

        if(gamepad2.a){
        }

    }


}
