package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode {
    private Robot robot;
    private GamepadEx controller1;
    private GamepadEx controller2;

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

        robot.getDrive().setDrive(controller1, currentTime);

        //robot.Block_Macro(controller2, currentTime, Robot.Block_macro_state.Null);

        robot.getBlockarm().rotate_arm(controller2);
        robot.getBlockarm().rotate_claw(controller2);
        robot.getBlockarm().clip(controller2);
        robot.getBlockarm().toggle_claw(controller2);
        robot.pullup(controller2.getRightY());

        robot.getIntake().setColor(controller1);
        robot.getIntake().Lower(controller1);
        robot.getIntake().control_beatbar(controller2);
        robot.getIntake().toggle_beatbar(controller1);
        try {
            robot.getIntake().setEject(controller2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.getIntake().update_servo(controller1);

        telemetry.addData("pos",robot.getIntake().getBeatBarPos());
        telemetry.addData("is_lower",robot.getIntake().getstring());
        telemetry.update();
    }
}
