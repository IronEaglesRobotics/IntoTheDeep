package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Main Teleop", group="TeleOp")
public class TeleOpMain extends CommandOpMode {
    private Robot robot;
    private GamepadEx controller1;
    private GamepadEx controller2;

    @Override
    public void initialize() {
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot = new Robot().init(hardwareMap);

        // Deploy hang at the start of the match
        robot.getHang().hangCommand.schedule();

        controller2.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whenPressed(robot.getHang().hangCommand);

        new Trigger(() -> Math.abs(controller2.getLeftY()) > 0.1)
                .whenActive(new Slides.LiftEncoderPositionCommand(robot.getSlides(), controller2.getLeftY()));
    }
}
