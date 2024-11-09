package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot;
    GamepadEx controller1;
    public boolean EXTENDED = false;

    @Override
    public void runOpMode() throws InterruptedException {
        this.robot = new Robot().init(hardwareMap);
        controller1 = new GamepadEx(gamepad1);

        while (opModeInInit()) {
            controller1.readButtons();
            getTeam();
            telemetry.addData("Team:", robot.team);
            telemetry.update();
        }

        while (opModeIsActive()){
            //drive
            robot.getDrive().setInput(controller1);
            robot.getDrive().update();
            controller1.readButtons();

            robot.intakeMacro(controller1,getRuntime(),false);
            robot.scoringMacro(controller1,getRuntime(),false);

            robot.update();

            //Telemetry
            int PositionLeft = this.robot.getSlides().slidesL.getCurrentPosition();
            telemetry.addData("Slide Pos L", (PositionLeft));
            int PositionRight = this.robot.getSlides().slidesR.getCurrentPosition();
            telemetry.addData("Slide Pos R", (PositionRight));
            telemetry.addData("alpha:", robot.intake.getAlpha());
            telemetry.addData("INTAKEMACRO:", robot.intakeState);
            telemetry.addData("SCORING STATE: ", robot.scoringState);
            telemetry.addData("SpecStates: ", robot.specStep);
            telemetry.addData("R", (robot.getIntake().getR()));
            telemetry.addData("G", (robot.getIntake().getG()));
            telemetry.addData("B", (robot.getIntake().getB()));

            telemetry.update();

            //Intake color    R2
            //Intake Neutral  L2
            //Drive           Sticksu
            //Slow mode       Triangle
            //retract         L1
            //Score           R1
            //High basket     Dpad Up
            //Low basket      Dpad Left
            //High Bar        Triangle
            //Low Bar         Square
            //Intake Wall     Cross

        }

    }
    public void getTeam(){
        if (controller1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.team = "blue";
            robot.intake.targetColor = Robot.Intake.colors.BLUE;
            gamepad1.setLedColor(0,0,255,100000);
        } else if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
            robot.team = "red";
            robot.intake.targetColor = Robot.Intake.colors.RED;
            gamepad1.setLedColor(255,0,0,100000);
        }
    }
}