// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.conveyor.ConveyorAutomation;
// import frc.robot.commands.coneGrabber.ConeGrabberMO;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.intake.IntakeAutomation;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.ShooterAutomation;
import frc.robot.devices.PCM;
// import frc.robot.devices.Lemonlight;
import frc.robot.oi.drivers.ControllerDriver;
import frc.robot.oi.drivers.JoystickDriver;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.ConeGrabber;
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
  private final Conveyor conveyor;
  private final Intake intake;
  private final Shooter shooter;
  // private final ConeGrabber coneGrabber;
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  // private final ConeGrabberMO coneGrabberMO;
  private final ControllerDriver controller;
  private final JoystickDriver joystick;
  private final PCM pcm;
  // private final Lemonlight limelight;

  private final Command teleInit;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    scheduler = CommandScheduler.getInstance();
    controller = new ControllerDriver(Ports.CONTROLLER);
    joystick = new JoystickDriver(Ports.JOYSTICK);
    drivetrain = new Drivetrain();
    conveyor = new Conveyor();
    intake = new Intake();
    shooter = new Shooter();
    // coneGrabber = new ConeGrabber();
    // coneGrabberMO = new ConeGrabberMO(coneGrabber, controller.buttonA, controller.buttonB);
    pcm = new PCM();
    // limelight = new Lemonlight("limelight", true, true);
    // Configure the button bindings
    configureButtonBindings();
    setDefaultCommands();

    teleInit = new SequentialCommandGroup(
      // new InstantCommand(() -> pcm.enableCompressorDigital()),
      new InstantCommand(() -> initTelemetry())
    );
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // controller.buttonX.whileHeld(new FullAutoconeGrabber(drivetrain, coneGrabber, limelight));
    controller.buttonX.whileHeld(
        new Shoot(shooter, conveyor, drivetrain, joystick.trigger, joystick.axisY, joystick.axisZ));
    controller.buttonY.whenPressed(new InstantCommand(() -> intake.toggleIntakeState()));
  }

  private void setDefaultCommands() {
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, controller.leftX, controller.leftY));
    conveyor.setDefaultCommand(new ConveyorAutomation(conveyor));
    intake.setDefaultCommand(new IntakeAutomation(intake));
    shooter.setDefaultCommand(new ShooterAutomation(shooter));
    // coneGrabber.setDefaultCommand(coneGrabberMO);
  }

  /**
   * Runs when the robot initializes in teleop mode
   */
  public void teleopInit() {
    scheduler.schedule(teleInit);
  }

  public void initTelemetry() {
    SmartDashboard.putData("PCM", pcm);
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
