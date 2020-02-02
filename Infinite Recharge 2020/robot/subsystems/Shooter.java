/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  public Shooter() {

  }

  public void init() {
    /*
     * magazineMotor.configFactoryDefault();
     * 
     * magazineMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,
     * 0, TIMEOUT); magazineMotor.selectProfileSlot(0, 0); // slot #, PID #
     * 
     * magazineMotor.setInverted(false);
     * 
     * magazineMotor.setSelectedSensorPosition(0);
     * magazineMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
     * 
     * magazineMotor.configMotionAcceleration(40960, TIMEOUT); // 400 Optical
     * Encoder accel and velocity targets
     * magazineMotor.configMotionCruiseVelocity(20480, TIMEOUT);
     * 
     * magazineMotor.configPeakOutputForward(1., TIMEOUT);
     * magazineMotor.configPeakOutputReverse(-1., TIMEOUT);
     * 
     * magazineMotor.configNominalOutputForward(0, TIMEOUT);
     * magazineMotor.configNominalOutputReverse(0, TIMEOUT);
     * 
     * magazineMotor.configAllowableClosedloopError(0, 5, TIMEOUT); // Error for
     * 2048 CTRE Encoder
     * 
     * magazineMotor.config_IntegralZone(0, 100, TIMEOUT); // I-zone limits
     * 
     * magazineMotor.config_kP(0, .75, TIMEOUT); magazineMotor.config_kI(0, .001,
     * TIMEOUT); magazineMotor.config_kD(0, 1., TIMEOUT); magazineMotor.config_kF(0,
     * 0., TIMEOUT);
     */

    System.out.println("  - Shooter Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
