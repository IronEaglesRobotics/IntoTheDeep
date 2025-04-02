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

import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "basket auto",preselectTeleOp = "Main Teleop")
public class commandBasketAuto extends CommandOpMode {
    Robot robot;
    int stage = -1;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot().init(hardwareMap,null,new Pose(0,0,Math.toRadians(90)));
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        robot.getFollower().update();
        score().whenFinished(()->stage = 0).schedule();
        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
            robot.getFollower().update();
            switch (stage){
                case 0:
                    pickUp1().whenFinished(()->stage = 1).schedule();
                    stage = -1;
                    break;
                case 1:
                    score().whenFinished(()->stage = 2).schedule();
                    stage = -1;
                    break;
                case 2:
                    pickUp2().whenFinished(()->stage = 3).schedule();
                    stage = -1;
                    break;
                case 3:
                    score().whenFinished(()->stage = 4).schedule();
                    stage = -1;
                    break;
                case 4:
                    pickup3().whenFinished(()->stage = 5).schedule();
                    stage = -1;
                    break;
                case 5:
                    score().whenFinished(()->stage = 6).schedule();
                    stage = -1;
                    break;
                case 6:
                    park().schedule();
                    stage = -1;
                    break;
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
    Command score() {
        return robot.follow(robot.EZ().moveToWithHeading(18,7,Math.toRadians(125)))
                .alongWith(robot.getSlides().up())
                .andThen(robot.getIntakeArm().upCommand())
                .andThen(robot.getIntake().ejectIntake())
                .andThen(new WaitCommand(1000))
                .andThen(robot.getIntake().reverseIntake())
                .andThen(new WaitCommand(1000));
    }
    Command pickUp1(){
        return robot.getIntake().offIntake()
                .andThen(new Intake.storeIntake(robot.getIntake()).alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(12,10,0)))
                .andThen(robot.getIntake().onIntake())
                .andThen(new WaitCommand(200))
                .andThen(robot.follow(robot.getFollower().pathBuilder()
                        .addBezierLine(new Point(12,10),
                            new Point(24,10))
                        .setConstantHeadingInterpolation(0)
                        .build()))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.getIntake().storeIntake());
    }
    Command pickUp2(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntake().storeIntake().alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(12,21,0)))
                .andThen(robot.getIntake().onIntake())
                .andThen(robot.follow(robot.getFollower().pathBuilder()
                        .addBezierLine(new Point(12,21),
                            new Point(26,21))
                        .setConstantHeadingInterpolation(0)
                        .build()))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.getIntake().storeIntake());
    }
    Command pickup3(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntake().storeIntake().alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(18,7,Math.toRadians(45))))
                .andThen(robot.getIntake().onIntake())
                .andThen(robot.follow(robot.getFollower().pathBuilder()
                        .addBezierLine(new Point(18,7),
                                new Point(23.5,15.5))
                        .setConstantHeadingInterpolation(Math.toRadians(45))
                        .build()))
                .andThen(new WaitCommand(700))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.getIntake().storeIntake());
    }
    Command park(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntake().storeIntake().alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(53,0,Math.toRadians(-90))))
                .andThen(robot.getPusher().activateCommand())
                .andThen(robot.follow(robot.getFollower().pathBuilder()
                        .addBezierLine(new Point(53,0),
                                new Point(53,-15))
                        .setConstantHeadingInterpolation(Math.toRadians(-90))
                        .build()))
                .andThen(robot.getPusher().offCommand());
    }
}
