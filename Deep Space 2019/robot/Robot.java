/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* The VM is configured to automatically run this class, and to call the
* functions corresponding to each mode, as described in the TimedRobot
* documentation. If you change the name of this class or the package after
* creating this project, you must also update the build.gradle file in the
* project.
*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.*;

import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  public static Drivetrain m_drivetrain;
  public static Gyro m_gyro;
  public static Vision m_vision;
  public static RearLift m_rearLift;
  public static Boomerang m_boomerang;
  public static OI m_oi;
  
  public static boolean bootCycle;
  public static boolean enableBoomDeploy = false;
  
  Command m_autonomousCommand;
  Command m_teleopCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  /**
  * This function is run when the robot is first started up and should be
  * used for any initialization code.
  */
  @Override
  public void robotInit() {
    // This method is run automatically upon deploying the code to the RoboRio
    // Instantiate objects for subsystems, operator input, commands, etc
    // NOTE: ORDER DEPENDENT
    m_drivetrain = new Drivetrain();
    m_gyro = new Gyro();
    m_vision = new Vision();
    m_boomerang = new Boomerang();
    m_rearLift = new RearLift();
    m_oi = new OI(); //Always instantiate OI last
    
    m_gyro.reset();
    m_drivetrain.init();
    //m_boomerang.init();
    m_rearLift.init();

    bootCycle = true;
    
    // chooser.addOption("My Auto", new MyAutoCommand());
    // m_chooser.setDefaultOption("Default Swerve", new Drive());
  }
  
  /**
  * This function is called every robot packet, no matter the mode. Use
  * this for items like diagnostics that you want ran during disabled,
  * autonomous, teleoperated and test.
  *
  * <p>This runs after the mode specific periodic functions, but before
  * LiveWindow and SmartDashboard integrated updating.
  */
  @Override
  public void robotPeriodic() {
    // System.out.println("Lift Position = "+Robot.m_boomerang.getLiftPosition());
    // System.out.println("Gyro Position = "+Robot.m_gyro.getPitchDeg());
    SmartDashboard.putData(Scheduler.getInstance());
    SmartDashboard.putBoolean("Centric Set", m_drivetrain.getCentric());
    SmartDashboard.putBoolean("Snake Mode", m_drivetrain.snakeMode);
    SmartDashboard.putBoolean("Ball Target Mode", BallTargetMode.ballTarget);
    SmartDashboard.putBoolean("Hatch Target Mode", HatchTargetMode.hatchTarget);
    SmartDashboard.putNumber("Gyro Yaw", m_gyro.getYawDeg());
    SmartDashboard.putNumber("Gyro Pitch", m_gyro.getPitchDeg());
    SmartDashboard.putNumber("Gyro Roll", m_gyro.getRollDeg());
    SmartDashboard.putData("Lvl 3 boomerang", new Level3Boomerang());
    SmartDashboard.putData("Collision Sensor", new CollisionSensor());
    SmartDashboard.putData("Rear Lift Drive", new RearLiftDrive());
    SmartDashboard.putData("Rear Lift Retract", new RearLiftRetract());
    SmartDashboard.putData("Calibrate Drivetrain", new CalibrateDriveTrain());
  }
  
  /**
  * This function is called once each time the robot enters Disabled mode.
  * You can use it to reset any subsystem information you want to clear when
  * the robot is disabled.
  */
  
  @Override
  public void disabledInit() {
    System.out.println("//////////////////// DISABLED Init /////////////////");    
    //Reset Drivetrain Modes and Swerve Math Variables to clear rotation tracking and reverse
    // Robot.m_drivetrain.reset(); //DON'T USE THIS.  ONLY FOR TESTING BEFORE CAL WORKED
  }
  
  @Override
  public void disabledPeriodic() {
  }
  
  /**
  * This autonomous (along with the chooser code above) shows how to select
  * between different autonomous modes using the dashboard. The sendable
  * chooser code works with the Java SmartDashboard. If you prefer the
  * LabVIEW Dashboard, remove all of the chooser code and uncomment the
  * getString code to get the auto name from the text box below the Gyro
  *
  * <p>You can add additional auto modes by adding additional commands to the
  * chooser code above (like the commented example) or additional comparisons
  * to the switch structure below with additional strings & commands.
  */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_chooser.getSelected();
    
    /*
    * String autoSelected = SmartDashboard.getString("Auto Selector",
    * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
    * = new MyAutoCommand(); break; case "Default Auto": default:
    * autonomousCommand = new ExampleCommand(); break; }
    */
    
    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
      //   m_autonomousCommand.start();
      // }
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
      
      if (bootCycle && RobotMap.ENABLE_DRIVETRAIN_CALIBRATION){
        Scheduler.getInstance().add(new CalibrateDriveTrain());
        Scheduler.getInstance().run();
      }
      if (bootCycle)
        m_rearLift.setLiftSpeed(-1000);
        Timer.delay(.5);
        m_rearLift.rearLiftMotor.setSelectedSensorPosition(0);
      
      bootCycle = false;

      // Scheduler.getInstance().add(new HatchGrabHold());
      // Scheduler.getInstance().run();
      // Scheduler.getInstance().add(new BoomerangLift(RobotMap.LOW_TARGET_LIFT_LEVEL));
      // Scheduler.getInstance().run();
      // Scheduler.getInstance().add(new RearLiftHold());
      // Scheduler.getInstance().run();
  
      System.out.println("//////////////////// Teleop /////////////////");
      Scheduler.getInstance().add(new Drive());
    }
    
    /**
    * This function is called periodically during operator control.
    */
    @Override
    public void teleopPeriodic() {
      //Add Drive to the command stack, it is always running from here on until DS Disable
      Scheduler.getInstance().run();
      if (Robot.m_oi.getOperatorDpad() == 0){
        new RearLiftRetract();
        }
      }
    
    /**
    * This function is called periodically during test mode.
    */
    @Override
    public void testPeriodic() {
    }
  }
  
