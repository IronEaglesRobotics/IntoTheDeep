package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.lib.Config.FL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_TURN;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_TURN;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Drive {
    private DcMotor fl, fr, bl, br;
    private double lastTime;
    private double curSpeed = DEFAULT_SPEED;
    private double curTurn = DEFAULT_TURN;

    private static double lerp(double a, double b, double t)
    { return a + t * (b - a); }

    public Drive Init(HardwareMap hardwareMap) {
        fl = hardwareMap.get(DcMotor.class, FL_WHEEL);
        fr = hardwareMap.get(DcMotor.class, FR_WHEEL);
        bl = hardwareMap.get(DcMotor.class, BL_WHEEL);
        br = hardwareMap.get(DcMotor.class, BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        lastTime = System.currentTimeMillis();

        return  this;
    }

    public void setDrive(GamepadEx gamepad, double currentTime) {
        // if not using dt lerp speed is dependent on loop freq... not good this fix.
        double deltaTime = (currentTime - lastTime);

        double speedMod = gamepad.getButton(GamepadKeys.Button.A) ? SLOW_SPEED : DEFAULT_SPEED;
        double turnMod = gamepad.getButton(GamepadKeys.Button.A) ? SLOW_TURN : DEFAULT_TURN;

        // interpolate instead of instant to fix jitter maybe, idk if ftc already has something for this...
        double step = 1-Math.exp(-7 * deltaTime);
        curSpeed = Math.min(lerp(curSpeed, speedMod, step), 1);
        curTurn = Math.min(lerp(curTurn, turnMod, step), 1);

        double x = gamepad.getLeftX() * curSpeed, y = -gamepad.getLeftY() * curSpeed, z = gamepad.getRightX() * curTurn;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);

        fl.setPower(((x + y + z)/max));
        fr.setPower(((-x + y - z)/max));
        bl.setPower(((-x + y + z)/max));
        br.setPower(((x + y - z)/max));

        lastTime = currentTime;
    }
}
