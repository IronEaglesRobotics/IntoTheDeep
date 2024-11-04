package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.lib.Config.BEAT_BAR;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_BLUE;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_RED;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_YELLOW;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_LOWER;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_PICKUP;
import static org.firstinspires.ftc.teamcode.lib.Config.COLOR_SENSOR;
import static org.firstinspires.ftc.teamcode.lib.Config.EJECT;
import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_LEFT;
import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_RIGHT;
import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class intake {
    Servo Rot1,Rot2,Eject;
    CRServo Beat_bar;
    ColorSensor c_sensor;
    boolean beatbar_flipped;
    boolean in_lower = false;
    double rot1,rot2,eject = 0;
    Color_check color_check = new Color_check();
    color c_input = color.yellow;
    private double pickupTime = 0;
    private boolean pickingUp = false;
    private AnalogInput beat_bar_pos;

    public intake Init(HardwareMap HardwareMap){
        Rot1 = HardwareMap.get(Servo.class,INTAKE_LEFT);
        Rot2 = HardwareMap.get(Servo.class,INTAKE_RIGHT);
        Eject = HardwareMap.get(Servo.class,EJECT);
        Beat_bar = HardwareMap.get(CRServo.class,BEAT_BAR);
        c_sensor = HardwareMap.get(ColorSensor.class,COLOR_SENSOR);
        beat_bar_pos = HardwareMap.get(AnalogInput.class,"servo_encoder");
        Rot2.setDirection(Servo.Direction.REVERSE);
        Rot1.scaleRange(0,.6);
        Rot2.scaleRange(0,.6);

        return this;
    }
    public void setColor(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_COLOR_BLUE)){
            c_input = color.blue;
        } else if (gamepadEx.wasJustReleased(BIND_COLOR_RED)) {
            c_input = color.red;
        } else if (gamepadEx.wasJustReleased(BIND_COLOR_YELLOW)) {
            c_input = color.yellow;
        }
    }
    public void toggle_beatbar (GamepadEx gamepad){
        Beat_bar.setPower(gamepad.getRightX());
    }

    public boolean getbeatbar_pos(){
        return beatbar_flipped;
    }
    public void toggle_lower(){
        in_lower = !in_lower;
        rot1 = in_lower ? 1 : 0;
        rot2 = rot1;
    }
    public void Lower(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_INTAKE_LOWER)) toggle_lower();
        update_servo();
    }
    public void pickup(GamepadEx gamepadEx, double currentTime) throws InterruptedException {
        if (gamepadEx.wasJustReleased(BIND_INTAKE_PICKUP)){
            pickingUp = true;
            pickupTime = currentTime;
        }

        if (pickingUp) {
            if (currentTime - pickupTime < 1000) {
               // color_check.check(c_input);
            } else {
                pickingUp = false;
            }
        }
        update_servo();
    }
    void eject() throws InterruptedException {
        eject = 0;
        update_servo();
        sleep(1000);
        eject = 1;
    }
    public void setEject(double target){
        eject = target;
        update_servo();
    }
    void update_servo(){
        Rot1.setPosition(rot1);
        Rot2.setPosition(rot2);
        Eject.setPosition(eject);
    }
    public double getBeatBarPos(){
        return beat_bar_pos.getVoltage();
    }
}