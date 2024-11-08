package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="PotentialAuto", group= "")
public class PotentialAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    public Servo leftHand;
    public Servo extendo;
    public Servo arm1;
    public Servo arm2;
    public Servo arm3;

    @Override
    public void runOpMode() {
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "lf");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "lb");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");
        leftHand = hardwareMap.servo.get("aS");
        leftHand.scaleRange(0,2);
        extendo = hardwareMap.servo.get("eX");
        arm1 = hardwareMap.servo.get("a1");
        arm2 = hardwareMap.servo.get("a2");
        arm3 = hardwareMap.servo.get("a3");

        waitForStart();
        if (opModeIsActive()) {

            while (opModeIsActive()) {




            }
        }
    }

}
