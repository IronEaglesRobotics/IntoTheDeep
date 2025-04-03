package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.commands.FollowPath;
import org.firstinspires.ftc.teamcode.pedroPathing.commands.TeleopMovement;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.nio.file.Path;

public class Robot {
    private Intake intake;
    private IntakeArm intakeArm;
    private Claw claw;
    private Hang hang;
    private static Slides slides;
    private pusher pusher;
    public TeleopMovement drive;
    private EZpathing EZ;

    public enum DriveState {manuel, automatic}

    public static DriveState driveState = DriveState.manuel;

    public Robot init(HardwareMap hardwareMap, GamepadEx driveGamepad, Pose start) {
        intake = new Intake(hardwareMap);
        intakeArm = new IntakeArm(hardwareMap);
        claw = new Claw(hardwareMap);
        hang = new Hang(hardwareMap);
        slides = new Slides(hardwareMap);
        pusher = new pusher(hardwareMap);
        Constants.setConstants(FConstants.class, LConstants.class);
        drive = new TeleopMovement(new Follower(hardwareMap),true,driveGamepad,1,
                1,-1,-1);
        drive.getFollower().setPose(start);
        EZ = new EZpathing(drive.getFollower());
        return this;
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
    public Follower getFollower(){return drive.getFollower();}
    public Claw getClaw() {
        return claw;
    }
    public pusher getPusher() {
        return pusher;
    }
    public void setSpeed(double speed){drive.setSpeed(speed);}
    public DriveState getDriveState() {
        return driveState;
    }
    public FollowPath follow(PathChain path){return new FollowPath(getFollower(),path);}
    public FollowPath follow(PathChain path,double maxPower) {return new FollowPath(getFollower(),path,maxPower);}
    public EZpathing EZ(){return EZ;}

    public void update() {
        intake.periodic();
        intakeArm.periodic();
        slides.periodic();
        pusher.periodic();
    }
    public enum activeMode {MACRO, STANDARD}
}
