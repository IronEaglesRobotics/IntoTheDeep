package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic: Omni Linear OpMode", group="Linear OpMode")
//@Disabled
public class HolonomicDrive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lfront = null;
    private DcMotor lback = null;
    private DcMotor rfront = null;
    private DcMotor rback = null;

    @Override
    public void runOpMode() {
        lfront = hardwareMap.get(DcMotor.class, "lf");
        lback = hardwareMap.get(DcMotor.class, "lb");
        rfront = hardwareMap.get(DcMotor.class, "rf");
        rback = hardwareMap.get(DcMotor.class, "rb");

        lfront.setDirection(DcMotor.Direction.REVERSE);
        lback.setDirection(DcMotor.Direction.REVERSE);
        rfront.setDirection(DcMotor.Direction.FORWARD);
        rback.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double max;

            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            double lfp = axial + lateral + yaw;
            double rfp = axial - lateral - yaw;
            double lbp = axial - lateral + yaw;
            double rbp = axial + lateral - yaw;

            max = Math.max(Math.abs(lfp), Math.abs(rfp));
            max = Math.max(max, Math.abs(lbp));
            max = Math.max(max, Math.abs(rbp));

            if (max > 1.0) {

            }
        }
    }
}
