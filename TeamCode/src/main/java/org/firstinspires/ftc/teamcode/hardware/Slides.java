package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.lib.Config.SLIDES_BACK;
import static org.firstinspires.ftc.teamcode.lib.Config.SLIDES_FRONT;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Slides extends SubsystemBase {
    private final DcMotor slide;
    private final DcMotor slide2;
    public PIDController controller = new PIDController(KP, KI, KD);
    private int target = 0;

    public static double KP = 0.0006;
    public static double KI = 0.0009;
    public static double KD = 0;
    public static double TOLERANCE = 400;
    // p:.001, I:0.000005, D:0.000035

    public static int POSITION_MIN = 0;
    public static int POSITION_MAX = 50000;
    
    public static int POSITION_DOWN = 2000;
    public static int POSITION_AFTER_CLIP = 25000;
    public static int POSITION_BEFORE_CLIP = 36000;
    public static int POSITION_SCORE_HIGH = 44300;

    public Slides(HardwareMap hardwareMap) {
        slide = hardwareMap.get(DcMotor.class, SLIDES_FRONT);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slide2 = hardwareMap.get(DcMotor.class, SLIDES_BACK);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTarget(int pos) {
        target = Math.min(Math.max(pos, POSITION_MIN), POSITION_MAX);
    }

    public void setTarget(Position pos) {
        int value = 0;
        switch (pos) {
            case DOWN:
                value = POSITION_DOWN;
                break;
            case PRECLIP:
                value = POSITION_BEFORE_CLIP;
                break;
            case POSTCLIP:
                value = POSITION_AFTER_CLIP;
                break;
            case SCORE_LOW:
                value = POSITION_SCORE_HIGH;
                break;
//            case SCORE_HIGH:
//                value = POSITION_SCORE_HIGH;
//                break;
            default:
                value = POSITION_MIN; // or handle unexpected cases
        }
        target = Math.min(Math.max(value, POSITION_MIN), POSITION_MAX);
    }

    public int getTarget() {
        return target;
    }

    public boolean atTarget() {
        return controller.atSetPoint();
    }

    public int getPos(){return slide2.getCurrentPosition();}

    public void cancel() {
        target = -slide2.getCurrentPosition();
    }

    public void targetReset() {
        target = POSITION_MIN;
    }

    @Override
    public void periodic() {
        double result;
        controller.setPID(KP, KI, KD);
        controller.setTolerance(TOLERANCE);

        result = controller.calculate(-slide2.getCurrentPosition(), target);
        result = Math.min(Math.max(result, -1), 1);
        slide.setPower((result));
        slide2.setPower((result));
    }

    public enum Position {
        DOWN,
        POSTCLIP,
        PRECLIP,
        SCORE_LOW
    }
//    public DPadDownCommand dPadDownCommand = new DPadDownCommand(this);
//    public DPadUpCommand dPadUpCommand = new DPadUpCommand(this);
    public LiftPositionCommand up(){return new LiftPositionCommand(this,Position.SCORE_LOW);}
    public LiftPositionCommand down(){return new LiftPositionCommand(this,Position.DOWN);}
    public  LiftPositionCommand preclip(){return new LiftPositionCommand(this,Position.PRECLIP);}
    public LiftPositionCommand postclip(){return new LiftPositionCommand(this,Position.POSTCLIP);}
    
    public static class LiftPositionCommand extends CommandBase {
        Position position;
        Slides slides;
        double time = System.currentTimeMillis();

        public LiftPositionCommand(Slides slides, Position position) {
            this.slides = slides;
            this.position = position;

            addRequirements(slides);
        }

        @Override
        public void initialize() {
            slides.setTarget(position);
        }

        @Override
        public boolean isFinished() {
            return slides.atTarget() && (System.currentTimeMillis() > time + 200);
        }

        @Override
        public void end(boolean i){
            if (!i) {
                slides.cancel();
            }
        }
    }

    public static class LiftEncoderPositionCommand extends InstantCommand {
        double position;
        Slides slides;

        public LiftEncoderPositionCommand(Slides slides, double position) {
            this.slides = slides;
            this.position = position;

            addRequirements(slides);
        }

        @Override
        public void initialize() {
            slides.setTarget(this.slides.getTarget() + (int) (position * 1000));
        }
    }

    public static class DPadUpCommand extends InstantCommand {
        Position curPosition = Position.DOWN;
        Slides slides;
        int index;

        public DPadUpCommand(Slides slides) {
            this.slides = slides;

            addRequirements(slides);
        }

        @Override
        public void initialize() {
            Position foo = curPosition;
            int idx = foo.ordinal();
            idx = Math.min(idx + 1, Position.values().length-1);
            Position nextFoo = Position.values()[idx];
            slides.setTarget(nextFoo);
            curPosition = nextFoo;
        }
    }
    public static class DPadDownCommand extends InstantCommand {
        Position curPosition = Position.DOWN;
        Slides slides;
        int index;

        public DPadDownCommand(Slides slides) {
            this.slides = slides;

            addRequirements(slides);
        }

        @Override
        public void initialize() {
            Position foo = curPosition;
            int idx = foo.ordinal();
            idx = Math.max(idx - 1, 0);
            Position nextFoo = Position.values()[idx];
            slides.setTarget(nextFoo);
            curPosition = nextFoo;
        }
    }
}