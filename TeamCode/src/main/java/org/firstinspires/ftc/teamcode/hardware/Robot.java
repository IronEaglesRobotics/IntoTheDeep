package org.firstinspires.ftc.teamcode.hardware;
import static org.firstinspires.ftc.teamcode.lib.Config.*;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.hardware.block_arm.*;

public class Robot
{
    public DcMotor fl, fr, bl, br;
    public Hang_arm hangArm;
    public intake intake;
    public block_arm block_arm;
    public enum Block_macro_state {idle,take,grab,score}
    Block_macro_state block_macro_state = Block_macro_state.idle;
    public enum hang_macro {idle,pullup,stop}
    hang_macro hangMacro = hang_macro.idle;
    double delay;

    public Robot init(HardwareMap hardwareMap)
    {
        hangArm = new Hang_arm().Init(hardwareMap);
        intake = new intake().Init(hardwareMap);
        block_arm = new block_arm().Init(hardwareMap);

        fl = hardwareMap.get(DcMotor.class, FL_WHEEL);
        fr = hardwareMap.get(DcMotor.class, FR_WHEEL);
        bl = hardwareMap.get(DcMotor.class, BL_WHEEL);
        br = hardwareMap.get(DcMotor.class, BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        return this;
    }

    public Hang_arm getHangArm() {
        return hangArm;
    }

    public intake getIntake() {
        return intake;
    }

    public block_arm getBlockarm() {
        return block_arm;
    }
    public void Block_Macro(GamepadEx gamepadEx, double curtime){
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

}
