package frc.robot.devices;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticsControlModule;

public class PCM extends PneumaticsControlModule implements Sendable {

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("pressure switch", this::getPressureSwitch, null);
        builder.addDoubleProperty("current", this::getCompressorCurrent, null);
    }
}
