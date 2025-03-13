package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.localization.Pose;
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
        robot = new Robot().init(hardwareMap,null,new Pose(0,0,Math.toRadians(90)));
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        score().andThen(pickUp1()).andThen(score()).andThen(pickUp2()).andThen(score()).andThen(park()).schedule();
        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
        }
    }
    Command score() {
        return robot.getSlides().up()
                .andThen(robot.follow(robot.EZ().moveToWithHeading(26,6,Math.toRadians(135))))
                .andThen(robot.getIntakeArm().upCommand())
                .andThen(robot.getIntake().ejectIntake())
                .andThen(new WaitCommand(1000))
                .andThen(robot.getIntake().reverseIntake())
                .andThen(new WaitCommand(1500));
    }
    Command pickUp1(){
        return robot.getIntake().offIntake()
                .andThen(new Intake.storeIntake(robot.getIntake()).alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(14,13,0)))
                .andThen(robot.getIntake().onIntake())
                .andThen(new WaitCommand(200))
                .andThen(robot.follow(robot.EZ().moveTo(24,13)))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.getIntake().storeIntake());
    }
    Command pickUp2(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntake().storeIntake().alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(500))
                .andThen(robot.getSlides().down())
                .alongWith(robot.follow(robot.EZ().moveToWithHeading(14,23,0)))
                .andThen(robot.getIntake().onIntake())
                .andThen(robot.follow(robot.EZ().moveTo(24,23)))
                .andThen(robot.getIntake().offIntake())
                .andThen(robot.getIntake().storeIntake());
    }
    Command pickup3(){
        return robot.getIntake().offIntake()
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(robot.getSlides().down())
                //movement
                .andThen(robot.getIntake().runIntake());
                //movement
    }
    Command park(){
        return robot.getIntake().offIntake()
                .andThen(new Intake.storeIntake(robot.getIntake()).alongWith(new WaitCommand(400)))
                .andThen(robot.getIntakeArm().downCommand())
                .andThen(new WaitCommand(1000))
                .andThen(robot.getSlides().down())
                .andThen(robot.follow(robot.EZ().turn(Math.toRadians(215))));
    }
}
