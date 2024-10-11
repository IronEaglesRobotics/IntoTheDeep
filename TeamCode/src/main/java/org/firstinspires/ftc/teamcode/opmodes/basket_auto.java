package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.block_arm;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;

@Autonomous(name = "Basket_auto",preselectTeleOp = "Basic: Iterative OpMode")
public class basket_auto extends LinearOpMode {
    Robot bot = new Robot().init(hardwareMap);
    MecanumDrive drive = new MecanumDrive(hardwareMap);
    GamepadEx controller = new GamepadEx(gamepad1);
    Trajectory traj1;
    Trajectory traj2;
    Trajectory traj3;
    Pose2d start = new Pose2d(0,0,Math.toRadians(180));
    Pose2d clip = new Pose2d(27,0,Math.toRadians(180));
    Pose2d pickup1 = new Pose2d(24,-24,Math.toRadians(-45));
    Pose2d drop = new Pose2d(5,-53,Math.toRadians(45));
    Pose2d pickup2 = new Pose2d(24,-36,Math.toRadians(-45));
    Pose2d pickup3 = new Pose2d(36,-24,Math.toRadians(-90));
    Pose2d park = new Pose2d(36,24,Math.toRadians(270));
    protected void clip() throws InterruptedException{
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
    protected void pickup(double cur_time){
        bot.getIntake().toggle_lower();
        traj2 = drive.trajectoryBuilder(traj1.end())
                .lineToSplineHeading(pickup1)
                .back(5)
                .build();
        traj3 = drive.trajectoryBuilder(traj3.end())
                .lineToSplineHeading(drop)
                .build();
        drive.followTrajectory(traj2);
        bot.getIntake().toggle_beatbar();
        bot.Block_Macro(controller,cur_time, Robot.Block_macro_state.grab);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        double time = System.currentTimeMillis();
        clip();
        wait(20);
        pickup(time);
    }
}
