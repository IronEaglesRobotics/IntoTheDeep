package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
@Disabled
public class AlexOpModeTest extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fl, fr, bl, br;

    @Override
    public void init()
    {
        telemetry.addData("Status", "init starting...");

        fl = hardwareMap.get(DcMotor.class, "frontRightDrive");
        fr = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        bl = hardwareMap.get(DcMotor.class, "backLeftDrive");
        br = hardwareMap.get(DcMotor.class, "backRightDrive");

        fl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "INIT DONE");
    }

    @Override
    public void start()
    { runtime.reset(); }

    @Override
    public void loop()
    {
        double flPow, frPow, blPow, brPow;
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;
        double max = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(z),1);

        flPow = (x + y + z)/max;
        frPow = (-x + y - z)/max;
        blPow = (-x + y + z)/max;
        brPow = (x + y - z)/max; //

        fl.setPower(flPow);
        fr.setPower(frPow);
        bl.setPower(blPow);
        br.setPower(brPow);
    }
}
