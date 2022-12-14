package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.Functions;
import frc.robot.utilities.lists.Ports;

public class Shooter extends SubsystemBase {

    public static final double
        MAX_RPM = 5000,
        P = 0,
        I = 0,
        D = 0,
        FF = 0,
        IZ = 0;

    private boolean isShooting;

    private final CANSparkMax mainMotor =
        new CANSparkMax(Ports.SHOOTER_MOTOR_MAIN, MotorType.kBrushless);

    private final CANSparkMax followMotor =
        new CANSparkMax(Ports.SHOOTER_MOTOR_FOLLOW, MotorType.kBrushless);

    private final Solenoid hoodSolenoid =
        new Solenoid(Ports.PCM, PneumaticsModuleType.REVPH, Ports.HOOD_SOLENOID);

    private final RelativeEncoder encoder = mainMotor.getEncoder();

    private final SparkMaxPIDController pidController = mainMotor.getPIDController();

    public Shooter() {
        pidController.setP(P);
        pidController.setI(I);
        pidController.setD(D);
        pidController.setFF(FF);
        pidController.setIZone(IZ);
        pidController.setOutputRange(-1.0, 1.0);
        mainMotor.enableVoltageCompensation(12);

        zeroEncoders();
        hoodSolenoid.set(false);
        followMotor.follow(mainMotor, true);
        isShooting = false;
    }

    public void setMotorPower(double power) {
        mainMotor.set(power);
    }

    public void stop() {
        setMotorPower(0);
    }

    public void zeroEncoders() {
        setEncoderValue(0);
    }

    public void setEncoderValue(double position) {
        encoder.setPosition(position);
    }

    public void setMotorTargetSpeed(double speed) {
        speed = Functions.clampDouble(speed, MAX_RPM, -MAX_RPM);
    }

    public double getShooterRPM() {
        return encoder.getVelocity();
    }

    public void setIsShooting(boolean value) {
        isShooting = value;
    }

    public boolean getIsShooting() {
        return isShooting;
    }
}
