package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {

  private Joystick joystick;
  private DifferentialDrive robotDrive;

  /**
   * Constructor for ArcadeDrive
   *
   * @param drivetrain The drivetrain subsystem
   * @param joystick The joystick controlling the drivetrain
   */
  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick) {

    this.joystick = joystick;
    robotDrive = new DifferentialDrive(Drivetrain.left, Drivetrain.right);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    robotDrive.arcadeDrive(-joystick.getY(), joystick.getX());
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
