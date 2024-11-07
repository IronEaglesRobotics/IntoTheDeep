package org.firstinspires.ftc.teamcode.hardware;

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

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class intake {
    Servo Rot1,Rot2,Eject;
    CRServo Beat_bar;
    ColorSensor c_sensor;
    double eject = eject_rot_out;
    double target_pos = 0;
    public static double rot1 = .58;
    color c_input = color.yellow;
    private AnalogInput beat_bar_pos;
    public enum color { red,blue,yellow}
    public static double KP = 0.1;
    public static double TOL = 0.01;
    public static double KD = 0.1;
    public static double MAX_DELTA = 0.75;
    //PControler
    public PDController armPDcontroller;
    public double armTarget;

    public intake Init(HardwareMap HardwareMap){
        Rot1 = HardwareMap.get(Servo.class,INTAKE_LEFT);
        Rot2 = HardwareMap.get(Servo.class,INTAKE_RIGHT);
        Eject = HardwareMap.get(Servo.class,EJECT);
        Beat_bar = HardwareMap.get(CRServo.class,BEAT_BAR);
        c_sensor = HardwareMap.get(ColorSensor.class,COLOR_SENSOR);
        beat_bar_pos = HardwareMap.get(AnalogInput.class,"servo_encoder");
        Rot2.setDirection(Servo.Direction.REVERSE);
        this.armPDcontroller = new PDController(KP,KD);

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
    public void beatbar_on(){
        Beat_bar.setPower(1);
    }
    public void beatbar_off(){
        Beat_bar.setPower(0);
    }
    public void intake_up() throws InterruptedException {
        rot1 = lower_rot_in;
        update_servo();
        sleep(1000);
        rot1 = .58;
    }
    public String getstring(){
        return armTarget + " , " + Rot1.getPosition() + " , " + armPDcontroller.calculate(Rot1.getPosition());
    }
    public void intake_up(GamepadEx gamepadEx) throws InterruptedException {
        if (gamepadEx.wasJustReleased(BIND_INTAKE_UP)) intake_up();
        update_servo();
    }
    public void intake_lower() throws InterruptedException {
        rot1 = lower_rot_out;
        update_servo();
        sleep(500);
        rot1 = 0.12;
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
    public boolean isAtTarget() {
        return this.armPDcontroller.atSetPoint();
    }

    public void update_servo() {
        Rot1.setPosition(rot1);
        Rot2.setPosition(rot1);
        Eject.setPosition(eject);
       /*if (beat_bar_pos.getVoltage() != target_pos) Beat_bar.setPower(-1);
        armPDcontroller.setSetPoint(armTarget);
        armPDcontroller.setTolerance(TOL);
        armPDcontroller.setP(KP);
        armPDcontroller.setD(KD);

        if (!isAtTarget()) {
            double delta = armPDcontroller.calculate(Rot1.getPosition());
            if (Math.abs(armPDcontroller.getPositionError()) > .1) {
                delta = Math.min(Math.copySign(MAX_DELTA, delta), delta);
            }
            Rot1.setPosition(Rot1.getPosition() + delta);
            Rot2.setPosition(Rot1.getPosition() + delta);
        }*/
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