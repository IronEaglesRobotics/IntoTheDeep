package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class block_arm {
    boolean claw_open = false;
    Servo Claw, Claw_rot, Main_rot;
    double claw, claw_rot, main_rot = 0;
    public enum Position {pickup,score,wall}
    public enum Claw_pos {up,right,left,down}

    public block_arm Init(HardwareMap HardwareMap){
        Claw = HardwareMap.get(Servo.class,"block_claw");
        Claw_rot = HardwareMap.get(Servo.class,"claw_rot");
        Main_rot = HardwareMap.get(Servo.class,"main_rot");
        return this;
    }
    public void toggle_claw(){
        claw_open = !claw_open;
        Claw.setPosition(claw_open ? 0 : 1);
    }
    public void rotate_claw (Claw_pos pos){
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
    public void rotate_arm (Gamepad gamepad){
        main_rot += gamepad.left_stick_y * .05;
    }
    public void set_grab(Position pos){
        if (pos == Position.pickup){
            main_rot = 1;
            this.rotate_claw(Claw_pos.up);
            if (!claw_open){
                this.toggle_claw();
            }
        } else if (pos == Position.wall){
            main_rot = 0;
            claw_rot = 0;
        } else if (pos == Position.score){
            main_rot = .5;
            claw_rot = 0;
        }
    }
}