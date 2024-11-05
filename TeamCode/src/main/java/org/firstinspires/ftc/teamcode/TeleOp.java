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
            robot.extendo.setPosition(1);
            robot.tilt.setPosition(1);
        }

        if(gamepad1.a) {
            robot.tilt.setPosition(0);

            robot.extendo.setPosition(0);
        }

        if(gamepad1.left_trigger > 0) {
            robot.intake.setSpin(1);
        }

        if(gamepad2.right_bumper){
            robot.claw.setPosition(.1);
        }

        if(gamepad2.y) {
            robot.lift.setTargetPosition(1);
            robot.lift.setPower(0.5);
            robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.cjoint1.setPosition(1);
            robot.cjoint2.setPosition(0);
            robot.cjoint3.setPosition(-0.5);
        }

        if(gamepad2.a){
            robot.lift.setTargetPosition(0);
            robot.lift.setPower(0.5);
            robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.cjoint1.setPosition(0);
            robot.cjoint2.setPosition(-0.25);
            robot.cjoint3.setPosition(0);
        }

    }


}
