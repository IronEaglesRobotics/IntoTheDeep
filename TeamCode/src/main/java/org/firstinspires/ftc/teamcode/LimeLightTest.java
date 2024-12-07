package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LimeLightTest extends LinearOpMode {

    private Limelight3A limelight;
    private volatile LLResult latestResult;
    private final AtomicInteger state = new AtomicInteger(0);
    private static final int STOPPED = 0, STARTING = 1, RUNNING = 2;


    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        waitForStart();

        while (opModeIsActive()) {


        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                for (LLResultTypes.ColorResult cr : colorResults) {
                    telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
                }
            }
        }

    }
}}