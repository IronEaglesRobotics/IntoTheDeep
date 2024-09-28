package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.SampleMecanumDrive;

import lombok.Getter;

@Config
public class Robot {
    @Getter
    public Arm arm;
    @Getter
    private SampleMecanumDrive drive;


//Init Hardwaremap
    public Robot init(HardwareMap hardwareMap) {
        this.drive = new SampleMecanumDrive(hardwareMap);
        this.arm = new Arm().init(hardwareMap);
        return this;
    }


//Arm
    public static class Arm{
        //variables
        public static double EXTENDED = .475;
        public static double RETRACTED = .03;

        //servos
        private Servo extendo;

        public Arm init(HardwareMap hardwareMap) {
            this.extendo = hardwareMap.get(Servo.class, "extendo");
            return this;
        }

        public void extend() {
            this.extendo.setPosition(EXTENDED);
        }

        public void retract() {
            this.extendo.setPosition(RETRACTED);
        }

    }
}
