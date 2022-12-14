package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.State;

public class ShooterAutomation extends CommandBase {
    
    Shooter shooter;

    private State state;

    public ShooterAutomation(Shooter shooter) {
        this.shooter = shooter;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        state = shooter.getState();
    }

    @Override
    public void execute() {
        state = shooter.getState();

        // update states
        if (state == State.SPOOLING
            && Math.abs(shooter.getShooterRPM() - shooter.getTargetRPM()) <= Shooter.RPM_TOLERANCE) {

            shooter.setState(State.READY_TO_SHOOT);

        } else if (state == State.READY_TO_SHOOT
            && Math.abs(shooter.getShooterRPM() - shooter.getTargetRPM()) > Shooter.RPM_TOLERANCE) {

            shooter.setState(State.SPOOLING);
        }

        // set target speed based on shooter state
        if (state == State.IDLE && shooter.getTargetRPM() != 0) {
            shooter.setTargetRPM(0);
        }
        
        shooter.spoolToSpeed();
    }
    
    @Override
    public void end(final boolean interrupted) {
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
