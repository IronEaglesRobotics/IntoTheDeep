package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode {
    private Robot robot;
    private GamepadEx controller1;
    private GamepadEx controller2;
    private GoBildaPinpointDriver odo;
    private boolean c_mode = false;

    @Override
    public void init() {
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot = new Robot().init(hardwareMap);
    }

    @Override
    public void loop() {
        double currentTime = System.currentTimeMillis();

        controller1.readButtons();
        controller2.readButtons();

        if (controller2.wasJustReleased(GamepadKeys.Button.RIGHT_BUMPER)){
            c_mode = !c_mode;
        }

        robot.getDrive().setDrive(c_mode ? controller2 : controller1, currentTime); // my little surprise

        //robot.Block_Macro(controller2, currentTime, Robot.Block_macro_state.Null);

        robot.getBlockarm().rotate(controller2);
        robot.getBlockarm().clip(controller2);
        robot.getBlockarm().toggle_claw(controller2);
        robot.getBlockarm().set_grab(controller2);
        robot.pullup(controller2.getRightY());

        robot.getIntake().control_beatbar(controller2);
        robot.getIntake().Beatbar_toggle(controller2);
        try {
            robot.getIntake().setEject(controller2);
            robot.getIntake().intakeToggle(controller2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.update(time);

        telemetry.addData("pos",robot.getIntake().getBeatBarPos());
        telemetry.addData("is_lower",robot.getIntake().getstring());
        telemetry.addData("slides",robot.getBlockarm().slides.getTarget());
        telemetry.update();
    }
}
