package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.Nullable;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardware.block_arm.*;

public class Robot {
    private Drive drive;
    private Hang_arm hangArm;
    private intake intake;
    private block_arm block_arm;
    public enum Block_macro_state {idle,take,grab,score,Null}
    private Block_macro_state block_macro_state = Block_macro_state.idle;
    private enum hang_macro {idle,pullup,stop}
    private hang_macro hangMacro = hang_macro.idle;
    double delay;

    public Robot init(HardwareMap hardwareMap) {
        drive = new Drive().Init(hardwareMap);
        hangArm = new Hang_arm().Init(hardwareMap);
        intake = new intake().Init(hardwareMap);
        block_arm = new block_arm().Init(hardwareMap);

        return this;
    }

    public Drive getDrive() { return drive; }
    public Hang_arm getHangArm() {
        return hangArm;
    }
    public intake getIntake() {
        return intake;
    }
    public block_arm getBlockarm() { return block_arm; }

    public void Block_Macro(GamepadEx gamepadEx, double curtime,Block_macro_state temp_macro){
        if (temp_macro != Block_macro_state.Null){
            block_macro_state = temp_macro;
        }
        switch (block_macro_state){
            case idle:
                if (gamepadEx.wasJustReleased(GamepadKeys.Button.RIGHT_BUMPER)){
                    block_macro_state = Block_macro_state.take;
                } else if (gamepadEx.wasJustReleased(GamepadKeys.Button.LEFT_BUMPER)) {
                    block_macro_state = Block_macro_state.score;
                }
                break;
            case take:
                if (intake.getbeatbar_pos()) {
                    intake.toggle_lower();
                }
                block_arm.set_grab(Position.pickup);
                delay = curtime + 300;
                block_macro_state = Block_macro_state.score;
                break;
            case grab:
                block_arm.toggle_claw();
                delay = curtime + 300;
                block_macro_state = Block_macro_state.score;
            case score:
                block_arm.set_grab(Position.score);
                block_macro_state = Block_macro_state.idle;
        }
        block_arm.update_claws();
        intake.update_servo();
    }

    public void Hang_Macro(GamepadEx gamepadEx,double cur_time){
        switch (hangMacro) {
            case idle:
                if (gamepadEx.wasJustReleased(GamepadKeys.Button.LEFT_STICK_BUTTON)){
                    delay = cur_time + 1;
                    hangMacro = hang_macro.pullup;
                }
                break;
            case pullup:
                if (cur_time<delay){
                    hangArm.pullup(1);
                } else hangMacro = hang_macro.stop;
                break;
            case stop:
                hangArm.pullup(0);
        }
    }
    public void update(){
        block_arm.update_claws();
        intake.update_servo();
    }
}
