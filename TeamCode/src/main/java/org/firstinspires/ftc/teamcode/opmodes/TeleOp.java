package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.hardware.pedroPathing.constants.LConstants;

import java.util.Locale;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot;
    GamepadEx controller1;
    GamepadEx controller2;
    public boolean EXTENDED = false;
    public static int HANGUP = 0;
    public static int HANGDOWN = -4600;
    public static int HANGAUTO = 7000;


    @Override
    public void runOpMode() throws InterruptedException {
        this.robot = new Robot().init(hardwareMap,true);
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot.getFollower().startTeleopDrive();

        while (opModeInInit()) {
            controller1.readButtons();
            controller2.readButtons();
            getTeam();
            robot.wrist.intake();
            telemetry.addData("Team:", robot.team);
            telemetry.update();
            Constants.setConstants(FConstants.class, LConstants.class);

        }

        while (opModeIsActive()){
            //drive
//            robot.mecDrive.setInput(controller1);
//            robot.limelight.enableLimeligh();
//            robot.getFollower().startTeleopDrive();
            robot.getFollower().setTeleOpMovementVectors(gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
            robot.getFollower().update();
//            robot.mecDrive.up
            controller1.readButtons();
            controller2.readButtons();

            robot.intakeMacro(controller1,getRuntime(),false);

            if (controller1.isDown(GamepadKeys.Button.LEFT_STICK_BUTTON)){
                robot.slides.slidesL.setTargetPosition(-600);
                robot.slides.slidesL.setPower(.3);
                robot.slides.slidesR.setTargetPosition(-600);
                robot.slides.slidesR.setPower(.3);
            } else if (controller1.wasJustReleased(GamepadKeys.Button.LEFT_STICK_BUTTON)){
                robot.getSlides().slidesL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.getSlides().slidesR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getSlides().slidesL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.getSlides().slidesR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                robot.scoringMacro(controller1, getRuntime(), false);
            }
            robot.update();

            if(controller1.isDown(GamepadKeys.Button.DPAD_LEFT)) {
                robot.intake.sweep();
            } else {
                robot.intake.stow();
            }

            //Telemetry
            int PositionLeft = this.robot.getSlides().slidesL.getCurrentPosition();
            telemetry.addData("Slide Pos L", (PositionLeft));
            int PositionRight = this.robot.getSlides().slidesR.getCurrentPosition();
            telemetry.addData("Slide Pos R", (PositionRight));
            telemetry.addData("alpha:", robot.intake.getAlpha(robot.intake.intakeSensor));
            telemetry.addData("COLOR:", robot.getIntake().sampleColor);
            telemetry.addData("INTAKEMACRO:", robot.intakeState);
            telemetry.addData("SCORING STATE: ", robot.scoringState);
            telemetry.addData("SpecStates: ", robot.specStep);
            telemetry.addData("R", (robot.getIntake().intakeSensor.red()));
            telemetry.addData("G", (robot.getIntake().intakeSensor.green()));
            telemetry.addData("B", (robot.getIntake().intakeSensor.blue()));
            telemetry.addData("LF", (robot.getMecDrive().leftFront.getCurrent(CurrentUnit.AMPS)));
            telemetry.addData("RF", (robot.getMecDrive().rightFront.getCurrent(CurrentUnit.AMPS)));
            telemetry.addData("LB", (robot.getMecDrive().leftBack.getCurrent(CurrentUnit.AMPS)));
            telemetry.addData("RB", (robot.getMecDrive().rightBack.getCurrent(CurrentUnit.AMPS)));
            telemetry.addData("LS", (robot.slides.slidesL.getCurrent(CurrentUnit.AMPS)));
            telemetry.addData("RS", (robot.slides.slidesR.getCurrent(CurrentUnit.AMPS)));

//            telemetry.addData("color", (robot.getIntake().getColor(robot.getIntake().intakeSensor));
//            telemetry.addData("hang", (robot.getHang().depression.getCurrentPosition()));
//            telemetry.addData("Tx",(robot.limelight.getSampleTx()));
//            telemetry.addData("Ty",robot.limelight.getSampleTY());
//            telemetry.addData("Corrected Pose",robot.calcCorrection(robot.limelight.getSampleTx(),robot.limelight.getSampleTY()));

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