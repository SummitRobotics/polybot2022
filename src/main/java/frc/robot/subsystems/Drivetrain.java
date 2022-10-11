package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;

public class Drivetrain extends SubsystemBase {

    public final CANSparkMax left = new CANSparkMax(Ports.LEFT_DRIVE, MotorType.kBrushless);
    public final CANSparkMax right = new CANSparkMax(Ports.RIGHT_DRIVE, MotorType.kBrushless);

    public Drivetrain() {
        right.setInverted(true);
    }
}
