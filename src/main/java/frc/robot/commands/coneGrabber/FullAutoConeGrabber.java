package frc.robot.commands.coneGrabber;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.devices.Lemonlight;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ConeGrabber;
import frc.robot.utilities.Functions;

public class FullAutoConeGrabber extends CommandBase {
    private static final double
        DISTANCE_FROM_CONE = 0,
        MOVE_P = 0.2,
        MOVE_I = 0,
        MOVE_D = 0,
        ALIGN_P = 0.2,
        ALIGN_I = 0,
        ALIGN_D = 0;    

    // subsystems
    private Drivetrain drivetrain;
    private ConeGrabber coneGrabber;

    // devices
    private Lemonlight limelight;

    // PID controllers
    private PIDController movePID;
    private PIDController alignPID;

    // tracker variables
    private double limelightDistanceEstimate;
    private boolean limelightHasTarget;
    private double horizontalOffset;

    /**
     * Constructor for FullAutoConeGrabber
     * 
     * @param drivetrain The drivetrain subsystem
     * @param limelight The limelight device
     */
    public FullAutoConeGrabber(Drivetrain drivetrain, ConeGrabber coneGrabber, Lemonlight limelight) {

        this.drivetrain = drivetrain;
        this.coneGrabber = coneGrabber;
        this.limelight = limelight;
        this.movePID = new PIDController(MOVE_P, MOVE_I, MOVE_D);
        this.movePID = new PIDController(ALIGN_P, ALIGN_I, ALIGN_D);

        movePID.setTolerance(1, 1);
        movePID.setSetpoint(DISTANCE_FROM_CONE);
        alignPID.setTolerance(1, 1);
        alignPID.setSetpoint(0);

        addRequirements(drivetrain, coneGrabber);
    }

    @Override
    public void initialize() {
        movePID.reset();
        alignPID.reset();
    }

    @Override
    public void execute() {
        limelightHasTarget = limelight.hasTarget();
        limelightDistanceEstimate = Lemonlight.getDistanceInches(limelight.getVerticalOffset());
        horizontalOffset = limelight.getHorizontalOffset();

        if (limelightHasTarget) {
            double alignPower = -alignPID.calculate(horizontalOffset);
            double movePower =  -Functions.clampDouble(movePID.calculate(limelightDistanceEstimate), 0.3, -0.3);

            // System.out.println("align: " + alignPower + "   drive: " + movePower);
            drivetrain.setLeftMotorPower(movePower + alignPower);
            drivetrain.setRightMotorPower(movePower - alignPower);

            if (alignPID.atSetpoint() && limelightDistanceEstimate < 3) {
                coneGrabber.setClamp(false);
            }
        }

    }

    @Override
    public boolean isFinished() {
        return !limelight.hasTarget() || movePID.atSetpoint();
    }
}
