/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.*;
import frc.robot.subsystems.LED;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public RobotContainer m_robotContainer;
  private static boolean bootCycle;
  private static boolean enableDrivetrainCalibration = true;

  private Command m_autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // m_gyro.reset();
    RobotContainer.m_drivetrain.init();
    // RobotContainer.m_magazine.init();
    // RobotContainer.m_intake.init();
    // RobotContainer.m_shooter.init();
    // RobotContainer.m_lift.init();
    // RobotContainer.m_tilt.init();

    bootCycle = true;
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    
    // SmartDashboard.putBoolean("Centric Set", m_drivetrain.getCentric());
    // SmartDashboard.putNumber("Gyro Yaw", m_gyro.getYawDeg());
    // SmartDashboard.putNumber("Gyro Pitch", m_gyro.getPitchDeg());
    // SmartDashboard.putNumber("Gyro Roll", m_gyro.getRollDeg());
    SmartDashboard.putNumber("Ball Count" , RobotContainer.m_magazine.getBallCount());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    RobotContainer.m_led.clearLED();
    System.out.println("//////////////////// DISABLED Init /////////////////");
  }

  @Override
  public void disabledPeriodic() {
    RobotContainer.m_led.rainbowLED();
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    // Adding calDriveTrain to scheduler if booting (ie not enable/disable in DS)
    System.out.println("//////////////////// TeleopInit /////////////////");

    // m_gyro.reset();
    Timer.delay(.5);

    // if (bootCycle && enableDrivetrainCalibration){
    // Scheduler.getInstance().add(new CalibrateDriveTrain());
    // Scheduler.getInstance().run();
    // }

    // m_vision.setCamMode(1); // default to regular vision mode, not tracking mode
    // m_vision.ledOff();

    CommandScheduler.getInstance().schedule(new HighSpeedDrive());
    CommandScheduler.getInstance().run();

    bootCycle = false;

    System.out.println("//////////////////// Teleop /////////////////");
    CommandScheduler.getInstance().schedule(new LoadMagazine());
    CommandScheduler.getInstance().schedule(new Drive());
    // CommandScheduler.getInstance().schedule(new GetColor());
    RobotContainer.m_led.clearLED();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    RobotContainer.m_led.dispBallCountLED();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    System.out.println("X: "+RobotContainer.getDriverX());
    System.out.println("Y: "+RobotContainer.getDriverY());
    System.out.println("Z: "+RobotContainer.getDriverZ());
  }
}
