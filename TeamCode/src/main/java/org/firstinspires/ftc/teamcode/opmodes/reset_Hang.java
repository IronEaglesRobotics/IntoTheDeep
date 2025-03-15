package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "reset hang")
public class reset_Hang extends CommandOpMode {
    @Override
    public void initialize() {
        Robot robot = new Robot().init(hardwareMap,null,new Pose(0,0,0));
        robot.getHang().under();
    }
}
