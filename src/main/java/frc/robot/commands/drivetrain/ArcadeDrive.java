package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {

  private DifferentialDrive robotDrive;
  private OIAxis axisX;
  private OIAxis axisY;

  /**
   * Constructor for ArcadeDrive
   *
   * @param drivetrain The drivetrain subsystem
   * @param joystick The joystick controlling the drivetrain
   */
  public ArcadeDrive(Drivetrain drivetrain, OIAxis axisX, OIAxis axisY) {

    this.axisX = axisX;
    this.axisY = axisY;
    robotDrive = new DifferentialDrive(Drivetrain.left, Drivetrain.right);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    robotDrive.arcadeDrive(axisX.get(), axisY.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
