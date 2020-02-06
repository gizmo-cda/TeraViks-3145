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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Lift extends SubsystemBase {
  private WPI_TalonSRX liftMotor = new WPI_TalonSRX(RobotMap.LIFT_TalonSRX_CAN_ID);
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  /**
   * Creates a new Lift.
   */
  public Lift() {

  }

  public void liftRetract(){
    liftMotor.set(ControlMode.PercentOutput, 1.);
  }

  public void initLiftMotor(){
    liftMotor.configFactoryDefault();

    liftMotor.setInverted(false);

    liftMotor.configPeakOutputForward(1., TIMEOUT);
    liftMotor.configPeakOutputReverse(-1., TIMEOUT);

    liftMotor.configNominalOutputForward(0, TIMEOUT);
    liftMotor.configNominalOutputReverse(0, TIMEOUT);

    System.out.println("  - Lift Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
