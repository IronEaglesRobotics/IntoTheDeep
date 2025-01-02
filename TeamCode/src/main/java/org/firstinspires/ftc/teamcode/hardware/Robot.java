package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriverRR;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.ActionCommand;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

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
    Action clipMovement;
    public moveToClip toClip;

    public Robot init(HardwareMap hardwareMap) {
        drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
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
        toClip = new moveToClip(clipMovement,Set.of(slides),drive);
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

    // drive macros
    public static class moveToClip extends ActionCommand {
        PinpointDrive Drive;
        public moveToClip(Action action, Set<Subsystem> requirements,PinpointDrive drive) {
            super(action, requirements);
            Drive = drive;
        }
        @Override
        public void initialize() {
            driveState = DriveState.automatic;
            Drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),0));
            Actions.runBlocking(action);
        }
        @Override
        public void execute() {
            TelemetryPacket packet = new TelemetryPacket();
            action.preview(packet.fieldOverlay());
            finished = !action.run(packet);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            Drive.updatePoseEstimate();
        }
        @Override
        public void end(boolean I){
            driveState = DriveState.manuel;
        }
    }
}