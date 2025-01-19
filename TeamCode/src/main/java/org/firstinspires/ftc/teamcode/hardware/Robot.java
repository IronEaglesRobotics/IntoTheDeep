package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriverRR;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

import java.util.Collections;
import java.util.Set;

public class Robot {
    private PinpointDrive drive;
    private Intake intake;
    private IntakeArm intakeArm;
    private Claw claw;
    private Hang hang;
    private static Slides slides;
    private pusher pusher;
    public GoBildaPinpointDriverRR odo;
    public enum DriveState {manuel,automatic}
    public static DriveState driveState = DriveState.manuel;
    public
    Action clipMovement;

    public Robot init(HardwareMap hardwareMap,Pose2d pose) {
        drive = new PinpointDrive(hardwareMap, pose);
        intake = new Intake(hardwareMap);
        intakeArm = new IntakeArm(hardwareMap);
        claw = new Claw(hardwareMap);
        hang = new Hang(hardwareMap);
        slides = intakeArm.slides;
        pusher = new pusher(hardwareMap);
        odo = hardwareMap.get(GoBildaPinpointDriverRR.class,"odo");
        clipMovement = drive.actionBuilder(odo.getPositionRR())
                .afterDisp(1,()->{ this.getSlides().setTarget(Slides.Position.PRECLIP);})
                .splineToSplineHeading(new Pose2d(-25,0,0),0)
                .build();
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

    public pusher getPusher() {
        return pusher;
    }
    public DriveState getDriveState() {
        return driveState;
    }

    public void update(){
        intake.periodic();
        intakeArm.periodic();
        slides.periodic();
        pusher.periodic();
    }
    public void runTeleOp(GamepadEx controller, activeMode mode){
        controller.readButtons();
        switch (mode) {
            case macro:
                controller.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(getClaw().adaptClaw().andThen(new WaitCommand(250)).andThen(getSlides().preclip())
                                ,getClaw().closeCommand());
                // automates clip process
                controller.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                        .whenPressed(getSlides().postclip()
                                .andThen(getClaw().openCommand())
                                .andThen(new WaitCommand(300))
                                .andThen(getSlides().down())
                                .andThen(getClaw().adaptClaw())
                                .andThen(new WaitCommand(250))
                                .andThen(getSlides().preclip()));
                // changes target color for intake
                controller.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .toggleWhenPressed(getIntake().setBlue,getIntake().setRed);
                // preps robot for high basket score
                controller.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(getSlides().up()
                                .andThen(getIntakeArm().upCommand())
                                .andThen(getIntake().ejectIntake()));
                // changes target color for intake
                controller.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .toggleWhenPressed(getIntake().setBlue,getIntake().setRed);
                break;
            case standard:
                // controls raising slides
                controller.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(getSlides().up());
                // controls slides
                controller.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(getSlides().postclip().andThen(new Claw.ClawCommand(getClaw(),true)));
                // controls raising slides
                controller.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(getClaw().closeCommand().andThen(new WaitCommand(500)).andThen(getSlides().preclip()));
                // rotates intake arm up
                controller.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .toggleWhenPressed(getIntakeArm().upCommand(),getIntakeArm().downCommand());
                // toggles claw
                controller.getGamepadButton(GamepadKeys.Button.A)
                        .toggleWhenPressed(new Claw.ClawCommand(getClaw(),true),new Claw.ClawCommand(getClaw(),false));
                break;
        }
        // macros rotating arm up and extending intake
        controller.getGamepadButton(GamepadKeys.Button.X)
                .toggleWhenPressed(getIntake().reverseIntake(),getIntake().offIntake());
        // puts intake all the way up
        controller.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new Intake.storeIntake(getIntake()));
        // turns intake on and off
        controller.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenPressed(getIntake().runIntake(),getIntake().offIntake());
        // controls pusher
        controller.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whenPressed(getPusher().activateCommand())
                .whenReleased(getPusher().offCommand());
        // extends and retracts intake
        controller.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(getIntakeArm().inCommand(),getIntakeArm().outCommand());
        // controls lowering slides
        controller.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(getIntakeArm().downCommand().andThen(getSlides().down()));
    }
    public enum activeMode {macro, standard}
    public runActionCommand runAction(Action action){return new runActionCommand(action,drive,this);}
    public Command toClip(){
        driveState = DriveState.automatic;
        return new runActionCommand(clipMovement,drive,this);
    }

    // drive macros

    public static class runActionCommand implements Command {
        PinpointDrive Drive;
        Action action;
        private boolean finished;
        private Robot robot;
        public runActionCommand(Action action,PinpointDrive drive,Robot robot) {
            this.action = action;
            Drive = drive;
            this.robot = robot;
        }
        @Override
        public void initialize() {
            Drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),0));
        }

        @Override
        public void execute() {
            TelemetryPacket packet = new TelemetryPacket();
            action.preview(packet.fieldOverlay());
            finished = !action.run(packet);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            Drive.updatePoseEstimate();
            robot.update();
        }
        @Override
        public boolean isFinished(){return finished;}
        @Override
        public Set<Subsystem> getRequirements() {
            return Collections.emptySet();
        }
        public void end(boolean i){
            driveState = DriveState.manuel;
        }
    }
}