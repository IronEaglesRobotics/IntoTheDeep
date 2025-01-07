package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "Move forward 1 tile", group = "Test")
public class autotest extends LinearOpMode {

    Action tab1;
    boolean actionCompleted = false;

    @Override
    public void runOpMode() throws InterruptedException {
        while(!isStarted()) {
            Robot rob = new Robot(hardwareMap);
            GamepadEx controller = new GamepadEx(gamepad1);

            Pose2d start = new Pose2d(-63, -20, Math.toRadians(90));
            MecanumDrive mec = new MecanumDrive(hardwareMap, start);
            telemetry.addData("yeah","this is pissing me off");
            telemetry.update();
            tab1 = mec.actionBuilder(start)
                    .lineToY(10)
                    .build();

            telemetry.addData("Status:", "init complete");
            telemetry.update();
        }


        telemetry.addData("Status:", "Starting loop");
        telemetry.update();
//        if (true){
        Actions.runBlocking(tab1);
        telemetry.addData("Status:", "asdasasdasdsa loop");
        telemetry.update();
        actionCompleted = true;
        telemetry.addData("Status:", "run complete");
//        }
        telemetry.update();

//    @
    }


//    public void start() {
//        actionCompleted = false;
//    }
//    public void stop() {
//
//    }
}