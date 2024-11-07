package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    Robot robot;

    @Override
    public void init() {
        this.robot = new Robot(this.hardwareMap);
    }


    @Override


    public void loop() {
//        robot.drive.handleInput(this.gamepad1, this.gamepad2);

//        if (gamepad1.y) {
//            //extendo out
//            robot.extendo.moveTo(0.4);
//            robot.tiltRight.setPosition(0); //0.5
//            robot.tiltLeft.setPosition(0);
//
//        }
//
//        if (gamepad1.a) {
//            //extendo in & intake tilt up
//            robot.tiltRight.setPosition(0); //0
//            robot.tiltLeft.setPosition(0);
//            robot.extendo.moveTo(0);
//        }
//
//        if(gamepad1.left_trigger > 0) {
//            //intake tilt down & spin
//            robot.tiltRight.setPosition(0.18); //0
//            robot.tiltLeft.setPosition(0.18);
//            robot.intake.setSpin(1);
//        }
//
//        else {
//            //when doing nothing intake doesn't spin & tilts up
//            robot.intake.setSpin(0);
//            robot.tiltRight.setPosition(0);
//            robot.tiltLeft.setPosition(0); //0.5
//        }

        if(gamepad1.b){
            robot.lift.setTargetPosition(50);
        //    robot.lift.setPower(0.1);

        }

        if(gamepad1.dpad_up){
            robot.arm.setPosition(1);
        }

        if(gamepad1.dpad_down){
            robot.elbow.setPosition(0);
        }
        if(gamepad1.dpad_right){
            robot.wrist.setPosition(0);
        }
        if(gamepad1.dpad_left){
            robot.elbow.setPosition(1);
        }
    }
}