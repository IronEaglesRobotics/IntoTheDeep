package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive.PARAMS;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriverRR;
import com.acmerobotics.roadrunner.ftc.LazyImu;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.roadrunner.GoBildaPinpointDriver;

@Config
@TeleOp(name = "odotest")
public final class ODOtest extends LinearOpMode {
    GoBildaPinpointDriverRR odo;
    LazyImu lazyImu;
    IMU imu;
    public static double yawscale = 1;
    public RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection =
            RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
    public RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection =
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    @Override
    public void runOpMode() throws InterruptedException {
        odo = hardwareMap.get(GoBildaPinpointDriverRR.class,"odo");
        odo.setEncoderResolution(GoBildaPinpointDriverRR.goBILDA_SWINGARM_POD);
        odo.setPosition(new Pose2d(0,0,0));
        odo.setOffsets(DistanceUnit.MM.fromInches(PARAMS.xOffset), DistanceUnit.MM.fromInches(PARAMS.yOffset));
        lazyImu = new LazyImu(hardwareMap,"imu",new RevHubOrientationOnRobot(logoFacingDirection, usbFacingDirection));
        imu = hardwareMap.get(IMU.class,"imu");
        odo.recalibrateIMU();
        waitForStart();
        while (opModeIsActive()) {
            odo.setYawScalar(yawscale);
            YawPitchRollAngles headings = imu.getRobotYawPitchRollAngles();
            Pose2D pose = odo.getPosition();
            odo.update();
            telemetry.addData("x: ", pose.getX(DistanceUnit.INCH));
            telemetry.addData("y: ",pose.getY(DistanceUnit.INCH));
            telemetry.addData("ODO Heading: ",pose.getHeading(AngleUnit.RADIANS));
            telemetry.addData("trueIMU Headings",headings.toString());
            telemetry.addData("yawsacle",odo.getYawScalar());
            telemetry.update();
        }
    }
}
