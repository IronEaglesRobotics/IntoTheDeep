package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.block_arm;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.intake.*;

@Autonomous(name =  "clip_auto",preselectTeleOp = "Basic: Iterative OpMode")
public class clip_auto extends LinearOpMode {
    Robot bot = new Robot().init(hardwareMap);
    MecanumDrive drive = new MecanumDrive(hardwareMap);
    Trajectory traj1;
    Trajectory traj3;

    Pose2d start = new Pose2d(0,0,0);
    Pose2d clip = new Pose2d(27,0,0);
    Pose2d pickup1 = new Pose2d(24,24,Math.toRadians(45));
    Pose2d drop1 = new Pose2d(24,12,Math.toRadians(135));
    Pose2d pickup2 = new Pose2d(24,24,Math.toRadians(45));
    Pose2d drop2 = new Pose2d(24,24,Math.toRadians(135));
    Pose2d pickup3 = new Pose2d(36,24,Math.toRadians(90));
    Pose2d drop3 = new Pose2d(36,24,Math.toRadians(180));
    Pose2d grab = new Pose2d(5,36,Math.toRadians(180));

    protected void clip_prep(){
        traj1 = drive.trajectoryBuilder(start)
                .lineToSplineHeading(clip)
                .build();
        bot.getBlockarm().set_grab(block_arm.Position.preclip);
        drive.followTrajectory(traj1);
    }
    protected void score1(){
        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .lineToSplineHeading(pickup1)
                .forward(5)
                .build();
        traj3 = drive.trajectoryBuilder(traj2.end())
                .lineToSplineHeading(drop1)
                .build();
        drive.followTrajectory(traj2);
        bot.getIntake().toggle_beatbar();
        bot.getIntake().toggle_lower();
        bot.getBlockarm().set_grab(block_arm.Position.pickup);
        drive.followTrajectory(traj3);
        bot.getIntake().setEject(0);
    }
    protected void score2(){
        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .lineToSplineHeading(pickup2)
                .forward(5)
                .build();
        Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                .lineToSplineHeading(drop2)
                .build();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        clip_prep();
        bot.getBlockarm().set_grab(block_arm.Position.postclip);
        wait(500);
        bot.getBlockarm().toggle_claw();
        score1();

        bot.update();
    }
}
