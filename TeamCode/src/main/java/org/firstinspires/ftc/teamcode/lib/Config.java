package org.firstinspires.ftc.teamcode.lib;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class Config {
    // wheels
    public static final String FL_WHEEL = "front_left";
    public static final String FR_WHEEL = "front_right";
    public static final String BL_WHEEL = "back_left";
    public static final String BR_WHEEL = "back_right";

    // drive speed
    public static final double DEFAULT_SPEED = 1;
    public static final double SLOW_SPEED = 0.5;
    public static final int LERP_SPEED = 5;

    // turn speed
    public static final double DEFAULT_TURN = 1;
    public static final double SLOW_TURN = 0.5;

    public static final double HSpos = 1;

    // keybinds

    // gamepad1
    public static final GamepadKeys.Button BIND_SPEED = GamepadKeys.Button.X;
    public static final GamepadKeys.Button BIND_INTAKE_PICKUP = GamepadKeys.Button.A;
    public static final GamepadKeys.Button BIND_INTAKE_LOWER = GamepadKeys.Button.B;
    public static final GamepadKeys.Button BIND_COLOR_BLUE = GamepadKeys.Button.DPAD_RIGHT;
    public static final GamepadKeys.Button BIND_COLOR_RED = GamepadKeys.Button.DPAD_LEFT;
    public static final GamepadKeys.Button BIND_COLOR_YELLOW = GamepadKeys.Button.DPAD_UP;

    // gamepad2
    public static final GamepadKeys.Button BIND_CLAW_UP = GamepadKeys.Button.DPAD_UP;
    public static final GamepadKeys.Button BIND_CLAW_DOWN = GamepadKeys.Button.DPAD_DOWN;
    public static final GamepadKeys.Button BIND_CLAW_LEFT = GamepadKeys.Button.DPAD_LEFT;
    public static final GamepadKeys.Button BIND_CLAW_RIGHT = GamepadKeys.Button.DPAD_RIGHT;
}
