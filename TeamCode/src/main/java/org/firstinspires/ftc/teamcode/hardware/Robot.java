package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

public class Robot {
    private PinpointDrive drive;
    private Intake intake;
    private IntakeArm intakeArm;
    private Claw claw;
    private Hang hang;
    private Slides slides;
    private boolean beatbar = false;

    public Robot init(HardwareMap hardwareMap) {
        drive = new PinpointDrive(hardwareMap, new Pose2d(0,0,0));
        intake = new Intake(hardwareMap);
        intakeArm = new IntakeArm(hardwareMap);
        claw = new Claw(hardwareMap);
        hang = new Hang(hardwareMap);
        slides = new Slides(hardwareMap);

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


//    public armActivate activate = new armActivate(intake,intakeArm);

//    public void activateIntake(){
//        intakeArm.toggleExtension();
//        intake.toggleWrist();
//        if (beatbar) {
//            intake.startBeatBar();
//        }
//        beatbar = !beatbar;
//    }

//    public static class armActivate extends SequentialCommandGroup {
//        Intake intake;
//        IntakeArm intakeArm;
//
//        private armActivate(Intake tIntake, IntakeArm tIntakeArm){
//            intake = tIntake;
//            intakeArm = tIntakeArm;
//
//            addCommands(intakeArm.extendCommand,
//                    intake.activeIntake
//            );
//        }
        public static class armDeactivate extends SequentialCommandGroup {
            Intake intake;
            IntakeArm intakeArm;

            private armDeactivate(Intake tIntake, IntakeArm tIntakeArm) {
                intake = tIntake;
                intakeArm = tIntakeArm;

                addCommands(intakeArm.extendCommand,
                        intake.offIntake
                );
            }
        }

    }