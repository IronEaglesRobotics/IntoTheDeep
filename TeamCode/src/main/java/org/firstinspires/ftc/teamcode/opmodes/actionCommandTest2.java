package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "action test",group = "TeleOp")
public class actionCommandTest2 extends OpMode {
    GamepadEx controller1;
    Robot robot;
    TrajectoryActionBuilder builder;
    Pose2d start = new Pose2d(0, 0, 0);
    Vector2d toBar = new Vector2d(12, 12);
    Pose2d toPickup = new Pose2d(12,0,Math.toRadians(0));
    Pose2d reset1 = new Pose2d(0,12,Math.toRadians(0));
    public void initialize() {
//        Actions.runBlocking(robot.getDrive().actionBuilder(new Pose2d(0,0,0)).setTangent(Math.toRadians(45)).lineToYLinearHeading(12,Math.toRadians(90)).build());
//        controller1.getGamepadButton(GamepadKeys.Button.A)
//                .whenPressed(robot.runAction((builder.splineToConstantHeading(toBar, 0).build())));
//        controller1.getGamepadButton(GamepadKeys.Button.B)
//                .whenPressed(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,0,0)).lineToX(12).build()));
//        controller1.getGamepadButton(GamepadKeys.Button.X)
//                .whenPressed(robot.runAction(robot.getDrive().actionBuilder(robot.getDrive().pose).splineToConstantHeading(start.position,0).build()));
//        controller1.getGamepadButton(GamepadKeys.Button.Y)
//                .whenPressed(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,0,0)).lineToY(12).build()));
    }

    @Override
    public void init() {
        controller1 = new GamepadEx(gamepad1);
        robot = new Robot().init(hardwareMap,new Pose2d(0,0,0));
        builder = robot.getDrive().actionBuilder(start);
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            Actions.runBlocking(robot.getDrive().actionBuilder(new Pose2d(0,0,0)).turn(Math.toRadians(360)).build());
        }
    }
}
