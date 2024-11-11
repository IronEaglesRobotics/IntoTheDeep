package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Slides;
import org.firstinspires.ftc.teamcode.hardware.block_arm;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous
public class time_based_auto extends LinearOpMode {

    public void runOpMode() throws InterruptedException{
        Robot bot = new Robot().init(hardwareMap);
        org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive drive = new MecanumDrive(hardwareMap);
        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d(0,0,0))
                .lineToSplineHeading(new Pose2d(-25,-30,0))
                .addDisplacementMarker(() -> {bot.getBlockarm().set_grab(block_arm.Position.wall);})
                .addDisplacementMarker(() -> {bot.update(System.currentTimeMillis());})
                .addDisplacementMarker(() -> {bot.getBlockarm().toggle_claw();})
                .build();
        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .lineToSplineHeading(new Pose2d(0,25,0))
                .build();

        waitForStart();

        bot.getBlockarm().set_grab(block_arm.Position.postclip);
        bot.update(System.currentTimeMillis());
        drive.followTrajectory(traj1);
        bot.update(System.currentTimeMillis());
        bot.getBlockarm().slides.setTarget(Slides.Position.DOWN);
        drive.followTrajectory(traj2);
        bot.update(System.currentTimeMillis());
    }
}
