// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.oi.drivers.ControllerDriver;
import frc.robot.oi.drivers.JoystickDriver;
import frc.robot.oi.drivers.LaunchpadDriver;
import frc.robot.subsystems.ConeGrabber;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.utilities.lists.Ports;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ConeGrabberMO;
import edu.wpi.first.wpilibj.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem(); 
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final Drivetrain drivetrain;
  private final frc.robot.subsystems.ConeGrabber coneGrabber;

 
  private final ArcadeDrive arcadeDrive;
 private final JoystickDriver joystick;
 private final LaunchpadDriver launchPad;
 private final ControllerDriver controller;
  private final ConeGrabberMO coneGrabberMO;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    drivetrain = new Drivetrain();
    joystick = new JoystickDriver(Ports.JOYSTICK_PORT);
    arcadeDrive = new ArcadeDrive(drivetrain, joystick.axisX, joystick.axisY);
    coneGrabber = new ConeGrabber();
    launchPad = new LaunchpadDriver(Ports.LAUNCHPAD_PORT);
    controller = new ControllerDriver(Ports.CONTROLLER_PORT);
    coneGrabberMO = new ConeGrabberMO(coneGrabber, controller.buttonA, controller.buttonB);
    // Configure the button bindings
    setDefaultCommands();
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  private void setDefaultCommands() {
    drivetrain.setDefaultCommand(arcadeDrive);

    coneGrabber.setDefaultCommand(coneGrabberMO);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
