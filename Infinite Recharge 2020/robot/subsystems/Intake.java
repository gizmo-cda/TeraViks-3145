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

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE_TalonSRX_CAN_ID);
  /**
   * Creates a new Intake.
   */
  public Intake() {
  }

  public void intakeBall(){
    intakeMotor.set(ControlMode.PercentOutput, 1.);
  }

  public void reverseIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -1.);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
