package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Twist2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Main Teleop", group="TeleOp")
public class TeleOpMain extends CommandOpMode {
    private Robot robot;
    private GamepadEx controller1;
    private GamepadEx controller2;
    private GamepadEx[] controllers = new GamepadEx[4];
    private double speed = 1;
    private Robot.activeMode mode = Robot.activeMode.macro;
    private int switcher = 1;

    @Override
    public void initialize() {
        boolean first = true;
        if (first){
            CommandScheduler.getInstance().reset();
            first = false;
        }
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        controllers[2] = controller2;
        robot = new Robot().init(hardwareMap,new Pose2d(0,0,0));
        controller1.readButtons();
        controller2.readButtons();

        controller1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(robot.runAction(robot.getDrive().actionBuilder(robot.getDrive().pose).splineToLinearHeading(robot.getDrive().pose.plus(new Twist2d(new Vector2d(20,35),Math.toRadians(180))),Math.toRadians(-110)).build())
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(robot.getDrive().pose).setTangent(Math.toRadians(190)).lineToX(34.5).build())));
        controller1.getGamepadButton(GamepadKeys.Button.Y)
                .toggleWhenPressed(()->speed = 1,()->speed = .5);
        controller1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(robot.runAction(robot.getDrive().actionBuilder(robot.getDrive().pose).lineToY(robot.getDrive().pose.position.y+24).lineToXLinearHeading(robot.getDrive().pose.position.x+24,robot.getDrive().pose.heading.plus(Math.toRadians(90))).build()));

        switch (mode) {
            case macro:
                controller2.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(robot.getClaw().adaptClaw().andThen(new WaitCommand(250)).andThen(robot.getSlides().preclip())
                                ,robot.getClaw().closeCommand());
                // automates clip process
                controller2.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                        .whenPressed(robot.getSlides().postclip()
                                .andThen(robot.getClaw().openCommand())
                                .andThen(new WaitCommand(300))
                                .andThen(robot.getSlides().down())
                                .andThen(robot.getClaw().adaptClaw())
                                .andThen(new WaitCommand(250))
                                .andThen(robot.getSlides().preclip()));
                // changes target color for intake
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .toggleWhenPressed(robot.getIntake().setBlue,robot.getIntake().setRed);
                // preps robot for high basket score
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(robot.getSlides().up()
                                .andThen(new WaitCommand(1000))
                                .andThen(robot.getIntakeArm().upCommand())
                                .andThen(robot.getIntake().ejectIntake()));
                // changes target color for intake
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .toggleWhenPressed(robot.getIntake().setBlue,robot.getIntake().setRed);
                break;
            case standard:
                // controls raising slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(robot.getSlides().up());
                // controls slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(robot.getSlides().postclip().andThen(new Claw.ClawCommand(robot.getClaw(),true)));
                // controls raising slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(robot.getClaw().closeCommand().andThen(new WaitCommand(500)).andThen(robot.getSlides().preclip()));
                // rotates intake arm up
                controller2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .toggleWhenPressed(robot.getIntakeArm().upCommand(),robot.getIntakeArm().downCommand());
                // toggles claw
                controller2.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(new Claw.ClawCommand(robot.getClaw(),true),new Claw.ClawCommand(robot.getClaw(),false));
                break;
        }
        // macros rotating arm up and extending intake
        controller2.getGamepadButton(GamepadKeys.Button.X)
                .toggleWhenPressed(robot.getIntake().reverseIntake(),robot.getIntake().offIntake());
        // puts intake all the way up
        controller2.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new Intake.storeIntake(robot.getIntake()));
        // turns intake on and off
        controller2.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenPressed(robot.getIntake().runIntake(),robot.getIntake().offIntake());
        // controls pusher
        controller2.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whenPressed(robot.getPusher().activateCommand())
                .whenReleased(robot.getPusher().offCommand());
        // extends and retracts intake
        controller2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(robot.getIntakeArm().inCommand(),robot.getIntakeArm().outCommand());
        // controls lowering slides
        controller2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new Intake.storeIntake(robot.getIntake())
                        .andThen(new WaitCommand(200))
                        .andThen(robot.getIntakeArm().downCommand())
                        .andThen(new WaitCommand(750))
                        .andThen(robot.getSlides().down()));
        controller2.getGamepadButton(GamepadKeys.Button.START)
                .toggleWhenPressed(()-> mode = Robot.activeMode.standard,()-> mode = Robot.activeMode.macro);
    }
    @Override
    public void run(){
        CommandScheduler.getInstance().run();
        // drive controls
        if (robot.getDriveState() == Robot.DriveState.manuel) {
            robot.getDrive().setDrivePowers(new PoseVelocity2d(
                    new Vector2d(-controller1.getLeftY() * speed, controller1.getLeftX() * speed)
                    , -controller1.getRightX()*speed
            ));
        }
        telemetry.addData("target color",robot.getIntake().target);
        telemetry.addData("color",robot.getIntake().getColor());
        telemetry.addData("target",robot.getSlides().getTarget());
        telemetry.addData("color",robot.getSlides().getPos());
        telemetry.addData("speed",robot.getSlides().controller.calculate(-robot.getSlides().getPos(),robot.getSlides().getTarget()));
        telemetry.addData("mode", mode);
        telemetry.update();
    }
}
