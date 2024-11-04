package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Config
@Autonomous(name = "bucketAuto")
public class bucketAuto extends LinearOpMode {
    protected Pose2d initialPosition;
    private Robot robot;

    final static Pose2d RIGHT_PRELOAD = new Pose2d(-42, -40, Math.toRadians(220));
    final static Pose2d CENTER_PRELOAD = new Pose2d(-50, -35.5, Math.toRadians(240));
    final static Pose2d LEFT_PRELOAD = new Pose2d(-68, -21.5, Math.toRadians(180));
    //Board Scores
    final static Pose2d RIGHT_BOARD = new Pose2d(-72.75, -24.5, Math.toRadians(180));
    final static Pose2d CENTER_BOARD = new Pose2d(-72, -36, Math.toRadians(178));
    final static Pose2d LEFT_BOARD = new Pose2d(-73, -41, Math.toRadians(200));

    final static Pose2d BACK_OFF =  new Pose2d(-63, -64,Math.toRadians(180));
    final static Pose2d PARK = new Pose2d(-80, -63, Math.toRadians(180));

    @Override
    public void runOpMode() throws InterruptedException {

        while (!this.isStarted()) {

            this.telemetry.update();
        }
    }
}
