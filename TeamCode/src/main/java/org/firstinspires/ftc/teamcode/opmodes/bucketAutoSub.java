package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.MathFunctions;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Robot.scoringStates;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.LConstants;


@Config
@Autonomous(name = "BucketAutoSub")
public class bucketAutoSub extends OpMode {
    private Robot robot;
    private Telemetry telemetryA;
    GamepadEx controller1;
    private double timer;
    private boolean foo = false;
    private boolean boo = false;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    int step = 0;

    private final Pose initialPosition = new Pose(36, 12, Math.toRadians(180));
    //    private final Pose control1 = new Pose(30, 24,P);
    private final Pose bucket = new Pose(16, 25, Math.toRadians(-135));
    private final Pose bucket2 = new Pose(14, 24, Math.toRadians(-135));
    private final Pose bucket3 = new Pose(10, 25, Math.toRadians(-135));
    private final Pose get1 = new Pose(20, 43, Math.toRadians(-100));
    private final Pose get2 = new Pose(11.5, 43, Math.toRadians(-90));
    private final Pose get3 = new Pose(7, 45, Math.toRadians(-40));
    private final Pose getSubControl = new Pose(24, 68, Point.CARTESIAN);
    private final Pose getSubControl2 = new Pose(36, 40, Point.CARTESIAN);
    private final Pose getSub = new Pose(48.25, 68, Math.toRadians(180));
    private final Pose getPreload = new Pose(67, 16, Math.toRadians(120));

    private PathChain scorePreload;
    private PathChain getSample1;
    private PathChain getSample2;
    private Path getSample3;
    private Path scoreBucket1;
    private Path scoreBucket2;
    private PathChain scoreBucket3;
    private Path getFromSub;
    private Path getPreloadPath;
    private Path scoreBucket4;
    private Path scoreBucket5;

    public void getTeam() {
        if (controller1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            robot.team = "blue";
            robot.intake.targetColor = Robot.Intake.colors.BLUE;
            gamepad1.setLedColor(0, 0, 255, 100000);
        } else if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robot.team = "red";
            robot.intake.targetColor = Robot.Intake.colors.RED;
            gamepad1.setLedColor(255, 0, 0, 100000);
        }
    }

    public void buildPaths() {
        scorePreload = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(initialPosition),
//                new Point(control1),
                                new Point(MathFunctions.addPoses(bucket, new Pose(0, -3)))
                        ))
                .setLinearHeadingInterpolation(initialPosition.getHeading(), bucket.getHeading())
                .build();

//        scorePreload;

        getSample1 = follower.pathBuilder()
                .addPath(new BezierLine(
                        new Point(MathFunctions.addPoses(bucket, new Pose(0, -3))),
                        new Point(MathFunctions.addPoses(get1, new Pose(-1, -10)))
                ))
                .setLinearHeadingInterpolation(bucket.getHeading(), get1.getHeading())
                .addPath(new BezierLine(
                        new Point(MathFunctions.addPoses(get1, new Pose(-1, -10))),
                        new Point(get1)
                ))
                .setConstantHeadingInterpolation(get1.getHeading())
                .build();
//        getSample1.setLinearHeadingInterpolation(bucket.getHeading(), get1.getHeading());

        getSample2 = follower.pathBuilder()
                .addPath(new BezierLine(
                        new Point(bucket),
                        new Point(MathFunctions.addPoses(get2, new Pose(0, -10)))
                ))
                .setLinearHeadingInterpolation(bucket.getHeading(), get2.getHeading())
                .addPath(new BezierLine(
                        new Point(MathFunctions.addPoses(get2, new Pose(0, -10))),
                        new Point(get2)
                ))
                .setConstantHeadingInterpolation(get2.getHeading())
                .build();
//        getSample2.setLinearHeadingInterpolation(bucket.getHeading(), get2.getHeading());

        getSample3 = new Path(new BezierLine(
                new Point(MathFunctions.addPoses(bucket, new Pose(-2, 2))),
                new Point(get3)
        ));
        getSample3.setLinearHeadingInterpolation(bucket.getHeading(), get3.getHeading());

        getFromSub = new Path(new BezierCurve(
                new Point(MathFunctions.addPoses(bucket, new Pose(-4, 5))),
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
                new Point(MathFunctions.addPoses(bucket, new Pose(-2, 2)))
        ));
        scoreBucket2.setLinearHeadingInterpolation(get2.getHeading(), bucket.getHeading());

        scoreBucket3 = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Point(get3),
                        new Point(MathFunctions.addPoses(bucket, new Pose(-4, 1)))
                ))
                .setLinearHeadingInterpolation(get3.getHeading(), bucket.getHeading())
                .build();
//        scoreBucket3.setLinearHeadingInterpolation(get3.getHeading(), bucket.getHeading());

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
            case 0: //0
                robot.scoringState = scoringStates.BUCKET;
                robot.AUTO = true;
                if (robot.getSlides().getPosition() > 400) {
                    follower.followPath(scorePreload, true);
                    setPathState(1);
                    foo = false;
                }
                break;
            case 1: //1
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + .2) {
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(2); // End the autonomous routine
                        foo = false;
//                        robot.claw.close();
                    }
                }
                break;
            case 2: //2
                if (!follower.isBusy()) {
                    follower.followPath(getSample1);
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    robot.scoringState = scoringStates.BUCKETR;
                    timer = getRuntime();
                    setPathState(3); // End the autonomous routine
                    foo = false;
                }
                break;

            case 3: //3
                switch (step) {
                    case 0:
                        if (!follower.isBusy()) {
                            if (!foo) {
//                                robot.bucketStep = 0;
                                foo = true;
                                timer = getRuntime();
                            }
                            if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70 || getRuntime() > timer + 1.5) {
                                if (!boo){
                                    timer = getRuntime();
                                    boo = true;
                                }
                                if (getRuntime() > timer + .5) {
                                    robot.AUTO = true;
                                    boo = false;
                                    step++;
                                }
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
            case 4: //4
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + .2) {
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(5); // End the autonomous routine
//                        robot.claw.close();
                        foo = false;
                    }
                }
                break;
            case 5: //5
//                if (!follower.isBusy()) {
                    follower.followPath(getSample2, true);
                    if (getRuntime() > timer + .3) {
                        if (!foo) {
                            robot.bucketStep = 0;
                            foo = true;
                        }
                        robot.scoringState = scoringStates.BUCKETR;
                        setPathState(6); // End the autonomous routine
                        foo = false;
                        step = 0;
                    }
//                }
                break;
            case 6: //6
                switch (step) {
                    case 0:
                        if (!follower.isBusy()) {
                            if (!foo) {
//                                robot.bucketStep = 0;
                                foo = true;
                                timer = getRuntime();
                            }
                            if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70 || getRuntime() > timer + 1.5) {
                                if (!boo) {
                                    timer = getRuntime();
                                    boo = true;
                                }
                                if (getRuntime() > timer + .5) {
                                    robot.AUTO = true;
                                    boo = false;
                                    step++;
                                }
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
            case 7: //7
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.AUTO = false;
                    robot.claw.open();
                    if (getRuntime() > timer + .2) {
                        robot.mini = true;
                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(8); // End the autonomous routine
                        robot.claw.close();
                        timer = getRuntime();
                        foo = false;
                    }
                }
                break;
            case 8: //8
                if (!follower.isBusy()) {
                    robot.claw.close();
                    follower.followPath(getSample3);

                    step = 0;
                }
                if (getRuntime() > timer + .275) {
                    if (!foo) {
                        robot.bucketStep = 0;
                        foo = true;
                    }
                    setPathState(9); // End the autonomous routine
                    robot.scoringState = scoringStates.BUCKETR;
                    foo = false;
                }
                break;
//                if (follower.atParametricEnd()){
//                }
            case 9: //9
                switch (step) {
                    case 0:
                        if (!follower.isBusy()) {
                            if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70 || getRuntime() > timer + 1.5) {
                                if (!boo) {
                                    timer = getRuntime();
                                    boo = true;
                                }
                                if (getRuntime() > timer + .5) {
                                    robot.AUTO = true;
                                    boo = false;
                                    step++;
                                }
                            }
                        }
                        break;
                    case 1:
//                        robot.claw.close();
                        robot.scoringState = scoringStates.BUCKET;
//                        follower.setXOffset();
                        if (robot.slides.getPosition() > 300) {
                            follower.followPath(scoreBucket3, true);
                            timer = getRuntime();
                            foo = false;
                            setPathState(10); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 10: //10
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.claw.open();
                    robot.AUTO = false;
                    robot.mini = false;
                    robot.stay = true;
//                    robot.intakeState = Robot.intakeStates.EXTENDED;
                    if (getRuntime() > timer + .3) {
//                        robot.mini = true;
//                        robot.intakeState = Robot.intakeStates.EXTENDED;
                        setPathState(14); // End the autonomous routine
//                        robot.claw.close();
                        foo = false;
                        timer = getRuntime();
                        follower.followPath(getFromSub);

                    }
                }
                break;
//            case 11:
//                if (!follower.isBusy()) {
//                    robot.claw.close();
//                    follower.followPath(getPreloadPath);
//                }
//                if (getRuntime() > timer + .5) {
//                    if (!foo) {
//                        robot.bucketStep = 0;
//                        foo = true;
//                    }
//                    robot.scoringState = scoringStates.BUCKETR;
//                    setPathState(12); // End the autonomous routine
//                    foo = false;
//                    step = 0;
//                }
//
////                follower.followPath(
////                        new Path(new BezierLine(
////                                new Point(getSub),
////                                new Point(MathFunctions.addPoses(getSub, robot.calcCorrection(robot.limelight.getSampleTx(), robot.limelight.getSampleTY())))
////                        )));
//                break;
//            case 12:
//                switch (step) {
//                    case 0:
//                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 80) {
//                            if (!foo) {
//                                robot.bucketStep = 0;
//                                foo = true;
//                                timer = getRuntime();
//                            }
//                            if (getRuntime() > timer + .3) {
//                                robot.AUTO = true;
//                                follower.followPath(scoreBucket4);
//                                step++;
//                            }
//                        }
//                        break;
//                    case 1:
//                        robot.scoringState = scoringStates.BUCKET;
//                        if (robot.slides.getPosition() > 300) {
//                            timer = getRuntime();
//                            foo = false;
//                            setPathState(13); // End the autonomous routine
//                        }
//                        break;
//                }
//                break;
//            case 13:
//                if (!follower.isBusy()) {
//                    if (!foo) {
//                        timer = getRuntime();
//                        foo = true;
//                    }
//                    robot.claw.open();
//                    robot.AUTO = false;
//                    robot.mini = false;
//                    robot.stay = true;
//                    if (getRuntime() > timer + .3) {
//                        setPathState(14); // End the autonomous routine
//                        timer = getRuntime();
//                        foo = false;
//
//                    }
//                }
//                break;
            case 14: //11
                if (!follower.isBusy()) {
                    robot.claw.close();
                }
                if (getRuntime() > timer + 2) {
                    robot.mini = true;
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
            case 15: //12
                switch (step) {
                    case 0:
                        if (!follower.isBusy()) {
                            robot.stay = true;
                            robot.intake.sweep();
                            if (!foo) {
                                timer = getRuntime();
                                foo = true;
                            }
                            if (foo && getRuntime() > timer + 1.5) {
                                robot.intakeState = Robot.intakeStates.EXTENDED;
                                step++;
                                timer = getRuntime();
                            }
                        }
                        break;
                    case 1:
                        if (!follower.isBusy() && timer + .2 < getRuntime()) {
                            robot.stay = false;
                            foo = false;
                            timer = getRuntime();
                            step++;
                        }
                        break;
                    case 2:
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
                        } else {
                            if (timer + 2 > getRuntime() && timer + .4 < getRuntime()) {
                                robot.getExtendo().extend();
                            } else if (timer + 2.2 > getRuntime()) {
                                robot.getExtendo().mini();
                                robot.intakeState = Robot.intakeStates.OUTTAKE;
                            } else{
                                timer = getRuntime();
                            }
                        }
                        break;
                    case 3:
                        robot.scoringState = scoringStates.BUCKET;
//                        if (robot.slides.getPosition() > 300) {
                        robot.intake.stow();
                        follower.followPath(scoreBucket5);
                        timer = getRuntime();
                        foo = false;
                        setPathState(16); // End the autonomous routine
//                        }
                        break;
                }
                break;
            case 16: //13
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.claw.open();
                    robot.AUTO = false;
                    robot.stay = true;
                    if (getRuntime() > timer + .3) {
                        setPathState(17); // End the autonomous routine
                        timer = getRuntime();
                        foo = false;
                    }
                }
                break;
            case 17: //14
                if (!follower.isBusy()) {
                    robot.claw.close();
                    follower.followPath(getFromSub);
                }
                if (getRuntime() > timer + 2.7) {
                    setPathState(18); // End the autonomous routine
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
            case 18: //15
                switch (step) {
                    case 0:
                        if (!follower.isBusy()) {
                            robot.stay = false;
                            robot.intake.sweep();
                            if (getRuntime() > timer + 3) {
                                Path temp = new Path(new BezierLine(
                                        new Point(getSub),
                                        new Point(MathFunctions.addPoses(getSub, new Pose(12, 0)))
                                ));
                                temp.setPathEndTimeoutConstraint(2);
                                temp.setConstantHeadingInterpolation(180);
                                temp.setReversed(true);
                                follower.followPath(temp);
                                foo = false;
                                step++;
                            }
                        }
                        break;
                    case 1:
                        if (!follower.isBusy()) {
                            robot.intakeState = Robot.intakeStates.EXTENDED;
                            step++;
                        }
                    case 2:

                        if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 100 && !follower.isBusy()) {

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
                    case 3:
                        robot.scoringState = scoringStates.BUCKET;
                        if (robot.slides.getPosition() > 300) {
                            robot.intake.stow();
                            follower.followPath(scoreBucket5);
                            timer = getRuntime();
                            foo = false;
                            setPathState(19); // End the autonomous routine
                        }
                        break;
                }
                break;
            case 19: //16
                if (!follower.isBusy()) {
                    if (!foo) {
                        timer = getRuntime();
                        foo = true;
                    }
                    robot.claw.open();
                    robot.AUTO = false;
                    if (getRuntime() > timer + .3) {
                        setPathState(-1); // End the autonomous routine
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
        telemetry.addData("LF", (robot.getMecDrive().leftFront.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("RF", (robot.getMecDrive().rightFront.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("LB", (robot.getMecDrive().leftBack.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("RB", (robot.getMecDrive().rightBack.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("LS", (robot.slides.slidesL.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("RS", (robot.slides.slidesR.getCurrent(CurrentUnit.AMPS)));
        telemetry.addData("step:", step);
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
        controller1.readButtons();
        getTeam();
        telemetry.addData("color:", robot.intake.targetColor);
        telemetry.update();
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