package org.firstinspires.ftc.teamcode.opmodes;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.fasterxml.jackson.databind.deser.std.JsonLocationInstantiator;
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

    final static Vector2d SPECIMEN = new Vector2d(2,-32);
    final static Pose2d PICKUP_1 = new Pose2d(36.5, -54,Math.toRadians(265));
    final static Pose2d SPECIMEN2 = new Pose2d(5,-32,Math.toRadians(90));

    final static Vector2d PLOW1 = new Vector2d(40,-32);
    final static Vector2d PLOW2 = new Vector2d(44,-6);
    final static Vector2d PLOW3 = new Vector2d(50,-45);

    final static Vector2d PLOW4 = new Vector2d(54,-6);
    final static Vector2d PLOW5 = new Vector2d(54,-58);



    final static Vector2d PARK = new Vector2d(30,-8);


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

    protected void getSpec() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        builder.turn(Math.toRadians(-120));
        builder.splineToSplineHeading(PICKUP_1, Math.toRadians(270));
        builder.lineToConstantHeading(PICKUP_1.vec().plus(new Vector2d(0,-4)),
                MecanumDrive.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(20)
        );
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 0;
        while ((this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE)) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }

    protected void specScoreAgain() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTO=true;
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

    protected void specScoreAgain2() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

        robot.AUTO=true;
        builder.setReversed(true);
        builder.waitSeconds(.5);
        builder.splineToLinearHeading(SPECIMEN2.plus(new Pose2d(-4,-2.5)), Math.toRadians(90));
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

        builder.turn(Math.toRadians(-180));
        builder.setTangent(0);

        builder.splineToConstantHeading(PLOW1, Math.toRadians(90),
                MecanumDrive.getVelocityConstraint(80, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(60)
        );
        builder.setTangent(90);
        builder.splineToConstantHeading(PLOW2, Math.toRadians(0),
                MecanumDrive.getVelocityConstraint(80, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(60)
        );
//        builder.setTangent(0);
        builder.splineToConstantHeading(PLOW3, Math.toRadians(270),
                MecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(80)
        );

        builder.waitSeconds(0.05);

        builder.setTangent(90);
        builder.splineToConstantHeading(PLOW4, Math.toRadians(0),
                MecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(80)
        );
        builder.setTangent(0);
        builder.splineToConstantHeading(PLOW5, Math.toRadians(270),
                MecanumDrive.getVelocityConstraint(40, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                MecanumDrive.getAccelerationConstraint(40)
        );

        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.specStep = 0;
        while ((this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE)) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }


    protected void park() {
        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();

//        builder.turn(Math.toRadians(-120));
        builder.splineToSplineHeading(PICKUP_1, Math.toRadians(270));
//        builder.lineToConstantHeading(PICKUP_1.vec().plus(new Vector2d(0,-4)),
//                MecanumDrive.getVelocityConstraint(20, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                MecanumDrive.getAccelerationConstraint(20)
//        );
        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
        robot.scoringState = Robot.scoringStates.SPECIMENR;
        timer = getRuntime() + 5;
        robot.specStep = 0;
        while ((this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE)) {
            this.robot.update();
            this.robot.scoringMacro(controller1, this.getRuntime(), true);
            this.robot.intakeMacro(controller1,getRuntime(), true);
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot().init(hardwareMap);
        initialPosition = new Pose2d(34.25, -60, Math.toRadians(90));
        this.robot.getDrive().setPoseEstimate(initialPosition);
        controller1 = new GamepadEx(gamepad1);

        while (!this.isStarted()) {

            this.telemetry.update();
        }
        specScore();
        getSpec();
////        sleep(200);
//        specScoreAgain();
//        robot.AUTO=false;
//        plow();
//        specScoreAgain2();
//        robot.AUTO=false;
        park();
        robot.slides.slidesTo(0);
        while (timer > getRuntime()) {
            robot.update();
            robot.slides.slidesTo(0);
        }

        sleep(1000);

//        sleep(200);
//
//        toBucket();
//        robot.AUTO = false;
//        sleep(500);
//        scoreBucket();
//        robot.claw.open();
//        sleep(200);
//
//        toSampleTwo();
////            sleep(200);
//        toBucket();
//        robot.AUTO = false;
//        sleep(500);
//        scoreBucket();
//        robot.claw.open();
//        sleep(200);
//
//        toSampleThree();
////            sleep(200);
//        toBucket();
//        robot.AUTO = false;
//        sleep(500);
//        scoreBucket();
//        robot.claw.open();
//        sleep(200);
//
//        park();




    }
}
