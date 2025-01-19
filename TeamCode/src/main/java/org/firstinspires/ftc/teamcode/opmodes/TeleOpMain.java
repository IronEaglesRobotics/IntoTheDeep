package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Robot;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
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
    }
    @Override
    public void run(){
        robot.runTeleOp(controller2,mode);
        controller2.getGamepadButton(GamepadKeys.Button.START)
                .toggleWhenPressed(()-> mode = Robot.activeMode.standard,()-> mode = Robot.activeMode.macro);
        CommandScheduler.getInstance().run();
        // drive controls
        controller1.readButtons();
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
