package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.lib.Config.BL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FR_WHEEL;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Slides;

@TeleOp(name = "presentation_op")
public class presentation_op extends OpMode {
    //Drive drive;
    private DcMotor fl, fr, bl, br;
    Servo Servo1;
    Servo Servo2;
    double servo1 = .7;
    double servo2 = .7;
    DcMotor Motor1;
    DcMotor Motor2;
    int target_pos = 0;
    int motor1;
    int motor2;
    Slides slides;

    private double increment = 0.000001;
    private boolean buttonADown = false;
    private boolean buttonBDown = false;
    private boolean buttonXDown = false;
    private boolean rightBumperDown = false;

    @Override
    public void init(){
        fl = hardwareMap.get(DcMotor.class, FL_WHEEL);
        fr = hardwareMap.get(DcMotor.class, FR_WHEEL);
        bl = hardwareMap.get(DcMotor.class, BL_WHEEL);
        br = hardwareMap.get(DcMotor.class, BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        Motor1 = hardwareMap.get(DcMotor.class,"motor1");
        Motor2 = hardwareMap.get(DcMotor.class,"motor2");
        Servo1 = hardwareMap.get(Servo.class,"rot1");
        Servo2 = hardwareMap.get(Servo.class,"rot2");
        Motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Servo2.setDirection(Servo.Direction.REVERSE);
        Servo1.setPosition(servo1);
        Servo2.setPosition(servo2);
        //drive = new Drive().Init(hardwareMap);
        slides = new Slides(hardwareMap);
    }

    @Override
    public void loop(){
        //drive.setDrive(controller,System.currentTimeMillis());
        double x =gamepad1.left_stick_x, y = -gamepad1.left_stick_y , z = gamepad1.right_stick_x;
        fl.setPower(((x + y + z)));
        fr.setPower(((-x + y - z)));
        bl.setPower(((-x + y + z)));
        br.setPower(((x + y - z)));

        servo1 += gamepad2.left_stick_x;
        servo2 += gamepad2.left_stick_x;
        target_pos += (int) (gamepad2.right_stick_y  * 50);
        servo1 = Math.max(Math.min(servo1,1),0);
        servo2 = Math.max(Math.min(servo2,1),0);
        target_pos = Math.max(Math.min(target_pos,60000),0);

        if (gamepad1.dpad_down) {
            slides.setTarget(Slides.Position.DOWN);
        } else if (gamepad1.dpad_up){
            slides.setTarget(Slides.Position.TIER3);
        } else if (gamepad1.dpad_left){
            slides.setTarget(Slides.Position.TIER2);
        } else if (gamepad1.dpad_right){
            slides.setTarget(Slides.Position.TIER1);
        }

        if (gamepad1.a && !buttonADown) {
            buttonADown = true;
            slides.controller.setP(slides.controller.getP()+increment);
        } else if (!gamepad1.a) {
            buttonADown = false;
        }

        if (gamepad1.b && !buttonBDown) {
            buttonBDown = true;
            slides.controller.setI(slides.controller.getI()+increment);
        } else if (!gamepad1.b) {
            buttonBDown = false;
        }

        if (gamepad1.x && !buttonXDown) {
            buttonXDown = true;
            slides.controller.setD(slides.controller.getD()+increment);
        } else if (!gamepad1.x) {
            buttonXDown = false;
        }

        if (gamepad1.right_bumper && !rightBumperDown) {
            rightBumperDown = true;
            increment = -increment;
        } else if (!gamepad1.right_bumper) {
            rightBumperDown = false;
        }

        slides.update(System.currentTimeMillis());
        //Servo1.setPosition(servo1);
        //Servo2.setPosition(servo2);

        /*if (target_pos > bl.getCurrentPosition() && bl.getCurrentPosition() > 0) {
            motor1 = 1;
            motor2 = 1;
            Motor1.setPower(.7);
            Motor2.setPower(.7);
        }
        else if(target_pos < bl.getCurrentPosition()){
            motor1 = 1;
            motor2 = 1;
            Motor1.setPower(-.7);
            Motor2.setPower(-.7);
        } else {
            motor1 = 0;
            motor2 = 0;
            Motor1.setPower(0);
            Motor2.setPower(0);
        }*/

        telemetry.addData("encoder",Motor1.getCurrentPosition());
        telemetry.addData("target",target_pos);
        telemetry.addData("servo1",servo1);
        telemetry.addData("servo2",servo2);
        telemetry.addData("motor1",motor1);
        telemetry.addData("motor2", motor2);
        telemetry.addData("motor2", slides.controller.calculate(Motor2.getCurrentPosition(), slides.target));
        telemetry.addData("increment", increment);
        telemetry.addData("PID", slides.controller.getP()+"", slides.controller.getI()+"", slides.controller.getD()+"");
        telemetry.update();
    }
}
