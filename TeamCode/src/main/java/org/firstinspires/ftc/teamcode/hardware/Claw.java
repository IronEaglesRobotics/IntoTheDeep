package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.BLOCK_CLAW;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.Config;

public class Claw extends SubsystemBase {
    Servo Claw;
    double claw;

    public Claw(HardwareMap hardwareMap) {
        Claw = hardwareMap.get(Servo.class,BLOCK_CLAW);
    }

    public void open() {
        claw = Config.BLOCK_CLAW_OPEN;
    }

    public void close() {
        claw = Config.BLOCK_CLAW_CLOSED;
    }

    @Override
    public void periodic(){
        Claw.setPosition(claw);
    }

    public final ClawCommand openCommand = new ClawCommand(this, true);
    public final ClawCommand closeCommand = new ClawCommand(this, false);

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
                claw.open();
            } else {
                claw.close();
            }
        }

        @Override
        public boolean isFinished() {
            return System.currentTimeMillis() >= endTime;
        }
    }
}
