package frc.robot.commands.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorAutomation extends CommandBase {

    public enum State {
        FULL,
        FEED_FORWARD,
        IDLE,
        SHOOTING,
        BACKING_UP,
    }

    private State state;

    Conveyor conveyor;

    private double ballCount;
    private boolean bottomLimit, midLimit, topLimit, previousMidLimit, previousTopLimit;
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
        // previousBottomLimit = conveyor.getBottomLimit();
        previousMidLimit = conveyor.getMidLimit();
        previousTopLimit = conveyor.getTopLimit();

        if (conveyor.getBottomLimit() && conveyor.getMidLimit() && conveyor.getTopLimit()) {
            ballCount = CAPACITY;
        } else {
            throw new RuntimeException("Not enough balls were preloaded!");
        }
    }

    @Override
    public void execute() {

        bottomLimit = conveyor.getBottomLimit();
        midLimit = conveyor.getMidLimit();
        topLimit = conveyor.getTopLimit();

        // Update state
        if (state == State.IDLE && ballCount >= CAPACITY) {
            state = State.FULL;
        } else if (state == State.IDLE && bottomLimit) {
            state = State.FEED_FORWARD;
        } else if (state == State.FEED_FORWARD && midLimit && !previousMidLimit) {
            state = State.IDLE;
        } else if (state == State.BACKING_UP && midLimit) {
            state = State.IDLE;
        } else if (state == State.SHOOTING && !topLimit && previousTopLimit) {
            state = State.BACKING_UP;
            conveyor.setIsShooting(false);
        } else if ((state == State.IDLE || state == State.FULL) && conveyor.getIsShooting()) {
            state = State.SHOOTING;
        }

        // Set belt speed
        if (state == State.IDLE || state == State.FULL) {
            conveyor.setBeltPower(0);
        } else if (state == State.FEED_FORWARD || state == State.SHOOTING) {
            conveyor.setBeltPower(BELT_SPEED);
        } else if (state == State.BACKING_UP) {
            conveyor.setBeltPower(-BELT_SPEED);
        }

        // Set funnel speed
        if (state == State.IDLE) {
            conveyor.setFunnelPower(FUNNEL_SPEED);
        } else {
            conveyor.setFunnelPower(0);
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
