package com.example.meepmeeplibrary;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0,0,0))
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-26,-18),-Math.PI/2)
                .waitSeconds(2)
                .build());
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-26,-18,0))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-13,0,Math.toRadians(135)),0)
                .lineToX(-25)
                .splineToLinearHeading(new Pose2d(-38,0,Math.toRadians(45)),Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-20,0,Math.toRadians(135)),0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}