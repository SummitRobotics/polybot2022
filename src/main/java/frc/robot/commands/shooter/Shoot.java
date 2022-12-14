package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

    Shooter shooter;
    Conveyor conveyor;

    public Shoot(Shooter shooter, Conveyor conveyor, OIButton indexButton, OIAxis turnAxis) {
        this.shooter = shooter;
        this.conveyor = conveyor;
    }
}
