package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.Intake;
import frc.robot.utilities.SimpleButton;

/**
 * Manual override for the intake.
 */
public class IntakeMO extends CommandBase {
    
    Intake intake;

    OIAxis controlAxis;
    OIAxis.PrioritizedAxis prioritizedControlAxis;

    OIButton grabButton;
    OIButton.PrioritizedButton prioritizedGrabButton;

    OIButton liftButton;
    OIButton.PrioritizedButton prioritizedLiftButton;

    SimpleButton simpleGrabButton;
    SimpleButton simpleLiftButton;

    boolean previousClampStatus;
    boolean previousLiftStatus;

    /**
     * Manual override for the intake.
     *
     * @param intake the intake subsystem
     * @param controlAxis the control axis used to control the intake motor
     * @param controlButton the control button used to control the up/down position of the intake
     */
    public IntakeMO(Intake intake, OIButton grabButton, OIButton liftButton) {
        addRequirements(intake);

        this.intake = intake;
        this.grabButton = grabButton;
        this.liftButton = liftButton;
    }

    @Override
    public void initialize() {
        prioritizedGrabButton = grabButton.prioritize(10);
        prioritizedLiftButton = grabButton.prioritize(10);
        simpleGrabButton = new SimpleButton(prioritizedGrabButton::get);
        simpleLiftButton = new SimpleButton(prioritizedLiftButton::get);
        previousClampStatus = true;
        previousLiftStatus = false;
    }

    @Override
    public void execute() {
        if (simpleGrabButton.get()) {
            intake.toggleClamp();
        }

        if (simpleLiftButton.get()) {
            intake.toggleLift();
        }

        if (previousClampStatus != intake.getClampStatus()) {
            if (intake.getClampStatus() == true) {
                System.out.println("Clamp position is: open");
            } else {
                System.out.println("Clamp position is: closed");
            }

            previousClampStatus = intake.getClampStatus();
        }

        if (previousLiftStatus != intake.getLiftStatus()) {
            if (intake.getLiftStatus() == true) {
                System.out.println("Lift position is: up");
            } else {
                System.out.println("Lift position is: down");
            }

            previousLiftStatus = intake.getLiftStatus();
        }

    }

    @Override
    public void end(final boolean interrupted) {
        simpleGrabButton = null;
        simpleLiftButton = null;
        prioritizedGrabButton.destroy();
        prioritizedLiftButton.destroy();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
