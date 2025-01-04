package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Hang;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Slides;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.function.BooleanSupplier;

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

        controller1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(robot.toClip.interruptOn(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return controller1.wasJustReleased(GamepadKeys.Button.X);
                    }
                }));
        // controls hang
        controller2.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whenPressed(new Hang.HangCommand(robot.getHang()));
        // controls lowering slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(robot.getSlides().down()).whenPressed(new WaitCommand(300).andThen(new Claw.ClawCommand(robot.getClaw(),true)));
        // controls raising slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(robot.getSlides().up());
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(robot.getSlides().postclip()).whenPressed(new WaitCommand(300).andThen(new Claw.ClawCommand(robot.getClaw(),true)));
        // controls raising slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(robot.getSlides().preclip());
        // macros rotating arm up and extending intake
        controller2.getGamepadButton(GamepadKeys.Button.X)
                .toggleWhenPressed(new Intake.reverseIntake(robot.getIntake()),new Intake.offIntake(robot.getIntake()));
        // extends intake
        controller2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(robot.getIntakeArm().extendCommand());
        // rotates intake arm up
            controller2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(robot.getIntakeArm().rotateCommand());

        controller2.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenPressed(new Intake.runIntake(robot.getIntake()),new Intake.offIntake(robot.getIntake()));

        controller2.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whenPressed(robot.getPusher().activateCommand)
                .whenReleased(robot.getPusher().offCommand);

        controller2.getGamepadButton(GamepadKeys.Button.A)
                .toggleWhenPressed(new Claw.ClawCommand(robot.getClaw(),true),new Claw.ClawCommand(robot.getClaw(),false));

        controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .toggleWhenPressed(robot.getIntake().setBlue,robot.getIntake().setRed);

        // manual control of slides
        new Trigger(() -> Math.abs(controller2.getLeftY()) > 0.1)
                .whenActive(new Slides.LiftEncoderPositionCommand(robot.getSlides(), controller2.getLeftY()));

        // there comments
    }
    @Override
    public void run(){
        CommandScheduler.getInstance().run();
        // drive controls
        if (robot.getDriveState() == Robot.DriveState.manuel) {
            robot.getDrive().setDrivePowers(new PoseVelocity2d(
                    new Vector2d(controller1.getLeftY(), controller1.getLeftX())
                    , -controller1.getRightX()
            ));
        }
        telemetry.addData("target color",robot.getIntake().target);
        telemetry.addData("color",robot.getIntake().getColor());
        telemetry.addData("target",robot.getSlides().getTarget());
        telemetry.addData("color",robot.getSlides().getPos());
        telemetry.addData("speed",robot.getSlides().controller.calculate(-robot.getSlides().getPos(),robot.getSlides().getTarget()));
        telemetry.update();
    }
}
