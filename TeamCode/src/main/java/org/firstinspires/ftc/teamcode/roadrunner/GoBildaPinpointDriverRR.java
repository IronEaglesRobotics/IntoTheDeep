//package org.firstinspires.ftc.teamcode.roadrunner;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//
//public class GoBildaPinpointDriverRR extends GoBildaPinpointDriver {
//
//    public GoBildaPinpointDriverRR(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned) {
//        super(deviceClient, deviceClientIsOwned);
//    }
//
//    public Pose2d getPositionRR() {
//        Pose2D pose = this.getPosition();
//        return new Pose2d(pose.getX(DistanceUnit.INCH), pose.getY(DistanceUnit.INCH), pose.getHeading(AngleUnit.DEGREES));
//    }
//
//    public void setPosition(Pose2d pose) {
//        this.setPosition(new Pose2D(DistanceUnit.INCH, pose.position.x, pose.position.y, AngleUnit.DEGREES, pose.heading.toDouble()));
//    }
//}
//
