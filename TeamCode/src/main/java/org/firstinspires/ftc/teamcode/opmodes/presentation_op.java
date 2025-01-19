package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.lib.Config.BL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FR_WHEEL;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Slides;

@TeleOp(name = "presentation_op")
@Config
public class presentation_op extends OpMode {
    //Drive drive;
    private DcMotor fl, fr, bl, br;
    DcMotor Motor1;
    DcMotor Motor2;
    public static int target_pos = 0;
    Slides slides;

    private double increment = 0.000001;

    @Override
    public void init(){
        fl = hardwareMap.get(DcMotor.class, FL_WHEEL);
        fr = hardwareMap.get(DcMotor.class, FR_WHEEL);
        bl = hardwareMap.get(DcMotor.class, BL_WHEEL);
        br = hardwareMap.get(DcMotor.class, BR_WHEEL);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        Motor1 = hardwareMap.get(DcMotor.class,"slides_front");
        Motor2 = hardwareMap.get(DcMotor.class,"slides_back");
        Motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //drive = new Drive().Init(hardwareMap);
        slides = new Slides(hardwareMap);
    }

    @Override
    public void loop(){
        //drive.setDrive(controller,System.currentTimeMillis());
        target_pos = Math.max(Math.min(target_pos,60000),0);

        Motor1.setPower(gamepad1.left_stick_y);
        Motor2.setPower(gamepad1.left_stick_y);

        telemetry.addData("encoder",Motor2.getCurrentPosition());
        telemetry.addData("target",slides.getTarget());
        telemetry.addData("motor2", slides.controller.calculate(-Motor2.getCurrentPosition(), slides.getTarget()));
        telemetry.update();
    }
}
