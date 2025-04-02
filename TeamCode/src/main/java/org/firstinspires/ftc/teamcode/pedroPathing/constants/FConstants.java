package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import static org.firstinspires.ftc.teamcode.lib.Config.BL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.BR_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FL_WHEEL;
import static org.firstinspires.ftc.teamcode.lib.Config.FR_WHEEL;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizers;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = FL_WHEEL;
        FollowerConstants.leftRearMotorName = BL_WHEEL;
        FollowerConstants.rightFrontMotorName = FR_WHEEL;
        FollowerConstants.rightRearMotorName = BR_WHEEL;

        FollowerConstants.useBrakeModeInTeleOp = true;
        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 30;

        FollowerConstants.xMovement = 77.95;
        FollowerConstants.yMovement = 57.79;

        FollowerConstants.forwardZeroPowerAcceleration = -32.22;
        FollowerConstants.lateralZeroPowerAcceleration = -62.74;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.25,0,0.02,0);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(2,0,0.3,0);
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.01,0,0.0008,0.6,0.1);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.4;
        FollowerConstants.pathEndHeadingConstraint = 0.007;

        FollowerConstants.automaticHoldEnd = true;
    }
}
