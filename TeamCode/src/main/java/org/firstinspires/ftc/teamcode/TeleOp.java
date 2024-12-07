package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.PublicKey;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    Robot robot;

    GamepadEx controller1;
    GamepadEx controller2;
    boolean STATE = true;

    // get drivetrain working
    //get arm init working
    //get specimen scoring working
    //auto (only after everything else)


    public int intakeSteps = 1;
    public int swingStep = 1;
    public double swingTimer = 0;

    public static int SLIDES_DOWN = 0;
    public static int SLIDES_PICKUP = 500;
    public static int SLIDES_SCORE1 = 1900;
    public static int SLIDES_HIGH_BUCKET = 3000;
    public static int SLIDES_SCORE2 = 1550;

    public static double armPickUp = 0.05;
    public static double armFloor = 0.01;
    public static double armInit = 0.25;
    public static double armBucket = 0.25;
    public static double armSpec = 0.05;
    public static double armScore = 0.03;
    public static double clawOpen = 0.5;
    public static double clawInit = 0.05;
    public static double clawClose = 0;
    public static double wristFloor;
    public static double wristScore;


    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);

        robot.arm.setPosition(armInit);
        robot.claw.setPosition(clawInit);


    }


    @Override
    public void loop() {

        controller1.readButtons();
        controller2.readButtons();

        robot.drive.handleInput(gamepad1);
        //intakeMacro(controller1);
        //scoreMacro(controller1);
        telemetry.addData("scoreStep", swingStep);
        telemetry.addData("intakeStep", intakeSteps);
        telemetry.update();

        if (gamepad2.dpad_up) {
            robot.lift.setTargetPosition(SLIDES_HIGH_BUCKET, 0.5);
            robot.arm.setPosition(armBucket);
            //robot.wrist.setPosition(wristScore);
        }

        if (gamepad2.dpad_down) {
            robot.lift.setTargetPosition(SLIDES_DOWN, 1);
         //   robot.claw.setPosition(clawClose);
            robot.arm.setPosition(armFloor);
            // robot.wrist.setPosition(wristFloor);
        }

        if (gamepad2.dpad_left) {
            robot.lift.setTargetPosition(SLIDES_PICKUP, 0.2);
            robot.arm.setPosition(armPickUp);
        }

        if(gamepad2.y){
            robot.lift.setTargetPosition(SLIDES_SCORE2, 0.5);
            robot.arm.setPosition(armScore);
        }

//        swingMacro(controller2);

//        if (){


//        }


        if (gamepad2.dpad_right) {
            robot.lift.setTargetPosition(SLIDES_SCORE1, 0.5);
            robot.arm.setPosition(armScore);
        }

        if (gamepad2.a) {
            robot.claw.setPosition(clawOpen);
        } else if (gamepad2.b) {
            robot.claw.setPosition(clawClose);
        }

    }}

//    public void swingMacro(GamepadEx cont2){
//        switch (swingStep){
//            case 1:
//                //default
//                if(cont2.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    //lift uppy
//                    robot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                    robot.lift.setTargetPosition(SLIDES_PICKUP,0.2); // up?
////                    swingTimer = getRuntime() +
//                    swingStep++;
//                }
//                break;
//            case 2:
//                if (robot.lift.lift.getCurrentPosition() > 800) {
//                    robot.arm.setPosition(armSpec); // somewhere
//                    swingStep = 1;
//                }
//        }
//    }


//    public void intakeMacro(GamepadEx cont1) {
//        switch (intakeSteps) {
//            case 1: //Idle state
//                //Actions
//
//                break;
//            case 2: // Extended state
//                //Actions
//
//                //Switch to intaking state
//                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
//                    intakeSteps++;
//                }
//                break;
//            case 3: //Intaking
//                //actions
//
//                if (cont1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3) { //BUTTON
//
//                } else {
//
//                }
//
//                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
//                    intakeSteps = 1;
//                }
//                break;
//        }
//
//    }
