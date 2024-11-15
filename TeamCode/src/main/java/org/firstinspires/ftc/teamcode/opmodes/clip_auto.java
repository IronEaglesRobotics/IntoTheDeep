package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "clip_auto", preselectTeleOp = "Main Teleop")
public class clip_auto extends CommandOpMode {
    Robot robot;

    @Override
    public void initialize() {
        robot = new Robot().init(hardwareMap);
        Action act1 = robot.getDrive().actionBuilder(new Pose2d(0,0,0))
                .splineToSplineHeading(new Pose2d(-25,-30,0),0)
                .build();

    }
}
