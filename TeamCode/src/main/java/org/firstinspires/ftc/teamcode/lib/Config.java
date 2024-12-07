package org.firstinspires.ftc.teamcode.lib;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class Config {
    // -- hardware map start -- //

    // wheels, DcMotor
    public static final String FL_WHEEL = "front_left";
    public static final String FR_WHEEL = "front_right";
    public static final String BL_WHEEL = "back_left";
    public static final String BR_WHEEL = "back_right";

    // slides, DcMotor
    public static final String SLIDES_FRONT = "slides_front";
    public static final String SLIDES_BACK = "slides_back";

    // intake
    // Servo
    public static final String INTAKE_LEFT = "intake_left";
    public static final String INTAKE_RIGHT = "intake_right";
    public static final String EJECT = "eject";
    // CRServo
    public static final String BEAT_BAR = "beat_bar";
    // ColorSensor
    public static final String COLOR_SENSOR = "c_sensor";

    public static final String HANG = "hang";
    public static final String HANG_LIFT = "hang_lift";

    public static final String BLOCK_CLAW = "block_claw";
    public static final String CLAW_ROT = "claw_rot";
    public static final String LEFT_ARM = "left_arm";
    public static final String RIGHT_ARM = "right_arm";

    // -- hardware map end -- //

    // drive speed
    public static final double DEFAULT_SPEED = 1;
    public static final double SLOW_SPEED = 0.2;
    public static final int LERP_SPEED = 5;

    // turn speed
    public static final double DEFAULT_TURN = 1;
    public static final double SLOW_TURN = 0.2;

    public static final double HSpos = 1;

    // random configs
    public static final double BEATBAR_CHANGE = 1;

    // Servo ranges
    public static final double BLOCK_CLAW_CLOSED = 0;
    public static final double BLOCK_CLAW_OPEN = .25;
    public static final double claw_rot_flat = .38;
    public static final double claw_rot_90 = .04;
    public static final double main_rot_in = .04;
    public static final double main_rot_out = .55;
    public static final double main_rot_score = .35;
    public static final double main_rot_init = .8;
    public static final double lower_rot_in = .52;
    public static final double lower_rot_out = .3;
    public static final double eject_rot_in = .02;
    public static final double eject_rot_out = .55;

    public static double extendlowscale2 = .325;
    public static double extendhighscale2 = .93;
    public static double extendlowscale1 = 0;
    public static double extendhighscale1 = .58;

    // keybinds

    // gamepad1
    public static final GamepadKeys.Button BIND_SPEED = GamepadKeys.Button.Y;

    // gamepad2
    public static final GamepadKeys.Button BIND_TOGGLE_CLAW = GamepadKeys.Button.A;
    public static final GamepadKeys.Button BIND_INTAKE_EJECT = GamepadKeys.Button.B;
    public static final GamepadKeys.Button BIND_ROTATE_ARM = GamepadKeys.Button.X;
    public static final GamepadKeys.Button BIND_SLIDES_CLIP = GamepadKeys.Button.DPAD_RIGHT;
    public static final GamepadKeys.Button BIND_SLIDES_WALL = GamepadKeys.Button.DPAD_LEFT;
    public static final GamepadKeys.Button BIND_SLIDES_HIGH = GamepadKeys.Button.DPAD_UP;
    public static final GamepadKeys.Button BIND_SLIDES_DOWN = GamepadKeys.Button.DPAD_DOWN;
    public static final GamepadKeys.Button BIND_CLIP = GamepadKeys.Button.LEFT_BUMPER;
    public static final GamepadKeys.Button BIND_INTAKE_TOGGLE = GamepadKeys.Button.RIGHT_STICK_BUTTON;
    public static final GamepadKeys.Button BIND_BEATBAR_TOGGLE = GamepadKeys.Button.LEFT_STICK_BUTTON;
}
