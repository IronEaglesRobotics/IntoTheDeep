package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "lib test")
public class libtest extends OpMode {
    Robot robot;
    GamepadEx controller1;
    GamepadEx controller2;
    @Override
    public void init() {
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot = new Robot().init(hardwareMap,controller1,new Pose(0,0,Math.toRadians(180)));
        robot.getFollower().update();
        robot.getFollower().followPath(robot.EZ().moveToWithHeading(10,0,Math.toRadians(180)));
    }

    @Override
    public void loop() {
        robot.getFollower().update();
    }
}
