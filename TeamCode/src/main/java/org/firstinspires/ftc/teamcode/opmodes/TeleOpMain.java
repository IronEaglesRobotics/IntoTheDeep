package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.Drawing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
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
    private double speed = 1;
    private Robot.activeMode mode = Robot.activeMode.macro;
    @Override
    public void initialize() {
        boolean first = true;
        if (first){
            CommandScheduler.getInstance().reset();
            first = false;
            Robot.driveState = Robot.DriveState.manuel;
        }
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot = new Robot().init(hardwareMap,new Pose2d(0,0,0));
        controller1.readButtons();
        controller2.readButtons();

        controller1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(robot.setPose(new Pose2d(0,0,0)));
        controller1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(robot.toClip());
//        controller1.getGamepadButton(GamepadKeys.Button.B)
//                .whenPressed(robot.toObservation());
//        controller1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
//                .whenPressed(robot.toBasket());
        controller1.getGamepadButton(GamepadKeys.Button.Y)
                .toggleWhenPressed(()->speed = 1,()->speed = .5);
        controller1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()-> Robot.driveState = Robot.DriveState.manuel);

        switch (mode) {
            case macro:
                controller2.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(robot.getClaw().adaptClaw().andThen(new WaitCommand(250)).andThen(robot.getSlides().preclip())
                                ,robot.getClaw().closeCommand());
                // automates clip process
                controller2.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                        .whenPressed(robot.getSlides().postclip()
                                .andThen(new WaitCommand(300))
                                .andThen(robot.getClaw().openCommand())
                                .andThen(robot.getSlides().down())
                                .andThen(robot.getClaw().adaptClaw())
                                .andThen(new WaitCommand(250))
                                .andThen(robot.getSlides().preclip()));
                // changes target color for intake
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .toggleWhenPressed(robot.getIntake().setBlue,robot.getIntake().setRed);
                // preps robot for high basket score
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(robot.getIntake().storeIntake()
                                .andThen(robot.getSlides().up())
                                .andThen(new WaitCommand(1000))
                                .andThen(robot.getIntakeArm().upCommand())
                                .andThen(robot.getIntake().ejectIntake()));
                break;
            case standard:
                // controls raising slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(robot.getSlides().up());
                // controls slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(robot.getSlides().postclip().andThen(new Claw.ClawCommand(robot.getClaw(),true)));
                // safely move to high basket position
                controller2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(robot.getSlides().up()
                                .andThen(new WaitCommand(1000))
                                .andThen(robot.getIntakeArm().upCommand())
                                .andThen(robot.getIntake().ejectIntake()));
                // controls raising slides
                controller2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(robot.getClaw().closeCommand().andThen(new WaitCommand(500)).andThen(robot.getSlides().preclip()));
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
        controller1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(robot.getPusher().activateCommand())
                .whenReleased(robot.getPusher().offCommand());
        // extends and retracts intake
        controller2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
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
        robot.getDrive().updatePoseEstimate();
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
        telemetry.addData("mode", robot.getDriveState());
        telemetry.addData("pose",robot.getDrive().pose.toString());
        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), robot.getDrive().pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        robot.getDrive().updatePoseEstimate();
        telemetry.update();
    }
}
