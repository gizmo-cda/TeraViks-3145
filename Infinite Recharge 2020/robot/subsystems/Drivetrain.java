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

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveDrive;
import java.util.Queue;
import java.util.LinkedList;

public class Drivetrain extends SubsystemBase {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonFX frontRightDriveMotor = new WPI_TalonFX(RobotMap.FRONT_RIGHT_DRIVE_TalonFX_CAN_ID);
  private final WPI_TalonFX frontLeftDriveMotor = new WPI_TalonFX(RobotMap.FRONT_LEFT_DRIVE_TalonFX_CAN_ID);
  private final WPI_TalonFX rearLeftDriveMotor = new WPI_TalonFX(RobotMap.REAR_LEFT_DRIVE_TalonFX_CAN_ID);
  private final WPI_TalonFX rearRightDriveMotor = new WPI_TalonFX(RobotMap.REAR_RIGHT_DRIVE_TalonFX_CAN_ID);
  
  private final WPI_TalonFX frontRightSteerMotor = new WPI_TalonFX(RobotMap.FRONT_RIGHT_STEER_TalonFX_CAN_ID);
  private final WPI_TalonFX frontLeftSteerMotor = new WPI_TalonFX(RobotMap.FRONT_LEFT_STEER_TalonFX_CAN_ID);
  private final WPI_TalonFX rearLeftSteerMotor = new WPI_TalonFX(RobotMap.REAR_LEFT_STEER_TalonFX_CAN_ID);
  private final WPI_TalonFX rearRightSteerMotor = new WPI_TalonFX(RobotMap.REAR_RIGHT_STEER_TalonFX_CAN_ID);
  
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
  
  private boolean snakeMode = false; //Crab = false, Snake = True

  private boolean hiLo = false; //High or Low Speed Drivetrain Mode
  
  private boolean targetTrackMode = false;
  private boolean isDriveInverted = false;

  private Queue<Double> txQueue = new LinkedList<Double>();
  private Queue<Double> tyQueue = new LinkedList<Double>();
  private double queueSumX = 0.;
  private double queueSumY = 0.;
  private double txAverage = 0.;
  private double tyAverage = 0.;
  private double tx = 0.;
  private double ty = 0.;
  private double distance;
  private double tiltPosition;
  
  public Drivetrain(){
    //Create the Swerve Drive Modules for each wheel
    frontRightWheel = new SwerveModule("FrontRightWheel", frontRightDriveMotor, frontRightSteerMotor);
    frontLeftWheel = new SwerveModule("FrontLeftWheel", frontLeftDriveMotor, frontLeftSteerMotor);
    rearLeftWheel = new SwerveModule("RearLeftWheel", rearLeftDriveMotor, rearLeftSteerMotor);
    rearRightWheel = new SwerveModule("RearRightWheel", rearRightDriveMotor, rearRightSteerMotor);
    
    //Now Build the complete Swerve Drive Object with all four Wheel Modules
    m_SwerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel);
  }
  
  public void init(){
    System.out.println("**Initializing Drivetrain Motors");
    m_SwerveDrive.initMotors();
  }
  
  public void reset(){
    turnOffCentric();
    setCrabMode();
    setHighSpeedDriveMode();
    System.out.println("**Drivetrain reset to CrabMode, Centric Off, Coast, and Low Speed");
    //m_SwerveDrive.reset(); DO NOT USE UNLESS TESTING WITH KNOWLEDGE OF IMPACT
  }
  
  public void calSteering(){
    m_SwerveDrive.calSteerMotors();
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
  
  public void setHighSpeedDriveMode(){
    hiLo = true;
  }

  public void setLowSpeedDriveMode(){
    hiLo = false;
  }
  
  public void setTargetTrackMode(boolean mode){
    targetTrackMode = mode;
    if (!targetTrackMode) {
      txQueue.clear();
      tyQueue.clear();
      queueSumX = 0.;
      queueSumY = 0.;
    }
  }

  public boolean getTargetTrackMode(){
    return targetTrackMode;
  }

  public void move(double fwd, double str, double rcw){
    // This is Yaw angle +/- 180 in degrees
    yaw = RobotContainer.m_gyro.getYawDeg();
    roll = RobotContainer.m_gyro.getRollDeg();
    pitch = RobotContainer.m_gyro.getPitchDeg();

    if(isDriveInverted){
      roll = -roll;
      pitch = -pitch;
    }
    
    // Detect too much roll angle and strafe into the roll or too much pitch and drive Fwd/Rev accordingly
    if (roll > maxRoll || roll < -maxRoll) antiRoll(roll);
    if (pitch > maxPitch || pitch < -maxPitch) antiFlip(pitch);
    
    // Override Joystick Inputs for str and rcw when using Vision Tracking
    if (targetTrackMode){
      tx = RobotContainer.m_vision.getTx();
      ty = RobotContainer.m_vision.getTy();

      // take in 10 pitch readings and average them out
      if (txQueue.size() < 10){
        txQueue.add(tx);
        queueSumX += tx;
      } else { 
        queueSumX-=txQueue.remove();
        txQueue.add(tx);
        queueSumX += tx;
      }
  
      txAverage = queueSumX / txQueue.size();
      
      if (targetTrackMode) str = 0.;

      rcw = .05*txAverage;

      // take in 10 pitch readings and average them out
      if (tyQueue.size() < 10){
       tyQueue.add(ty);
       queueSumY += ty;
     } else { 
       queueSumY-=tyQueue.remove();
       tyQueue.add(ty);
       queueSumY += ty;
     }

      tyAverage = queueSumY / tyQueue.size();

      distance = RobotMap.DIFFERENTIAL_HEIGHT / Math.tan(RobotMap.CAMERA_MOUNTING_ANGLE + tyAverage);

      tiltPosition = Math.asin(RobotMap.CONSTANT_K * distance) / 2. * RobotMap.PULSES_PER_RADIAN;

      RobotContainer.m_tilt.setTiltAngle(tiltPosition);
    }
    
    if(!isDriveInverted){
    m_SwerveDrive.setMotors(fwd, str, rcw, centric, yaw, reverseEn, snakeMode, hiLo);
    } else m_SwerveDrive.setMotors(-fwd, -str, rcw, centric, yaw, reverseEn, snakeMode, hiLo);
  }
  
  private void antiRoll(double roll){
    setCrabMode();
    
    while (roll > 1. || roll < -1.){
      power = -roll/maxRoll;
      if (power > 1.) power = 1; else if (power < -1.) power = -1.;
      if (roll > 1.) m_SwerveDrive.setMotors(0, power, 0., centric, yaw, reverseEn, snakeMode, hiLo);
      if (roll < -1.) m_SwerveDrive.setMotors(0, power, 0., centric, yaw, reverseEn, snakeMode, hiLo);
      roll = RobotContainer.m_gyro.getRollDeg();
    }
  }
  
  private void antiFlip(double pitch){
    
    while (pitch > 1. || pitch < -1.){
      power = -pitch/maxPitch;
      if (power > 1.) power = 1; else if (power < -1.) power = -1.;
      if (pitch > 1.) m_SwerveDrive.setMotors(power, 0., 0., centric, yaw, reverseEn, snakeMode, hiLo);
      if (pitch < -1.) m_SwerveDrive.setMotors(power, 0., 0., centric, yaw, reverseEn, snakeMode, hiLo);
      pitch = RobotContainer.m_gyro.getPitchDeg();
    }
  }

  public void quickStop(){
    m_SwerveDrive.stopDriveMotors();
  }
  
  public void emergencyStop(){
    m_SwerveDrive.emergencyStopMotors();
  }
  
  public void driveDistance(double gyro, boolean hiLo, double distance) {
    m_SwerveDrive.setMotorsForDistance(0.5, centric, gyro, reverseEn, snakeMode, hiLo, distance);
  }

  // Sets a boolean to invert the control of the drive motors
  public void invertDrive(){
    if(isDriveInverted) {
      isDriveInverted = false;
    } else isDriveInverted = true;
  }
  
  /*@Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new Drive());
  }*/
}
