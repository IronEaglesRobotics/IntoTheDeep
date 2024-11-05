package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.BEATBAR_CHANGE;
import static org.firstinspires.ftc.teamcode.lib.Config.BEAT_BAR;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_BLUE;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_RED;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_COLOR_YELLOW;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_EJECT;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_LOWER;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_PICKUP;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_INTAKE_UP;
import static org.firstinspires.ftc.teamcode.lib.Config.COLOR_SENSOR;
import static org.firstinspires.ftc.teamcode.lib.Config.EJECT;
import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_LEFT;
import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_RIGHT;
import static org.firstinspires.ftc.teamcode.lib.Config.eject_rot_in;
import static org.firstinspires.ftc.teamcode.lib.Config.eject_rot_out;
import static org.firstinspires.ftc.teamcode.lib.Config.lower_rot_in;
import static org.firstinspires.ftc.teamcode.lib.Config.lower_rot_out;
import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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
    boolean in_lower = true;
    boolean is_180 = false;
    double eject = eject_rot_out;
    double target_pos = 0;
    double rot1 = lower_rot_in;
    color c_input = color.yellow;
    private double pickupTime = 0;
    private boolean pickingUp = false;
    private AnalogInput beat_bar_pos;
    public enum color { red,blue,yellow}

    public intake Init(HardwareMap HardwareMap){
        Rot1 = HardwareMap.get(Servo.class,INTAKE_LEFT);
        Rot2 = HardwareMap.get(Servo.class,INTAKE_RIGHT);
        Eject = HardwareMap.get(Servo.class,EJECT);
        Beat_bar = HardwareMap.get(CRServo.class,BEAT_BAR);
        c_sensor = HardwareMap.get(ColorSensor.class,COLOR_SENSOR);
        beat_bar_pos = HardwareMap.get(AnalogInput.class,"servo_encoder");
        Rot2.setDirection(Servo.Direction.REVERSE);

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
        update_servo();
    }
    public void control_beatbar(GamepadEx gamepad){
        Beat_bar.setPower(gamepad.getRightX());
    }
    public void toggle_beatbar(){
        target_pos += BEATBAR_CHANGE;
    }
    public void toggle_beatbar(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_INTAKE_PICKUP)){
            toggle_beatbar();
        }
    }
    public void intake_up() throws InterruptedException {
        rot1 = lower_rot_in;
        update_servo();
        sleep(1000);
        rot1 = .58;
    }
    public String getstring(){
        return in_lower + " , " + rot1;
    }
    public void intake_up(GamepadEx gamepadEx) throws InterruptedException {
        if (gamepadEx.wasJustReleased(BIND_INTAKE_UP)) intake_up();
        update_servo();
    }
    public void intake_lower() throws InterruptedException {
        rot1 = lower_rot_out;
        update_servo();
        sleep(500);
        rot1 = .2;
    }
    public void intake_lower(GamepadEx gamepadEx) throws InterruptedException {
        if (gamepadEx.wasJustReleased(BIND_INTAKE_LOWER)) intake_lower();
        update_servo();
    }
    public void eject() throws InterruptedException {
        eject = eject_rot_in;
        update_servo();
        sleep(500);
        eject = eject_rot_out;
    }
    public void setEject(GamepadEx gamepad1) throws InterruptedException{
        if (gamepad1.wasJustReleased(BIND_INTAKE_EJECT) && !(getcolor() == c_input)){
            eject();
        }
        update_servo();
    }
    public void update_servo(){
        Rot1.setPosition(rot1);
        Rot2.setPosition(rot1);
        Eject.setPosition(eject);
        //if (pos_filler != target_pos) Beat_bar.setPower(-1);
    }
    public void update_servo(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.RIGHT_BUMPER)) update_servo();
    }
    public double getBeatBarPos(){
        return beat_bar_pos.getVoltage();
    }
    public color getcolor(){
        int sensitivity = 30;
        color temp_color = color.yellow;
        if (c_sensor.blue()>c_sensor.red()+sensitivity) temp_color = color.blue;
        else if (c_sensor.green()>c_sensor.red()) temp_color = color.yellow;
        else if (c_sensor.red()>c_sensor.blue()+sensitivity) temp_color = color.red;

        return temp_color;
    }
}