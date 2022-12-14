package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.oi.inputs.OIButton;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.State;
import frc.robot.utilities.SimpleButton;

public class Shoot extends CommandBase {

    Shooter shooter;
    Conveyor conveyor;
    Drivetrain drivetrain;

    OIButton indexButton;
    OIButton.PrioritizedButton prioritizedIndexButton;
    SimpleButton simpleIndexButton;

    OIButton activeButton;
    OIButton.PrioritizedButton prioritizedActiveButton;

    OIAxis turnAxis;
    OIAxis.PrioritizedAxis prioritizedTurnAxis;

    OIAxis speedAxis;
    OIAxis.PrioritizedAxis prioritizedSpeedAxis;

    public Shoot(
        Shooter shooter,
        Conveyor conveyor,
        Drivetrain drivetrain,
        OIButton indexButton,
        OIButton activeButton,
        OIAxis turnAxis,
        OIAxis speedAxis) {

        this.shooter = shooter;
        this.conveyor = conveyor;
        this.drivetrain = drivetrain;
        this.indexButton = indexButton;
        this.activeButton = activeButton;
        this.turnAxis = turnAxis;

        addRequirements(shooter, conveyor, drivetrain);
    }

    @Override
    public void initialize() {

        // set up buttons and axes
        prioritizedTurnAxis = turnAxis.prioritize(8);
        prioritizedSpeedAxis = speedAxis.prioritize(8);
        prioritizedIndexButton = indexButton.prioritize(8);
        prioritizedActiveButton = activeButton.prioritize(8);
        simpleIndexButton = new SimpleButton(prioritizedIndexButton::get);

        // start motor spooling
        shooter.setState(State.SPOOLING);
    }

    @Override
    public void execute() {
        drivetrain.setLeftMotorPower(prioritizedTurnAxis.get());
        drivetrain.setLeftMotorPower(-prioritizedTurnAxis.get());
        shooter.setTargetRPM(prioritizedSpeedAxis.get());

        // We never want to be idle when this command is running
        if (shooter.getState() == State.IDLE) shooter.setState(State.SPOOLING);

        // Tell the conveyor to index a ball when the conditions are right
        if (shooter.getState() == State.READY_TO_SHOOT
            && (conveyor.getState() == Conveyor.State.IDLE
                || conveyor.getState() == Conveyor.State.FULL)
            && simpleIndexButton.get()) {
                conveyor.setState(Conveyor.State.SHOOTING);
            }
    }

    @Override
    public void end(final boolean interrupted) {
        drivetrain.stop();
        conveyor.stop();
        shooter.stop();
        simpleIndexButton = null;
        prioritizedTurnAxis.destroy();
        prioritizedSpeedAxis.destroy();
        prioritizedActiveButton.destroy();
        prioritizedIndexButton.destroy();
    }

    @Override
    public boolean isFinished() {
        return !prioritizedActiveButton.get();
    }
}
