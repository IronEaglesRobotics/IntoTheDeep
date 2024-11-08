package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.hardware.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode {
    private Robot robot;
    private GamepadEx controller1;
    private GamepadEx controller2;
    private GoBildaPinpointDriver odo;

    @Override
    public void init() {
        controller1 = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);
        robot = new Robot().init(hardwareMap);
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
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
        robot.getBlockarm().set_grab(controller2);
        robot.getBlockarm().set_slides(controller2);
        robot.pullup(controller2.getRightY());

        robot.getIntake().setColor(controller1);

        robot.getIntake().control_beatbar(controller2);
        try {
            robot.getIntake().setEject(controller2);
            robot.getIntake().intake_up(controller1);
            robot.getIntake().intake_lower(controller1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.update(time);

        telemetry.addData("pos",robot.getIntake().getBeatBarPos());
        telemetry.addData("is_lower",robot.getIntake().getstring());
        telemetry.addData("slides",robot.getBlockarm().slides.getTarget());
        telemetry.addData("pos",odo.getVelX());
        telemetry.update();
    }
}
