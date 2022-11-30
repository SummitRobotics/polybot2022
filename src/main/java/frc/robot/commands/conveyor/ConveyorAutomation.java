package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorAutomation extends CommandBase {
    Conveyor conveyor;

    private double ballCount;
    private boolean bottomLimit;
    private boolean topLimit;
    private boolean previousBottomLimit;
    private boolean previousTopLimit;

    private static double BELT_SPEED = 0.75;

    public ConveyorAutomation(Conveyor conveyor) {
        this.conveyor = conveyor;

        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        ballCount = 0;
        previousBottomLimit = conveyor.getBottomLimit();
        previousTopLimit = conveyor.getTopLimit();
    }

    @Override
    public void execute() {
        bottomLimit = conveyor.getBottomLimit();
        topLimit = conveyor.getTopLimit();

        if (bottomLimit && !previousBottomLimit) {
            ballCount++;
        }

        if (!topLimit && previousTopLimit) {
            ballCount--;
        }

        if (!topLimit) {
            conveyor.setBeltPower(BELT_SPEED);
        } else {
            conveyor.setBeltPower(0);
        }

        previousTopLimit = topLimit;
        previousBottomLimit = bottomLimit;
        conveyor.setBallCount(ballCount);
    }

    @Override
    public void end(final boolean interrupted) {
        conveyor.stop();
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
