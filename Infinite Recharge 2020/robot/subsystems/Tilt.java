/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Tilt extends SubsystemBase {
  private final WPI_TalonFX tiltMotor = new WPI_TalonFX(RobotMap.TILT_TalonFX_CAN_ID);
  
  private int FwdLimitSwitch;

  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  /**
   * Creates a new Tilt.
   */
  public Tilt() {

  }

  public double getTiltPosition() {
    return tiltMotor.getSelectedSensorPosition(0);
  }

  public void setTiltVert() {
    tiltMotor.set(ControlMode.MotionMagic, RobotMap.MAGAZINE_VERTICAL);
  }

  public void setTiltLow(){
    tiltMotor.set(ControlMode.MotionMagic, RobotMap.MAGAZINE_LOW);
  }

  public void setTiltAngle(double tiltPosition) {
    tiltMotor.set(ControlMode.MotionMagic, tiltPosition);
  }

  public void setTiltControlWheel(){
    tiltMotor.set(ControlMode.MotionMagic, RobotMap.MAGAZINE_CONTROL_WHEEL);
  }

  public void stopTilt(){
    tiltMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void calMagVert(){
    System.out.println("  - Calibrating Tilt");
        
    //Set current position to a known value and start the motor open-loop, but slow
    tiltMotor.set(ControlMode.PercentOutput, .3);

    FwdLimitSwitch = tiltMotor.isFwdLimitSwitchClosed();
    
    //
    delay(40);
    
    //While the motor is running check to see when the encoder has been reset
    while (FwdLimitSwitch == 0) {
      FwdLimitSwitch = tiltMotor.isFwdLimitSwitchClosed();
      delay(20);
    }

    tiltMotor.setSelectedSensorPosition(0);
  
    tiltMotor.set(ControlMode.PercentOutput, 0.);

    //Give the motor extra time to stop
    delay(60);
    
    // Set tilt to upper position and hold that position
    setTiltVert();
  }

  private void delay(int msec){
    try{
        Thread.sleep(msec);
    }
    catch (Exception e){
        System.out.println("Error in Waitloop");
    }
}

  public void init() {
    tiltMotor.configFactoryDefault();

    tiltMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT);
    tiltMotor.selectProfileSlot(0, 0); // slot #, PID #

    tiltMotor.setInverted(true);
    tiltMotor.setNeutralMode(NeutralMode.Brake);

    tiltMotor.setSelectedSensorPosition(0);
    tiltMotor.configClearPositionOnQuadIdx(false, TIMEOUT);

    tiltMotor.configMotionAcceleration(12000, TIMEOUT); // 400 Optical Encoder accel and velocity targets
    tiltMotor.configMotionCruiseVelocity(12000, TIMEOUT);

    tiltMotor.configPeakOutputForward(1., TIMEOUT);
    tiltMotor.configPeakOutputReverse(-1., TIMEOUT);

    tiltMotor.configNominalOutputForward(0, TIMEOUT);
    tiltMotor.configNominalOutputReverse(0, TIMEOUT);

    tiltMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    tiltMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
    
    tiltMotor.configAllowableClosedloopError(0, 10, TIMEOUT); // Error for 2048 CTRE Encoder

    tiltMotor.config_IntegralZone(0, 50, TIMEOUT); // I-zone limits

    tiltMotor.config_kP(0, .9, TIMEOUT);
    tiltMotor.config_kI(0, .001, TIMEOUT);
    tiltMotor.config_kD(0, .1, TIMEOUT);
    tiltMotor.config_kF(0, 0., TIMEOUT);

    System.out.println("  - Tilt Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
