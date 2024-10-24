package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;

@TeleOp(name = "mechanicumOpMode", group = "movement")
public class MechanicumWheels extends LinearOpMode {

    //defines motors
    public DcMotor FL;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor BR;


    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.dcMotor.get("frontLeft");
        BL = hardwareMap.dcMotor.get("backLeft");
        FR = hardwareMap.dcMotor.get("frontRight");
        BR = hardwareMap.dcMotor.get("backRight");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);

        ColorSensor foo = new ColorSensor() {
            @Override
            public int red() {
                return 0;
            }

            @Override
            public int green() {
                return 0;
            }

            @Override
            public int blue() {
                return 0;
            }

            @Override
            public int alpha() {
                return 0;
            }

            @Override
            public int argb() {
                return 0;
            }

            @Override
            public void enableLed(boolean enable) {

            }

            @Override
            public void setI2cAddress(I2cAddr newAddress) {

            }

            @Override
            public I2cAddr getI2cAddress() {
                return null;
            }

            @Override
            public Manufacturer getManufacturer() {
                return null;
            }

            @Override
            public String getDeviceName() {
                return "";
            }

            @Override
            public String getConnectionInfo() {
                return "";
            }

            @Override
            public int getVersion() {
                return 0;
            }

            @Override
            public void resetDeviceConfigurationForOpMode() {

            }

            @Override
            public void close() {

            }
        };

        waitForStart();

        while (opModeIsActive()) {
            //defines inputs
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;


            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) +Math.abs(rx), 1);

            //sets motor power based on input
            double FLPower = (y + x + rx / denominator);
            double BLPower = (y - x + rx / denominator);
            double FRPower = (y - x - rx / denominator);
            double BRPower = (y + x - rx / denominator);

            FL.setPower(FLPower);
            BL.setPower(BLPower);
            FR.setPower(FRPower);
            BR.setPower(BRPower);
        }

    }
}
