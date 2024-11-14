package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

public class Robot {
    private PinpointDrive drive;
    private Intake intake;
    private IntakeArm intakeArm;
    private Claw claw;
    private Hang hang;
    private Slides slides;

    public Robot init(HardwareMap hardwareMap) {
        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
        intake = new Intake(hardwareMap);
        intakeArm = new IntakeArm(hardwareMap);

        return this;
    }

    public PinpointDrive getDrive() {
        return drive;
    }

    public Intake getIntake() {
        return intake;
    }

    public IntakeArm getIntakeArm() {
        return intakeArm;
    }

    public Hang getHang() {
        return hang;
    }

    public Slides getSlides() {
        return slides;
    }

    public Claw getClaw() {
        return claw;
    }
}