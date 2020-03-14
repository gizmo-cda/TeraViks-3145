/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.commands.LoadMagazine;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE_TalonSRX_CAN_ID);
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  private boolean magFull = false;

  /**
   * Creates a new Intake.
   */
  public Intake() {
  }

  public void intakeBall(){
    if (!magFull){
      intakeMotor.set(ControlMode.PercentOutput, 1.);
      
      if (!CommandScheduler.getInstance().isScheduled(new LoadMagazine())){
        CommandScheduler.getInstance().schedule(new LoadMagazine());
      }
    } else stopIntake();
    // RobotContainer.m_led.intakeLED();
  }

  public void reverseIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -1.);
  }

  public void stopIntake(){
    intakeMotor.set(ControlMode.PercentOutput, 0.);
  }
  
  public void init(){
    intakeMotor.configFactoryDefault();
    
    intakeMotor.setInverted(false);
        
    intakeMotor.configPeakOutputForward(1., TIMEOUT);
    intakeMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    intakeMotor.configNominalOutputForward(0, TIMEOUT);
    intakeMotor.configNominalOutputReverse(0, TIMEOUT);

    System.out.println("  - Intake Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
