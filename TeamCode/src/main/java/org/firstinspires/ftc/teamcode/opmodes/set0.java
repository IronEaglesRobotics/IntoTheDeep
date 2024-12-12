package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
//tresrh
@Config
@TeleOp (name = "setO")
public class set0 extends OpMode {
    Servo Servo1;
    Servo Servo2;
    DcMotor motor;
    public static double servo1 = 0;
    public static double servo2 = 0;
    public static double lowscale2 = .325;
    // wrist .27
    public static double highscale2 = .93;
    public static double lowscale1 = 0;
    public static double highscale1 = .58;
    CRServo crServo;
    @Override
    public void init(){
        Servo1 = hardwareMap.get(Servo.class,"left_arm");
        Servo2 = hardwareMap.get(Servo.class,"right_arm");
        crServo = hardwareMap.get(CRServo.class,"crservo");
        motor = hardwareMap.get(DcMotor.class,"motor");
    }
    @Override
    public void loop()  {
        Servo1.scaleRange(lowscale1,highscale1);
        Servo2.scaleRange(lowscale2, highscale2);
        Servo2.setDirection(Servo.Direction.REVERSE);
        servo1 += gamepad1.left_stick_x;
        servo2 += gamepad1.left_stick_x;
        crServo.setPower(gamepad1.right_stick_x);
        servo1 = Math.max(Math.min(servo1,1),0);
        servo2 = Math.max(Math.min(servo2,1),0);
        Servo1.setPosition(servo1);
        Servo2.setPosition(servo2);
        telemetry.addData("servo1",servo1);
        telemetry.addData("servo2",servo2);
        telemetry.addData("motor",motor.getCurrentPosition());
    }
}

