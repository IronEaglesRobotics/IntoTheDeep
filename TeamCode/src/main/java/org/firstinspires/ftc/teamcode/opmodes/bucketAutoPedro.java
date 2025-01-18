package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Robot.scoringStates;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.LConstants;


@Config
@Autonomous(name = "BucketAutoPedro")
public class bucketAutoPedro extends OpMode {
    private Robot robot;
    private Telemetry telemetryA;
    GamepadEx controller1;
    private double timer;
    private boolean foo = false;
    private boolean boo = true;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    int step = 0;

    private final Pose initialPosition = new Pose(36, 12, Math.toRadians(180));
//    private final Pose control1 = new Pose(30, 24,P);
    private final Pose bucket = new Pose(16, 22, Math.toRadians(-135));
    private final Pose get1 = new Pose(20,48,Math.toRadians(-100));
    private final Pose get2 = new Pose(12,48,Math.toRadians(-90));
    private final Pose get3 = new Pose(11,48,Math.toRadians(-45));
    private final Pose getSubControl = new Pose(24,68, Point.CARTESIAN);
    private final Pose getSub = new Pose(48,68, Math.toRadians(180));

    private Path scorePreload;
    private Path getSample1;
    private Path getSample2;
    private Path getSample3;
    private Path scoreBucket1;
    private Path scoreBucket2;
    private Path scoreBucket3;
    private Path getFromSub;

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(
                new Point(initialPosition),
//                new Point(control1),
                new Point(bucket)
        ));
        scorePreload.setLinearHeadingInterpolation(initialPosition.getHeading(),bucket.getHeading());

        getSample1 = new Path(new BezierLine(
                new Point(bucket),
                new Point(get1)
        ));
        getSample1.setLinearHeadingInterpolation(bucket.getHeading(),get1.getHeading());

        getSample2 = new Path(new BezierLine(
                new Point(bucket),
                new Point(get2)
        ));
        getSample2.setLinearHeadingInterpolation(bucket.getHeading(),get2.getHeading());

        getSample3 = new Path(new BezierLine(
                new Point(bucket),
                new Point(get3)
        ));
        getSample3.setLinearHeadingInterpolation(bucket.getHeading(),get3.getHeading());

        getFromSub = new Path(new BezierCurve(
                new Point(bucket),
                new Point(getSubControl),
                new Point(getSub)
        ));
        getFromSub.setTangentHeadingInterpolation();
        getFromSub.setReversed(true);

        scoreBucket1 = new Path(new BezierCurve(
                new Point(get1),
                new Point(bucket)
        ));
        scoreBucket1.setLinearHeadingInterpolation(get1.getHeading(),bucket.getHeading());

        scoreBucket2 = new Path(new BezierCurve(
                new Point(get2),
                new Point(bucket)
        ));
        scoreBucket2.setLinearHeadingInterpolation(get2.getHeading(),bucket.getHeading());

        scoreBucket3 = new Path(new BezierCurve(
                new Point(get3),
                new Point(bucket)
        ));
        scoreBucket3.setLinearHeadingInterpolation(get3.getHeading(),bucket.getHeading());




//        getSample1.setReversed(true);


    }

    public void pathUpdate(){
        switch(pathState){
            case 0:
                robot.scoringState = scoringStates.BUCKET;
                robot.AUTO =true;
                if(robot.getSlides().getPosition() > 750) {
                    follower.followPath(scorePreload);
//                }

//                if(!follower.isBusy()){
                    setPathState(1);
                    timer = getRuntime();
                }
                break;
            case 1:
                if (!follower.isBusy()) {
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + 1.75){
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(2); // End the autonomous routine
//                        robot.claw.close();
                    }
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    if (!foo){
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;
                    follower.followPath(getSample1);
//                    follower.breakFollowing();
                    setPathState(3); // End the autonomous routine
                    foo = false;
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 3:
                switch(step){
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition()<100) {
                            if (!foo){
                                robot.bucketStep = 0;
                                foo = false;
                            }
                            robot.AUTO = true;
                            step ++;
                        }
                        break;
                    case 1:
                        robot.claw.close();
                        robot.scoringState = scoringStates.BUCKET;
                            if(robot.slides.getPosition()>1250) {
                                follower.followPath(scoreBucket1);
                                setPathState(4); // End the autonomous routine
                                timer = getRuntime();
                            }
                            break;
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + 2){
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(5); // End the autonomous routine
                        robot.claw.close();
                    }
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    if (!foo){
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    follower.followPath(getSample2);
                    if(getRuntime() > timer + .5) {
                        robot.scoringState = scoringStates.BUCKETR;
//                    follower.breakFollowing();
                        setPathState(6); // End the autonomous routine
                        foo = false;
                        step = 0;
                    }
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 6:
                switch(step){
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition()<100) {
                            if (!foo){
                                robot.bucketStep = 0;
                                foo = true;
                                timer = getRuntime();
                            }
                            if (getRuntime() > timer + .3) {
                                robot.AUTO = true;
                                step++;
                            }
                        }
                        break;
                    case 1:
                        robot.claw.close();
                        robot.scoringState = scoringStates.BUCKET;
                        if(robot.slides.getPosition()>1250) {
                            follower.followPath(scoreBucket2);
                            setPathState(7); // End the autonomous routine
                            timer = getRuntime();
                            foo = false;
                        }
                        break;
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + 2){
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(8); // End the autonomous routine
                        robot.claw.close();
                        timer = getRuntime();
                    }
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    robot.claw.close();
                    follower.followPath(getSample3);

//                    if(getRuntime() > timer + .125){
                        if (!foo){
                            robot.bucketStep = 0;
                            foo = true;
                        }
                        robot.scoringState = scoringStates.BUCKETR;
                        setPathState(9); // End the autonomous routine
                        foo = false;
                        step = 0;
//                    }
//                    follower.breakFollowing();
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 9:
                switch(step){
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition()<100) {
                            if (!foo){
                                robot.bucketStep = 0;
                                foo = true;
                                timer = getRuntime();
                            }
                            if (getRuntime() > timer + .3) {
                                robot.AUTO = true;
                                step++;
                            }
                        }
                        break;
                    case 1:
//                        robot.claw.close();
                        robot.scoringState = scoringStates.BUCKET;
                        if(robot.slides.getPosition()>1250) {
                            follower.followPath(scoreBucket3);
                            timer = getRuntime();
                            foo = false;
                            setPathState(10); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 10:
                if (!follower.isBusy()) {
                    robot.claw.open();
                    robot.AUTO = false;
                    if (getRuntime() > timer + 2.25){
//                        robot.mini = true;
//                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(11); // End the autonomous routine
//                        robot.claw.close();
                        timer = getRuntime();

                    }
                }
                break;
            case 11:
                if (!follower.isBusy()) {
                    robot.claw.close();
                    follower.followPath(getFromSub);
                }
                    if(getRuntime() > timer + .25){
                        if (!foo){
                            robot.bucketStep = 0;
                            foo = true;
                        }
                        robot.scoringState = scoringStates.BUCKETR;
                        setPathState(-1); // End the autonomous routine
                        foo = false;
                        step = 0;
                    }
//                    follower.breakFollowing();

                break;
        }

    }



    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


//    final static Vector2d SPECIMEN = new Vector2d(-1, -25);
//    final static Pose2d PICKUP_1YE = new Pose2d(-57, -41, Math.toRadians(263));
//    final static Pose2d BUCKET_1 = new Pose2d(-53, -51, Math.toRadians(225));
//    final static Vector2d BUCKET_2 = new Vector2d(-65, -55);
//    final static Pose2d PICKUP_2 = new Pose2d(-63, -40, Math.toRadians(279));
//    final static Pose2d PICKUP_3 = new Pose2d(-70, -40, Math.toRadians(289));
//    final static Pose2d PARK = new Pose2d(-13, -4, Math.toRadians(90));


//    protected void specScore() {
//        Action builder = robot.getTrajectoryActionBuilder()
//                .strafeToLinearHeading(SPECIMEN,Math.toRadians(10))
//                .splineTo(SPECIMEN,1)
//                .build();
//
//        Actions.runBlocking(
//                new ParallelAction(
//                        builder,
//                        builder,
//                        new InstantAction(() -> robot.slides.slidesTo(10)),
//                        new InstantAction(()-> sleep(199999)),
//                        builder,
//
//                )
//        );
//    }
//protected void specScore() {
//        Action builder = robot.getTrajectoryActionBuilder()
//                .strafeToLinearHeading(SPECIMEN,Math.toRadians(10))
//                .splineTo(SPECIMEN,1)
//                .build();
//
//        Actions.runBlocking(
//                new ParallelAction(
//                        builder,
//                        builder,
//                        new InstantAction(() -> robot.slides.slidesTo(10)),
//                        new InstantAction(()-> sleep(199999))
//
//                )
//        );
//    }
//
//
//    protected void specScore1() {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.setReversed(true);
//        builder.setTangent(0);
//        builder.splineToConstantHeading(SPECIMEN, Math.toRadians(90));
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//        robot.specStep = 4;
//        timer = getRuntime() + 3;
//        robot.scoringState = scoringStates.SPECIMENGRAB;
//        while (this.robot.getDrive().isBusy() || timer > getRuntime()) {
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            if (getRuntime() > timer - 1) {
//                robot.AUTO = true;
//                robot.specStep = 6;
//            }
//        }
//    }
//
//    protected void getSample() {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.setTangent(270);
//        builder.setReversed(true);
//        builder.splineToLinearHeading(PICKUP_1YE, Math.toRadians(265));
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//
//        timer = getRuntime() + 3;
//        robot.scoringState = scoringStates.BUCKETR;
//        robot.bucketStep = 0;
//        boolean initialized = false;
//        while ((this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE)) {
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            this.robot.intakeMacro(controller1, getRuntime(), true);
//            if (getRuntime() > timer - 1 && !initialized) {
//                robot.intakeState = Robot.intakeStates.EXTENDED;
//                initialized = true;
//            }
//
//        }
//    }
//
//    protected void toBucket(double x, double y) {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.lineToSplineHeading(BUCKET_1.plus(new Pose2d(x, y)));
//        builder.lineTo(BUCKET_2.plus(new Vector2d(x, y)),
//                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                MecanumDrive.getAccelerationConstraint(30)
//        );
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//
//        robot.AUTO = true;
//        while (this.robot.getDrive().isBusy() && robot.specStep != 1) {
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            this.robot.intakeMacro(controller1, getRuntime(), true);
//        }
//    }
//
//    protected void toSampleTwo() {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.lineToLinearHeading(PICKUP_2);
//        builder.lineToLinearHeading(PICKUP_2.plus(new Pose2d(0, 5)));
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//        timer = getRuntime() + 3;
//        robot.scoringState = scoringStates.BUCKETR;
//        robot.bucketStep = 0;
//        robot.intakeState = Robot.intakeStates.EXTENDED;
//        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE && timer > getRuntime()) {
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            this.robot.intakeMacro(controller1, getRuntime(), true);
//        }
//        robot.intakeState = Robot.intakeStates.HASSAMPLE;
//
//    }
//
//    protected void toSampleThree() {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.lineToLinearHeading(PICKUP_3);
//        builder.lineToLinearHeading(PICKUP_3.plus(new Pose2d(1, 5)),
//                MecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                MecanumDrive.getAccelerationConstraint(30)
//        );
//        builder.addTemporalMarker(0.1, robot.getIntake()::down);
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//
//        timer = getRuntime() + 3;
//        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE && timer > getRuntime()) {
//            if (timer < getRuntime() + 2.5 && timer > getRuntime() && foo) {
//                foo = false;
//                robot.scoringState = scoringStates.BUCKETR;
//                robot.bucketStep = 0;
//            }
//            if (timer - 2 < getRuntime() && boo) {
//                robot.intakeState = Robot.intakeStates.EXTENDED;
//                boo = false;
//            }
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            this.robot.intakeMacro(controller1, getRuntime(), true);
//        }
//        robot.intakeState = Robot.intakeStates.HASSAMPLE;
//    }

//    protected void park() {
//        TrajectorySequenceBuilder builder = this.robot.getTrajectorySequenceBuilder();
//
//        builder.setReversed(true);
//        builder.splineToLinearHeading(PARK, Math.toRadians(0));
//        builder.setReversed(false);
//        this.robot.getDrive().followTrajectorySequenceAsync(builder.build());
//
//        robot.scoringState = scoringStates.BUCKETR;
//        robot.bucketStep = 0;

//        while (this.robot.getDrive().isBusy() || robot.intakeState != Robot.intakeStates.IDLE) {
//            this.robot.update();
//            this.robot.scoringMacro(controller1, this.getRuntime(), true);
//            this.robot.intakeMacro(controller1, getRuntime(), true);
//        }
//    }
    @Override
    public void loop() {

        follower.update();
        pathUpdate();
        robot.scoringMacro(controller1, this.getRuntime(), true);
        robot.intakeMacro(controller1, getRuntime(), true);
        robot.update();




        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
        telemetry.update();


    }

    @Override
    public void init() {
        robot = new Robot().init(hardwareMap);
        robot.arm.intakeSpecimen();
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        controller1 = new GamepadEx(gamepad1);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(initialPosition);
        buildPaths();

        robot.specStep = 0;
//        robot.scoringState = scoringStates.SPECIMENGRAB;
    }

    @Override
    public void init_loop() {
        robot.getClaw().close();
//        this.telemetry.update();
    }


    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {
    }

}