package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.BIND_CLAW_DOWN;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_CLAW_LEFT;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_CLAW_RIGHT;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_CLAW_UP;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class block_arm {
    boolean claw_open,is_90,is_180 = false;
    Servo Claw, Claw_rot, Main_rot1, Main_rot2;
    double claw, claw_rot, main_rot = 0;
    public Slides slides;
    public enum Position {pickup,score,wall, preclip,postclip}

    public block_arm Init(HardwareMap HardwareMap){
        Claw = HardwareMap.get(Servo.class,"block_claw");
        Claw_rot = HardwareMap.get(Servo.class,"claw_rot");
        Main_rot1 = HardwareMap.get(Servo.class,"left_arm");
        Main_rot2 = HardwareMap.get(Servo.class,"right_arm");
        Main_rot2.setDirection(Servo.Direction.REVERSE);
        slides = new Slides(HardwareMap);

        return this;
    }
    public void toggle_claw(){
        claw_open = !claw_open;
        claw = claw_open ? 1 : 0;
    }
    public void toggle_claw(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.A)){
            toggle_claw();
        }
    }
    public void rotate_claw(){
        is_90 = !is_90;
        claw_rot = is_90 ? 0 : .5;
    }
    public void rotate_claw(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.B)){
            rotate_claw();
        }
    }

    public void rotate_arm (){
        is_180 = !is_180;
        main_rot = is_180 ? .05 : .55;
    }
    public void rotate_arm(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(GamepadKeys.Button.X)){
            rotate_arm();
        }
    }
    public void set_grab(Position pos){
        if (pos == Position.pickup){
            slides.setTarget(Slides.Position.DOWN);
            main_rot = 1;
            rotate_claw();
            if (!claw_open){
                toggle_claw();
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
        update_claws();
    }
    /*public void clip(GamepadEx gamepadEx){
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)){
            set_grab(Position.postclip);
        } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.B)){
            toggle_claw();
        }
        update_claws();
    }*/
    public void update_claws(){
        Claw.setPosition(claw);
        Claw_rot.setPosition(claw_rot);
        Main_rot1.setPosition(main_rot);
        Main_rot2.setPosition(main_rot);
    }
}
