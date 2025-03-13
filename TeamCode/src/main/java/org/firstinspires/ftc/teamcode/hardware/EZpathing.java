package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class EZpathing {
    Follower follower;
    public EZpathing(Follower tFollower){
        follower = tFollower;
    }
    public PathChain moveTo(Pose pose){
        return follower.pathBuilder().addBezierLine(
                new Point(follower.getPose()),
                new Point(pose))
                .setConstantHeadingInterpolation(follower.getPose().getHeading())
                .build();
    }
    public PathChain moveToVia(Pose target,Pose via){
        return follower.pathBuilder().addBezierCurve(
                new Point(follower.getPose()),
                new Point(via),
                new Point(target))
                .setConstantHeadingInterpolation(follower.getPose().getHeading())
                .build();
    }
    public PathChain moveToWithHeading(Pose pose){
        return follower.pathBuilder().addBezierLine(
                        new Point(follower.getPose()),
                        new Point(pose))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(),
                        pose.getHeading())
                .build();
    }
    public PathChain moveToViaWithHeading(Pose target,Pose via){
        return follower.pathBuilder().addBezierCurve(
                        new Point(follower.getPose()),
                        new Point(via),
                        new Point(target))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(),
                        target.getHeading())
                .build();
    }
    public PathChain moveTo(double x, double y){
        return this.moveTo(new Pose(x,y));
    }
    public PathChain moveToWithHeading(double x, double y, double heading){
        return this.moveToWithHeading(new Pose(x,y,heading));
    }
    public PathChain moveToVia(double targetX, double targetY,double viaX,double viaY){
        return this.moveToVia(new Pose(targetX,targetY),new Pose(viaX,viaY));
    }
    public PathChain moveToViaWithHeading(double targetX, double targetY,double targetH,double viaX,double viaY){
        return this.moveToViaWithHeading(new Pose(targetX,targetY,targetH),new Pose(viaX,viaY));
    }
    public PathChain turn(double tHeading){
        return follower.pathBuilder().setLinearHeadingInterpolation(follower.getPose().getHeading(),tHeading).build();
    }
}
