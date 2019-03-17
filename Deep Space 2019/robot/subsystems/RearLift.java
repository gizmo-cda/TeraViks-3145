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
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class RearLift extends Subsystem {
  // Create the rear lift and drive motor Objects
  private final WPI_TalonSRX rearLiftMotor = new WPI_TalonSRX(RobotMap.REAR_ROBOT_LIFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rearLiftDriveMotor = new WPI_TalonSRX(RobotMap.REAR_ROBOT_LIFT_DRIVE_TalonSRX_CAN_ID);

  // Talon controllers' timeout
  private static final int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  public RearLift(){
  }

  public void init(){
    initRearLiftMotor();
    initRearLiftDriveMotor();
  }

  public void reset(){
    setLiftSpeed(-1000);
    Timer.delay(.5);
    rearLiftMotor.setSelectedSensorPosition(0);
  }

  public void setDriveSpeed(double speed){
    rearLiftDriveMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setDriveStop(){
    rearLiftDriveMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void setLiftPosition(double position){
    rearLiftMotor.set(ControlMode.Position, position);
  }

  public void setLiftSpeed(double speed){
    rearLiftMotor.set(ControlMode.PercentOutput, speed);
  }

  private int getLiftPosition(){
    return rearLiftMotor.getSelectedSensorPosition();
  }

  public void liftToLevel(int level){
    int frontPosition = Robot.m_boomerang.getLiftPosition();
    double frontVel = -3000.;
    
    setLiftPosition(level);
    Robot.m_boomerang.setLiftVelocity(frontVel);

    while (getLiftPosition() < level){
      frontPosition = Robot.m_boomerang.getLiftPosition();
      if (frontPosition <= 100.){
        Robot.m_boomerang.setLiftLevel(RobotMap.NEGATIVE_SLACK_LIFT_LEVEL);
      }
      Timer.delay(.01);
    }
    setLiftPosition(level);
    Robot.m_boomerang.setLiftLevel(RobotMap.NEGATIVE_SLACK_LIFT_LEVEL);
  }
  
  //Talon configuration for the lift motor
  private void initRearLiftMotor(){
    rearLiftMotor.configFactoryDefault();
    
    rearLiftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    rearLiftMotor.selectProfileSlot(0, 0); //slot #, PID #

    rearLiftMotor.setInverted(false);
    rearLiftMotor.setNeutralMode(NeutralMode.Brake);

    rearLiftMotor.setSensorPhase(true);
    rearLiftMotor.setSelectedSensorPosition(0);
    rearLiftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rearLiftMotor.configPeakOutputForward(1., TIMEOUT);
    rearLiftMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rearLiftMotor.configNominalOutputForward(0, TIMEOUT);
    rearLiftMotor.configNominalOutputReverse(0, TIMEOUT);

    rearLiftMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    rearLiftMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

    rearLiftMotor.configAllowableClosedloopError(0, 8, TIMEOUT);  //400 Optical Encoder Error

    rearLiftMotor.config_kP(0, .5, TIMEOUT); //400 Optical Encoder Gain
    rearLiftMotor.config_kI(0, 0, TIMEOUT);  
    rearLiftMotor.config_kD(0, 1, TIMEOUT);
    rearLiftMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Rear Lift Motor Initialized");
  }
  //Talon configuration for the lift drive motor
  private void initRearLiftDriveMotor(){
    rearLiftDriveMotor.configFactoryDefault();
    
    rearLiftDriveMotor.setInverted(true);
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