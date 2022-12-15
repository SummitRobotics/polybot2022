package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.State;

public class IntakeAutomation extends CommandBase {
    Intake intake;

    private static final double INTAKE_SPEED = 0.75;

    public IntakeAutomation(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setMotorPower((intake.getState() == State.UP) ? INTAKE_SPEED : 0);
    }

    @Override
    public void end(final boolean interrupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
