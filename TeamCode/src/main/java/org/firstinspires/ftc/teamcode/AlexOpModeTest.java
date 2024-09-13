package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
@Disabled
public class AlexOpModeTest extends OpMode
{   // OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor ld1 = null;
    private DcMotor ld2 = null;
    private DcMotor rd1 = null;
    private DcMotor rd2 = null;

    @Override
    public void init()
    {
        telemetry.addData("Status", "init starting...");

        // Hardware variable init
        ld1 = hardwareMap.get(DcMotor.class, "left_drive_1");
        ld2 = hardwareMap.get(DcMotor.class, "left_drive_2");
        rd1 = hardwareMap.get(DcMotor.class, "right_drive_1");
        rd2 = hardwareMap.get(DcMotor.class, "right_drive_2");

        ld1.setDirection(DcMotor.Direction.REVERSE);
        ld2.setDirection(DcMotor.Direction.FORWARD);
        rd1.setDirection(DcMotor.Direction.REVERSE);
        rd2.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "INIT DONE");
    }

    @Override
    public void start()
    { runtime.reset(); }

    @Override
    public void loop()
    {
        double dx, dy = -gamepad1.left_stick_x;
        double ld1Pow, ld2Pow, rd1Pow, rd2Pow;

        // broke rn fix later
//        ld1Pow = x + y + z;
//        ld2Pow = -x + y - z;
//        rd1Pow = -x + y + z;
//        rd2Pow = x + y - z;

        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
    }
}
