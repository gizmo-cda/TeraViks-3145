/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Tilt extends SubsystemBase {
  private final WPI_TalonFX tiltMotor = new WPI_TalonFX(RobotMap.TILT_TalonFX_CAN_ID);
  private final DigitalInput homeSwitch = new DigitalInput(RobotMap.TILT_HOME_SWITCH);
  
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  private boolean hasBeenHomed;

  /**
   * Creates a new Tilt.
   */
  public Tilt() {

  }

  public void getTiltPosition() {
    tiltMotor.getSelectedSensorPosition(0);
  }

  public void tilt(double tiltPosition) {
    tiltMotor.set(ControlMode.Position, tiltPosition);
  }

  public void calTilt(){
    hasBeenHomed = false;
    for (double i = tiltMotor.getSelectedSensorPosition(0); homeSwitch.get() && !hasBeenHomed; i += 0.001) {
			tiltMotor.set(ControlMode.MotionMagic, i);
    }
    hasBeenHomed = true;	
    tiltMotor.setSelectedSensorPosition(0, 0, TIMEOUT);
		tiltMotor.set(ControlMode.MotionMagic, 10);
  }

  public void initTiltMotor() {
    tiltMotor.configFactoryDefault();

    tiltMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT);
    tiltMotor.selectProfileSlot(0, 0); // slot #, PID #

    tiltMotor.setInverted(false);
    tiltMotor.setNeutralMode(NeutralMode.Brake);

    tiltMotor.setSelectedSensorPosition(0);
    tiltMotor.configClearPositionOnQuadIdx(false, TIMEOUT);

    tiltMotor.configMotionAcceleration(40960, TIMEOUT); // 400 Optical Encoder accel and velocity targets
    tiltMotor.configMotionCruiseVelocity(20480, TIMEOUT);

    tiltMotor.configPeakOutputForward(1., TIMEOUT);
    tiltMotor.configPeakOutputReverse(-1., TIMEOUT);

    tiltMotor.configNominalOutputForward(0, TIMEOUT);
    tiltMotor.configNominalOutputReverse(0, TIMEOUT);

    tiltMotor.configAllowableClosedloopError(0, 5, TIMEOUT); // Error for 2048 CTRE Encoder

    tiltMotor.config_IntegralZone(0, 100, TIMEOUT); // I-zone limits

    tiltMotor.config_kP(0, .75, TIMEOUT);
    tiltMotor.config_kI(0, .001, TIMEOUT);
    tiltMotor.config_kD(0, 1., TIMEOUT);
    tiltMotor.config_kF(0, 0., TIMEOUT);

    System.out.println("  - Tilt Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
