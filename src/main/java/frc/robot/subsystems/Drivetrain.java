package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Functions;
import frc.robot.utilities.lists.Ports;


public class Drivetrain extends SubsystemBase{
    private final CANSparkMax m_leftMotor = new CANSparkMax(Ports.LEFT_DRIVE, MotorType.kBrushless);
    private final CANSparkMax m_rightMotor = new CANSparkMax(Ports.RIGHT_DRIVE, MotorType.kBrushless);

    public Drivetrain() {
        m_rightMotor.setInverted(true);
        
    }

    public void setLeftMotorPower(double power) {
        m_leftMotor.set(Functions.clampDouble(-1, 1, power));
    }

    public void setRightMotorPower(double power) {
        m_rightMotor.set(Functions.clampDouble(-1, 1, power));
    }
}
