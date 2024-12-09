package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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

        controller1.readButtons();
        controller2.readButtons();

        // Deploy hang and extension at the start of the match
        robot.getHang().hangCommand.schedule();

        // controls hang
        controller2.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whenPressed(robot.getHang().hangCommand);
        // controls lowering slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(robot.getSlides().dPadDownCommand);
        // controls raising slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(robot.getSlides().dPadUpCommand);
        // macros rotating arm up and extending intake
//        controller2.getGamepadButton(GamepadKeys.Button.Y)
//                .whenPressed(robot.getIntakeArm().raiseCommand);
        // extends intake
        controller2.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(robot.getIntakeArm().extendCommand);
        // rotates intake arm up
//        controller2.getGamepadButton(GamepadKeys.Button.B)
//                .whenPressed(robot.getIntakeArm().rotateCommand);

        // manual control of slides
        new Trigger(() -> Math.abs(controller2.getLeftY()) > 0.1)
                .whenActive(new Slides.LiftEncoderPositionCommand(robot.getSlides(), controller2.getLeftY()));

        // there comments
    }
    @Override
    public void run(){
        CommandScheduler.getInstance().run();
        // drive controls
        robot.getDrive().setDrivePowers(new PoseVelocity2d(
                new Vector2d(controller1.getLeftY(),controller1.getLeftX())
                ,controller1.getRightX()
        ));
    }
}
