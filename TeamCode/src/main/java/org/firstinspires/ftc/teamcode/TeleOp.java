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


//        if (gamepad1.y) {
//            //extendo out
//            robot.extendo.moveTo( 0);
//            robot.tiltRight.setPosition(0.2); //0.5
//            robot.tiltLeft.setPosition(0.2);
//
//        } else if (gamepad1.a) {
//            //extendo in & intake tilt up
//            robot.tiltRight.setPosition(0.2); //0
//            robot.tiltLeft.setPosition(0.2);
//            robot.extendo.moveTo(0.26);
//        } else if(gamepad1.left_trigger > 0) {
//            //intake tilt down & spin
//            robot.tiltRight.setPosition(.395); //0
//            robot.tiltLeft.setPosition(.395);
//            robot.intake.setSpin(-1);
//        } else if(gamepad1.right_trigger > 0) {
//            //intake tilt down & spinout
//            robot.tiltRight.setPosition(.395); //0
//            robot.tiltLeft.setPosition(.395);
//            robot.intake.setSpin(1);
//        }  else {
//            //when doing nothing intake doesn't spin & tilts up
//            robot.tiltRight.setPosition(0.2); //0.5
//            robot.tiltLeft.setPosition(0.2);
//            robot.intake.setSpin(0);
//
//        }

//        if(gamepad1.dpad_up){
//            //extend arm
//            robot.arm.setPosition(0.9);
//            robot.claw.setPosition(0);
//
//            //lift slides
//            robot.lift.setTargetPosition(600);
//            robot.lift.setMode(RUN_TO_POSITION);
//            robot.lift.setPower(0.25);
//        }
//
//        if(gamepad1.dpad_down){
//            //lowers slides?
//            robot.arm.setPosition(0.9);
//
//            robot.lift.setTargetPosition(250);
//            robot.lift.setMode(RUN_TO_POSITION);
//            robot.lift.setPower(0.75);
//            // robot.claw.setPosition(.2);
//
//            // robot.lift.setTargetPosition(100);
//            // robot.lift.setMode(RUN_TO_POSITION);
//            //  robot.lift.setPower(0.25);
//
//        }
//        if(gamepad1.dpad_right){
//            //
//            robot.arm.setPosition(0.9);
//            robot.elbow.setPosition(0);
//            robot.claw.setPosition(0);
//
//
//
//
//        }
//        if(gamepad1.dpad_left){
//            robot.claw.setPosition(0.2);
//            robot.arm.setPosition(1);
//
//            robot.lift.setTargetPosition(0);
//            robot.lift.setMode(RUN_TO_POSITION);
//            robot.lift.setPower(0.1);
//
//
//
//        }
        if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && STATE) {
            robot.claw.setPosition(.2);
            STATE = false;
        } else if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robot.claw.setPosition(0);
            STATE = true;
        }
        if(gamepad1.dpad_up){
            robot.lift.setTargetPosition(650);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(.50);
            robot.arm.setPosition(armScore);
            robot.wrist.setPosition(wristScore);
            robot.elbow.setPosition(elbow);
        }
        if(gamepad1.dpad_down) {
            robot.arm.setPosition(armRest);
            robot.lift.setTargetPosition(SLIDES_PICKUP);
            robot.arm.setPosition(armScore);
            robot.wrist.setPosition(wristScore);
            robot.elbow.setPosition(elbow);
        }
//        if(gamepad2.a){
//            robot.lift.setTargetPosition(0);
//            robot.lift.setMode(RUN_TO_POSITION);
//            robot.lift.setPower(.1);
//        }
//        if(gamepad2.b) {
//            robot.arm.setPosition(arm);
//            robot.elbow.setPosition(elbow);
//            robot.wrist.setPosition(0);
//        }
//        if(gamepad2.x){
//            robot.claw.setPosition(0);
//            robot.arm.setPosition(0);
//            robot.elbow.setPosition(0);
//            robot.wrist.setPosition(0);
//        }


    }

    public void intakeMacro(GamepadEx cont1) {
        switch (intakeSteps) {
            case 1: //Idle state
                //Actions
                robot.tiltRight.setPosition(TILTUP); //0.5
                robot.tiltLeft.setPosition(TILTUP);
                robot.extendo.moveTo(EXTENDO_RETRACT);

                //switch to extended state
                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
                    intakeSteps++;
                } else if (cont1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3) { //BUTTON
                    robot.intake.setSpin(-1);
                } else {
                    robot.intake.setSpin(0);
                }
                break;
            case 2: // Extended state
                //Actions
                robot.extendo.moveTo(EXTENDO_EXTEND);

                //Switch to intaking state
                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
                    intakeSteps++;
                }
                break;
            case 3: //Intaking
                //actions
                robot.tiltRight.setPosition(TILTDOWN); //0.5
                robot.tiltLeft.setPosition(TILTDOWN);

                if (cont1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3) { //BUTTON
                    robot.intake.setSpin(-1);
                } else {
                    robot.intake.setSpin(1);
                }

                if (cont1.wasJustPressed(GamepadKeys.Button.Y)) { //BUTTON
                    intakeSteps = 1;
                }
                break;
        }

    }
}

//    public void scoreMacro(GamepadEx controller1) {
//        switch (scoreSteps){
//            case 1: //Idle state
//                //Actions
//
//                robot.arm.setPosition(armRest);
//                robot.lift.setTargetPosition(SLIDES_DOWN, .1);
//                robot.wrist.setPosition(wristRest);
//                robot.elbow.setPosition(elbow);
//                robot.claw.setPosition(clawOpen);
//
//                //put retracted normal positions here
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps++;
//                }
//
//                break;
//            case 2: // Extended state
//                //Actions
//                robot.lift.setTargetPosition(SLIDES_PICKUP,1);
//                //
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps++;
//                }
//                break;
//            case 3: //Intaking
//                //actions
//                robot.arm.setPosition(armScore);
//                robot.elbow.setPosition(elbowScore);
//                robot.wrist.setPosition(wristScore);
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps++;
//                }
//                break;
//            case 4: //Intaking
//                //actions
//                robot.claw.setPosition(clawClose);
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps++;
//                }
//                break;
//            case 5: //Intaking
//                //actions
//                robot.lift.setTargetPosition(SLIDES_SCORE1,1);
//                robot.elbow.setPosition(elbowScore2);
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps++;
//                }
//                break;
//            case 6: //Intaking
//                //actions
//                robot.elbow.setPosition(elbowScore2);
//                robot.lift.setTargetPosition(SLIDES_SCORE2,1);
//
//                if(controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
//                    scoreSteps = 1;
//                }
//                break;
//
//        }
//
//    }
//
//}