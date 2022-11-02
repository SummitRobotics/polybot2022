package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.utilities.lists.Ports;

public class Intake extends SubsystemBase {
    // private final Solenoid clamp =
        // new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.CLAMP_SOLENOID);
    // private final Solenoid lift =
        // new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.LIFT_SOLENOID);
    private boolean clampStatus;
    private boolean liftStatus;

    public Intake() {
        // clamp.set(true);
        clampStatus = true;
        // lift.set(false);
        liftStatus = false;
    }

    public void setLift(boolean position) {
        // lift.set(position);
        liftStatus = position;
    }

    public void setClamp(boolean position) {
        // clamp.set(position);
        clampStatus = position;
    }

    public void toggleLift() {
        setLift(!liftStatus);
    }

    public void toggleClamp() {
        setClamp(!clampStatus);
    }

    public boolean getClampStatus() {
        return clampStatus;
    }

    public String getClampStatusString() {
        return getClampStatus() ? "Open" : "Closed";
    }

    public boolean getLiftStatus() {
        return liftStatus;
    }

    public String getLiftStatusString() {
        return getLiftStatus() ? "Up" : "Down";
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("Clamp Status", this::getClampStatusString, null);
        builder.addStringProperty("Lift Status", this::getLiftStatusString, null);
    }
}
