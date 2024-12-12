package org.firstinspires.ftc.teamcode.roadrunner.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TimeTurn;
import com.acmerobotics.roadrunner.TurnActionFactory;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.Drawing;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.Collections;
import java.util.Set;

@TeleOp(name = "Tommy Is A Ninny")
public class turntest extends OpMode {
    Robot robot;
    GamepadEx gamepadEx;

    @Override
    public void init() {
        this.telemetry = FtcDashboard.getInstance().getTelemetry();
        robot = new Robot().init(hardwareMap);
        this.robot.getDrive().pinpoint.setYawScalar(1f);
        this.gamepadEx = new GamepadEx(this.gamepad1);
    }

    @Override
    public void loop() {
        this.gamepadEx.readButtons();
        if (this.gamepadEx.wasJustPressed(GamepadKeys.Button.A)) {
            Action turnAction = this.robot.getDrive().actionBuilder(this.robot.getDrive().pose).turn(Math.toRadians(90)).build();

            Actions.runBlocking(turnAction);
        }

        if (this.gamepadEx.wasJustPressed(GamepadKeys.Button.B)) {
            Action turnAction = this.robot.getDrive().actionBuilder(new Pose2d(0,0,0)).lineToXLinearHeading(12, Math.toRadians(90)).build();

            Actions.runBlocking(turnAction);
        }

        robot.getDrive().updatePoseEstimate();
        this.telemetry.addData("yawscalar", this.robot.getDrive().pinpoint.getYawScalar());
        this.telemetry.addData("heading", this.robot.getDrive().pose.heading.toDouble());
        this.telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), this.robot.getDrive().pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}
