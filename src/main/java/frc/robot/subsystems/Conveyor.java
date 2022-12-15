package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;

public class Conveyor extends SubsystemBase {

    public static double
        BELT_SPEED = 0.75,
        FUNNEL_SPEED = 0.75,
        CAPACITY = 5;

    // state
    public enum State {
        FULL,
        FEED_FORWARD,
        IDLE,
        SHOOTING,
        BACKING_UP,
    }

    private State state;

    // ball count
    private double ballCount;

    // motors
    private final CANSparkMax
        belt = new CANSparkMax(Ports.CONVEYOR_BELT, MotorType.kBrushless), 
        funnel = new CANSparkMax(Ports.CONVEYOR_FUNNEL, MotorType.kBrushless);

    // encoders
    private final RelativeEncoder
        beltEncoder = belt.getEncoder(), 
        funnelEncoder = funnel.getEncoder();

    // sensors
    private final DigitalInput
        topLimit = new DigitalInput(Ports.TOP_LIMIT),
        midLimit = new DigitalInput(Ports.MID_LIMIT),
        bottomLimit = new DigitalInput(Ports.BOTTOM_LIMIT);

    /**
     * Subsystem to control the conveyor of the robot
     */
    public Conveyor() {
        if (getBottomLimit() && getMidLimit() && getTopLimit()) {
            ballCount = CAPACITY;
            state = State.FULL;
        } else {
            throw new RuntimeException("Not enough balls were preloaded!");
        }
    }

    public void setBeltPower(double power) {
        belt.set(power);
    }

    public void setFunnelPower(double power) {
        funnel.set(power);
    }

    public double getBeltEncoderPosition() {
        return beltEncoder.getPosition();
    }

    public double getFunnelEncoderPosition() {
        return funnelEncoder.getPosition();
    }

    public void setBeltEncoder(double position) {
        beltEncoder.setPosition(position);
    }

    public void setFunnelEncoder(double position) {
        funnelEncoder.setPosition(position);
    }

    public double getBeltRPM() {
        return beltEncoder.getVelocity();
    }

    public double getFunnelRPM() {
        return funnelEncoder.getVelocity();
    }

    public void zeroBeltEncoder() {
        beltEncoder.setPosition(0);
    }

    public void zeroFunnelEncoder() {
        funnelEncoder.setPosition(0);
    }

    public void stop() {
        belt.stopMotor();
        funnel.stopMotor();
    }

    public boolean getTopLimit() {
        return topLimit.get();
    }

    public boolean getMidLimit() {
        return midLimit.get();
    }

    public boolean getBottomLimit() {
        return bottomLimit.get();
    }

    public void setBallCount(double count) {
        ballCount = count;
    }

    public double getBallCount() {
        return ballCount;
    }

    public void setState(State value) {
        state = value;
    }

    public State getState() {
        return state;
    }

    public String getStateAsString() {
        if (state == State.FULL) {
            return "Full";
        } else if (state == State.FEED_FORWARD) {
            return "Feed Forward";
        } else if (state == State.IDLE) {
            return "Idle";
        } else if (state == State.SHOOTING) {
            return "Shooting";
        } else {
            return "Backing Up";
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("conveyor");
        builder.addBooleanProperty("top_limit", this::getTopLimit, null);
        builder.addBooleanProperty("mid_limit", this::getMidLimit, null);
        builder.addBooleanProperty("bottom_limit", this::getBottomLimit, null);
        builder.addDoubleProperty("number_of_balls", this::getBallCount, null);
        builder.addStringProperty("state", this::getStateAsString, null);
    }
}
