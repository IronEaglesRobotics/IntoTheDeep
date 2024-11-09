package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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

    public static double arm;
    public static double elbow;
    public static double tilt;

    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
        controller1=new GamepadEx(gamepad2);

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

        if (gamepad1.y) {
            //extendo out
            robot.extendo.moveTo( 0);
            robot.tiltRight.setPosition(0.2); //0.5
            robot.tiltLeft.setPosition(0.2);

        } else if (gamepad1.a) {
            //extendo in & intake tilt up
            robot.tiltRight.setPosition(0.2); //0
            robot.tiltLeft.setPosition(0.2);
            robot.extendo.moveTo(0.26);
        } else if(gamepad1.left_trigger > 0) {
            //intake tilt down & spin
            robot.tiltRight.setPosition(.395); //0
            robot.tiltLeft.setPosition(.395);
            robot.intake.setSpin(-1);
        } else if(gamepad1.right_trigger > 0) {
            //intake tilt down & spinout
            robot.tiltRight.setPosition(.395); //0
            robot.tiltLeft.setPosition(.395);
            robot.intake.setSpin(1);
        }  else {
            //when doing nothing intake doesn't spin & tilts up
            robot.tiltRight.setPosition(0.2); //0.5
            robot.tiltLeft.setPosition(0.2);
            robot.intake.setSpin(0);

        }

        if(gamepad1.dpad_up){
            robot.arm.setPosition(0.9);
            robot.claw.setPosition(0);

            robot.lift.setTargetPosition(600);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(0.25);
        }

        if(gamepad1.dpad_down){
            robot.arm.setPosition(0.9);

            robot.lift.setTargetPosition(250);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(0.75);
            // robot.claw.setPosition(.2);

            // robot.lift.setTargetPosition(100);
            // robot.lift.setMode(RUN_TO_POSITION);
            //  robot.lift.setPower(0.25);

        }
        if(gamepad1.dpad_right){
            robot.arm.setPosition(0.9);
            robot.elbow.setPosition(0);
            robot.claw.setPosition(0);




        }
        if(gamepad1.dpad_left){
            robot.claw.setPosition(0.2);
            robot.arm.setPosition(1);

            robot.lift.setTargetPosition(0);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(0.1);



        }
        if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && STATE) {
            robot.claw.setPosition(.2);
            STATE = false;
        } else if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robot.claw.setPosition(0);
            STATE = true;
        }
        if(gamepad2.y){
            robot.lift.setTargetPosition(300);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(-.25);
        }
        if(gamepad2.a){
            robot.lift.setTargetPosition(0);
            robot.lift.setMode(RUN_TO_POSITION);
            robot.lift.setPower(.1);
        }
        if(gamepad2.b) {
            robot.arm.setPosition(arm);
            robot.elbow.setPosition(elbow);
            robot.wrist.setPosition(0);
        }
        if(gamepad2.x){
            robot.claw.setPosition(0);
            robot.arm.setPosition(0);
            robot.elbow.setPosition(0);
            robot.wrist.setPosition(0);
        }

    }
}