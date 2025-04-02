package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "clipAutoCommand", preselectTeleOp = "Main Teleop")
public class commandClipAuto extends CommandOpMode {
    Robot robot;
    int stage = 3;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot().init(hardwareMap,null,new Pose(0,0,Math.toRadians(180)));
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        robot.getFollower().update();
        //clip().schedule();
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
            robot.getFollower().update();
            switch (stage) {
//                case 0:
//                    if (!robot.getFollower().isBusy()) {
//                        placeBlock1().schedule();
//                        stage = -1;
//                    }
//                    break;
//                case 1:
//                    placeBlock2().whenFinished(() -> stage = 3).schedule();
//                    stage = -1;
//                    break;
                case 3:
                    grab().whenFinished(()->stage = 4).schedule();
                    stage = -1;
                    break;
//                case 4:
//                    clip3(3).whenFinished(()->stage = 5).schedule();
//                    stage = -1;
//                    break;
//                case 5:
//                    grab().whenFinished(()->stage = 6).schedule();
//                    stage = -1;
//                    break;
//                case 6:
//                    clip3(0).whenFinished(()->stage = 7).schedule();
//                    stage = -1;
//                    break;
//                case 7:
//                    grab().whenFinished(()->stage = 8).schedule();
//                    stage = -1;
//                    break;
//                case 8:
//                    clip3(-2).whenFinished(()->stage = 9).schedule();
//                    stage = -1;
//                    break;
                default:
                    sleep(20);
                    break;

            }
            MultipleTelemetry telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
            telemetryA.addData("target",robot.getSlides().getTarget());
            telemetryA.addData("current pos",robot.getSlides().getPos());
            telemetryA.addData("stage",stage);
            telemetryA.update();
            robot.getFollower().telemetryDebug(telemetryA);
        }

    }

    Command clip(){
        return robot.getSlides().preclip()
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(31,7,Math.toRadians(180))));
    }
    Command placeBlock1(){
        return robot.getSlides().postclip()
                .andThen(new WaitCommand(100))
                .andThen(robot.getClaw().openCommand())
                .andThen(new WaitCommand(100))
                .andThen(robot.getSlides().down()
                .alongWith(robot.follow(robot.getFollower().pathBuilder().addBezierCurve(
                        new Point(robot.getFollower().getPose()),
                                new Point(0,-27),
                                new Point(32,-30),
                                new Point(50,-40))
                    .setConstantHeadingInterpolation(robot.getFollower().getPose().getHeading())
                    .addBezierLine(new Point(50,-43),new Point(7,-43))
                    .build())))
                .whenFinished(()-> stage = 1);
    }

    Command placeBlock2(){
        return robot.follow(robot.getFollower().pathBuilder()
                    .addBezierCurve(new Point(robot.getFollower().getPose()),
                        new Point(50,-40),
                        new Point(50,-50))
                    .setConstantHeadingInterpolation(robot.getFollower().getPose().getHeading())
                    .addBezierLine(new Point(50,-50),new Point(7,-50))
                    .build());
    }
    Command placeBlock3(){
        return robot.follow(robot.getFollower().pathBuilder().addBezierCurve(
                        new Point(robot.getFollower().getPose()),
                        new Point(50,-50),
                        new Point(50,-56))
                .setConstantHeadingInterpolation(robot.getFollower().getPose().getHeading())
                .addBezierLine(new Point(50,-58),new Point(7,-57))
                .build());
    }
//    Command grab(){
//        return robot.getSlides().down()
//                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(0,-36,0,
//                        10,-36)))
//                .andThen(robot.getPusher().offCommand())
//                .andThen(robot.getClaw().adaptClaw());
//    }
    Command grab() {
        return robot.getSlides().down()
                .andThen(robot.getClaw().openCommand())
                .alongWith(robot.follow(robot.getFollower().pathBuilder().addBezierLine(
                        new Point(robot.getFollower().getPose()), new Point(15,-36))
                                .setLinearHeadingInterpolation(robot.getFollower().getPose().getHeading(),
                                0)
                        .build()))
                .andThen(new WaitCommand(200))
                .andThen(robot.follow(robot.getFollower().pathBuilder().addBezierLine(
                        new Point(robot.getFollower().getPose()),new Point(0,-36))
                                .setConstantHeadingInterpolation(0)
                        .build()))
                .andThen(new WaitCommand(700))
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(300));
    }

    Command clip3(double y){
        return robot.follow(robot.getFollower().pathBuilder().addBezierLine(new Point(robot.getFollower().getPose()),
                                new Point(18,y))
                        .setLinearHeadingInterpolation(robot.getFollower().getPose().getHeading(),Math.toRadians(180))
                        .addBezierLine(new Point(robot.getFollower().getPose()),new Point(32,y))
                        .setConstantHeadingInterpolation(Math.toRadians(180))
                        .build())
                .alongWith(robot.getSlides().preclip())
                .andThen(new WaitCommand(200))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
}
