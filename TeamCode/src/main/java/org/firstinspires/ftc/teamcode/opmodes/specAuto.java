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
import com.pedropathing.pathgen.PathChain;
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
@Autonomous(name = "specAuto")
public class specAuto extends OpMode {
    private Robot robot;
    private Telemetry telemetryA;
    GamepadEx controller1;
    private double timer;
    private boolean foo = false;
    private boolean boo = true;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    public static int step = 0;

    public static int y1 = 60;
    public static int y2 = 60;
    public static int y3 = 19;
    public static int y4 = 46;
    public static int y5 = 46;
    public static int y6 = 46;

    public static int x1 = 135;
    public static int x2 = 142;
    public static int x3 = 142;
    public static int x4 = 46;
    public static int x5 = 46;
    public static int x6 = 46;


    private final Pose initialPosition = new Pose(84, 12, Math.toRadians(-90));
    //    private final Pose control1 = new Pose(30, 24,P);
    private final Pose specPreload = new Pose(80, 44, Math.toRadians(-90));
    private final Pose plow1 = new Pose(120, 60, Math.toRadians(-90));
    private final Pose plowcontrol1 = new Pose(130, 24, Math.toRadians(-90));
    private final Pose plowcontrol2 = new Pose(100, 60, Math.toRadians(-90));
    private final Pose plow2 = new Pose(120, 20, Math.toRadians(-90)); // line 3
    private final Pose plow3control1 = new Pose(140, 65); // line 5
    private final Pose plow3 = new Pose(135, 20); // line 5
    private final Pose plow4 = new Pose(135, 60);
    private final Pose plow5 = new Pose(140, 60);
    private final Pose plow6 = new Pose(140, 11.4); //pickup
    private final Pose spec2 = new Pose(80, 43);
    private final Pose spec2Control = new Pose(80, 30);
    private final Pose specPickup = new Pose(124, 12);
    private final Pose specPickupControl1 = new Pose(80, 30);
    private final Pose specPickupControl2 = new Pose(124, 40);
    private final Pose specScore = new Pose(80, 43);
    private final Pose specScoreControl1 = new Pose(120, 40);
    private final Pose specScoreControl2 = new Pose(80, 20);




    private Path scorePreload;
    private Path plowPath1;
    private Path plowPath2;
    private Path plowPath3;
    private Path plowPath4;
    private Path plowPath5;
    private Path scoreSpec1;
    private Path specPickupPath;
    private Path specScorePath;
    private Path specPickupPath2;
    private Path scoreBucket5;
    private PathChain plow;


    public void buildPaths() {
        scorePreload = new Path(new BezierLine(
                new Point(initialPosition),
                new Point(specPreload)
        ));
        scorePreload.setConstantHeadingInterpolation(Math.toRadians(-90));
//
        plowPath1 = new Path(new BezierCurve(
                new Point(specPreload),
                new Point(plowcontrol1),
                new Point(plowcontrol2),
                new Point(plow1)
        ));
        plowPath1.setConstantHeadingInterpolation(Math.toRadians(-90));

        plowPath2 = new Path(new BezierCurve(
                new Point(plow1),
                new Point(plow3control1),
                new Point(plow3)
        ));

        plowPath3 = new Path(new BezierLine(
                new Point(plow3),
                new Point(plow4)
        ));

        plowPath4 = new Path(new BezierLine(
                new Point(plow4),
                new Point(plow5)
        ));

        plowPath5 = new Path(new BezierLine(
                new Point(plow5),
                new Point(plow6)
        ));

        scoreSpec1 = new Path(new BezierCurve(
                new Point(plow6),
                new Point (spec2Control),
                new Point(spec2)
        ));
        scoreSpec1.setConstantHeadingInterpolation(Math.toRadians(-90));

        specPickupPath = new Path(new BezierCurve(
                new Point(spec2),
                new Point (specPickupControl1),
                new Point (specPickupControl2),
                new Point(specPickup)
        ));
        specPickupPath.setConstantHeadingInterpolation(Math.toRadians(-90));

        specScorePath = new Path(new BezierCurve(
                new Point(specPickup),
                new Point (specScoreControl1),
                new Point (specScoreControl2),
                new Point(specScore)
        ));
        specScorePath.setConstantHeadingInterpolation(Math.toRadians(-90));

        specPickupPath2 = new Path(new BezierCurve(
                new Point(specScore),
                new Point (specPickupControl1),
                new Point (specPickupControl2),
                new Point(specPickup)
        ));
        specPickupPath2.setConstantHeadingInterpolation(Math.toRadians(-90));


        plow = follower.pathBuilder()
                .addPath(plowPath1) // First path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(new Path(new BezierLine(new Point(plow1), new Point(plow2)))) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(new Path(new BezierCurve(new Point(plow2), new Point(new Pose(120,60)), new Point(new Pose(130,60))))) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(plowPath2) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(plowPath3) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(plowPath4) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .addPath(plowPath5) // Second path
                .setConstantHeadingInterpolation(Math.toRadians(-90))

                .build();



    }

    public void pathUpdate() {
        switch (pathState) {
            case 0:
                robot.scoringState = scoringStates.SPECIMENGRAB;
                robot.specStep = 4;
                    follower.followPath(scorePreload);
                    setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    robot.SCORESPEC = true;
                        setPathState(2); // End the autonomous routine
                        timer = getRuntime();
                }
                break;
            case 2:
                if (getRuntime() > timer + .5) {
                    robot.RETURNSPEC = true;
                    follower.followPath(plow,true);

//                    if (!follower.isBusy()) {
                        setPathState(3); // End the autonomous routine
//                    }

                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    robot.GRABSPEC = true;
                    if (robot.slides.getPosition() > 300) {
                        setPathState(4); // End the autonomous routine
                    }
                }
                break;
            case 4:
                switch (step){
                    case 0:
                        follower.followPath(scoreSpec1);
                        step ++;
                        break;
                    case 1:
                        if(!follower.isBusy()){
                            robot.SCORESPEC = true;
                            setPathState(5); // End the autonomous routine
                            timer = getRuntime();
                        }
                        break;
                }
                break;
            case 5:
                step = 0;
                if (getRuntime() > timer + .5) {
                    robot.RETURNSPEC = true;
                    follower.followPath(specPickupPath);
                    robot.GRABSPEC = false;
//                    if (!follower.isBusy()) {
                    setPathState(6); // End the autonomous routine
//                    }
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    robot.GRABSPEC = true;
                    if (robot.slides.getPosition() > 300) {
                        setPathState(7); // End the autonomous routine
                    }
                }
                break;
            case 7:
                switch (step){
                    case 0:
                        follower.followPath(specScorePath);
                        step ++;
                        break;
                    case 1:
                        if(!follower.isBusy()){
                            robot.SCORESPEC = true;
                            setPathState(8); // End the autonomous routine
                            timer = getRuntime();
                        }
                        break;
                }
                break;
            case 8:
                step = 0;
                if (getRuntime() > timer + .5) {
                    robot.RETURNSPEC = true;
                    follower.followPath(specPickupPath2);
                    robot.GRABSPEC = false;
//                    if (!follower.isBusy()) {
                    setPathState(9); // End the autonomous routine
//                    }
                }
                break;
            case 9:
                if (!follower.isBusy()) {
                    robot.GRABSPEC = true;
                    if (robot.slides.getPosition() > 300) {
                        setPathState(10); // End the autonomous routine
                    }
                }
                break;
            case 10:
                switch (step){
                    case 0:
                        follower.followPath(specScorePath);
                        step ++;
                        break;
                    case 1:
                        if(!follower.isBusy()){
                            robot.SCORESPEC = true;
                            setPathState(-1); // End the autonomous routine
                            timer = getRuntime();
                        }
                        break;
                }
                break;
//            case 4:
//                if (!follower.isBusy()) {
//                    robot.AUTO = false;
//                    robot.claw.open();
//                    if (getRuntime() > timer + 1.75) {
//                        robot.mini = true;
//                        robot.intakeState = Robot.intakeStates.EXTENDED;
//                        setPathState(5); // End the autonomous routine
//                        robot.claw.close();
//                    }
//                }
//                break;
//            case 5: //LSDFSFLKLKSFKLSFJKLSKLF
//                if (!follower.isBusy()) {
//                    follower.followPath(getSample2);
//
//                    if (!foo) {
//                        robot.bucketStep = 0;
//                        foo = true;
//                    }
//
//                    if (getRuntime() > timer + .2) {
//                        robot.scoringState = scoringStates.BUCKETR;
//                        setPathState(6); // End the autonomous routine
//                        foo = false;
//                        step = 0;
//                    }
//                }
//                break;
//            case 6:
//                switch (step) {
//                    case 0:
//                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70) {
//                            if (!foo) {
//                                robot.bucketStep = 0;
//                                foo = true;
//                                timer = getRuntime();
//                            }
//                            if (getRuntime() > timer + .3) {
//                                robot.AUTO = true;
//                                step++;
//                            }
//                        }
//                        break;
//                    case 1:
//                        robot.claw.close();
//                        robot.scoringState = scoringStates.BUCKET;
//                        if (robot.slides.getPosition() > 300) {
//                            follower.followPath(scoreBucket2);
//                            setPathState(7); // End the autonomous routine
//                            timer = getRuntime();
//                            foo = false;
//                        }
//                        break;
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()) {
//                    robot.AUTO = false;
//                    robot.claw.open();
//                    if (getRuntime() > timer + 1.75) {
//                        robot.mini = true;
//                        robot.intakeState = Robot.intakeStates.EXTENDED;
//                        setPathState(8); // End the autonomous routine
//                        robot.claw.close();
//                        timer = getRuntime();
//                    }
//                }
//                break;
//            case 8: //LKSDKLJSDJKLKLSDFKLJSDF
//                if (!follower.isBusy()) {
//                    robot.claw.close();
//                    follower.followPath(getSample3);
//
////                    if(getRuntime() > timer + .125){
//                    if (!foo) {
//                        robot.bucketStep = 0;
//                        foo = true;
//                    }
//                    robot.scoringState = scoringStates.BUCKETR;
//                    setPathState(9); // End the autonomous routine
//                    foo = false;
//                    step = 0;
//                }
//                break;
////                if (follower.atParametricEnd()){
////                }
//            case 9:
//                switch (step) {
//                    case 0:
//                        if (!follower.isBusy() && robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 70) {
//                            if (!foo) {
//                                robot.bucketStep = 0;
//                                foo = true;
//                                timer = getRuntime();
//                            }
//                            if (getRuntime() > timer + .3) {
//                                robot.AUTO = true;
//                                step++;
//                            }
//                        }
//                        break;
//                    case 1:
////                        robot.claw.close();
//                        robot.scoringState = scoringStates.BUCKET;
////                        follower.setXOffset();
//                        if (robot.slides.getPosition() > 300) {
//                            follower.followPath(scoreBucket3);
//                            timer = getRuntime();
//                            foo = false;
//                            setPathState(10); // End the autonomous routine
//                        }
//                        break;
//                }
//                break;
//            case 10:
//                if (!follower.isBusy()) {
//                    robot.claw.open();
//                    robot.AUTO = false;
//                    robot.mini = false;
//                    robot.intakeState = Robot.intakeStates.EXTENDED;
//                    if (getRuntime() > timer + 2) {
////                        robot.mini = true;
////                        robot.intakeState = Robot.intakeStates.EXTENDED;
//                        setPathState(11); // End the autonomous routine
////                        robot.claw.close();
//                        timer = getRuntime();
//
//                    }
//                }
//                break;
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
//                    robot.claw.open();
//                    robot.AUTO = false;
//                    robot.mini = false;
//                    robot.stay   = true;
//                    if (getRuntime() > timer + 2.3) {
//                        setPathState(14); // End the autonomous routine
//                        timer = getRuntime();
//
//                    }
//                }
//                break;
//            case 14:
//                if (!follower.isBusy()) {
//                    robot.claw.close();
//                    follower.followPath(getFromSub);
//                }
//                if (getRuntime() > timer + 2) {
//                    robot.intakeState = Robot.intakeStates.EXTENDED;
//                    setPathState(15); // End the autonomous routine
//                    foo = false;
//                    step = 0;
//                }
//                if (getRuntime() > timer + .5) {
//                    if (!foo) {
//                        robot.bucketStep = 0;
//                        foo = true;
//                    }
//                    robot.scoringState = scoringStates.BUCKETR;
//
//                }
//
////                follower.followPath(
////                        new Path(new BezierLine(
////                                new Point(getSub),
////                                new Point(MathFunctions.addPoses(getSub, robot.calcCorrection(robot.limelight.getSampleTx(), robot.limelight.getSampleTY())))
////                        )));
//                break;
//            case 15:
//                switch (step) {
//                    case 0 :
//                        if (!follower.isBusy()){
//                            robot.stay = false;
//                            if (getRuntime() > timer +3) {
//                                Path temp = new Path(new BezierLine(
//                                        new Point(getSub),
//                                        new Point(MathFunctions.addPoses(getSub, new Pose(12, 0)))
//                                ));
//                                temp.setConstantHeadingInterpolation(180);
//                                temp.setReversed(true);
//                                follower.followPath(temp);
//                                foo = false;
//                                step++;
//                            }
//                        }
//                        break;
//                    case 1:
//                        if (robot.intakeState == Robot.intakeStates.IDLE && robot.slides.getPosition() < 100) {
//
//                            if (!foo) {
//                                robot.bucketStep = 0;
//                                foo = true;
//                                timer = getRuntime();
//                            }
//                            if (getRuntime() > timer + .5) {
//                                robot.AUTO = true;
//                                step++;
//                            }
//                        }
//                        break;
//                    case 2:
//                        robot.scoringState = scoringStates.BUCKET;
//                        if (robot.slides.getPosition() > 300) {
//                            follower.followPath(scoreBucket5);
//                            timer = getRuntime();
//                            foo = false;
//                            setPathState(16); // End the autonomous routine
//                        }
//                        break;
//                }
//                break;
//            case 16:
//                if (!follower.isBusy()) {
//                    robot.claw.open();
//                    robot.AUTO = false;
//                    if (getRuntime() > timer + 2) {
//                        setPathState(-1); // End the autonomous routine
//                        timer = getRuntime();
//
//                    }
//                }
//                break;

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
        robot.scoringMacroAuto(controller1, this.getRuntime());
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

        robot.specStep = 4;
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