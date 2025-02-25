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
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.FConstantsOld;
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
    private final Pose specPreload = new Pose(80, 43.75, Math.toRadians(-90));
    private final Pose plow1 = new Pose(120, 60, Math.toRadians(-90));
    private final Pose plowcontrol1 = new Pose(130, 24, Math.toRadians(-90));
    private final Pose plowcontrol2 = new Pose(100, 60, Math.toRadians(-90));
    private final Pose plow2 = new Pose(120, 20, Math.toRadians(-90)); // line 3
    private final Pose plow3control1 = new Pose(144, 65); // line 5
    private final Pose plow3 = new Pose(140, 9); // line 5
    private final Pose plow4 = new Pose(135, 60);
    private final Pose plow5 = new Pose(145, 60);
    private final Pose plow6 = new Pose(140, 13); //pickup
    private final Pose spec2 = new Pose(80, 43.2);
    private final Pose spec2Control = new Pose(80, 30);
    private final Pose specPickup = new Pose(124, 11.3);
    private final Pose specPickupControl1 = new Pose(80, 30);
    private final Pose specPickupControl2 = new Pose(124, 40);
    private final Pose specScore = new Pose(80, 43.5);
    private final Pose specScoreControl1 = new Pose(120, 40);
    private final Pose specScoreControl2 = new Pose(70, 25);




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
                new Point(plow3),
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

//                .addPath(plowPath3) // Second path
//                .setConstantHeadingInterpolation(Math.toRadians(-90))

//                .addPath(plowPath4) // Second path
//                .setConstantHeadingInterpolation(Math.toRadians(-90))
//
//                .addPath(plowPath5) // Second path
//                .setConstantHeadingInterpolation(Math.toRadians(-90))

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
                        step = 0;
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
                    robot.SCORESPEC = false;
                    if (robot.slides.getPosition() > 300) {
                        setPathState(10); // End the autonomous routine
                    }
                }
                break;
            case 10:
                switch (step){
                    case 0:
                        if (!follower.isBusy()) {
                            follower.followPath(specScorePath);
                            timer = getRuntime();
                            step++;
                        }
                        break;
                    case 1:
                        if(!follower.isBusy() && timer + 2.2 < getRuntime()){
                            robot.SCORESPEC = true;
                            setPathState(-1); // End the autonomous routine
//                            timer = getRuntime();
                        }
                        break;
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
        robot.scoringMacroAuto(controller1, this.getRuntime());
        robot.intakeMacro(controller1, getRuntime(), true);
        robot.update();


        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
        telemetry.update();

//        follower.startTeleopDrive();


    }

    @Override
    public void init() {
        robot = new Robot().init(hardwareMap);
//        robot.arm.intakeSpecimen();
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        controller1 = new GamepadEx(gamepad1);

        Constants.setConstants(FConstantsOld.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(initialPosition);
        buildPaths();

        robot.specStep = 4;
    }

    @Override
    public void init_loop() {
        robot.getClaw().close();
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