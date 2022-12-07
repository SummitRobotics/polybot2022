package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.Intake;
import frc.robot.utilities.SimpleButton;

public class IntakeMO extends CommandBase {
    
    Intake intake;
    OIAxis axis;
    OIAxis.PrioritizedAxis prioritizedAxis;
    OIButton button;
    OIButton.PrioritizedButton prioritizedButton;
    SimpleButton simpleButton;
    
    public IntakeMO(Intake intake, OIAxis axis, OIButton button) {
        this.intake = intake;
        this.axis = axis;
        this.button = button;
    }

    @Override
    public void initialize() {
        this.prioritizedAxis = axis.prioritize(10);
        simpleButton = new SimpleButton(prioritizedButton::get);
    }

    @Override
    public void execute() {
        intake.setMotorPower(prioritizedAxis.get());
        if (simpleButton.get()) intake.toggleIntakeState();
    }

    @Override
    public void end(final boolean interrupted) {
        intake.stop();
        simpleButton = null;
        prioritizedAxis.destroy();
        prioritizedButton.destroy();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
