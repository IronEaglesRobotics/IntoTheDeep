package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Basket_auto",preselectTeleOp = "Basic: Iterative OpMode")
public class basket_auto extends LinearOpMode {
    Pose2d start = new Pose2d(0,0,Math.toRadians(180));
    Pose2d clip = new Pose2d(27,0,Math.toRadians(180));
    Pose2d pickup1 = new Pose2d(24,-24,Math.toRadians(-45));
    Pose2d drop1 = new Pose2d(5,-53,Math.toRadians(45));
    Pose2d pickup2 = new Pose2d(24,-24,Math.toRadians(-45));
    Pose2d drop2 = new Pose2d(5,-53,Math.toRadians(45));
    Pose2d pickup3 = new Pose2d(36,-24,Math.toRadians(-90));
    Pose2d drop3 = new Pose2d(5,-53,Math.toRadians(45));
    Pose2d park = new Pose2d(36,24,Math.toRadians(270));
    @Override
    public void runOpMode() throws InterruptedException {

    }
}
