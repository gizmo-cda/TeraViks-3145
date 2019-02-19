/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
// This package operates the robot's rear lift mechanisim. 

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class RearLift extends Subsystem {
  // Create the rear lift and drive motor Objects
  private final WPI_TalonSRX rearLiftMotor = new WPI_TalonSRX(RobotMap.REAR_ROBOT_LIFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearLiftDriveMotor = new WPI_TalonSRX(RobotMap.REAR_ROBOT_LIFT_DRIVE_TalonSRX_CAN_ID);

  // Talon controllers' timeout
  public static final int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  public RearLift(){
  }

  public void init(){
    initRearLiftMotor();
    initRearLiftDriveMotor();
  }

  private void setDriveSpeed(){
    rearLiftDriveMotor.set(ControlMode.PercentOutput, 1.);
  }

  private void setDriveStop(){
    rearLiftDriveMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void setLiftPosition(double position){
    rearLiftMotor.set(ControlMode.Position, position);
  }

  private void setLiftSpeed(double speed){
    rearLiftMotor.set(ControlMode.PercentOutput, speed);
  }

  private int getLiftPosition(){
    return rearLiftMotor.getSelectedSensorPosition();
  }

  private void setLiftVelocity(double velocity){
    rearLiftMotor.set(ControlMode.Velocity, velocity);
  }

  public void liftToLevel(int level){
    int frontPosition = Robot.m_boomerang.getLiftPosition();
    int rearPosition = getLiftPosition();
    double pitch = Robot.m_gyro.getPitchDeg();
    double frontVel = 60000.;
    double rearVel = 120000.;

    Robot.m_boomerang.setLiftVelocity(frontVel);

    while (rearPosition < level){
      setLiftVelocity(rearVel);
      if (frontPosition <= 5000.){
        Robot.m_boomerang.setLiftLevel(0.);
      }
      if (pitch < -2){
        rearVel += 1300;
      }
      else if (pitch > 2){
        rearVel -= 1300;
      }
      frontPosition = Robot.m_boomerang.getLiftPosition();
      rearPosition = getLiftPosition();
      pitch = Robot.m_gyro.getPitchDeg();
      Timer.delay(.02);
    }
      setLiftPosition(rearPosition);
  }
  
  //Talon configuration for the lift motor
  private void initRearLiftMotor(){
    rearLiftMotor.configFactoryDefault();
    
    rearLiftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    rearLiftMotor.selectProfileSlot(0, 0); //slot #, PID #

    rearLiftMotor.setInverted(false);
    rearLiftMotor.setNeutralMode(NeutralMode.Brake);

    rearLiftMotor.setSensorPhase(false);
    rearLiftMotor.setSelectedSensorPosition(0);
    rearLiftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rearLiftMotor.configPeakOutputForward(1., TIMEOUT);
    rearLiftMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rearLiftMotor.configNominalOutputForward(0, TIMEOUT);
    rearLiftMotor.configNominalOutputReverse(0, TIMEOUT);
    
    rearLiftMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    rearLiftMotor.config_kP(0, .15, TIMEOUT);
    rearLiftMotor.config_kI(0, 0, TIMEOUT);
    rearLiftMotor.config_kD(0, 1, TIMEOUT);
    rearLiftMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Rear Lift Motor Initialized");
  }
  //Talon configuration for the lift drive motor
  private void initRearLiftDriveMotor(){
    rearLiftDriveMotor.configFactoryDefault();
    
    rearLiftDriveMotor.setInverted(false);
    rearLiftDriveMotor.setNeutralMode(NeutralMode.Brake);

    rearLiftDriveMotor.configPeakOutputForward(1., TIMEOUT);
    rearLiftDriveMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rearLiftDriveMotor.configNominalOutputForward(0, TIMEOUT);
    rearLiftDriveMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Rear Lift Drive Motor Initialized");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new Drive());
  }
}