package org.firstinspires.ftc.teamcode.opmodes;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode
{
    private Robot robot;

    @Override
    public void init()
    { robot = new Robot().init(hardwareMap); }

    @Override
    public void loop()
    {
        double x = gamepad1.left_stick_x, y = -gamepad1.left_stick_y, z = gamepad1.right_stick_x;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);
        
        robot.fl.setPower((x + y + z)/max);
        robot.fr.setPower((-x + y - z)/max);
        robot.bl.setPower((-x + y + z)/max);
        robot.bl.setPower((x + y - z)/max);
    }
}
