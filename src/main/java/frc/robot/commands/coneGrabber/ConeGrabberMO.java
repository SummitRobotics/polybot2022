package frc.robot.commands.coneGrabber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.ConeGrabber;
import frc.robot.utilities.SimpleButton;

/**
 * Manual override for the cone grabber.
 */
public class ConeGrabberMO extends CommandBase {
    
    ConeGrabber coneGrabber;

    OIAxis controlAxis;
    OIAxis.PrioritizedAxis prioritizedControlAxis;

    OIButton grabButton;
    OIButton.PrioritizedButton prioritizedGrabButton;

    OIButton liftButton;
    OIButton.PrioritizedButton prioritizedLiftButton;

    SimpleButton simpleGrabButton;
    SimpleButton simpleLiftButton;

    /**
     * Manual override for the cone grabber.
     *
     * @param coneGrabber the cone grabber subsystem
     * @param controlAxis the control axis used to control the coneGrabber motor
     * @param controlButton the control button used to control the up/down position of the coneGrabber
     */
    public ConeGrabberMO(ConeGrabber coneGrabber, OIButton grabButton, OIButton liftButton) {
        addRequirements(coneGrabber);

        this.coneGrabber = coneGrabber;
        this.grabButton = grabButton;
        this.liftButton = liftButton;
    }

    @Override
    public void initialize() {
        prioritizedGrabButton = grabButton.prioritize(10);
        prioritizedLiftButton = liftButton.prioritize(10);
        simpleGrabButton = new SimpleButton(prioritizedGrabButton::get);
        simpleLiftButton = new SimpleButton(prioritizedLiftButton::get);
    }

    @Override
    public void execute() {
        if (simpleGrabButton.get()) {
            coneGrabber.toggleClamp();
            System.out.println("Clamp position is: " + (coneGrabber.getClampStatus() ? "open" : "closed"));
        }

        if (simpleLiftButton.get()) {
            coneGrabber.toggleLift();
            System.out.println("Lift position is: " + (coneGrabber.getLiftStatus() ? "up" : "down"));
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
