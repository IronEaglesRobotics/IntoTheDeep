package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.MathFunctions;
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
@Autonomous(name = "BucketAutoBstem")
public class bucketAutoBstem extends OpMode {
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
    private final Pose bucket = new Pose(16, 24, Math.toRadians(-135));
    private final Pose bucket2 = new Pose(18, 26, Math.toRadians(-135));
    private final Pose bucket3 = new Pose(14, 27, Math.toRadians(-135));
    private final Pose get1 = new Pose(21, 48, Math.toRadians(-100));
    private final Pose get2 = new Pose(13.5, 48, Math.toRadians(-90));
    private final Pose get3 = new Pose(11.3, 50, Math.toRadians(-45));
    private final Pose getSubControl = new Pose(24, 68, Point.CARTESIAN);
    private final Pose getSubControl2 = new Pose(32, 40, Point.CARTESIAN);
    private final Pose getSub = new Pose(43, 68, Math.toRadians(180));
    private final Pose getPreload = new Pose(67, 16, Math.toRadians(120));

    private Path scorePreload;
    private Path getSample1;
    private Path getSample2;
    private Path getSample3;
    private Path scoreBucket1;
    private Path scoreBucket2;
    private Path scoreBucket3;
    private Path getFromSub;
    private Path getPreloadPath;
    private Path scoreBucket4;
    private Path scoreBucket5;

    public void buildPaths() {
        scorePreload = new Path(new BezierLine(
                new Point(initialPosition),
//                new Point(control1),
                new Point(bucket)
        ));
        scorePreload.setLinearHeadingInterpolation(initialPosition.getHeading(), bucket.getHeading());

        getSample1 = new Path(new BezierLine(
                new Point(bucket2),
                new Point(get1)
        ));
        getSample1.setLinearHeadingInterpolation(bucket2.getHeading(), get1.getHeading());

        getSample2 = new Path(new BezierLine(
                new Point(bucket),
                new Point(get2)
        ));
        getSample2.setLinearHeadingInterpolation(bucket.getHeading(), get2.getHeading());

        getSample3 = new Path(new BezierLine(
                new Point(bucket),
                new Point(get3)
        ));
        getSample3.setLinearHeadingInterpolation(bucket.getHeading(), get3.getHeading());

        getFromSub = new Path(new BezierCurve(
                new Point(bucket2),
                new Point(getSubControl),
                new Point(getSub)
        ));
        getFromSub.setTangentHeadingInterpolation();
        getFromSub.setReversed(true);

        getPreloadPath = new Path(new BezierLine(
                new Point(bucket),
                new Point(getPreload)
        ));
        getPreloadPath.setConstantHeadingInterpolation(getPreload.getHeading());
        getPreloadPath.setReversed(true);

        scoreBucket1 = new Path(new BezierCurve(
                new Point(get1),
                new Point(bucket)
        ));
        scoreBucket1.setLinearHeadingInterpolation(get1.getHeading(), bucket.getHeading());

        scoreBucket2 = new Path(new BezierCurve(
                new Point(get2),
                new Point(bucket)
        ));
        scoreBucket2.setLinearHeadingInterpolation(get2.getHeading(), bucket.getHeading());

        scoreBucket3 = new Path(new BezierCurve(
                new Point(get3),
                new Point(bucket3)
        ));
        scoreBucket3.setLinearHeadingInterpolation(get3.getHeading(), bucket.getHeading());

        scoreBucket4 = new Path(new BezierLine(
                new Point(getPreload),
                new Point(bucket2)
        ));
        scoreBucket4.setLinearHeadingInterpolation(getPreload.getHeading(), bucket2.getHeading());

        scoreBucket5 = new Path(new BezierCurve(
                new Point(getSub),
                new Point(getSubControl),
                new Point(getSubControl2),
                new Point(bucket2)
        ));
        scoreBucket5.setTangentHeadingInterpolation();






//        getSample1.setReversed(true);


    }

    public void pathUpdate() {
        switch (pathState) {
            case 0:
                robot.scoringState = scoringStates.BUCKET;
                robot.AUTO = true;
                if (robot.getSlides().getPosition() > 400) {
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
                    if (getRuntime() > timer + 1.8) {
                        robot.mini = false;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(10); // End the autonomous routine
//                        robot.claw.close();
                    }
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;
                    follower.followPath(getSample1);
//                    follower.breakFollowing();
                    setPathState(3); // End the autonomous routine
                    foo = false;
                    step = 0;
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 3:
                switch (step) {
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70) {
                            if (!foo) {
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
                        if (robot.slides.getPosition() > 300) {
                            follower.followPath(scoreBucket1);
                            setPathState(4); // End the autonomous routine
                            timer = getRuntime();
                            foo = false;
                        }
                        break;
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + 1.6) {
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(5); // End the autonomous routine
                        robot.claw.close();
                    }
                }
                break;
            case 5: //LSDFSFLKLKSFKLSFJKLSKLF
                if (!follower.isBusy()) {
                    follower.followPath(getSample2);

                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }

                    if (getRuntime() > timer + .2) {
                        robot.scoringState = scoringStates.BUCKETR;
                        setPathState(6); // End the autonomous routine
                        foo = false;
                        step = 0;
                    }
                }
                break;
            case 6:
                switch (step) {
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70) {
                            if (!foo) {
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
                        if (robot.slides.getPosition() > 300) {
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
                    if (getRuntime() > timer + 1.6) {
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(8); // End the autonomous routine
                        robot.claw.close();
                        timer = getRuntime();
                    }
                }
                break;
            case 8: //LKSDKLJSDJKLKLSDFKLJSDF
                if (!follower.isBusy()) {
                    robot.claw.close();
                    follower.followPath(getSample3);

//                    if(getRuntime() > timer + .125){
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;
                    setPathState(9); // End the autonomous routine
                    foo = false;
                    step = 0;
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 9:
                switch (step) {
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70) {
                            if (!foo) {
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
//                        follower.setXOffset();
                        if (robot.slides.getPosition() > 300) {
                            follower.followPath(scoreBucket3);
                            timer = getRuntime();
                            foo = false;
                            setPathState(13); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 10:
                if (!follower.isBusy()) {
                    robot.claw.open();
                    robot.AUTO = false;
                    robot.mini = false;
//                                        robot.stay   = true;
                    robot.intakeState = Robot.intakeStates.EXTENDED;
                    if (getRuntime() > timer + 2) {
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
                    follower.followPath(getPreloadPath);
                }
                if (getRuntime() > timer + .5) {
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;
                    setPathState(12); // End the autonomous routine
                    foo = false;
                    step = 0;
                }

//                follower.followPath(
//                        new Path(new BezierLine(
//                                new Point(getSub),
//                                new Point(MathFunctions.addPoses(getSub, robot.calcCorrection(robot.limelight.getSampleTx(), robot.limelight.getSampleTY())))
//                        )));
                break;
            case 12:
                switch (step) {
                    case 0:
                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 80) {
                            if (!foo) {
                                robot.bucketStep = 0;
                                foo = true;
                                follower.followPath(scoreBucket4);
                                timer = getRuntime();
                                step++;
                            }
//                            if (getRuntime() > timer + .3) {
//                            }
                        }
                        break;
                    case 1:
                        robot.AUTO = true;
                        robot.scoringState = scoringStates.BUCKET;
                        if (robot.slides.getPosition() > 300) {
                            timer = getRuntime();
                            foo = false;
                            setPathState(17); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 13:
                if (!follower.isBusy()) {
                    robot.claw.open();
                    robot.AUTO = false;
                    robot.mini = false;
//                    robot.stay   = true;
                    if (getRuntime() > timer + 2.2) {
//                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        foo = false;
                        setPathState(14); // End the autonomous routine
                        timer = getRuntime();

                    }
                }
                break;
            case 14:
                if (!follower.isBusy()) {
                    robot.claw.close();
                    robot.stay   = true;
                    follower.followPath(getFromSub);
                }
                if (getRuntime() > timer + 2) {
                    robot.intakeState = Robot.intakeStates.EXTENDED;
                    setPathState(15); // End the autonomous routine
                    foo = false;
                    step = 0;
                }
                if (getRuntime() > timer + .5) {
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;

                }

//                follower.followPath(
//                        new Path(new BezierLine(
//                                new Point(getSub),
//                                new Point(MathFunctions.addPoses(getSub, robot.calcCorrection(robot.limelight.getSampleTx(), robot.limelight.getSampleTY())))
//                        )));
                break;
            case 15:
                switch (step) {
                    case 0 :
                        if (!follower.isBusy()){
                            robot.stay = false;
                            if (getRuntime() > timer +3) {
                                Path temp = new Path(new BezierLine(
                                        new Point(getSub),
                                        new Point(MathFunctions.addPoses(getSub, new Pose(12, 0)))
                                ));
                                temp.setConstantHeadingInterpolation(180);
                                temp.setReversed(true);
                                follower.followPath(temp);
                                foo = false;
                                step++;
                            }
                        }
                        break;
                    case 1:
                        if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 100) {

                            if (!foo) {
                                robot.bucketStep = 0;
                                foo = true;
                                timer = getRuntime();
                            }
                            if (getRuntime() > timer + .5) {
                                robot.AUTO = true;
                                step++;
                            }
                        }
                        break;
                    case 2:
                        robot.scoringState = scoringStates.BUCKET;
                        if (robot.slides.getPosition() > 300) {
                            follower.followPath(scoreBucket5);
                            timer = getRuntime();
                            foo = false;
                            setPathState(16); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 16:
                if (!follower.isBusy()) {
                    robot.claw.open();
                    robot.AUTO = false;
                    if (getRuntime() > timer + 2) {
                        setPathState(-1); // End the autonomous routine
                        timer = getRuntime();

                    }
                }
                break;
            case 17:
                if (!follower.isBusy()) {
                    robot.claw.open();
                    robot.AUTO = false;
                    robot.mini = true;
//                    robot.stay   = true;
                    if (getRuntime() > timer + 2.6) {
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        foo = false;
                        setPathState(2); // End the autonomous routine
                        timer = getRuntime();

                    }
                }
                break;

        }

    }


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


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
//        robot.arm.intakeSpecimen();
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