package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorAutomation extends CommandBase {
    Conveyor conveyor;

    private double ballCount;
    private boolean bottomLimit, midLimit, topLimit, previousMidLimit, previousTopLimit, loading;
    // private boolean previousBottomLimit;

    private static double
        BELT_SPEED = 0.75,
        FUNNEL_SPEED = 0.75,
        CAPACITY = 5;

    public ConveyorAutomation(Conveyor conveyor) {
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        ballCount = 0;
        loading = false;
        // previousBottomLimit = conveyor.getBottomLimit();
        previousMidLimit = conveyor.getMidLimit();
        previousTopLimit = conveyor.getTopLimit();
    }

    @Override
    public void execute() {
        bottomLimit = conveyor.getBottomLimit();
        midLimit = conveyor.getMidLimit();
        topLimit = conveyor.getTopLimit();

        // Always run the funnel motor
        conveyor.setFunnelPower(FUNNEL_SPEED);

        // Start loading a ball if the bottom limit switch is triggered and we have space
        if (bottomLimit && ballCount < CAPACITY && !loading) {
            loading = true;
            ballCount++;
        }

        // If we're loading a ball, run the conveyor until the mid switch registers
        if (loading) {
            if (midLimit && !previousMidLimit) {
                conveyor.setBeltPower(0);
                loading = false;
            } else {
                conveyor.setBeltPower(BELT_SPEED);
            }
        }

        // Reduce the count if we fired a ball
        if (!topLimit && previousTopLimit) {
            ballCount--;
        }

        previousTopLimit = topLimit;
        previousMidLimit = midLimit;
        // previousBottomLimit = bottomLimit;
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
