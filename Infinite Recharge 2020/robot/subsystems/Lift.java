/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Lift extends SubsystemBase {
  private WPI_TalonFX liftMotor = new WPI_TalonFX(RobotMap.LIFT_TalonFX_CAN_ID);
  private Servo armServo = new Servo(RobotMap.ARM_SERVO_PWM_PORT);
  private int TIMEOUT = RobotMap.TalonFX_TIMEOUT;

  /**
   * Creates a new Lift.
   */
  public Lift() {
  }

  public void releaseArm(){
    armServo.set(.25);
  }

  public void retractWinch(){
    liftMotor.set(ControlMode.Position, RobotMap.LIFT_TOP_POSITION);
  }

  public void reverseWinch(){
    liftMotor.set(ControlMode.PercentOutput, -1.);
  }

  public void stopWinch(){
    liftMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void init(){
    liftMotor.configFactoryDefault();
    
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT); 
    liftMotor.selectProfileSlot(0, 0); // slot #, PID #
    
    liftMotor.setInverted(false);
    
    liftMotor.setSelectedSensorPosition(0);
    liftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    liftMotor.configMotionAcceleration(40960, TIMEOUT); // 400 Optical Encoder accel and velocity targets
    liftMotor.configMotionCruiseVelocity(20480, TIMEOUT);
    
    liftMotor.configPeakOutputForward(1., TIMEOUT);
    liftMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    liftMotor.configNominalOutputForward(0, TIMEOUT);
    liftMotor.configNominalOutputReverse(0, TIMEOUT);
    
    liftMotor.configAllowableClosedloopError(0, 5, TIMEOUT); // Error for2048 CTRE Encoder
    
    liftMotor.config_IntegralZone(0, 100, TIMEOUT); // I-zone limits
    
    liftMotor.config_kP(0, .05, TIMEOUT);
    liftMotor.config_kI(0, 0.0001, TIMEOUT);
    liftMotor.config_kD(0, 2., TIMEOUT);
    liftMotor.config_kF(0, .05, TIMEOUT);
    

    System.out.println("  - Lift Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
