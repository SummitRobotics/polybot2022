package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;

public class Intake extends SubsystemBase {
    // motor
    private final CANSparkMax motor = new CANSparkMax(Ports.INTAKE_MOTOR, MotorType.kBrushless);

    // encoder
    private final RelativeEncoder encoder = motor.getEncoder();

    // solenoids
    private final Solenoid
        solenoid = new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.INTAKE_SOLENOID);

    private static final double INTAKE_RATE = 0.5;

    public enum State {
        UP,
        DOWN
    }

    private State state;

    /**
     * Subsystem to control the intake of the robot
     */
    public Intake() {
        solenoid.set(false);
        state = State.UP;

        motor.setOpenLoopRampRate(INTAKE_RATE);
    }

    public void setMotorPower(double power) {
        motor.set(power);
    }

    public double getEncoderPosition() {
        return encoder.getPosition();
    }

    public void setEncoderPosition(double position) {
        encoder.setPosition(position);
    }

    public void zeroEncoder() {
        encoder.setPosition(0);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setIntakeState(State state) {
        if (this.state != state) {
            solenoid.toggle();
            this.state = state;
        }
    }

    public void toggleIntakeState() {
        solenoid.toggle();
        state = (state == State.UP) ? State.DOWN : State.UP;
    }

    public State getState() {
        return state;
    }

    public String getStateAsString() {
        return (state == State.UP) ? "Up" : "Down";
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("intake");
        builder.addStringProperty("state", this::getStateAsString, null);
    }
}
