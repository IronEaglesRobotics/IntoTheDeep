package org.firstinspires.ftc.teamcode.opmodes;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "specAuto")
public class MacroTest extends LinearOpMode {
    private Robot robot;
    GamepadEx controller1;
    private double timer;




    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        controller1 = new GamepadEx(gamepad1);

        while (!this.isStarted()) {
            this.telemetry.update();
        }

        new InstantAction(() -> robot.intake.down());


    }
}
