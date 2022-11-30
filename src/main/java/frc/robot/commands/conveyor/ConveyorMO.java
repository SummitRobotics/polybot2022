package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.subsystems.Conveyor;

public class ConveyorMO extends CommandBase {
    Conveyor conveyor;

    OIAxis axis;
    OIAxis.PrioritizedAxis prioritizedAxis;
    
    public ConveyorMO(Conveyor conveyor, OIAxis axis) {
        this.conveyor = conveyor;
        this.axis = axis;
    }
    
    @Override
    public void initialize() {
        this.prioritizedAxis = axis.prioritize(10);
    }

    @Override
    public void execute() {
        conveyor.setBeltPower(prioritizedAxis.get());
    }

    @Override
    public void end(final boolean interrupted) {
        conveyor.stop();
        prioritizedAxis.destroy();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
