package org.firstinspires.ftc.teamcode.opmodes;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.DEFAULT_TURN;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_SPEED;
import static org.firstinspires.ftc.teamcode.lib.Config.SLOW_TURN;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.lib.Config.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode
{
    private Robot robot;
    private double lastTime = System.currentTimeMillis();
    private double curSpeed = DEFAULT_SPEED;
    private double curTurn = DEFAULT_TURN;

    private static double lerp(double a, double b, double t)
    { return a + t * (b - a); }

    @Override
    public void init()
    { robot = new Robot().init(hardwareMap); }

    @Override
    public void loop()
    {
        // if not using dt lerp speed is dependent on loop freq... not good this fix.
        double time = System.currentTimeMillis();
        double deltaTime = (time - lastTime);
        
        double speedMod = gamepad1.a ? SLOW_SPEED : DEFAULT_SPEED;
        double turnMod = gamepad1.a ? SLOW_TURN : DEFAULT_TURN;

        // interpolate instead of instant to fix jitter maybe, idk if ftc already has something for this...
        double step = 1-Math.exp(-7 * deltaTime);
        curSpeed = Math.min(lerp(curSpeed, speedMod, step), 1);
        curTurn = Math.min(lerp(curTurn, turnMod, step), 1);
        
        double x = gamepad1.left_stick_x * curSpeed, y = -gamepad1.left_stick_y * curSpeed, z = gamepad1.right_stick_x * curTurn;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);
       /* double speed = .5;

        if (gamepad1.a) {
            speed = 1;
        }
        if (gamepad1.b) speed = .5;*/
        
        robot.fl.setPower(((x + y + z)/max));
        robot.fr.setPower(((-x + y - z)/max));
        robot.bl.setPower(((-x + y + z)/max));
        robot.br.setPower(((x + y - z)/max));

        lastTime = time;
    }
}
