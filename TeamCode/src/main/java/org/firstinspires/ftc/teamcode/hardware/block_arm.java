package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class block_arm {
    boolean claw_open = false;
    Servo Claw, Claw_rot, Main_rot;
    double claw, claw_rot, main_rot = 0;
    public Slides slides;
    public enum Position {pickup,score,wall, preclip,postclip}
    public enum Claw_pos {up,right,left,down}
    Claw_pos pos;

    public block_arm Init(HardwareMap HardwareMap){
        Claw = HardwareMap.get(Servo.class,"block_claw");
        Claw_rot = HardwareMap.get(Servo.class,"claw_rot");
        Main_rot = HardwareMap.get(Servo.class,"main_rot");
        slides = new Slides(HardwareMap);

        return this;
    }
    public void toggle_claw(){
        claw_open = !claw_open;
        claw = claw_open ? 0 : 1;
    }
    public void toggle_claw(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.A)){
            toggle_claw();
        }
    }
    public void rotate_claw (GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.DPAD_UP)){
            pos = Claw_pos.up;
        } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.DPAD_DOWN)){
            pos = Claw_pos.down;
        } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)){
            pos = Claw_pos.left;
        } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)){
            pos = Claw_pos.right;
        }
        if (pos == Claw_pos.up){
            claw_rot = .5;
        }   else if (pos == Claw_pos.right){
            claw_rot = .5;
        } else if (pos == Claw_pos.left){
            claw_rot = .5;
        } else if (pos == Claw_pos.down){
            claw_rot = .5;
        }
    }
    public void rotate_claw (Claw_pos pos) {
        if (pos == Claw_pos.up) {
            claw_rot = .5;
        } else if (pos == Claw_pos.right) {
            claw_rot = .5;
        } else if (pos == Claw_pos.left) {
            claw_rot = .5;
        } else if (pos == Claw_pos.down) {
            claw_rot = .5;
        }
    }
    public void rotate_arm (GamepadEx gamepadEx){
        main_rot += gamepadEx.getRightX();
        main_rot = Math.min(Math.max(main_rot,0),1);
    }
    public void rotate_arm (Position pos){
        if (pos == Position.wall){
            main_rot = 0;
        } else if (pos == Position.score){
            main_rot = 0.5;
        } else if (pos == Position.pickup){
            main_rot = 1;
        }
    }
    public void set_grab(Position pos){
        if (pos == Position.pickup){
            slides.setTarget(Slides.Position.DOWN);
            main_rot = 1;
            this.rotate_claw(Claw_pos.up);
            if (!claw_open){
                this.toggle_claw();
            }
        } else if (pos == Position.wall){
            slides.setTarget(Slides.Position.DOWN);
            main_rot = 0;
            claw_rot = 0;
        } else if (pos == Position.score){
            slides.setTarget(Slides.Position.TIER4);
            main_rot = .5;
            claw_rot = 0;
        } else if (pos == Position.preclip){
            slides.setTarget(Slides.Position.PRECLIP);
            main_rot = 0;
            claw_rot = 0;
        } else if (pos == Position.postclip){
            slides.setTarget(Slides.Position.POSTCLIP);
            main_rot = 0;
            claw_rot = 0;
        }
    }
    public void clip(GamepadEx gamepadEx){
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)){
            set_grab(Position.postclip);
        } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.B)){
            toggle_claw();
        }
    }
    public void update_claws(){
        Claw.setPosition(claw);
        Claw_rot.setPosition(claw_rot);
        Main_rot.setPosition(main_rot);
    }
}
