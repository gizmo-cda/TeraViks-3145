/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveDrive;

public class Boomerang extends Subsystem {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonSRX liftMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_LIFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE__TalonSRX_CAN_ID);
  private final WPI_TalonSRX shootMotor = new WPI_TalonSRX(RobotMap.SHOOT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rotateMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_ROTATE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX leftHatchMotor = new WPI_TalonSRX(RobotMap.HATCH_GRABBER_LEFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rightHatchMotor = new WPI_TalonSRX(RobotMap.HATCH_GRABBER_RIGHT_TalonSRX_CAN_ID);

  public Boomerang(){
  }

  public void liftLevelBall1(){
    liftMotor.setSelectedSensorPosition(foo);
  }
  public void ballIntake(){
    intakeMotor.set(mode, demand0, demand1Type, demand1);
  }
  public void ballEject() {
    shootMotor.set(mode, demand0, demand1Type, demand1);
  }
  public void boomerangDeploy() {
    rotateMotor.setSelectedSensorPosition(180); // pos will be a constant
  }
  
  // // levels 1 - 6
  // // intake
  // // shoot


  public void initSteer(){
}
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
