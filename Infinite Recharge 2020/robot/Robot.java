/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.commands.*;

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
  private static boolean enableCalibration = true;

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

    // RobotContainer.m_gyro.reset();
    // RobotContainer.m_drivetrain.init();
    RobotContainer.m_magazine.init();
    // RobotContainer.m_intake.init();
    RobotContainer.m_shooter.init();
    // RobotContainer.m_lift.init();
    RobotContainer.m_tilt.init();
    // RobotContainer.m_colorAndZipline.init();

    bootCycle = true;
    RobotContainer.m_drivetrain.setTargetTrackMode(true);
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

    // RobotContainer.m_colorAndZipline.getColor();

    // SmartDashboard.putBoolean("Centric Set", m_drivetrain.getCentric());
    // SmartDashboard.putNumber("Gyro Yaw", m_gyro.getYawDeg());
    // SmartDashboard.putNumber("Gyro Pitch", m_gyro.getPitchDeg());
    // SmartDashboard.putNumber("Gyro Roll", m_gyro.getRollDeg());
    SmartDashboard.putNumber("Ball Count" , RobotContainer.m_magazine.getBallCount());
    SmartDashboard.putBoolean("BallReady", RobotContainer.m_magazine.getBallReady());
    SmartDashboard.putBoolean("BallLoaded", RobotContainer.m_magazine.getBallLoaded());
    SmartDashboard.putBoolean("Mag Full", RobotContainer.m_magazine.getMagFull());
    SmartDashboard.putNumber("TyAverage", RobotContainer.m_drivetrain.getTyAvg());
    SmartDashboard.putNumber("TargetTiltPos", RobotContainer.m_drivetrain.getTargetTiltPos());
    SmartDashboard.putNumber("distance to target", RobotContainer.m_drivetrain.getDistance());
    SmartDashboard.putData(CommandScheduler.getInstance());
  }
  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    System.out.println("//////////////////// DISABLED Init /////////////////");
    // RobotContainer.m_led.clearLED();
    RobotContainer.m_tilt.stopTilt();
  }

  @Override
  public void disabledPeriodic() {
    // RobotContainer.m_led.rainbowLED();
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    Timer.delay(.5);

    if (bootCycle && enableCalibration){
      // CommandScheduler.getInstance().schedule(new CalibrateDriveTrain());
      CommandScheduler.getInstance().schedule(new CalibrateTilt());
    } else /*CommandScheduler.getInstance().schedule(new TiltMagToLow())*/;
    
    CommandScheduler.getInstance().schedule(new LoadMagazine());

    RobotContainer.m_drivetrain.maxDrivePower(.5);

    AutoPaths.driveForward();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // Adding calDriveTrain to scheduler if booting (ie not enable/disable in DS)
    System.out.println("//////////////////// TeleopInit /////////////////");

    Timer.delay(.5);

    if (bootCycle && enableCalibration){
      // CommandScheduler.getInstance().schedule(new CalibrateDriveTrain());
      CommandScheduler.getInstance().schedule(new CalibrateTilt());
    } /*else CommandScheduler.getInstance().schedule(new TiltMagToLow());*/

    // RobotContainer.m_drivetrain.maxDrivePower(1.);

    RobotContainer.m_shooterCam.setCamMode(1); // default to regular vision mode, not tracking mode
    RobotContainer.m_shooterCam.ledOff();

    RobotContainer.m_intakeCam.setCamMode(1); // default to regular vision mode, not tracking mode
    RobotContainer.m_intakeCam.ledOff();

    // CommandScheduler.getInstance().schedule(new DriveSpeed());
    // CommandScheduler.getInstance().run();

    bootCycle = false;

    System.out.println("//////////////////// Teleop /////////////////");
    CommandScheduler.getInstance().schedule(new LoadMagazine());
    // CommandScheduler.getInstance().schedule(new Drive());
    // RobotContainer.m_led.clearLED();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // RobotContainer.m_led.dispBallCountLED();
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
    // System.out.println("X: "+RobotContainer.getDriverX());
    // System.out.println("Y: "+RobotContainer.getDriverY());
    // System.out.println("Z: "+RobotContainer.getDriverZ());
  }
}
