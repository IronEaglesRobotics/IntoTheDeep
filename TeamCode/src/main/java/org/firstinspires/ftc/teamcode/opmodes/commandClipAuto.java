package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous(name = "clipAutoCommand", preselectTeleOp = "Main Teleop")
public class commandClipAuto extends CommandOpMode {
    Robot robot;
    int stage = 0;
    Pose start = new Pose(0,0,0);

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
        clip().schedule();
        while (opModeIsActive() && !isStopRequested()){
            CommandScheduler.getInstance().run();
            robot.getFollower().update();
//            switch (stage){
//                case 0:
//                    if (!robot.getFollower().isBusy()){
//                        placeBlock1().schedule();
//                    }
//                    break;
//
//                case 1:
//                    if (!robot.getFollower().isBusy()){
//                        placeBlock2().schedule();
//                    }
//                    break;
//
//                case 2:
//                    if (!robot.getFollower().isBusy()){
//                        Clip2().schedule();
//                    }
//                    break;
//
//                case 3:
//                    if (!robot.getFollower().isBusy()){
//                        Clip3().schedule();
//                    }
//                    break;
//
//                case 4:
//                    if (!robot.getFollower().isBusy()){
//                        grab().schedule();
//                    }
//                    break;
//
//            }
            MultipleTelemetry telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
            telemetryA.addData("target",robot.getSlides().getTarget());
            telemetryA.addData("current pos",robot.getSlides().getPos());
            telemetryA.update();
            robot.getFollower().telemetryDebug(telemetryA);
        }

    }
//    Command move(){
//        return robot.getClaw().closeCommand()
//                .andThen(clip())
//                .andThen(placeBlock1())
//                .andThen(placeBlock2())
//                .andThen(Clip2())
//                .andThen(Clip3())
//                .andThen(grab());
//    }
    Command clip(){
        return robot.getSlides().preclip()
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(34,0,Math.toRadians(180))))
                .andThen(robot.getSlides().postclip())
                .andThen(new WaitCommand(200))
                .andThen(robot.getClaw().openCommand());
    }
    Command placeBlock1(){
        return robot.getSlides().down()
                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(34,-34,Math.toRadians(180),
                        22,-17)))
                .andThen(robot.getPusher().activateCommand())
                .andThen(new WaitCommand(50))
                .andThen(robot.follow(robot.EZ().turn(Math.toRadians(90))))
                .andThen(robot.follow(robot.EZ().moveTo(0,-44)));
    }

    Command placeBlock2(){
        return robot.follow(robot.EZ().moveTo(34,-44))
                .alongWith(new WaitCommand(600)
                .andThen(robot.getPusher().offCommand()))
                .andThen(robot.getPusher().activateCommand())
                .andThen(robot.follow(robot.EZ().moveTo(0,-44)));
    }
    Command grab(){
        return robot.getSlides().down()
                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(0,-36,0,
                        10,-36)))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw());
    }
    Command Clip2(){
        return robot.getSlides().down()
                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(0,-36,0,
                        10,-36)))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(600))
                .andThen(robot.getSlides().preclip())
                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(34,5,Math.toRadians(180),
                        25,5)))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
    Command Clip3(){
        return robot.getSlides().down()
                .andThen(robot.follow(robot.EZ().moveToViaWithHeading(0,-36,0,
                        10,-36)))
                .andThen(robot.getPusher().offCommand())
                .andThen(robot.getClaw().adaptClaw())
                .andThen(new WaitCommand(600))
                .andThen(robot.getSlides().preclip())
                .alongWith(robot.follow(robot.EZ().moveToViaWithHeading(34,3,Math.toRadians(180),
                        25,3)))
                .andThen(robot.getSlides().postclip())
                .andThen(robot.getClaw().openCommand());
    }
}
