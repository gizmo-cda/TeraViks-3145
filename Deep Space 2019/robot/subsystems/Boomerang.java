/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.RobotMap;

public class Boomerang extends Subsystem {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonSRX liftMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_LIFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE__TalonSRX_CAN_ID);
  private final WPI_TalonSRX shootMotor = new WPI_TalonSRX(RobotMap.SHOOT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rotateMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_ROTATE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX leftHatchMotor = new WPI_TalonSRX(RobotMap.HATCH_GRABBER_LEFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rightHatchMotor = new WPI_TalonSRX(RobotMap.HATCH_GRABBER_RIGHT_TalonSRX_CAN_ID);
  
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;
  
  public Boomerang(){
  }
  
  public void init(){
    System.out.println("**Initializing Boomerang Motors");
    initLiftMotor();
    initIntakeMotor();
    initShootMotor();
    initRotateMotor();
    initLeftHatchMotor();
    initRightHatchMotor();
  }
  public void liftLevelBall(int level){
    liftMotor.set(ControlMode.Position, level);
  }
  public void ballIntake(){
    intakeMotor.set(ControlMode.PercentOutput, 0.5);
  }
  public void ballEject() {
    shootMotor.set(ControlMode.PercentOutput, 0.5);
  }
  public void ballStop(){
    shootMotor.set(ControlMode.PercentOutput, 0.);
    intakeMotor.set(ControlMode.PercentOutput, 0.);
  }
  public void boomerangDeploy() {
    rotateMotor.set(ControlMode.Position, 180.); // pos will be a constant
  }
  public void deployHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, .5);
    rightHatchMotor.set(ControlMode.PercentOutput, .5);
  }
  public void retractHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, -.5);
    rightHatchMotor.set(ControlMode.PercentOutput, -.5);
  }
  public void holdHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, .05);
    rightHatchMotor.set(ControlMode.PercentOutput, .05);
  }
  
  // // levels 1 - 6
  // // intake
  // // shoot
  
  
  public void initLiftMotor(){
    liftMotor.configFactoryDefault();
    
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    liftMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    liftMotor.setInverted(false);
    liftMotor.setNeutralMode(NeutralMode.Brake);
    
    liftMotor.setSensorPhase(false);
    liftMotor.setSelectedSensorPosition(0);
    liftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    liftMotor.configPeakOutputForward(.5, TIMEOUT);
    liftMotor.configPeakOutputReverse(-.5, TIMEOUT);
    
    liftMotor.configNominalOutputForward(0, TIMEOUT);
    liftMotor.configNominalOutputReverse(0, TIMEOUT);
    
    liftMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    liftMotor.config_kP(0, .15, TIMEOUT);
    liftMotor.config_kI(0, 0, TIMEOUT);
    liftMotor.config_kD(0, 1, TIMEOUT);
    liftMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Lift Motor Initialized");
  }
  
  public void initIntakeMotor(){
    intakeMotor.configFactoryDefault();
    
    // intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    // intakeMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    intakeMotor.setInverted(false);
    intakeMotor.setNeutralMode(NeutralMode.Brake);
    
    // intakeMotor.setSensorPhase(false);
    // intakeMotor.setSelectedSensorPosition(0);
    // intakeMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    intakeMotor.configPeakOutputForward(.3, TIMEOUT);
    intakeMotor.configPeakOutputReverse(-.3, TIMEOUT);
    
    intakeMotor.configNominalOutputForward(0, TIMEOUT);
    intakeMotor.configNominalOutputReverse(0, TIMEOUT);
    
    //intakeMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    // intakeMotor.config_kP(0, .15, TIMEOUT);
    // intakeMotor.config_kI(0, 0, TIMEOUT);
    // intakeMotor.config_kD(0, 1, TIMEOUT);
    // intakeMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Intake Motor Initialized");
  }
  
  public void initShootMotor(){
    shootMotor.configFactoryDefault();
    
    // shootMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    // shootMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    shootMotor.setInverted(false);
    shootMotor.setNeutralMode(NeutralMode.Brake);
    
    // shootMotor.setSensorPhase(false);
    // shootMotor.setSelectedSensorPosition(0);
    // shootMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    shootMotor.configPeakOutputForward(.3, TIMEOUT);
    shootMotor.configPeakOutputReverse(-.3, TIMEOUT);
    
    shootMotor.configNominalOutputForward(0, TIMEOUT);
    shootMotor.configNominalOutputReverse(0, TIMEOUT);
    
    // shootMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    // shootMotor.config_kP(0, .15, TIMEOUT);
    // shootMotor.config_kI(0, 0, TIMEOUT);
    // shootMotor.config_kD(0, 1, TIMEOUT);
    // shootMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Shoot Motor Initialized");
  }
  
  public void initRotateMotor(){
    rotateMotor.configFactoryDefault();
    
    rotateMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    rotateMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    rotateMotor.setInverted(false);
    rotateMotor.setNeutralMode(NeutralMode.Brake);
    
    rotateMotor.setSensorPhase(false);
    rotateMotor.setSelectedSensorPosition(0);
    rotateMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rotateMotor.configPeakOutputForward(.3, TIMEOUT);
    rotateMotor.configPeakOutputReverse(-.3, TIMEOUT);
    
    rotateMotor.configNominalOutputForward(0, TIMEOUT);
    rotateMotor.configNominalOutputReverse(0, TIMEOUT);
    
    rotateMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    rotateMotor.config_kP(0, .15, TIMEOUT);
    rotateMotor.config_kI(0, 0, TIMEOUT);
    rotateMotor.config_kD(0, 1, TIMEOUT);
    rotateMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Rotate Motor Initialized");
  }
  
  public void initLeftHatchMotor(){
    leftHatchMotor.configFactoryDefault();
    
    // leftHatchMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    // leftHatchMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    leftHatchMotor.setInverted(false);
    leftHatchMotor.setNeutralMode(NeutralMode.Brake);
    
    // leftHatchMotor.setSensorPhase(false);
    // leftHatchMotor.setSelectedSensorPosition(0);
    // leftHatchMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    leftHatchMotor.configPeakOutputForward(.5, TIMEOUT);
    leftHatchMotor.configPeakOutputReverse(-.5, TIMEOUT);
    
    leftHatchMotor.configNominalOutputForward(0, TIMEOUT);
    leftHatchMotor.configNominalOutputReverse(0, TIMEOUT);
    
    // leftHatchMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    // leftHatchMotor.config_kP(0, .15, TIMEOUT);
    // leftHatchMotor.config_kI(0, 0, TIMEOUT);
    // leftHatchMotor.config_kD(0, 1, TIMEOUT);
    // leftHatchMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Left Hatch Motor Initialized");
  }
  
  public void initRightHatchMotor(){
    rightHatchMotor.configFactoryDefault();
    
    // rightHatchMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    // rightHatchMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    rightHatchMotor.setInverted(true);
    rightHatchMotor.setNeutralMode(NeutralMode.Brake);
    
    // rightHatchMotor.setSensorPhase(false);
    // rightHatchMotor.setSelectedSensorPosition(0);
    // rightHatchMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rightHatchMotor.configPeakOutputForward(.5, TIMEOUT);
    rightHatchMotor.configPeakOutputReverse(-.5, TIMEOUT);
    
    rightHatchMotor.configNominalOutputForward(0, TIMEOUT);
    rightHatchMotor.configNominalOutputReverse(0, TIMEOUT);
    
    // rightHatchMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    // rightHatchMotor.config_kP(0, .15, TIMEOUT);
    // rightHatchMotor.config_kI(0, 0, TIMEOUT);
    // rightHatchMotor.config_kD(0, 1, TIMEOUT);
    // rightHatchMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Right Hatch Motor Initialized");
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
