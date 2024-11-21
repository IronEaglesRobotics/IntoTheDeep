package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.HANG;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Hang extends SubsystemBase {
    private DcMotor motor;

    public static int DEPLOYED_POSITION = 200;

    public Hang(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, HANG);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void deploy() {
        motor.setTargetPosition(DEPLOYED_POSITION);
        motor.setPower(1);
    }

    public void hang(){
        motor.setTargetPosition(0);
        motor.setPower(1);
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public boolean isDeployed() {
        return this.motor.getTargetPosition() >= DEPLOYED_POSITION * 0.9;
    }

    public final HangCommand hangCommand = new HangCommand(this);

    public static class HangCommand extends CommandBase {
        private final Hang hang;

        public HangCommand(Hang hang) {
            this.hang = hang;
            addRequirements(hang);
        }

        @Override
        public void initialize() {
            if (this.hang.isDeployed()) {
                hang.hang();
            } else {
                hang.deploy();
            }
        }

        @Override
        public boolean isFinished() {
            return !this.hang.isBusy();
        }
    }
}
