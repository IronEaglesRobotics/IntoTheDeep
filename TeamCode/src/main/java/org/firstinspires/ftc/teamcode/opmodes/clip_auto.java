package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.block_arm;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;

@Autonomous(name =  "clip_auto",preselectTeleOp = "Basic: Iterative OpMode")
public class clip_auto extends LinearOpMode {
    Robot bot = new Robot().init(hardwareMap);
    MecanumDrive drive = new MecanumDrive(hardwareMap);
    boolean isFirst = true;
    Trajectory traj1;
    Trajectory traj2;
    Trajectory traj3;

    Pose2d start = new Pose2d(0,0,0);
    Pose2d clip = new Pose2d(0,27,0);
    Pose2d reset = new Pose2d(24,24,Math.toRadians(180));
    Pose2d change = new Pose2d(10,0,0);
    Pose2d grab = new Pose2d(24,5,Math.toRadians(180));


    protected void clip(Pose2d start) throws InterruptedException{
        traj1 = drive.trajectoryBuilder(start)
                .lineToSplineHeading(clip)
                .build();
        bot.getBlockarm().set_grab(block_arm.Position.preclip);
        drive.followTrajectory(traj1);
        bot.getBlockarm().set_grab(block_arm.Position.postclip);
        wait(500);
        bot.getBlockarm().toggle_claw();
        bot.update();
        wait(100);
    }
    protected void push(){
        traj2 = drive.trajectoryBuilder(isFirst ? traj1.end() : traj2.end()) //makes sure it has right start pos
                .lineToSplineHeading(reset)
                .back(24)
                .strafeLeft(10)
                .forward(36)
                .build();
        drive.followTrajectory(traj2);
        reset.plus(change);
        isFirst = false;
        bot.update();
    }
    protected void Clip2() throws InterruptedException{
        traj3 = drive.trajectoryBuilder(traj2.end())
                .lineToSplineHeading(grab)
                .build();
        drive.followTrajectory(traj3);
        bot.getBlockarm().toggle_claw();
        wait(20);
        clip(traj3.end());
        bot.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        clip(start);
        wait(20);
        push();
        wait(20);
        push();
        wait(20);
        push();
        wait(20);
        bot.update();
    }
}
