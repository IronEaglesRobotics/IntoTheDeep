package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Vector2d SPECIMEN = new Vector2d(-10,-35);
        Vector2d PICKUP_1 = new Vector2d(-48,-42);
        Vector2d BUCKET_1 = new Vector2d(-55,-54);
        Pose2d PICKUP_2 = new Pose2d(-58,-42,Math.toRadians(270));
        Pose2d BUCKET_2 = new Pose2d(-55,-54,Math.toRadians(225));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setDimensions(12.5,15)
                .setConstraints(180, 80, Math.toRadians(360), Math.toRadians(360), 8)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-32, -60, Math.toRadians(90)))
                        .splineToConstantHeading(SPECIMEN,Math.toRadians(90))
                        .waitSeconds(1.5)
                        .setReversed(true)
                        .splineTo(PICKUP_1,Math.toRadians(90))
                        .setReversed(false)
                        .waitSeconds(1.5)
                        .splineTo(BUCKET_1,Math.toRadians(225))
                        .waitSeconds(1.5)
                        .lineToLinearHeading(PICKUP_2)
                        .waitSeconds(1.5)
                        .lineToLinearHeading(BUCKET_2)


                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}