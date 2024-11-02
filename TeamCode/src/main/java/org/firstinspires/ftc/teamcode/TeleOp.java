package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TeleOp extends OpMode {
    Robot robot;

    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
    }

    @Override
    public void loop() {
        robot.drive.handleInput(this.gamepad1, this.gamepad2);
    }
}
