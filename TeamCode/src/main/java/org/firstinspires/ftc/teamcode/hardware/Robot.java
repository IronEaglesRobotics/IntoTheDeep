package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.Nullable;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardware.block_arm.*;

public class Robot {
    private Drive drive;
    private intake intake;
    private block_arm block_arm;
    private DcMotor hang_motor;
    public enum Block_macro_state {idle,take,grab,score,Null}
    private Block_macro_state block_macro_state = Block_macro_state.idle;
    double delay;

    public Robot init(HardwareMap hardwareMap) {
        drive = new Drive().Init(hardwareMap);
        intake = new intake().Init(hardwareMap);
        block_arm = new block_arm().Init(hardwareMap);
        hang_motor = hardwareMap.get(DcMotor.class,"hang");

        return this;
    }

    public Drive getDrive() { return drive; }
    public intake getIntake() {
        return intake;
    }
    public block_arm getBlockarm() { return block_arm; }
    public void pullup(double input) {
        hang_motor.setPower(input);
    }

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
    public void update(){
        block_arm.update_claws();
        intake.update_servo();
    }
}
