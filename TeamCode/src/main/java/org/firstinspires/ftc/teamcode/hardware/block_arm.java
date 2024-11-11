package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.BIND_CLIP;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_ROTATE_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_SLIDES_CLIP;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_SLIDES_DOWN;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_SLIDES_HIGH;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_SLIDES_WALL;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_TOGGLE_CLAW;
import static org.firstinspires.ftc.teamcode.lib.Config.BLOCK_CLAW;
import static org.firstinspires.ftc.teamcode.lib.Config.CLAW_ROT;
import static org.firstinspires.ftc.teamcode.lib.Config.LEFT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.RIGHT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.block_claw_closed;
import static org.firstinspires.ftc.teamcode.lib.Config.block_claw_open;
import static org.firstinspires.ftc.teamcode.lib.Config.claw_rot_90;
import static org.firstinspires.ftc.teamcode.lib.Config.claw_rot_flat;
import static org.firstinspires.ftc.teamcode.lib.Config.main_rot_in;
import static org.firstinspires.ftc.teamcode.lib.Config.main_rot_init;
import static org.firstinspires.ftc.teamcode.lib.Config.main_rot_out;
import static org.firstinspires.ftc.teamcode.lib.Config.main_rot_score;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class block_arm {
    boolean claw_open,is_90,is_180 = false;
    Servo Claw, Claw_rot, Main_rot1, Main_rot2;
    double claw = block_claw_closed;
    double claw_rot = claw_rot_flat;
    double main_rot = main_rot_out;
    public Slides slides;
    public enum Position {pickup,score,wall, preclip,postclip, init}

    public block_arm Init(HardwareMap HardwareMap){
        Claw = HardwareMap.get(Servo.class,BLOCK_CLAW);
        Claw_rot = HardwareMap.get(Servo.class,CLAW_ROT);
        Main_rot1 = HardwareMap.get(Servo.class,LEFT_ARM);
        Main_rot2 = HardwareMap.get(Servo.class,RIGHT_ARM);
        Main_rot2.setDirection(Servo.Direction.REVERSE);
        slides = new Slides(HardwareMap);

        return this;
    }
    public void toggle_claw(){
        claw_open = !claw_open;
        claw = claw_open ? block_claw_open : block_claw_closed;
    }
    public void toggle_claw(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_TOGGLE_CLAW)){
            toggle_claw();
        }
    }
    public void rotate(){
        is_90 = !is_90;
        claw_rot = is_90 ? claw_rot_90 : claw_rot_flat;
        is_180 = !is_180;
        main_rot = is_180 ? main_rot_in : main_rot_out;
    }
    public void rotate(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_ROTATE_ARM)){
            rotate();
        }
    }

    /*public void rotate_arm (){
        is_180 = !is_180;
        main_rot = is_180 ? main_rot_in : main_rot_out;
    }
    public void rotate_arm(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_ROTATE_ARM)){
            rotate_arm();
        }
    }*/
    public void set_grab(Position pos){
        if (pos == Position.pickup){
            slides.setTarget(Slides.Position.DOWN);
            main_rot = main_rot_in;
            claw_rot = claw_rot_90;
            if (!claw_open){
                toggle_claw();
            }
        } else if (pos == Position.wall){
            slides.setTarget(Slides.Position.DOWN);
            main_rot = main_rot_out;
            claw_rot = claw_rot_flat;
        } else if (pos == Position.score){
            slides.setTarget(Slides.Position.TIER4);
            main_rot = main_rot_score;
            claw_rot = claw_rot_flat;
        } else if (pos == Position.preclip){
            slides.setTarget(Slides.Position.PRECLIP);
            main_rot = main_rot_out;
            claw_rot = claw_rot_flat;
        } else if (pos == Position.postclip){
            slides.setTarget(Slides.Position.POSTCLIP);
            main_rot = main_rot_out;
            claw_rot = claw_rot_flat;
        }
        update_claws();
    }
    public void set_grab(GamepadEx gamepadEx){
        if (gamepadEx.wasJustReleased(BIND_SLIDES_WALL)){
            set_grab(Position.pickup);
        } else if (gamepadEx.wasJustReleased(BIND_SLIDES_CLIP)) {
            set_grab(Position.preclip);
        } else if (gamepadEx.wasJustReleased(BIND_SLIDES_HIGH)) {
            set_grab(Position.score);
        } else if (gamepadEx.wasJustReleased(BIND_SLIDES_DOWN)){
            set_grab(Position.wall);
        }
    }
    /*public void set_slides(GamepadEx gamepadEx){

    }*/
    public void clip(GamepadEx gamepadEx){
        if (gamepadEx.wasJustPressed(BIND_CLIP)){
            set_grab(Position.wall);
        } else if (gamepadEx.wasJustReleased(BIND_CLIP)){
            toggle_claw();
        }
        update_claws();
    }
    public void update_claws(){
        Claw.setPosition(claw);
        Claw_rot.setPosition(claw_rot);
        Main_rot1.setPosition(main_rot);
        Main_rot2.setPosition(main_rot);
    }
}
