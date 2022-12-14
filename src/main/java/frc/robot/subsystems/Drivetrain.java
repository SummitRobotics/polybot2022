package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Functions;
import frc.robot.utilities.lists.Ports;

public class Drivetrain extends SubsystemBase {

    public static final CANSparkMax left = new CANSparkMax(Ports.LEFT_DRIVE, MotorType.kBrushless);
    public static final CANSparkMax right = new CANSparkMax(Ports.RIGHT_DRIVE, MotorType.kBrushless);

    public Drivetrain() {
        right.setInverted(true);
    }

    public void setLeftMotorPower(double power) {
        left.set(Functions.clampDouble(power, 1, -1));
    }

    public void setRightMotorPower(double power) {
        right.set(Functions.clampDouble(power, 1, -1));
    }

    public void stop() {
        right.set(0);
        left.set(0);
    }
}
