package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.subsystems.Conveyor;
import frc.robot.utilities.Functions;

public class ConveyorMO extends CommandBase {
    
    Conveyor conveyor;
    OIAxis axis1, axis2;
    OIAxis.PrioritizedAxis prioritizedAxis1, prioritizedAxis2;
    
    public ConveyorMO(Conveyor conveyor, OIAxis axis1, OIAxis axis2) {
        this.conveyor = conveyor;
        this.axis1 = axis1;
        this.axis2 = axis2;
    }
    
    @Override
    public void initialize() {
        this.prioritizedAxis1 = axis1.prioritize(10);
        this.prioritizedAxis2 = axis2.prioritize(10);
    }

    @Override
    public void execute() {
        conveyor.setBeltPower(Functions.clampDouble(prioritizedAxis1.get(), 1, -1));
        conveyor.setFunnelPower(Functions.clampDouble(prioritizedAxis2.get(), 1, -1));
    }

    @Override
    public void end(final boolean interrupted) {
        conveyor.stop();
        prioritizedAxis1.destroy();
        prioritizedAxis2.destroy();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
