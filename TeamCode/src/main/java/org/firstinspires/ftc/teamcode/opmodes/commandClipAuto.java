package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Twist2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Claw;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "clipAutoCommand", preselectTeleOp = "Main Teleop")
public class commandClipAuto extends CommandOpMode {
    Robot robot;
    Pose2d start = new Pose2d(0, 0, Math.toRadians(180));
    Vector2d toBar = new Vector2d(34, 0);
    Pose2d toPickup = new Pose2d(30,-35,Math.toRadians(0));
    Pose2d toWall = new Pose2d(10,-36,Math.toRadians(-2));
    Pose2d toBar2 = new Pose2d(20,3,Math.toRadians(180));
    Vector2d toPark = new Vector2d(0,-36);
    TrajectoryActionBuilder builder;


    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot().init(hardwareMap,new Pose2d(0,0,Math.toRadians(-90)));
        builder = robot.getDrive().actionBuilder(robot.getDrive().pose);
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        foo().schedule();
//        move().schedule();
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
            telemetry.addData("target",robot.getSlides().getTarget());
            telemetry.addData("current pos",robot.getSlides().getPos());
            telemetry.update();
        }
    }
    Command move(){
        return robot.getClaw().closeCommand().andThen(clip()).andThen(placeBlock1()).andThen(placeBlock2()).andThen(Clip2(true)).andThen(Clip3(false).andThen(grab()));
    }
    Command clip(){
        return robot.getSlides().preclip()
                .alongWith(robot.runAction(builder.lineToX(34).build()))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command placeBlock1(){
//        return robot.getSlides().down()
                //.alongWith(
        return robot.runAction(robot.getDrive().actionBuilder(
                new Pose2d(toBar,Math.toRadians(180)))
                .lineToX(22).setTangent(Math.toRadians(-90))
                .lineToYLinearHeading(-35,Math.toRadians(0))
                .build());
        //)
//                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(22,0,Math.toRadians(180))).build()));
//                .andThen(robot.getPusher().activateCommand())
//                .andThen(new WaitCommand(50))
//                .andThen(robot.runAction(robot.getDrive().actionBuilder(toPickup).turn(Math.toRadians(-90),new TurnConstraints(7,-Math.PI,Math.PI)).setTangent(Math.toRadians(-162)).lineToX(-10).build()));
    }

    Command foo() {
        return robot.runAction(robot.getDrive().actionBuilder(
                new Pose2d(0,-38, Math.toRadians(-90)))
                        .splineToSplineHeading(new Pose2d(35, -50, Math.toRadians(90)), Math.toRadians(-90))
                .build());
    }

    Command placeBlock2(){
        return robot.runAction(robot.getDrive().actionBuilder(new Pose2d(-10,-45,Math.toRadians(-90))).setTangent(0).lineToX(55).build())
                .alongWith(new WaitCommand(600)
                .andThen(robot.getPusher().offCommand()))
                .andThen(robot.getPusher().activateCommand())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(new Pose2d(35,-45,Math.toRadians(-90))).setTangent(0).lineToX(-10).build()));
    }
    Command grab(){
        return robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,-42,Math.toRadians(180))).splineToLinearHeading(toWall,Math.toRadians(50)).build())
                .andThen(robot.getSlides().down())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).setTangent(Math.toRadians(0)).lineToX(-4).build()))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw());
    }
    Command Clip2(boolean first){
        return robot.runAction(robot.getDrive().actionBuilder(new Pose2d(0,-42,Math.toRadians(180))).splineToLinearHeading(toWall,Math.toRadians(50)).build())
                .alongWith(robot.getSlides().down())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).setTangent(Math.toRadians(0)).lineToX(-4).build()))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(600))
                .andThen(robot.getSlides().preclip()
                .alongWith(robot.runAction(robot.getDrive().actionBuilder(toWall).splineToLinearHeading(toBar2.plus(new Twist2d(new Vector2d(0,first ? 0 : 4),0)),Math.toRadians(-110)).build())))
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toBar2).setTangent(Math.toRadians(190)).lineToX(34.5).build()))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command Clip3(boolean first){
        return robot.runAction(robot.getDrive().actionBuilder(toBar2).splineToLinearHeading(toWall,Math.toRadians(-50)).build())
                .alongWith(robot.getSlides().down())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toWall).setTangent(Math.toRadians(0)).lineToX(-4).build()))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(600))
                .andThen(robot.getSlides().preclip()
                        .alongWith(robot.runAction(robot.getDrive().actionBuilder(toWall).splineToLinearHeading(toBar2.plus(new Twist2d(new Vector2d(0,first ? 0 : 4),0)),Math.toRadians(-110)).build())))
                .andThen(robot.getSlides().preclip())
                .andThen(robot.runAction(robot.getDrive().actionBuilder(toBar2).setTangent(Math.toRadians(190)).lineToX(34.5).build()))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command park(){
        return robot.runAction(builder.splineToConstantHeading(toPark,Math.toRadians(90)).build())
                .alongWith(robot.getSlides().down());
    }
}
