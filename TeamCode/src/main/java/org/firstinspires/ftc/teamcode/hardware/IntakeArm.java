package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.extendhighscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.extendhighscale2;
import static org.firstinspires.ftc.teamcode.lib.Config.extendlowscale1;
import static org.firstinspires.ftc.teamcode.lib.Config.extendlowscale2;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
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
        periodic();
    }

    public void armUp() {
        motor.setTargetPosition(-200);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(-1);
    }
    public void armDown(){
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);
    }

    public void armIn() {
        ex_save = 0;;
    }
    public void armOut(){
        ex_save = 1;
    }
    public boolean isBusy(){
        return motor.isBusy();
    }

    public void periodic() {
        extension1.setPosition(ex_save);
        extension2.setPosition(ex_save);
    }
    public RotateCommand upCommand(){return new RotateCommand(this,true);}
    public RotateCommand downCommand(){return new RotateCommand(this,false);}

    public final ExtendCommand outCommand(){return new ExtendCommand(this,true);}
    public final ExtendCommand inCommand(){return new ExtendCommand(this,false);}

    public static class RotateCommand extends CommandBase {
        private final IntakeArm arm;
        private boolean target;

        public RotateCommand(IntakeArm intakeArm,boolean up) {
            arm = intakeArm;
            addRequirements(intakeArm);
            target = up;
        }

        public void initialize() {
            if(target){
                arm.armUp();
            } else {
                arm.armDown();
            }
        }
        public boolean isFinished(){
            return arm.isBusy();
        }
    }
    public static class ExtendCommand extends CommandBase {
        private final IntakeArm arm;
        private double time;
        private boolean target;

        public ExtendCommand(IntakeArm intakeArm,boolean out) {
            arm = intakeArm;
            addRequirements(intakeArm);
            target = out;
        }
        @Override
        public void initialize() {
            if(target){
                arm.armOut();
            } else {
                arm.armIn();
            }
            time = System.currentTimeMillis();
        }
        public boolean isFinished(){
            return time + 500 < System.currentTimeMillis();
        }
    }
}