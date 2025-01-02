package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_LEFT;
import static org.firstinspires.ftc.teamcode.lib.Config.INTAKE_RIGHT;
import static org.firstinspires.ftc.teamcode.lib.Config.LEFT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.RIGHT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.extendhighscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.extendhighscale2;
import static org.firstinspires.ftc.teamcode.lib.Config.extendlowscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.extendlowscale2;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeArm extends SubsystemBase {
    DcMotor motor;
    Servo extension1;
    Servo extension2;
    boolean up = false;
    boolean out = false;
    double rot_save = 0;
    double ex_save = 1;
    Slides slides;


    public IntakeArm(HardwareMap HardwareMap) {
        motor = HardwareMap.get(DcMotor.class,"motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension1 = HardwareMap.get(Servo.class, "extension1");
        extension2 = HardwareMap.get(Servo.class, "extension2");
        extension2.setDirection(Servo.Direction.REVERSE);
        extension1.scaleRange(extendlowscale1,extendhighscale1);
        extension2.scaleRange(extendlowscale2,extendhighscale2);
        slides = new Slides(HardwareMap);
    }

    public void toggleRotation() {
        if (!up){
            motor.setTargetPosition(-200);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(-1);
        } else {
            motor.setTargetPosition(0);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(1);
        }
    }

    public void toggleExtension() {
        ex_save = out ? 1 : 0;
        out = !out;
    }

    public boolean isOut() {
        return out;
    }

    public boolean isUp() {
        return up;
    }

    public void periodic() {
        extension1.setPosition(ex_save);
        extension2.setPosition(ex_save);
    }
    public RotateCommand rotateCommand = new RotateCommand(this);

    public final ExtendCommand extendCommand = new ExtendCommand(this);

    public RaiseCommand raiseCommand = new RaiseCommand(this,slides);

    public static class RotateCommand extends InstantCommand {
        private final IntakeArm arm;

        public RotateCommand(IntakeArm intakeArm) {
            arm = intakeArm;
            addRequirements(intakeArm);
        }

        public void initialize() {
            if (!arm.isOut()) {
                arm.toggleRotation();
            }
        }
    }
    public static class ExtendCommand extends InstantCommand {
        private final IntakeArm arm;

        public ExtendCommand(IntakeArm intakeArm) {
            arm = intakeArm;
            addRequirements(intakeArm);
        }
        @Override
        public void initialize() {
            arm.toggleExtension();
        }
    }
    public static class RaiseCommand extends SequentialCommandGroup {
        private final IntakeArm arm;
        private final Slides slides;

        public RaiseCommand(IntakeArm intakeArm,Slides tslides) {
            arm = intakeArm;
            slides = tslides;
            addRequirements(intakeArm);
            if (arm.isOut()){
                addCommands(
                    new ExtendCommand(arm),
                    new Slides.LiftPositionCommand(slides, Slides.Position.SCORE_LOW),
                    new RotateCommand(arm),
                    new ExtendCommand(arm)
                );
            } else {
                addCommands(
                        new RotateCommand(arm),
                        new ExtendCommand(arm)
                );
            }
        }

    }
}