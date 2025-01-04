package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.message.redux.ReceiveGamepadState;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "action test",group = "TeleOp")
public class actionCommandTest extends CommandOpMode {
    GamepadEx controller1;
    Robot robot;
    TrajectoryActionBuilder builder;
    Pose2d start = new Pose2d(0, 0, 0);
    Vector2d toBar = new Vector2d(-26, -18);
    Pose2d toPickup = new Pose2d(-13,0,Math.toRadians(135));
    Pose2d reset1 = new Pose2d(-20,0,Math.toRadians(135));
    @Override
    public void initialize() {
        controller1 = new GamepadEx(gamepad1);
        robot = new Robot().init(hardwareMap);
        builder = robot.getDrive().actionBuilder(start);
        controller1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(robot.runAction((builder.splineToConstantHeading(toBar, -Math.PI / 2).build())));
        controller1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(robot.runAction(builder.splineToLinearHeading(toPickup,0).build()));
        controller1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(robot.runAction(builder.setTangent(Math.toRadians(135)).lineToYConstantHeading(5).build()));
        controller1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(robot.runAction(builder.turn(Math.toRadians(90)).build()));
    }
}
