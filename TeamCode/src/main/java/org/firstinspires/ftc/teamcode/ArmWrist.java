package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class ArmWrist extends LinearOpMode {

    public static double elbowSpec;
    public static double elbowSub = 0.1;
    public static double wristWall;
    public static double armSpec = 0.5;
    public static double armWall = 0.5;
    public static double armdown = 0;
    public static double armInit = 0.5;
    public static double elbowInit = 0.8;
    public Servo elbow;
    public Servo arm1;
    public Servo arm2;
    public static double KP = 1.2;
    public static double KD = 0;
    public PDController armcontroller;



    @Override
    public void runOpMode() throws InterruptedException {

         elbow = hardwareMap.servo.get("elbow");
        arm1 = hardwareMap.servo.get("arm1");
        arm1.setDirection(Servo.Direction.REVERSE);
        arm2 = hardwareMap.servo.get("arm2");

        armcontroller = new PDController(KP, KD);

        waitForStart();

        while (opModeIsActive()) {


        if (gamepad1.b){
            elbow.setPosition(elbowSpec);
        }

        if(gamepad1.x){

            elbow.setPosition(elbowSub);
        }

            if (gamepad1.a) {
                arm1.scaleRange(0, .2);
                arm2.scaleRange(0, .2);
                arm1.setPosition(arm1.getPosition() - .01);
                arm2.setPosition(arm2.getPosition() - .01);
            }

            if (gamepad1.y) {
                arm1.scaleRange(0, .1);
                arm2.scaleRange(0, .1);
                arm1.setPosition(arm1.getPosition() + .01);
                arm2.setPosition(arm2.getPosition() + .01);
            }

        }
    }
}
