package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Conveyor.State;

public class ConveyorAutomation extends CommandBase {

    private State state;

    Conveyor conveyor;

    private boolean bottomLimit, midLimit, topLimit, previousMidLimit, previousTopLimit;
    // private boolean previousBottomLimit;

    public ConveyorAutomation(Conveyor conveyor) {
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void initialize() {
        // previousBottomLimit = conveyor.getBottomLimit();
        previousMidLimit = conveyor.getMidLimit();
        previousTopLimit = conveyor.getTopLimit();
        state = conveyor.getState();
    }

    @Override
    public void execute() {

        bottomLimit = conveyor.getBottomLimit();
        midLimit = conveyor.getMidLimit();
        topLimit = conveyor.getTopLimit();
        state = conveyor.getState();

        // Update state
        if (state == State.IDLE && conveyor.getBallCount() >= Conveyor.CAPACITY) {
            conveyor.setState(State.FULL);
        } else if (state == State.IDLE && bottomLimit) {
            conveyor.setState(State.FEED_FORWARD);
            conveyor.setBallCount(conveyor.getBallCount() + 1);
        } else if (state == State.FEED_FORWARD && midLimit && !previousMidLimit) {
            conveyor.setState(State.IDLE);
        } else if (state == State.BACKING_UP && midLimit) {
            conveyor.setState(State.IDLE);
        } else if (state == State.SHOOTING && !topLimit && previousTopLimit) {
            conveyor.setState(State.BACKING_UP);
            conveyor.setBallCount(conveyor.getBallCount() - 1);
        }

        // Set belt speed
        if (state == State.IDLE || state == State.FULL) {
            conveyor.setBeltPower(0);
        } else if (state == State.FEED_FORWARD || state == State.SHOOTING) {
            conveyor.setBeltPower(Conveyor.BELT_SPEED);
        } else if (state == State.BACKING_UP) {
            conveyor.setBeltPower(-Conveyor.BELT_SPEED);
        }

        // Set funnel speed
        if (state == State.IDLE) {
            conveyor.setFunnelPower(Conveyor.FUNNEL_SPEED);
        } else {
            conveyor.setFunnelPower(0);
        }

        previousTopLimit = topLimit;
        previousMidLimit = midLimit;
        // previousBottomLimit = bottomLimit;
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
