package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Vector2d SPECIMEN = new Vector2d(-2,-32);
        Pose2d PICKUP_1 = new Pose2d(15, -54,Math.toRadians(270));
        Pose2d SPECIMEN2 = new Pose2d(30,-3,Math.toRadians(90));
        Pose2d PLOW1 = new Pose2d(15,-48, Math.toRadians(225));
        Pose2d PLOW2 = new Pose2d(26,-41, Math.toRadians(140));


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setDimensions(12.5,15)
                .setConstraints(180, 80, Math.toRadians(360), Math.toRadians(360), 8)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-3, -60, Math.toRadians(270)))

                        .setReversed(true)
                        .splineToConstantHeading(SPECIMEN,Math.toRadians(90))
//                        .waitSeconds(1.5)
                        .setTangent(Math.toRadians(270))
//                        .turn(-Math.toRadians(120))
                        .splineToConstantHeading(PICKUP_1.vec(),Math.toRadians(0))
//                        .lineToLinearHeading(PICKUP_1.plus(new Pose2d(0,-3)))
//                        .setReversed(true)
//                        .setTangent(Math.toRadians(0))
                        .splineToConstantHeading(SPECIMEN2.vec(), Math.toRadians(90))
//                        .turn(-Math.toRadians(180))

                        .setReversed(false)
                        .splineToLinearHeading(PLOW1, Math.toRadians(45))
//                        .setTangent(90)
                        .lineToLinearHeading(PLOW2)
                        .waitSeconds(1.5)
//                        .splineTo(BUCKET_1,Math.toRadians(225))
//                        .waitSeconds(1.5)
//                        .lineToLinearHeading(PICKUP_2)
//                        .waitSeconds(1.5)
//                        .lineToLinearHeading(BUCKET_2)


                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}