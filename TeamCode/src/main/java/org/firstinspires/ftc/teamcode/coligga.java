package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.


@Config
@TeleOp(name = "Colour Sensor")
public class coligga extends OpMode {
    GamepadEx controller1;

    public  intakeMacroStates intakemacrostate = intakeMacroStates.IDLE;
    public ColorSensor foo;
    public int b;
    public int r;
    public int g;
    public int a;
    public String color;
    public DcMotor intake;
    public String targetColor = "yellow";
    public static double OUTTAKEDELAY = .75;
    double delay;



    @Override
    public void init() {
        this.foo = hardwareMap.colorSensor.get("test");
        this.foo.enableLed(true);
        this.controller1 = new GamepadEx(this.gamepad1);
        this.intake = hardwareMap.dcMotor.get("intake");
        this.intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

        //Read controller buttons
        controller1.readButtons();

        if (controller1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            getColor();
        }

//        if (controller1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
//            this.intake.setPower(1);
////            intakeSample(targetColor);
//        } else{
//            this.intake.setPower(0);
//        }

        if (controller1.wasJustPressed(GamepadKeys.Button.Y)){
            targetColor = "yellow";
        } else if (controller1.wasJustPressed(GamepadKeys.Button.X)){
            targetColor = "blue";
        } else if (controller1.wasJustPressed(GamepadKeys.Button.B)){
            targetColor = "red";
        }

        intakeMacro(controller1,getRuntime(),targetColor);



        telemetry.addData("color: ", color);
        telemetry.addData("blue: ", b);
        telemetry.addData("red: ", r);
        telemetry.addData("green: ", g);
        telemetry.addData("alpha: ", a);
        telemetry.addData("targetColor:", targetColor);
        telemetry.addData("STATE:", intakemacrostate);
        telemetry.update();
    }

//    public void intakeSample(String c){
//        if (a>50 && foo.blue()+foo.red()+foo.green() > 200)  {
//            getColor();
//            if (!color.equals(c)){
//                this.intake.setDirection(DcMotorSimple.Direction.REVERSE);
//            } else {
//                this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
//            }
//        } else {
//            this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
//        }
//    }

    public void getColor() {
        b=foo.blue();
        g=foo.green();
        r=foo.red();
        a=foo.alpha();

        if (r + b+ g < 200) {
            color = "null";
        } else if (b>g && b>r){
            color = "blue";
        } else if (g>b && g>r) {
            color = "yellow";
        } else if (r > b && r>g) {
            color = "red";
        } else {
            color = "null";
        }
    }

    public enum intakeMacroStates{
        IDLE, INTAKE, DETECT ,OUTTAKE
    }

    public void intakeMacro(GamepadEx gamepadEx, double runtime, String target){
        switch (intakemacrostate){
            case IDLE:
                this.intake.setPower(0);
                this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
                if (gamepadEx.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                    intakemacrostate = intakeMacroStates.INTAKE;
                }
                break;
            case INTAKE:
                this.intake.setPower(1);
                this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
                if (foo.alpha()>65){
                    intakemacrostate = intakeMacroStates.DETECT;
                }
                break;
            case DETECT:
                getColor();
                delay = runtime + OUTTAKEDELAY;
                if (!color.equals(target)){
                    intakemacrostate = intakeMacroStates.OUTTAKE;
                } else {
                    intakemacrostate = intakeMacroStates.IDLE;
                }
                break;
            case OUTTAKE:
                this.intake.setPower(1);
                this.intake.setDirection(DcMotorSimple.Direction.REVERSE);

                if(runtime>delay){
                    intakemacrostate = intakeMacroStates.INTAKE;
                } else if(controller1.wasJustReleased(GamepadKeys.Button.LEFT_BUMPER)){
                    intakemacrostate = intakeMacroStates.IDLE;
                }
                break;
        }
    }

}
