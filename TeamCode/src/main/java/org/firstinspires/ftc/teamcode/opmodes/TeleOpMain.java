package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.lib.Config.HSpos;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode {
    private Robot robot;
    private GamepadEx controller1 = new GamepadEx(gamepad1);
    private GamepadEx controller2 = new GamepadEx(gamepad2);

    @Override
    public void init() {
        robot = new Robot().init(hardwareMap);
    }

    @Override
    public void loop() {
        double currentTime = System.currentTimeMillis();

        robot.getDrive().setDrive(controller1, currentTime);

        robot.getHangArm().lift_hook(HSpos);
        robot.Block_Macro(controller2, currentTime);
        robot.Hang_Macro(controller2, currentTime);

        robot.getBlockarm().rotate_arm(controller2);
        robot.getBlockarm().rotate_claw(controller2);
        robot.getBlockarm().clip(controller2);
        robot.getBlockarm().toggle_claw(controller2);

        robot.getIntake().setColor(controller1);
        robot.getIntake().Lower(controller1);
        try {
            robot.getIntake().pickup(controller1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
