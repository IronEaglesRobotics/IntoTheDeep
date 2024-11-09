package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.hardware.roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.security.SecurityPermission;

import lombok.Getter;
import lombok.Setter;

@Config
public class Robot {
    @Getter
    public Claw claw;
    @Getter
    public Wrist wrist;
    @Getter
    private MecanumDrive drive;
    @Getter
    public Arm arm;
    @Getter
    public Slides slides;
    @Getter
    public Extendo extendo;
    @Getter
    public Intake intake;

    public double intakeDelay;
    public double outtakeDelay;
    public String team;


    //Init Hardwaremap
    public Robot init(HardwareMap hardwareMap) {
        this.drive = new MecanumDrive(hardwareMap);
        this.wrist = new Wrist().init(hardwareMap);
        this.arm = new Arm().init(hardwareMap);
        this.claw = new Claw().init(hardwareMap);
        this.slides = new Slides().init(hardwareMap);
        this.extendo = new Extendo().init(hardwareMap);
        this.intake = new Intake().init(hardwareMap);
        return this;
    }

    //Trajectory Sequence Builder
    public TrajectorySequenceBuilder getTrajectorySequenceBuilder() {
        this.drive.update();
        return this.drive.trajectorySequenceBuilder(this.drive.getPoseEstimate());
    }

    //Claw Class
    @Config
    public static class Claw {
        //Variables
        public static double OPEN = 1;
        public static double OPENSMALL = .8;
        public static double CLOSE = .63;
        //Servo
        public ServoImplEx claw;

        //init
        public Claw init(HardwareMap hardwareMap) {
            this.claw = hardwareMap.get(ServoImplEx.class, "claw");
            return this;
        }

        //Methods
        public void open() {
            claw.setPosition(OPEN);
        }

        public void openSmall() {
            claw.setPosition(OPENSMALL);
        }


        public void close() {
            claw.setPosition(CLOSE);
        }

        public void passiveclose() {
            claw.setPwmDisable();
        }

        public boolean isPWMEnabled() {
            return claw.isPwmEnabled();
        }
    }

    //Arm Class
    @Config
    public static class Arm {
        //variables
        public static double INTAKE = .7;
        public static double INTAKESPEC = .475;
        public static double OUTTAKESPEC = .2;
        public static double OUTTAKESAMPLE = .05;
        //PController
        public static double KP = 1.2;
        public static double KD = 0;
        public static double MAX_DELTA = .075;
        public static double TOL = 0.05;
        PDController armPDcontroller;
        private double armTarget;


        //servos
        private Servo armL;
        private Servo armR;

        public Arm init(HardwareMap hardwareMap) {
            this.armL = hardwareMap.get(Servo.class, "armL");
            this.armR = hardwareMap.get(Servo.class, "armR");
            this.armPDcontroller = new PDController(KP, KD);
//            this.armL.setDirection(Servo.Direction.REVERSE);
//            this.armL.setPosition(INTAKE);
//            this.armR.setPosition(INTAKE);
//            this.armPDcontroller.setSetPoint(INTAKE);
            return this;
        }

        public void intake() {
            moveArm(INTAKE);
        }

        public void outtakeSample() {
            moveArm(OUTTAKESAMPLE);
        }

        public void intakeSpecimen() {
            moveArm(INTAKESPEC);
        }

        public void outtakeSpecimen() {
            moveArm(OUTTAKESPEC);
        }

        private void moveArm(double position) {
            armPDcontroller.setSetPoint(position);
            armTarget = position;
        }

        public boolean isAtTarget() {
            return armPDcontroller.atSetPoint();
        }

        public void update() {
            armPDcontroller.setSetPoint(armTarget);
            armPDcontroller.setTolerance(TOL);
            armPDcontroller.setP(KP);
            armPDcontroller.setD(KD);

            if (!isAtTarget()) {
                double delta = armPDcontroller.calculate(armL.getPosition());
                if (Math.abs(armPDcontroller.getPositionError()) > .1) {
                    delta = Math.min(Math.copySign(MAX_DELTA, delta), delta);
                }
                armR.setPosition(armR.getPosition() + delta);
                armL.setPosition(armL.getPosition() + delta);
            }
        }
    }

    //Wrist Class
    @Config
    public static class Wrist {
        //variables
        public static double INTAKE = .4;
        public static double OUTTAKESAMPLE = .58;
        public static double INTAKESPEC = .33;
        public static double OUTTAKESPEC = .6;
        //PController
        public static double KP = 1.2;
        public static double KD = 0;
        public static double MAXDELTA = .05;
        public static double TOL = .005;
        private PDController wristPDcontroller;
        private double wristTarget;
        //servo
        private Servo wrist;

        //init
        public Wrist init(HardwareMap hardwareMap) {
            this.wrist = hardwareMap.get(Servo.class, "wrist");
            this.wrist.setPosition(INTAKE);
            this.wristPDcontroller = new PDController(KP, KD);
            this.wristPDcontroller.setSetPoint(INTAKE);
            return this;
        }

        public void intake() {
            moveWrist(INTAKE);
        }

        public void outtakeSample() {
            moveWrist(OUTTAKESAMPLE);
        }

        public void intakeSpecien() {
            moveWrist(INTAKESPEC);
        }

        public void outtakeSpec() {
            moveWrist(OUTTAKESPEC);
        }

        private void moveWrist(double position) {
            wristPDcontroller.setSetPoint(position);
            wristTarget = position;

        }

        public boolean isAtTarget() {
            return wristPDcontroller.atSetPoint();
        }

        public void update() {
            wristPDcontroller.setSetPoint(wristTarget);
            wristPDcontroller.setTolerance(TOL);
            wristPDcontroller.setP(KP);
            wristPDcontroller.setD(KD);
            double A = wrist.getPosition();
            double B = wristTarget;

            if (!isAtTarget()) {
                double delta = wristPDcontroller.calculate(A);
//                if (Math.abs(wristPcontroller.getPositionError()) > .1) {
//                    delta = Math.copySign(MAXDELTA, delta);
//                }
                wrist.setPosition(wrist.getPosition() + delta);
            }
        }
    }

    //Slides Class
    @Config
    public static class Slides {
        //Motors
        public DcMotorEx slidesL;
        public DcMotorEx slidesR;
        //Variables
//        public static int SLIDESPOWER = 1;
        public static int SLIDEUP = 860;
        public static int SLIDEHSPEC = 450;
//        public static int SLIDELSPEC = 300;
        public static int SLIDELBUCKET = 350;
        public static int SLIDEDOWN = 0;
        public static int SLIDEREST = 110;
        //PID
//        private static int TARGET = 20;
        public static double KP = 0.0014;
        public static double KI = 0.02;
        public static double KD = 0;
        public static double FF = 0.01;
        public static double TOL = 20;
        public static PIDController slidesPID = new PIDController(KP, KI, KD);

        //Init
        public Slides init(HardwareMap hardwareMap) {
            this.slidesL = hardwareMap.get(DcMotorEx.class, "slidesL");
            this.slidesR = hardwareMap.get(DcMotorEx.class, "slidesR");
            this.slidesL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.slidesR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slidesR.setTargetPosition(10);
            slidesL.setTargetPosition(10);
            this.slidesL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.slidesR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.slidesL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.slidesR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            this.slidesR.
            return this;
        }

        //Methods
        public void slideUp() {
            slidesTo(SLIDEUP);
        }

        public void slideRest() {
            slidesTo(SLIDEREST);
        }

        public void slideDown() {
            slidesTo(SLIDEDOWN);
        }

        public void slideStop() {
            slidesR.setTargetPosition(slidesR.getCurrentPosition());
            slidesL.setTargetPosition(slidesL.getCurrentPosition());
            slidesR.setPower(1);
            slidesL.setPower(1);
        }

        public int getPosition() {
            return slidesR.getCurrentPosition();
        }

        public void slidesTo(int position) {
            slidesR.setTargetPosition(position);
            slidesR.setPower(1);

            slidesL.setTargetPosition(position);
            slidesL.setPower(1);
        }

//        public void update(){
//            double pid,ff;
//            slidesPID.setPID(KP,KI,KD);
//            slidesPID.setTolerance(TOL);
//
//            pid = slidesPID.calculate(slidesL.getCurrentPosition(),TARGET);
//            ff = FF;
//            slidesL.setPower(pid+ff);
//
//            pid = slidesPID.calculate(slidesR.getCurrentPosition(),TARGET);
//            ff = FF;
//            slidesR.setPower(pid+ff);
//        }
    }

    //Extendo Class
    @Config
    public static class Extendo {
        //Servos
        private ServoImplEx extendoL;
        private ServoImplEx extendoR;

        //Variables
        public static double retract = .52;
        public static double extend = .22;
        public static double mini = .425;


        public Extendo init(HardwareMap hardwareMap) {
            this.extendoL = hardwareMap.get(ServoImplEx.class, "extendoL");
            this.extendoL.setDirection(Servo.Direction.REVERSE);
            this.extendoR = hardwareMap.get(ServoImplEx.class, "extendoR");
            this.extendoL.setPosition(retract);
            this.extendoR.setPosition(retract);
            return this;
        }

        public void extend() {
            extendoL.setPosition(extend);
            extendoR.setPosition(extend);
        }

        public void mini() {
            extendoL.setPosition(mini);
            extendoR.setPosition(mini);
        }

        public void retract() {
            extendoR.setPosition(retract);
            extendoL.setPosition(retract);
        }
    }

    @Config
    public static class Intake {
        //Servos
        private Servo intakeL;
        private Servo intakeR;
        private CRServo beater;
        private ColorSensor intakeSensor;


        //Variables
        public static double up = .34;
        public static double spit = .65;
        public static double down = .8;
        public static double INTAKE = 1;
        public static double OUTTAKE = -.3;

        public static int ALPHA = 100;

        @Getter
        private int b;
        @Getter
        private int r;
        @Getter
        private int g;
        @Getter
        private int a;

        public enum colors {
            RED, BLUE, YELLOW, NULL
        }

        public colors targetColor = colors.BLUE;
        public colors color;


        public Intake init(HardwareMap hardwareMap) {
            this.intakeL = hardwareMap.get(Servo.class, "intakeL");
            this.intakeR = hardwareMap.get(Servo.class, "intakeR");
            this.beater = hardwareMap.get(CRServo.class, "beater");
            this.intakeL.setDirection(Servo.Direction.REVERSE);
            this.intakeSensor = hardwareMap.colorSensor.get("colorSensor");
            this.intakeSensor.enableLed(true);
            this.intakeL.setPosition(up);
            this.intakeR.setPosition(up);
            return this;
        }

        public void down() {
            intakeL.setPosition(down);
            intakeR.setPosition(down);
        }

        public void up() {
            intakeL.setPosition(up);
            intakeR.setPosition(up);
        }

        public void spit() {
            intakeL.setPosition(spit);
            intakeR.setPosition(spit);
        }

        public void intake() {
            beater.setPower(INTAKE);
        }

        public void outtake() {
            beater.setPower(OUTTAKE);
        }

        public void pause() {
            beater.setPower(0);
        }

        public colors getColor() {
            colors color;
            b = intakeSensor.blue();
            g = intakeSensor.green();
            r = intakeSensor.red();
            a = intakeSensor.alpha();

            if (r + b + g < 190) {
                color = colors.NULL;
            } else if (b > g+10 && b > r+10) {
                color = colors.BLUE;
            } else if (g > b+25 && g > r+25) {
                color = colors.YELLOW;
            } else if (r > b+50 && r > g+50) {
                color = colors.RED;
            } else {
                color = colors.NULL;
            }
            return color;
        }

        public int getAlpha() {
            return intakeSensor.alpha();
        }

    }


    //Scoring Macro
    public int bucketStep;
    public boolean AUTO;
    public int specStep;
    boolean bucketH;
    boolean specH;

    public scoringStates scoringState = scoringStates.IDLE;

    public enum scoringStates {
        IDLE, BUCKET, SPECIMENGRAB, BUCKETR, SPECIMENR
    }

    public void scoringMacro(GamepadEx controller1, double runtime, boolean auto) {

        boolean Y = controller1.wasJustPressed(GamepadKeys.Button.Y); //BUCKETH
        boolean X = controller1.wasJustPressed(GamepadKeys.Button.X); // BUCKETL
        boolean A = controller1.wasJustPressed(GamepadKeys.Button.A); // SPECIMENINTAKE
        boolean D1 = controller1.wasJustPressed(GamepadKeys.Button.DPAD_UP); // SPECIMENH
        boolean D2 = controller1.wasJustPressed(GamepadKeys.Button.DPAD_LEFT); // SPECIMENL
        boolean L1 = controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER); // RETRACT
        boolean L2 = controller1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > .3;

        switch (scoringState) {
            case IDLE:
                //Idle Actions
                slides.slideDown();
                arm.intake();
                claw.openSmall();
                wrist.intake();
//                intake.pause();

                //switch states
                if (Y || AUTO) { //High Bucket
                    bucketStep = 0;
                    outtakeDelay = runtime + .75; //Delay for slides after grabbing
                    claw.close();
                    bucketH = true;
                    scoringState = scoringStates.BUCKET;
                } else if (X) { //Low Bucket
                    bucketStep = 0;
                    outtakeDelay = runtime + .75; //Delay for slides after grabbing
                    claw.close();
                    bucketH = false;
                    scoringState = scoringStates.BUCKET;
                } else if (A) { //Intake Spec
                    specStep = 0;
                    claw.close();
                    outtakeDelay = runtime + .3; //Delay for slides after grabbing
                    scoringState = scoringStates.SPECIMENGRAB;
                }
                break;
            case BUCKET:
                //Actions
                switch (bucketStep) {
                    case 0: // Slides go somewhere
                        if (runtime > outtakeDelay) {
                            if (bucketH) {
                                slides.slideUp();//slides to high bucket
                                claw.close();
                            } else {
                                slides.slidesTo(Slides.SLIDELBUCKET); //slides to low bucket
                            }
                            outtakeDelay = runtime + .25; // delay for slides to clear hopper
                            bucketStep++;
                        }
                        break;
                    case 1: //move arm and wrist to scoring position
                        if (runtime > outtakeDelay) {
                            arm.outtakeSample();
                            wrist.outtakeSample();
                            bucketStep++;
                        }
                        break;
                    case 2:
                        if (L1) { //open claw if open button pressed
                            claw.openSmall();
                            bucketStep++;
                        } else if (Y) {
                            bucketH = true;
                            bucketStep = 0;
                        } else if (X) {
                            bucketH = false;
                            bucketStep = 0;
                        }
                        break;
                    case 3:
                        if (L2) {
                            scoringState = scoringStates.BUCKETR;
                            bucketStep = 0;
                        }
                        break;

                }
                break;
            case BUCKETR:
                switch (bucketStep) {
                    case 0:
                        claw.close();
                        outtakeDelay = runtime + .2;
                        bucketStep++;
                        break;
                    case 1:
                        if (runtime > outtakeDelay) {
                            arm.intake();
                            wrist.intake();
                            outtakeDelay = runtime + .5;
                            bucketStep++;
                        }
                        break;
                    case 2:
                        if (runtime > outtakeDelay) {
                            slides.slideDown();
                            claw.openSmall();
                            scoringState = scoringStates.IDLE;
                        }
                        break;
                }
                break;
            case SPECIMENGRAB:
                switch (specStep) {
                    case 0: // Slides go somewhere
                        if (runtime > outtakeDelay) {
                            slides.slideRest();
                            outtakeDelay = runtime + .4; // delay for slides to clear hopper
                            specStep++;
                        }
                        break;
                    case 1: //move arm and wrist to scoring position
                        if (runtime > outtakeDelay) {
                            arm.intakeSpecimen();
                            wrist.intakeSpecien();
                            outtakeDelay = runtime + .4;
                            specStep++;
                        }
                        break;
                    case 2:
                        if (runtime > outtakeDelay && L1 || auto) { //open claw if open button pressed
                            claw.open();
                            specStep++;
                        }
                        break;
                    case 3:
                        if (L2) {
                            scoringState = scoringStates.SPECIMENR;
                            specStep = 0;
                        } else if (D1 || AUTO) {
                            outtakeDelay = runtime + .4;
                            claw.close();
                            specStep++;
                        }
                        break;
                    case 4:
                        if (runtime > outtakeDelay) {
                            slides.slidesTo(Slides.SLIDEHSPEC);
                            claw.passiveclose();
                            outtakeDelay = runtime + .3;
                            specStep++;
                        }
                        break;
                    case 5:
                        if (runtime > outtakeDelay) {
                            arm.outtakeSpecimen();
                            wrist.outtakeSpec();
                            if (!auto) {
                                specStep++;
                            }
                        }
                        break;
                    case 6:
                        if (L1) {
                            slides.slidesTo(slides.getPosition() - 400);
                            scoringState = scoringStates.SPECIMENR;
                            specStep = 0;
                        }
                        if (auto){
                            slides.slidesTo(slides.getPosition() - 400);
                            scoringState = scoringStates.SPECIMENR;
                            specStep = 0;
                        }
                        break;
                }
                break;
            case SPECIMENR:
                switch (specStep) {
                    case 0:
                        if (L2 || auto) {
                            outtakeDelay = runtime + .2;
                            specStep++;
                        } else if (D1){
                            outtakeDelay = runtime + .1;
                            scoringState = scoringStates.SPECIMENGRAB;
                            specStep = 3;
                        } else if (A){
                            outtakeDelay = runtime + .051;
                            scoringState = scoringStates.SPECIMENGRAB;
                            specStep = 1;
                        }
                        break;
                    case 1:
                        if (runtime > outtakeDelay) {
                            arm.intake();
                            wrist.intake();
                            claw.close();
                            outtakeDelay = runtime + .5;
                            specStep++;
                        }
                        break;
                    case 2:
                        if (runtime > outtakeDelay) {
                            slides.slideDown();
                            claw.openSmall();
                            scoringState = scoringStates.IDLE;
                            specStep = 0;
                        }
                        break;
                }
                break;

        }

    }

    //Intake Macro
    private boolean mini;


    public intakeStates intakeState = intakeStates.IDLE;

    public enum intakeStates {
        IDLE, EXTENDED, INTAKING, DETECT, OUTTAKE, HASSAMPLE
    }

    public void intakeMacro(GamepadEx controller1, double runtime, boolean auto) {

        switch (intakeState) {
            case IDLE:
                //Actions
                extendo.retract();
                intake.up();
                intake.pause();
                //Switch states
                if (controller1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > .3) {
                    intakeState = intakeStates.EXTENDED;
                    mini = false;
                    intakeDelay = runtime + 1;
                } else if (controller1.wasJustPressed(GamepadKeys.Button.B)) {
                    intakeState = intakeStates.EXTENDED;
                    intakeDelay = runtime + 1;
                    mini = true;
                }
                break;
            case EXTENDED:
                //Actions
                if(mini){
                    extendo.mini();
                } else {
                    extendo.extend();
                }
                //Switch States
                if (((controller1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > .3 || controller1.wasJustPressed(GamepadKeys.Button.B)) && runtime > intakeDelay ) || auto) {
                    intakeState = intakeStates.INTAKING;
                    intakeDelay = runtime + .25;
                }
                break;
            case INTAKING:
                //Actions
                intake.down();
                if (runtime>intakeDelay) {
                    intake.intake();
                }

                if (controller1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > .3) {
                    extendo.extend();
                } else if (controller1.wasJustPressed(GamepadKeys.Button.B)) {
                    extendo.mini();
                }

                //Switch States

                if (intake.getAlpha() > Intake.ALPHA) {
                    intakeState = intakeStates.DETECT;
                } else if (controller1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                intakeState = intakeStates.HASSAMPLE;
            }
                break;
            case DETECT:
                //Actions

                intake.color = intake.getColor();
                //Switch States
                if (intake.color != Intake.colors.YELLOW && intake.color != getIntake().targetColor) {
                    intakeState = intakeStates.OUTTAKE;
                    intakeDelay = runtime + 1.5;
                } else {
                    intakeState = intakeStates.HASSAMPLE;
                    intakeDelay = runtime + .3;
                }
                break;
            case HASSAMPLE:
                //Actions
                intake.up();
                extendo.retract();
                //Switch States
                if (runtime > intakeDelay) {
                    intake.outtake();
                    intakeState = intakeStates.IDLE;
                }
                break;
            case OUTTAKE:
                //Actions
                intake.outtake();
                intake.spit();
                //Switch States
                if (runtime > intakeDelay) {
                    intakeState = intakeStates.INTAKING;
                }
                break;
        }

    }


    //Robot Update
    public void update() {
        wrist.update();
        arm.update();
        drive.update();
//        slides.update();
    }
}
