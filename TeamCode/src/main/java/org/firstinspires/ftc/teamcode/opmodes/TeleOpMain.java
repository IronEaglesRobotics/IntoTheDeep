package org.firstinspires.ftc.teamcode.opmodes;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class TeleOpMain extends OpMode
{
    private Robot robot = new Robot();

    @Override
    public void init()
    {
        robot.init(hardwareMap);
    }

    @Override
    public void loop()
    {
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);

        double flPow = (x + y + z)/max;
        double frPow = (-x + y - z)/max;
        double blPow = (-x + y + z)/max;
        double brPow = (x + y - z)/max;

        robot.fl.setPower(flPow);
        robot.fr.setPower(frPow);
        robot.bl.setPower(blPow);
        robot.bl.setPower(brPow);
    }
}
