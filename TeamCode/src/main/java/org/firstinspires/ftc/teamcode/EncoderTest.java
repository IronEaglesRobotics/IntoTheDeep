package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class EncoderTest extends OpMode {
    private DcMotor motor;
    double ticks = 384.5;
    double newTarget;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        telemetry.addData("Hardware", "Initialized");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            encoder(2);
        }
        telemetry.addData("Motor Ticks: ", motor.getCurrentPosition());
        if(gamepad1.b){
            tracker();
        }
    }
    public void encoder(int turn){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        newTarget = ticks/turn;
        motor.setTargetPosition((int)newTarget);
        motor.setPower(0.5);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void tracker() {
        motor.setTargetPosition(0);
        motor.setPower(0.5);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
