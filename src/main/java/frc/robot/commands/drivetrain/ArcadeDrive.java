<<<<<<< HEAD
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
=======
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import frc.robot.oi.inputs.OIAxis;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {
  
    private Drivetrain m_drivetrain;
    private OIAxis m_powerAxis;
    private OIAxis m_turnAxis;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArcadeDrive(Drivetrain drivetrain, OIAxis powerAxis, OIAxis turnAxis) {
      m_drivetrain = drivetrain;
      m_powerAxis = powerAxis;
      m_turnAxis = turnAxis;
    
   addRequirements(drivetrain);
>>>>>>> a23a5f8 (jordan's changes)
  }

  // Called when the command is initially scheduled.
  @Override
<<<<<<< HEAD
  public void initialize() {}
=======
  public void initialize() {

  }
>>>>>>> a23a5f8 (jordan's changes)

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
<<<<<<< HEAD
    robotDrive.arcadeDrive(-joystick.getY(), joystick.getX());
=======

>>>>>>> a23a5f8 (jordan's changes)
  }

  // Called once the command ends or is interrupted.
  @Override
<<<<<<< HEAD
  public void end(boolean interrupted) {}
=======
  public void end(boolean interrupted) 
  {}
>>>>>>> a23a5f8 (jordan's changes)

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
