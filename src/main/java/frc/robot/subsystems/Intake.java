package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;

public class Intake extends SubsystemBase {
    private final Solenoid clamp =
        new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.CLAMP_SOLENOID);
    private final Solenoid lift =
        new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.LIFT_SOLENOID);
    private boolean clampStatus;
    private boolean liftStatus;

    public Intake() {
        clamp.set(true);
        clampStatus = true;
        lift.set(false);
        liftStatus = false;
    }

    public void setLift(boolean position) {
        lift.set(position);
        liftStatus = position;
    }

    public void setClamp(boolean position) {
        clamp.set(position);
        clampStatus = position;
    }

    public void toggleLift() {
        setLift(!liftStatus);
    }

    public void toggleClamp() {
        setClamp(!clampStatus);
    }
}
