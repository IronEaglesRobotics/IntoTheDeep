package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.


@Config
@TeleOp(name = "Colour Sensor")
public class coligga extends OpMode {
    GamepadEx controller1;

    public ColorSensor foo;
    public int b;
    public int r;
    public int g;
    public String color;



    @Override
    public void init() {
        this.foo = hardwareMap.colorSensor.get("test");
        this.foo.enableLed(true);
    }

    @Override
    public void loop() {

        //Read controller buttons
        controller1.readButtons();

        if (controller1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
            getColor();
        }

        telemetry.addData("color: ", color);
        telemetry.update();


    }

    public void getColor() {
        b=foo.blue();
        g=foo.green();
        r=foo.red();

        if (b>g && b>r){
            color = "blue";
        } else if (g>b && g>r) {
            color = "green";
        } else if (r > b && r>g) {
            color = "red";
        }
    }

}
