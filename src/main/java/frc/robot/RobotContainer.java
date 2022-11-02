// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.drivetrain.ArcadeDrive;
// import frc.robot.commands.intake.FullAutoIntake;
import frc.robot.commands.intake.IntakeMO;
// import frc.robot.devices.Lemonlight;
import frc.robot.oi.drivers.ControllerDriver;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.utilities.lists.Ports;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final CommandScheduler scheduler;

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final ArcadeDrive arcadeDrive;
  private final IntakeMO intakeMO;
  private final ControllerDriver controller;
  private final PneumaticsControlModule pcm;
  // private final Lemonlight limelight;

  private final Command teleInit;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    scheduler = CommandScheduler.getInstance();
    controller = new ControllerDriver(Ports.CONTROLLER);
    drivetrain = new Drivetrain();
    intake = new Intake();
    arcadeDrive = new ArcadeDrive(drivetrain, controller.leftX, controller.leftY);
    intakeMO = new IntakeMO(intake, controller.buttonA, controller.buttonB);
    pcm = new PneumaticsControlModule(Ports.PCM);
    // limelight = new Lemonlight("limelight", true, true);
    // Configure the button bindings
    configureButtonBindings();
    setDefaultCommands();

    teleInit = new SequentialCommandGroup(
      new InstantCommand(() -> pcm.enableCompressorDigital())
    );
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // controller.buttonX.whileHeld(new FullAutoIntake(drivetrain, intake, limelight));
  }

  private void setDefaultCommands() {
    drivetrain.setDefaultCommand(arcadeDrive);
    intake.setDefaultCommand(intakeMO);
  }

  /**
   * Runs when the robot initializes in teleop mode
   */
  public void teleopInit() {
    scheduler.schedule(teleInit);
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
