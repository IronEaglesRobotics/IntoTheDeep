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

    public static int DEPLOYED_POSITION = 9900;
    public static int RETURN_POSITION = 5400;

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
        motor.setTargetPosition(RETURN_POSITION);
        motor.setPower(1);
    }

    public void under(){
        motor.setTargetPosition(-DEPLOYED_POSITION);
        motor.setPower(1);
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public boolean isDeployed() {
        return this.motor.getTargetPosition() >= DEPLOYED_POSITION * 0.9;
    }
    public enum HangPos {Deployed, Retracted, Under}

    public final HangCommand hangDeploy() { return new HangCommand(this,HangPos.Deployed);}
    public  final HangCommand hangRetract() {return new HangCommand(this,HangPos.Retracted);}
    public  final HangCommand hangUnder() {return new HangCommand(this,HangPos.Under);}


    public static class HangCommand extends CommandBase {
        private final Hang hang;
        private HangPos target;
        public HangCommand(Hang hang, HangPos hangPos) {
            this.hang = hang;
            addRequirements(hang);
            target = hangPos;
        }

        @Override
        public void initialize() {
            if (target == HangPos.Retracted) {
                hang.hang();
            } else if (target == HangPos.Deployed){
                hang.deploy();
            } else {
                hang.under();
            }
        }

        @Override
        public boolean isFinished() {
            return !this.hang.isBusy();
        }
    }
}
