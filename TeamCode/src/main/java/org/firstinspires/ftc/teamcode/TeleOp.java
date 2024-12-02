package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.security.PublicKey;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    Robot robot;

    GamepadEx controller1;
    boolean STATE = true;

    // get drivetrain working
    //get arm init working
    //get specimen scoring working
    //auto (only after everything else)


    public int intakeSteps = 1;
    public int scoreSteps = 1;
//    public double intakeTimer;


    public static double arm;
    public static double elbow = 0;
    public static double tilt;

    public static double EXTENDO_EXTEND = 0;
    public static double EXTENDO_RETRACT = .26;


    public static double TILTUP = 0;
    public static double TILTDOWN = .175;


    public static int SLIDES_DOWN = 0;
    public static int SLIDES_PICKUP = 175;
    public static int SLIDES_SCORE1 = 650;
    public static int SLIDES_SCORE2 = 400;

    public static double armScore = 1;
    public static double armRest = 0.7;
    public static double elbowScore = 0.55;
    public static double elbowScore2 = 0.55;
    public static double elbowRest = 0;
    public static double wristScore = .7;
    public static double wristRest = .7;
    public static double clawOpen = .3;
    public static double clawClose = 0;


    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
        controller1 = new GamepadEx(gamepad1);

//        robot.extendo.moveTo(0.26);
//        robot.tiltRight.setPosition(0.1);
//        robot.tiltLeft.setPosition(0.1);

//        robot.arm.setPosition(arm);
//        robot.elbow.setPosition(elbow);
//        robot.wrist.setPosition(0);


    }


    @Override
    public void loop() {

        controller1.readButtons();
        robot.drive.handleInput(gamepad1);
        intakeMacro(controller1);
        //scoreMacro(controller1);
        telemetry.addData("scoreStep", scoreSteps);
        telemetry.addData("intakeStep", intakeSteps);
        telemetry.update();

    }

    public void intakeMacro(GamepadEx cont1) {
        switch (intakeSteps) {
            case 1: //Idle state
                //Actions

                break;
            case 2: // Extended state
                //Actions

                //Switch to intaking state
                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
                    intakeSteps++;
                }
                break;
            case 3: //Intaking
                //actions

                if (cont1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3) { //BUTTON

                } else {

                }

                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
                    intakeSteps = 1;
                }
                break;
        }

    }
}