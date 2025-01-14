package org.firstinspires.ftc.teamcode.opmodes;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

@Config
@Autonomous(name = "specAuto")
public class specAuto extends LinearOpMode {
    protected Pose2d initialPosition;
    private Robot robot;
    GamepadEx controller1;
    private double timer;

    final static Vector2d SPECIMEN = new Vector2d(1, -25);
    final static Pose2d GETSPEC = new Pose2d(42, -60, Math.toRadians(270));
    final static Pose2d SPECIMEN2 = new Pose2d(5, -32, Math.toRadians(90));


    final static Pose2d PLOW1 = new Pose2d(24, -40, Math.toRadians(225));
    final static Pose2d PLOW2 = new Pose2d(22, -50, Math.toRadians(140));

//    final static Pose2d PARK = new Pose2d(44, -60, Math.toRadians(90));

    protected void specScore(int tangent, double x) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.setReversed(true);
        builder.setTangent(tangent);
        builder.splineToConstantHeading(SPECIMEN.plus(new Vector2d(x,0)), Math.toRadians(90));

        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 4;
        timer = getRuntime() + 3;
        robot.scoringState = Robot.scoringStates.SPECIMENGRAB;
        boolean initialized = false;
        while (this.robot.getDrive().isBusy() || timer > getRuntime()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);

            if (!this.robot.getDrive().isBusy() && !initialized) {
                robot.AUTO = true;
                robot.specStep = 6;
                initialized = true;
                timer = getRuntime() + .5;
            }
            telemetry.addData("state", robot.scoringState);
            telemetry.addData("step", robot.specStep);
            telemetry.update();
        }
    }

    protected void getSpecFirst(double x, double y, int tangent) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTOSPEC = true;
        robot.specStep = 0;
        robot.scoringState = Robot.scoringStates.SPECIMENR;
        builder.lineToConstantHeading(SPECIMEN.plus(new Vector2d(0,-10)));
//        builder.setTangent(270);
        builder.splineToConstantHeading(GETSPEC.vec().plus(new Vector2d(2,0)), Math.toRadians(270));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        while (this.robot.getDrive().isBusy()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1, getRuntime(), true);
            telemetry.addData("state", robot.scoringState);
            telemetry.addData("step", robot.specStep);
            telemetry.update();

        }
    }

    protected void getSpec(double x, double y, int tangent) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTOSPEC = true;
        robot.specStep = 0;
        robot.scoringState = Robot.scoringStates.SPECIMENR;
        builder.setTangent(tangent);
        builder.splineToLinearHeading(GETSPEC.plus(new Pose2d(x,y)), Math.toRadians(270));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        while (this.robot.getDrive().isBusy()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1, getRuntime(), true);
            telemetry.addData("state", robot.scoringState);
            telemetry.addData("step", robot.specStep);
            telemetry.update();

        }
    }

    protected void specScoreAgain() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTO = true;
        builder.setReversed(true);
        builder.waitSeconds(.5);
        builder.splineToLinearHeading(SPECIMEN2, Math.toRadians(90));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//        robot.specStep = 4;
//        robot.scoringState = Robot.scoringStates.SPECIMENGRAB;
        while (this.robot.getDrive().isBusy()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
        }
    }

    protected void plow() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTOSPEC = true;
        robot.specStep = 0;
        robot.scoringState = Robot.scoringStates.SPECIMENR;

        builder.setReversed(true);
        builder.setTangent(270);

        builder.splineToSplineHeading(PLOW1, Math.toRadians(70));
        builder.splineToConstantHeading(PLOW1.vec().plus(new Vector2d(12,5)), Math.toRadians(70));
        builder.setReversed(false);
        builder.lineToLinearHeading(PLOW2.plus(new Pose2d(2,0)),
                MecanumDrive.getVelocityConstraint(120, 5, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(110)
        );
        builder.lineToLinearHeading(PLOW1.plus(new Pose2d(16,8)),
                MecanumDrive.getVelocityConstraint(120, 5, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(110)
        );
        builder.lineToLinearHeading(PLOW2.plus(new Pose2d(12,-2)),
                MecanumDrive.getVelocityConstraint(120, 5, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(110)
        );




        timer = getRuntime() + 3;
        robot.bucketStep = 0;
        boolean initialized = false;

        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 0;
        while ((this.robot.getDrive().isBusy() || timer + 4.9 > getRuntime())) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1, getRuntime(), true);
            if (getRuntime() > timer - 1 && !initialized) {
                robot.intakeState = Robot.intakeStates.EXTENDED;
                initialized = true;
            }

            if (getRuntime() < timer + 3.4 && getRuntime() > timer + 2.6){
                robot.intakeState = Robot.intakeStates.EXTENDED;
            }
            if (getRuntime() > timer + 4.9){
                robot.intakeState = Robot.intakeStates.HASSAMPLE;
            }


        }
    }

    protected void park(double x, double y, int tangent) {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.END = true;
        robot.specStep = 0;
        robot.scoringState = Robot.scoringStates.SPECIMENR;
        builder.lineToConstantHeading(SPECIMEN.plus(new Vector2d(0,-10)));
//        builder.setTangent(270);
//        builder.splineToLinearHeading(PARK, Math.toRadians(270));
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());

        while (this.robot.getDrive().isBusy()) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1, getRuntime(), true);
            telemetry.addData("state", robot.scoringState);
            telemetry.addData("step", robot.specStep);
            telemetry.update();

        }
    }






    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        initialPosition = new Pose2d(12, -60, Math.toRadians(270));
        this.robot.getDrive().setPoseEstimate(initialPosition);
        controller1 = new GamepadEx(gamepad1);

        while (!this.isStarted()) {
            this.telemetry.update();
        }

        specScore(90, -2);
        robot.AUTO = false;

        getSpecFirst(0,0,240);
        robot.claw.open();
//        sleep(100);
        robot.claw.close();
//        sleep(100);
        specScore(180,-1.5);
        robot.AUTO = false;

        plow();
//        sleep(100);

        getSpec(3,-4,180);
        robot.claw.open();
//        sleep(100);
        robot.claw.close();
//        sleep(100);
        specScore(180,-1);
        robot.AUTO = false;


        getSpec(3,-4,180);
        robot.claw.open();
//        sleep(100);
        robot.claw.close();
//        sleep(100);
        specScore(180,-.5);
        robot.AUTO = false;

        park(0,0,0);
        while (robot.getSlides().getPosition() > 10) {
            robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1, getRuntime(), true);
        }
        requestOpModeStop();


//        specScoreAgain();
//        robot.AUTO = false;
//        plow();
//        specScoreAgain2();
//        robot.AUTO = false;
//        park();
//        robot.slides.slidesTo(0);
//
//        sleep(1000);

    }
}
