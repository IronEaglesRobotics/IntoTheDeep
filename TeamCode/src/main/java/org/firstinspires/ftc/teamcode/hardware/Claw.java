package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.BLOCK_CLAW;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.Config;

public class Claw extends SubsystemBase {
    Servo Claw;
    double claw;
    Rev2mDistanceSensor dSensor;

    public Claw(HardwareMap hardwareMap) {
        Claw = hardwareMap.get(Servo.class,BLOCK_CLAW);
        dSensor = hardwareMap.get(Rev2mDistanceSensor.class,"dsensor");
    }

    public void openFunction() {
        claw = Config.BLOCK_CLAW_OPEN;
    }

    public void closeFunction() {
        claw = Config.BLOCK_CLAW_CLOSED;
    }

    @Override
    public void periodic(){
        Claw.setPosition(claw);
    }

    public  ClawCommand openCommand() {return new ClawCommand(this, true);}
    public  ClawCommand closeCommand() {return new ClawCommand(this, false);}
    public adaptiveClaw adaptClaw(){return new adaptiveClaw(this);}

    public static class ClawCommand extends CommandBase {
        private long endTime;
        private final Claw claw;
        private final boolean open;

        public ClawCommand(Claw claw, boolean open) {
            this.claw = claw;;
            this.open = open;
            addRequirements(claw);
        }

        @Override
        public void initialize() {
            this.endTime = System.currentTimeMillis() + 250;
            if (this.open) {
                claw.openFunction();
            } else {
                claw.closeFunction();
            }
        }

        @Override
        public boolean isFinished() {
            return System.currentTimeMillis() >= endTime;
        }
    }
    public static class adaptiveClaw extends CommandBase {
        Claw claw;
        public adaptiveClaw(Claw claw1){
            claw = claw1;
            addRequirements(claw);
        }
        public void initialize(){
            claw.openFunction();
        }
        public boolean isFinished(){
            return claw.dSensor.getDistance(DistanceUnit.INCH) < 2.5;
        }
        public void end(boolean i){
            claw.closeFunction();
        }
    }
}
