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
*  front left Drive and Steer |-----------| front right Drive and Steer    
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

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveDrive;

public class Drivetrain extends Subsystem {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonSRX frontRightDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX frontLeftDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearLeftDriveMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearRightDriveMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_TalonSRX_CAN_ID);
  
  private final WPI_TalonSRX frontRightSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_STEER_TalonSRX_CAN_ID);
  private final WPI_TalonSRX frontLeftSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_STEER_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearLeftSteerMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_STEER_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearRightSteerMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_STEER_TalonSRX_CAN_ID);
  
  private SwerveModule frontRightWheel;
  private SwerveModule frontLeftWheel;
  private SwerveModule rearLeftWheel;
  private SwerveModule rearRightWheel;
  
  private SwerveDrive m_SwerveDrive;
  
  private boolean centric = false;
  
  private double yaw = 0.; //NavX Gyro Yaw angle
  private double roll = 0.; //NavX Gyro Roll angle
  private double pitch = 0.; //NavX Gyro Pitch angle
  private double maxRoll = RobotMap.PITCH_THRESHOLD;
  private double maxPitch = RobotMap.ROLL_THRESHOLD;
  private double power = 0;
  
  private boolean reverseEn = true;  //Enables reversing wheel drive motors
  
  public boolean snakeMode = false; //Crab = false, Snake = True
  
  private boolean ballTrackMode = false;
  private boolean hatchTrackMode = false;
  
  public Drivetrain(){
    //Create the Swerve Drive Modules for each wheel
    frontRightWheel = new SwerveModule("FrontRightWheel", frontRightDriveMotor, frontRightSteerMotor);
    frontLeftWheel = new SwerveModule("FrontLeftWheel", frontLeftDriveMotor, frontLeftSteerMotor);
    rearLeftWheel = new SwerveModule("RearLeftWheel", rearLeftDriveMotor, rearLeftSteerMotor);
    rearRightWheel = new SwerveModule("RearRightWheel", rearRightDriveMotor, rearRightSteerMotor);
    
    //Now Build the complete Swerve Drive Object with all four Wheel Modules
    // m_SwerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel);
    m_SwerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel);
  }
  
  public void init(){
    m_SwerveDrive.initMotors();
    System.out.println("**Initializing Drivetrain Motors");
  }
  
  public void reset(){
    turnOffCentric();
    setCrabMode();
    coast();
    System.out.println("**Drivetrain reset to CrabMode, Centric Off, and Coast");
    m_SwerveDrive.reset();
  }
  
  public void calSteering(boolean checkPhase){
    m_SwerveDrive.calSteerMotors(checkPhase);
  }
  
  public void turnOnCentric(){
    centric = true;
    System.out.println("**Centric set to: "+centric);
  }
  
  public void turnOffCentric(){
    centric = false;
    System.out.println("**Centric set to: "+centric);
  }
  
  public boolean getCentric(){
    return centric;
  }
  
  public void setSnakeMode(){
    snakeMode = true;
    System.out.println("**Drivetrain Set To Snake Mode");
  }
  
  public void setCrabMode(){
    snakeMode = false;
    System.out.println("**Drivetrain Set To Crab Mode");
  }
  
  public void setBallTrackMode(boolean mode){
    this.ballTrackMode = mode;
  }

  public void setHatchTrackMode(boolean mode){
    this.hatchTrackMode = mode;
  }
  
  public void move(double fwd, double str, double rcw){
    // System.out.println(ballTrackMode);
    // This is Yaw angle +/- 180 in degrees
    if (centric) yaw = Robot.m_gyro.getYawDeg();
    
    // This is Yaw angle +/- 180 in degrees
    roll = Robot.m_gyro.getRollDeg();
    pitch = Robot.m_gyro.getPitchDeg();
    
    // Detect too much roll angle and strafe into the roll or too much pitch and drive FWD/Reverse accordingly
    if (roll > maxRoll || roll < -maxRoll) antiRoll(roll);
    if (pitch > maxPitch || pitch < -maxPitch) antiFlip(pitch);
    
    if (ballTrackMode && Robot.m_vision.getTv()==1.){
      str = 0.;
      rcw = .1*Robot.m_vision.getTx();
    }

    if (hatchTrackMode && Robot.m_vision.getTv()==1.){
      // if (!(Robot.m_vision.getTx() <= 1 && Robot.m_vision.getTx() >= -1)) {
      //   str -= .3*Robot.m_vision.getTx();
      // }
      // rcw = .02*Robot.m_vision.getTx();
    }
    
    m_SwerveDrive.setMotors(fwd, str, rcw, centric, yaw, reverseEn, snakeMode);
  }
  
  private void antiRoll(double roll){
    setCrabMode();
    
    while (roll > 1. || roll < -1.){
      power = -roll/maxRoll;
      if (power > 1.) power = 1; else if (power < -1.) power = -1.;
      if (roll > 1.) m_SwerveDrive.setMotors(0, power, 0., centric, yaw, reverseEn, snakeMode);
      if (roll < -1.) m_SwerveDrive.setMotors(0, power, 0., centric, yaw, reverseEn, snakeMode);
      roll = Robot.m_gyro.getRollDeg();
    }
  }
  
  private void antiFlip(double pitch){
    
    while (pitch > 1. || pitch < -1.){
      power = -pitch/maxPitch;
      if (power > 1.) power = 1; else if (power < -1.) power = -1.;
      if (pitch > 1.) m_SwerveDrive.setMotors(power, 0., 0., centric, yaw, reverseEn, snakeMode);
      if (pitch < -1.) m_SwerveDrive.setMotors(power, 0., 0., centric, yaw, reverseEn, snakeMode);
      pitch = Robot.m_gyro.getPitchDeg();
    }
  }
  
  public void coast(){
    m_SwerveDrive.setCoast();
  }
  
  public void brake(){
    m_SwerveDrive.setBrake();
  }
  
  public void quickStop(){
    m_SwerveDrive.stopDriveMotors();
  }
  
  public void emergencyStop(){
    m_SwerveDrive.emergencyStopMotors();
  }
  
  public void driveDistance(double gyro, double distance) {
    m_SwerveDrive.setMotorsForDistance(0.5, centric, gyro, reverseEn, snakeMode, distance);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new Drive());
  }
}