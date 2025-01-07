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
    private double timer;
    private boolean foo = true;
    private boolean boo = true;

    final static Vector2d SPECIMEN = new Vector2d(-3,-32.5);
    final static Vector2d PICKUP_1 = new Vector2d(-43,-21);
    final static Pose2d BUCKET_1 = new Pose2d(-51,-51, Math.toRadians(225));
    final static Vector2d BUCKET_2 = new Vector2d(-62.5,-55.5);
    final static Pose2d PICKUP_2 = new Pose2d(-57,-45,Math.toRadians(279));
    final static Pose2d PICKUP_3 = new Pose2d(-66,-48,Math.toRadians(291));
    final static Pose2d PARK = new Pose2d(-10,-4,Math.toRadians(90));

    protected void specScore() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.splineToConstantHeading(SPECIMEN, Math.toRadians(90));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 4;
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
        timer = getRuntime() + 3.5;
        robot.specStep = 6;
        robot.mini = true;
        robot.intakeState = Robot.intakeStates.INTAKING;
        while ((this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) && timer > getRuntime()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void toBucket(double x, double y) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.lineToSplineHeading(BUCKET_1.plus(new Pose2d(x,y)));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.AUTO = true;
//        timer = getRuntime() + .4;
        while (this.robot.getDrive().isBusy() && robot.specStep != 1) {
//            if (timer < getRuntime() && timer> getRuntime()-0.1) {
//            }
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void scoreBucket(double x, double y) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
        builder.lineTo(BUCKET_2.plus(new Vector2d(x,y)),
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
        builder.addTemporalMarker(0.1,robot.getIntake()::down);
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        timer = getRuntime() + 3;
        robot.scoringState = Robot.scoringStates.BUCKETR;
        robot.bucketStep = 0;
//        sleep(1500);
        robot.intakeState = Robot.intakeStates.EXTENDED;
        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE && timer > getRuntime()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
        robot.intakeState = Robot.intakeStates.HASSAMPLE;

    }

    protected void toSampleThree() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
        builder.lineToLinearHeading(PICKUP_3);
        builder.lineToLinearHeading(PICKUP_3.plus(new Pose2d(1,5)),
                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(30)
        );
        builder.addTemporalMarker(0.1,robot.getIntake()::down);
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        timer = getRuntime() + 3.5;
//        sleep(1500);
        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE && timer+1 > getRuntime()) {
            if(timer < getRuntime()+2.5 && timer > getRuntime() && foo) {
                foo = false;
                robot.scoringState = Robot.scoringStates.BUCKETR;
                robot.bucketStep = 0;
            }
            if(timer-2 < getRuntime() && boo) {
                boo = false;
                robot.intakeState = Robot.intakeStates.EXTENDED;
            }
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
        robot.intakeState = Robot.intakeStates.HASSAMPLE;
    }

    protected void park() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.setReversed(true);
        builder.splineToLinearHeading(PARK, Math.toRadians(0));
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
        robot.arm.intakeSpecimen();

        while (!this.isStarted()) {
            robot.arm.intakeSpecimen();
            this.telemetry.update();
        }
            specScore();
            getSample();
            sleep(200);

            robot.mini =false;
            toBucket(0,0);
            robot.AUTO = false;
            sleep(500);
            scoreBucket(0,0);
            robot.claw.open();
            sleep(200);

            toSampleTwo();
//            sleep(200);
            toBucket(1,1);
            robot.AUTO = false;
            sleep(500);
            scoreBucket(1,1);
            robot.claw.open();
            sleep(200);

            toSampleThree();
//            sleep(200);
            toBucket(2,2);
            robot.AUTO = false;
            sleep(500);
            scoreBucket(2,2);
            robot.claw.open();
            robot.getHang().setPosition(6900);
            sleep(200);

            park();



//chigazzz
    }
}
