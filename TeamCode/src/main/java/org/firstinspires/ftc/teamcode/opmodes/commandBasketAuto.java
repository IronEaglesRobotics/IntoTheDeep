package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "basket auto",preselectTeleOp = "Main Teleop")
public class commandBasketAuto extends CommandOpMode {
    Robot robot;
    boolean first = true;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot().init(hardwareMap,new Pose2d(0,0,Math.toRadians(90)));
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        score().andThen(pickUp1()).schedule();
        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
        }
    }
    Command score() {
        return robot.getSlides().up()
                .andThen(robot.getIntakeArm().upCommand()
                .alongWith(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,0,Math.toRadians(90))).splineToLinearHeading(new Pose2d(25,6,Math.toRadians(150)),Math.toRadians(90)).build())))
                .andThen(robot.getIntake().ejectIntake())
                .andThen(new WaitCommand(500))
                .andThen(robot.getIntake().reverseIntake())
                .andThen(new WaitCommand(1500));
    }
    Command pickUp1(){
        return robot.getIntake().offIntake()
                .andThen(new Intake.storeIntake(robot.getIntake()))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(robot.getSlides().down())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,12,Math.toRadians(90))).setTangent(Math.toRadians(0)).lineToX(10).turnTo(0).build()))
                .andThen(robot.getIntake().onIntake())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,12,0)).splineToConstantHeading(new Vector2d(33,first ? 12 : 22),Math.toRadians(first ? 90 : 20)).build()))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(33,first ? 12 : 22,0)).splineToLinearHeading(new Pose2d(0,0,Math.toRadians(90)),Math.toRadians(210)).build()))
                .whenFinished(()->first = false);
    }
    Command pickup2(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(robot.getSlides().down())
                .alongWith(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,12,Math.toRadians(45))).lineToX(10).turnTo(0).build()))
                .andThen(robot.getIntake().runIntake()
                .raceWith(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,12,0)).splineToConstantHeading(new Vector2d(20,22),Math.toRadians(60)).setTangent(Math.toRadians(45)).lineToY(26).build())))
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(24,26,0)).splineToLinearHeading(new Pose2d(0,0,Math.toRadians(90)),Math.toRadians(210)).build()));
    }
    Command park(){
        return robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,12,Math.toRadians(90))).splineToLinearHeading(new Pose2d(50,0,Math.toRadians(-90)),Math.toRadians(90)).build());
    }
}
