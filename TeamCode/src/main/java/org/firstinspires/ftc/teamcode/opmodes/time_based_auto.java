package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.block_arm;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous
public class time_based_auto extends LinearOpMode {

    public void runOpMode() throws InterruptedException{
        Robot bot = new Robot().init(hardwareMap);
        org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive drive = new MecanumDrive(hardwareMap);
        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(0,0,0))
                .lineToSplineHeading(new Pose2d(-30,-27,0))
                .addDisplacementMarker(() -> {bot.getBlockarm().set_grab(block_arm.Position.postclip);})
                .build();

        waitForStart();

        bot.getBlockarm().set_grab(block_arm.Position.preclip);
        drive.followTrajectory(traj1);
    }
}
