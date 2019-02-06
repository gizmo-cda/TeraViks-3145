/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/* Robot Drivetrain Assignment
*                        
*                              Front/Forward       
*                                   ^             
*                                   |             
*                                   |             
*  front left Drive and Steer |-----------| front right Drive & Steer    
*                             |           |       
*                             |           |  l    
*                             |           |  e    
*                             |           |  n    
*                             |           |  g    
*                             |           |  t    
*                             |           |  h    
*                             |           |       
*  rear left Drive and Steer  |-----------| rear right Drive and Steer 
*                                 width           
*
***********************
 * This Class Builds the Drivetrain
 * It instantiates the Talon Motor Controllers for the drivetrain.
 * It then creates a swerve module for each wheel passing the respective Motor Controllers.
 * It then creates a swere drive object and passes the 4 swerve modules to it.
 * It has simple methods to call for move, stop, brake, coast, toggle Cetnric.
 * The Drive Command depends on it, and Drive is the default command for this
 * subsystem.
 */

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.RobotMap;
import frc.robot.commands.Drive;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveDrive;

public class Drivetrain extends Subsystem {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonSRX frontRightDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX frontLeftDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearLeftDriveMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearRightDriveMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_TalonSRX_CAN_ID);

  private final WPI_TalonSRX frontRightSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX frontLeftSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearLeftSteerMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearRightSteerMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_STEER_TalonSRX_CAN_ID);
  
  private SwerveModule frontRightWheel;
  // private SwerveModule frontLeftWheel;
  // private SwerveModule rearLeftWheel;
  // private SwerveModule rearRightWheel;

  private SwerveDrive m_SwerveDrive;

  private boolean centric = false;

  private boolean reverseEn = true;

  public Drivetrain(){
    //Create the Swerve Drive Modules for each wheel
    frontRightWheel = new SwerveModule("FrontRightWheel", frontRightDriveMotor, frontRightSteerMotor);
    // frontLeftWheel = new SwerveModule("FrontLeftWheel", frontLeftDriveMotor, frontLeftSteerMotor);
    // rearLeftWheel = new SwerveModule("RearLeftWheel", rearLeftDriveMotor, rearLeftSteerMotor);
    // rearRightWheel = new SwerveModule("RearRightWheel", rearRightDriveMotor, rearRightSteerMotor);

    //Now Build the complete Swerve Drive Object with all four Wheel Modules
    // m_SwerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel);
    m_SwerveDrive = new SwerveDrive(frontRightWheel, frontRightWheel, frontRightWheel, frontRightWheel);
  }

  public void init(){
     m_SwerveDrive.initMotors();
  }

  public boolean calSteering(){
    boolean clear = m_SwerveDrive.calSteerMotors();
    return clear;
  }

  public void toggleCentric(){
    centric = !centric; 
    System.out.println("Centric is: "+centric);
  }

  public boolean getCentric(){
    return centric;
  }

  public void move(double fwd, double str, double rcw, double gyro){
    m_SwerveDrive.setMotors(fwd, str, rcw, centric, gyro, reverseEn);
  }

  public void coast(){
    m_SwerveDrive.setCoast();
  }

  public void brake(){
    m_SwerveDrive.setBrake();
  }

  public void stop(){
    frontRightWheel.stop();
    // frontLeftWheel.stop();
    // rearLeftWheel.stop();
    // rearRightWheel.stop();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }
}