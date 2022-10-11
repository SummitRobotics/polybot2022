import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.ConeGrabber;
import frc.robot.utilities.SimpleButton;
import frc.robot.utilities.lists.AxisPriorities;


/**
 * Manual override for the ConeGrabber.
 */
public class ConeGrabberMO extends CommandBase {
    
    ConeGrabber coneGrabber;

    OIButton controlLiftButton;
    OIButton.PrioritizedButton prioritizedLiftControlButton;

    OIButton controlClampButton;
    OIButton.PrioritizedButton prioritizedClampControlButton;

    SimpleButton prioritizedSimpleClampControlButton;
    SimpleButton prioritizedSimpleLiftControlButton;

    /**
     * Manual override for the ConeGrabber.
     *
     * @param ConeGrabber the ConeGrabber subsystem
     * @param controlClampButton the control button used to control the open/close position of the ConeGrabber
     * @param controlLiftButton the control button used to control the up/down position of the ConeGrabber
     */
    public ConeGrabberMO(ConeGrabber conegrabber, OIButton controlClampButton, OIButton controlLiftButton) {
        addRequirements(conegrabber);

        this.coneGrabber = conegrabber;
       
        this.controlLiftButton = controlClampButton;
        this.controlClampButton = controlLiftButton;
    }

    @Override
    public void initialize() {

        prioritizedLiftControlButton = controlLiftButton.prioritize(AxisPriorities.MANUAL_OVERRIDE);
        prioritizedClampControlButton = controlClampButton.prioritize(AxisPriorities.MANUAL_OVERRIDE);

        prioritizedSimpleClampControlButton = new SimpleButton(prioritizedClampControlButton::get);
        prioritizedSimpleLiftControlButton = new SimpleButton(prioritizedLiftControlButton::get);
    }

    @Override
    public void execute() {
       
        if (prioritizedSimpleClampControlButton.get()) {
            coneGrabber.toggleClampSolenoid();
        }

        if (prioritizedSimpleLiftControlButton.get()) {
            coneGrabber.toggleLiftSolenoid();
        }
    }

    @Override
    public void end(final boolean interrupted) {

        prioritizedSimpleLiftControlButton = null;
        prioritizedSimpleClampControlButton = null;
        prioritizedLiftControlButton.destroy();
        prioritizedClampControlButton.destroy();
       
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
