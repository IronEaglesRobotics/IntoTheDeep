package org.firstinspires.ftc.teamcode.opmodes;

import android.media.AudioRouting;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.geometry.euclidean.oned.OrientedPoint;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

@Config
@Autonomous(name = "bucketAuto")
public class bucketAuto extends LinearOpMode {
    protected Pose2d initialPosition;
    private Robot robot;
    GamepadEx controller1;

    final static Vector2d SPECIMEN = new Vector2d(-3,-32.5);
    final static Vector2d PICKUP_1 = new Vector2d(-46,-24);
    final static Pose2d BUCKET_1 = new Pose2d(-51,-51, Math.toRadians(225));
    final static Vector2d BUCKET_2 = new Vector2d(-62,-54);
    final static Pose2d PICKUP_2 = new Pose2d(-60,-42,Math.toRadians(273));
    final static Pose2d PICKUP_3 = new Pose2d(-68,-42,Math.toRadians(296));
    final static Vector2d PARK = new Vector2d(-30,-8);


    protected void specScore() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.splineToConstantHeading(SPECIMEN, Math.toRadians(90));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        robot.scoringState = Robot.scoringStates.SPECIMENGRAB;
        while (this.robot.getDrive().isBusy()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
        }
    }

    protected void getSample() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.setReversed(true);
        builder.splineTo(PICKUP_1, Math.toRadians(90));
        builder.setReversed(false);
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 6;
        robot.intakeState = Robot.intakeStates.INTAKING;
        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void toBucket() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.lineToSplineHeading(BUCKET_1);
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.AUTO = true;
        while (this.robot.getDrive().isBusy() && robot.specStep != 1) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void scoreBucket() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
        builder.lineTo(BUCKET_2,
                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(30)
        );
        this.robot.getDrive().followTrajectorySequence(builder.build());
    }

    protected void toSampleTwo() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.lineToLinearHeading(PICKUP_2);
        builder.lineToLinearHeading(PICKUP_2.plus(new Pose2d(0,5)),
                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(30)
        );
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.scoringState = Robot.scoringStates.BUCKETR;
        robot.bucketStep = 0;
        robot.intakeState = Robot.intakeStates.EXTENDED;
        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void toSampleThree() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.lineToLinearHeading(PICKUP_3);
        builder.lineToLinearHeading(PICKUP_3.plus(new Pose2d(1,5)),
                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(30)
        );
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        robot.scoringState = Robot.scoringStates.BUCKETR;
        robot.bucketStep = 0;
        robot.intakeState = Robot.intakeStates.EXTENDED;

        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void park() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.setReversed(true);
        builder.splineTo(PARK, Math.toRadians(0));
        builder.setReversed(false);
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        robot.scoringState = Robot.scoringStates.BUCKETR;
        robot.bucketStep = 0;

        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        initialPosition = new Pose2d(-34.25, -60, Math.toRadians(90));
        this.robot.getDrive().setPoseEstimate(initialPosition);
        controller1 = new GamepadEx(gamepad1);

        while (!this.isStarted()) {

            this.telemetry.update();
        }
            specScore();
            getSample();
            sleep(200);

            toBucket();
            robot.AUTO = false;
            sleep(500);
            scoreBucket();
            robot.claw.open();
            sleep(200);

            toSampleTwo();
            sleep(200);
            toBucket();
            robot.AUTO = false;
            sleep(500);
            scoreBucket();
            robot.claw.open();
            sleep(200);

            toSampleThree();
            sleep(200);
            toBucket();
            robot.AUTO = false;
            sleep(500);
            scoreBucket();
            robot.claw.open();
            sleep(200);

            park();




    }
}
