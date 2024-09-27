package org.firstinspires.ftc.teamcode.hardware;
import static org.firstinspires.ftc.teamcode.lib.Config.*;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.hardware.Hang_arm;

public class Robot
{
    public DcMotor fl, fr, bl, br;

    public Robot init(HardwareMap hmap)
    {
        Hang_arm.Init(hmap);
        fl = hmap.get(DcMotor.class, FL_WHEEL);
        fr = hmap.get(DcMotor.class, FR_WHEEL);
        bl = hmap.get(DcMotor.class, BL_WHEEL);
        br = hmap.get(DcMotor.class, BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        return this;
    }
}
