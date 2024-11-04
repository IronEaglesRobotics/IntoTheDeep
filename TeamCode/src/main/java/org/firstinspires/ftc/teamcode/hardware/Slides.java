package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.SLIDES_BACK;
import static org.firstinspires.ftc.teamcode.lib.Config.SLIDES_FRONT;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Slides {
    private DcMotor slide;
    private DcMotor slide2;

    /*public static double p = 0.0014;
    public static double i = 0.02;
    public static double d = 0;
    public static double f = 0.01;*/
    public static double p = 0.000009;
    public static double i = 0;
    public static double d = 0;
    private double f = 0;
    private double pTolerance = 20;
    public PIDController controller = new PIDController(p, i, d);

    public int targetMin = -60000;
    public int targetMax = 60000;

    public int down = 0;
    public int postclip = 7500;
    public int preclip = 10000;
    public int tier1 = 20000;
    public int tier2 = 35000;
    public int tier3 = 50000;
    public int tier4 = 60000;

    public int target = 0;

    public int manualSpeed = 20;

    public enum Position { DOWN, PRECLIP, POSTCLIP, TIER1, TIER2, TIER3,TIER4 }

    public Slides(HardwareMap hardwareMap) {
        slide = hardwareMap.get(DcMotor.class, SLIDES_FRONT);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide2 = hardwareMap.get(DcMotor.class, SLIDES_BACK);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTarget(int pos) {
        target = Math.min(Math.max(pos, targetMin), targetMax);
    }

    public void setTarget(Position pos) {
        int value = 0;
        switch (pos) {
            case DOWN: value = down; break;
            case PRECLIP: value = preclip; break;
            case POSTCLIP: value = postclip; break;
            case TIER1: value = tier1; break;
            case TIER2: value = tier2; break;
            case TIER3: value = tier3; break;
            case TIER4: value = tier4; break;
            default: value = targetMin; // or handle unexpected cases
        }
        target = Math.min(Math.max(value, targetMin), targetMax);
    }

    public void increaseTarget(double increase) {
        target += (int) (increase * manualSpeed);
        target = Math.min(targetMax, Math.max(targetMin, target));
    }

    public int getTarget() {
        return target;
    }

    public boolean atTarget() {
        return controller.atSetPoint();
    }

    public void cancel() {
        target = slide.getCurrentPosition();
    }

    public void targetReset() {
        target = targetMin;
    }

    public void update(double runTime) {
//        highPos = 720 + heightOffset;
//        midPos = 350 + heightOffset;
//        lowPos = heightOffset;
//        pickupPos = 20 + heightOffset;
//        downPos = heightOffset;// TODO add these back in

//        if (target == 0) {
//            slide.setPower(0);
//            slide2.setPower(0);
//        } else {
//            if (target < 5) {
//                slide.setPower(0);
//                slide2.setPower(0);
//            } else {
        double pid, ff;
        controller.setPID(p, i, d);
        controller.setTolerance(pTolerance);

        pid = controller.calculate(-slide.getCurrentPosition(), target);
        pid = Math.min(Math.max(pid,-1),1);
        ff = f;
        slide.setPower((pid + ff));
        slide2.setPower((pid + ff));

        //pid = controller.calculate(slide2.getCurrentPosition(), target);
        //ff = f;
        //slide2.setPower(pid + ff);
//            }
//        }
    }

    public String getTelemetry() {
        return String.format("Position: %s %s\nTarget: %s %s\nPower: %s %s\nHeightOffset: %s", slide.getCurrentPosition(), slide2.getCurrentPosition(), target, target, slide.getPower(), slide2.getPower());
    }
}