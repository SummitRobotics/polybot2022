package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class ConeGrabber extends SubsystemBase {
    private final Solenoid clampSolenoid =
    new Solenoid(Ports.PCM_1, PneumaticsModuleType.REVPH, Ports.SOLENOID_1);

    private final Solenoid liftSolenoid =
    new Solenoid(Ports.PCM_1, PneumaticsModuleType.REVPH, Ports.SOLENOID_2);

    private SolenoidPosition clampPosition;

    private SolenoidPosition liftPosition;

    public enum SolenoidPosition {
        UP,
        DOWN,
    }

    public void toggleClampSolenoid() {
        clampPosition = clampPosition == SolenoidPosition.DOWN ? SolenoidPosition.UP : SolenoidPosition.DOWN;
        setSolenoid(clampSolenoid, clampPosition);
    }

    public void toggleLiftSolenoid() {
        liftPosition = liftPosition == SolenoidPosition.DOWN ? SolenoidPosition.UP : SolenoidPosition.DOWN;
        setSolenoid(liftSolenoid, liftPosition);
    }

    public void setSolenoid(Solenoid s, SolenoidPosition value) {
        System.out.println("Set solenoid: " + s.getChannel() + " - " + value);
       // s.set(value == SolenoidPosition.UP);
    }

    
}
