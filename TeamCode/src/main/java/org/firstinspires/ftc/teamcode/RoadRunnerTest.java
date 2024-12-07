package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RoadRunnerTest extends LinearOpMode {

    @Override
    public void runOpMode() {
Pose2d startPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
MechanicumWheels drive = new MechanicumWheels(hardwareMap, startPose);

waitForStart();
while (opModeIsActive()) {
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;

    Pose2d newPose = new Pose2d(x, y, rx);

    drive.setDrivePower(newPose);
}

}
    }
