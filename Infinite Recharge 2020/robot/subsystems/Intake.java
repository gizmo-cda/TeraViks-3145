/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.commands.LoadMagazine;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX beaverTailMotor = new WPI_TalonSRX(RobotMap.BEAVER_TAIL_TalonSRX_CAN_ID);
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  private boolean magFull;

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
  }

  public void reverseIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -1.);
  }

  public void stopIntake(){
    intakeMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void lowerBeaverTail(){
    beaverTailMotor.set(ControlMode.Position, RobotMap.INTAKE_LOWER_POSITION);
  }

  public void raiseBeaverTail(){
    beaverTailMotor.set(ControlMode.Position, RobotMap.INTAKE_UPPER_POSITION);
  }

  
  public void initIntakeMotor(){
    intakeMotor.configFactoryDefault();
    
    intakeMotor.setInverted(false);
        
    intakeMotor.configPeakOutputForward(1., TIMEOUT);
    intakeMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    intakeMotor.configNominalOutputForward(0, TIMEOUT);
    intakeMotor.configNominalOutputReverse(0, TIMEOUT);

    System.out.println("  - Intake Motor Initialized");
  }

  public void initBeaverTailMotor(){
    beaverTailMotor.configFactoryDefault();
        
    beaverTailMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT);
    beaverTailMotor.selectProfileSlot(0, 0); //slot #, PID #

    beaverTailMotor.setInverted(false);
    beaverTailMotor.setNeutralMode(NeutralMode.Brake);

    beaverTailMotor.setSelectedSensorPosition(0);
    beaverTailMotor.configClearPositionOnQuadIdx(false, TIMEOUT);

    beaverTailMotor.configMotionAcceleration(40960, TIMEOUT);  //400 Optical Encoder accel and velocity targets
    beaverTailMotor.configMotionCruiseVelocity(20480, TIMEOUT);

    beaverTailMotor.configPeakOutputForward(1., TIMEOUT);
    beaverTailMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    beaverTailMotor.configNominalOutputForward(0, TIMEOUT);
    beaverTailMotor.configNominalOutputReverse(0, TIMEOUT);
    
    beaverTailMotor.configAllowableClosedloopError(0, 5, TIMEOUT);  //Error for 2048 CTRE Encoder

    beaverTailMotor.config_IntegralZone(0, 100, TIMEOUT); //I-zone limits
    
    beaverTailMotor.config_kP(0, .75, TIMEOUT);  
    beaverTailMotor.config_kI(0, .001, TIMEOUT);
    beaverTailMotor.config_kD(0, 1., TIMEOUT);
    beaverTailMotor.config_kF(0, 0., TIMEOUT);
    
    System.out.println("  - Beaver Tail Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
