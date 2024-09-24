package org.firstinspires.ftc.teamcode.hardware;
import org.firstinspires.ftc.teamcode.lib.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Robot
{
    public DcMotor fl, fr, bl, br;

    public Robot init(HardwareMap hmap)
    {
        fl = hmap.get(DcMotor.class, Config.FL_WHEEL);
        fr = hmap.get(DcMotor.class, Config.FR_WHEEL);
        bl = hmap.get(DcMotor.class, Config.BL_WHEEL);
        br = hmap.get(DcMotor.class, Config.BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        return this;
    }
}
