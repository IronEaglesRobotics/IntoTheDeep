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
            Drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),0));
        }
    }
}