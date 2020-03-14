/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;;

public class Shooter extends SubsystemBase {
  private final TalonFX shootMotorTop = new TalonFX(RobotMap.SHOOT_TOP_TalonFX_CAN_ID);
  private final TalonFX shootMotorBottom = new TalonFX(RobotMap.SHOOT_BOTTOM_TalonFX_CAN_ID);
  private int TIMEOUT = RobotMap.TalonFX_TIMEOUT;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {

  }

  public void init(){
    initTopShootMotor();
    initBottomShootMotor();
  }

  // Sets the motor velocities for each shooter motor independently
  public void shootBall(boolean trackMode){
    if (trackMode){
      shootMotorTop.set(ControlMode.Velocity, RobotMap.TOP_SHOOT_WHEEL_PULSES_PER_100MS);
      shootMotorBottom.set(ControlMode.Velocity, RobotMap.BOTTOM_SHOOT_WHEEL_PULSES_PER_100MS);
    } else {
      // shootMotorTop.set(ControlMode.Velocity, RobotMap.TOP_SHOOT_WHEEL_WALL_PULSES_PER_100MS);
      // shootMotorBottom.set(ControlMode.Velocity, RobotMap.BOTTOM_SHOOT_WHEEL_WALL_PULSES_PER_100MS);
      shootMotorTop.set(ControlMode.Velocity, RobotMap.TOP_SHOOT_WHEEL_PULSES_PER_100MS);
      shootMotorBottom.set(ControlMode.Velocity, RobotMap.BOTTOM_SHOOT_WHEEL_PULSES_PER_100MS);
    }
  }

  // Stops the motors
  public void stopMotors() {
    shootMotorTop.set(ControlMode.Velocity, 0.);
    shootMotorBottom.set(ControlMode.Velocity, 0.);
  }

  private void initTopShootMotor() {
    shootMotorTop.configFactoryDefault();
    
    shootMotorTop.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT); 
    shootMotorTop.selectProfileSlot(0, 0); // slot #, PID #
    
    shootMotorTop.setInverted(false);
    shootMotorTop.setNeutralMode(NeutralMode.Coast);
    shootMotorTop.configClosedloopRamp(.25, TIMEOUT);
    
    shootMotorTop.setSelectedSensorPosition(0);
    shootMotorTop.configClearPositionOnQuadIdx(false, TIMEOUT);
  
    shootMotorTop.configPeakOutputForward(1., TIMEOUT);
    shootMotorTop.configPeakOutputReverse(-1., TIMEOUT);
    
    shootMotorTop.configNominalOutputForward(0, TIMEOUT);
    shootMotorTop.configNominalOutputReverse(0, TIMEOUT);
    
    shootMotorTop.configAllowableClosedloopError(0, 20, TIMEOUT); // Error for2048 CTRE Encoder
    
    shootMotorTop.config_IntegralZone(0, 100, TIMEOUT); // I-zone limits
    
    shootMotorTop.config_kP(0, .15, TIMEOUT);
    shootMotorTop.config_kI(0, 0.0001, TIMEOUT);
    shootMotorTop.config_kD(0, 2., TIMEOUT);
    shootMotorTop.config_kF(0, .05, TIMEOUT);
    

    System.out.println("  - Top Shooter Motor Initialized");
  }

  private void initBottomShootMotor(){
    shootMotorBottom.configFactoryDefault();
    
    shootMotorBottom.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT); 
    shootMotorBottom.selectProfileSlot(0, 0); // slot #, PID #
    
    shootMotorBottom.setInverted(true);
    shootMotorBottom.setNeutralMode(NeutralMode.Coast);
    shootMotorBottom.configClosedloopRamp(.25, TIMEOUT);
    
    shootMotorBottom.setSelectedSensorPosition(0);
    shootMotorBottom.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    shootMotorBottom.configPeakOutputForward(1., TIMEOUT);
    shootMotorBottom.configPeakOutputReverse(-1., TIMEOUT);
    
    shootMotorBottom.configNominalOutputForward(0, TIMEOUT);
    shootMotorBottom.configNominalOutputReverse(0, TIMEOUT);
    
    shootMotorBottom.configAllowableClosedloopError(0, 20, TIMEOUT); // Error for2048 CTRE Encoder
    
    shootMotorBottom.config_IntegralZone(0, 100, TIMEOUT); // I-zone limits
    
    shootMotorBottom.config_kP(0, .15, TIMEOUT);
    shootMotorBottom.config_kI(0, 0.0001, TIMEOUT);
    shootMotorBottom.config_kD(0, 2., TIMEOUT);
    shootMotorBottom.config_kF(0, .05, TIMEOUT);
    

    System.out.println("  - Bottom Shooter Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
