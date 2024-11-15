package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.LEFT_ARM;
import static org.firstinspires.ftc.teamcode.lib.Config.RIGHT_ARM;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeArm extends SubsystemBase {
    Servo rotation1;
    Servo rotation2;
    Servo extension1;
    Servo extension2;
    boolean up = false;
    boolean out = false;
    double rot_save = 0;
    double ex_save = 0;


    public IntakeArm(HardwareMap HardwareMap) {
        rotation1 = HardwareMap.get(Servo.class, LEFT_ARM);
        rotation2 = HardwareMap.get(Servo.class, RIGHT_ARM);
        rotation2.setDirection(Servo.Direction.REVERSE);

        extension1 = HardwareMap.get(Servo.class, "");
        extension2 = HardwareMap.get(Servo.class, "");
        extension2.setDirection(Servo.Direction.REVERSE);

    }

    public void toggleRotation() {
        rot_save = up ? .2 : 0;
        up = !up;
    }

    public void toggleExtenssion() {
        ex_save = out ? .5 : 0;
        out = !out;
    }

    public boolean isRotBusy() {
        return rotation1.getPosition() == rot_save;
    }
    public boolean isExtBusy() {
        return rotation1.getPosition() == ex_save;
    }


    public void periodic() {
        rotation1.setPosition(rot_save);
        rotation2.setPosition(rot_save);
        extension1.setPosition(ex_save);
        extension2.setPosition(ex_save);
    }
    public RotateCommand rotateCommand = new RotateCommand(this);

    public ExtendCommand extendCommand = new ExtendCommand(this);

    public RaiseCommand raiseCommand = new RaiseCommand(this);

    public static class RotateCommand extends InstantCommand {
        private final IntakeArm arm;

        public RotateCommand(IntakeArm intakeArm) {
            arm = intakeArm;
            addRequirements(intakeArm);
        }

        public void initialize() {
            arm.toggleRotation();
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
            arm.toggleExtenssion();
        }
    }
    public static class RaiseCommand extends SequentialCommandGroup {
        private final IntakeArm arm;

        public RaiseCommand(IntakeArm intakeArm) {
            arm = intakeArm;
            addRequirements(intakeArm);
            addCommands(
                    new RotateCommand(arm),
                    new ExtendCommand(arm)
            );
        }

    }
}