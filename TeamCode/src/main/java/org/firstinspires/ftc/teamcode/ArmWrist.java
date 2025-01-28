package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class ArmWrist extends LinearOpMode {

    public static int elbowSpec;
    public static int elbowSub;
    public static int wristWall;
    public static double armSpec = 0.5;
    public static double armWall = 0.5;
    //public Servo elbow;
    public Servo arm1;
    public Servo arm2;

    @Override
    public void runOpMode() throws InterruptedException {
        // elbow = hardwareMap.servo.get("elbow");
        arm1 = hardwareMap.servo.get("arm1");
        arm1.setDirection(Servo.Direction.REVERSE);
        arm2 = hardwareMap.servo.get("arm2");

        waitForStart();

        while (opModeIsActive()) {


//        if (gamepad1.a){
//            elbow.setPosition(0);
//        }
//
//        if(gamepad1.y){
//
//            elbow.setPosition(elbowSub);
//        }

            if (gamepad1.a) {
                arm1.setPosition(0+0.02);
                arm2.setPosition(0);
            }

            if (gamepad1.y) {
                arm1.setPosition(armSpec+0.02);
                arm2.setPosition(armSpec);
            }

        }
    }
}
