package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.lib.Config.BIND_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.FL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_TURN;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_TURN;
import static org.firstinspires.ftc.teamcode.lib.Config.LERP_SPEED;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
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

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lastTime = System.currentTimeMillis();

        return  this;
    }

    public void setDrive(GamepadEx gamepad, double currentTime) {
        boolean speedDown = gamepad.getButton(BIND_SPEED);
        double speedMod = speedDown ? SLOW_SPEED : DEFAULT_SPEED;

        double x = gamepad.getLeftX() * speedMod, y = -gamepad.getLeftY() * speedMod, z = gamepad.getRightX() * speedMod;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);

        fl.setPower(((x + y + z)/max));
        fr.setPower(((-x + y - z)/max));
        bl.setPower(((-x + y + z)/max));
        br.setPower(((x + y - z)/max));

        lastTime = currentTime;
    }
}
