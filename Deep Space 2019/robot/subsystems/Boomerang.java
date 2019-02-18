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

  public void setLiftLevel(double position){
    liftMotor.set(ControlMode.Position, position);
  }

  public void setLiftLevelMotionMagic(double position){
    liftMotor.set(ControlMode.MotionMagic, position);
  }

  public void setLiftVelocity(double velocity){
    liftMotor.set(ControlMode.Velocity, velocity);
  }

  //Used for testing
  public void setLiftSpeed(double speed){
    liftMotor.set(ControlMode.PercentOutput, speed);
  }

  //Used for testing
  public int getLiftPosition(){
    return liftMotor.getSelectedSensorPosition();
  }

  public void startBallIntake(){
    intakeMotor.set(ControlMode.PercentOutput, 0.5);
  }

  public void startBallEject() {
    shootMotor.set(ControlMode.PercentOutput, 0.75);
  }

  public void stopBallMotors(){
    shootMotor.set(ControlMode.PercentOutput, 0.);
    intakeMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void deployBoomerang() {
    rotateMotor.set(ControlMode.MotionMagic, RobotMap.BOOMERANG_DEPLOYED_POSITION);
  }

  public void retractBoomerang() {
    rotateMotor.set(ControlMode.MotionMagic, RobotMap.BOOMERANG_RETRACTED_POSITION);
  }

  public void extendHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, 1.);
    rightHatchMotor.set(ControlMode.PercentOutput, 1.);
  }

  public void retractHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, -1.);
    rightHatchMotor.set(ControlMode.PercentOutput, -1.);
  }

  public void holdExtendedHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, .05);
    rightHatchMotor.set(ControlMode.PercentOutput, .05);
  }

  public void holdRetractedHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, -.3);
    rightHatchMotor.set(ControlMode.PercentOutput, -.3);
  }

  public void stopHatchMotors() {
    leftHatchMotor.set(ControlMode.PercentOutput, 0.);
    rightHatchMotor.set(ControlMode.PercentOutput, 0.);
  }
  
  private void initLiftMotor(){
    liftMotor.configFactoryDefault();
    
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    liftMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    liftMotor.setInverted(false);
    liftMotor.setNeutralMode(NeutralMode.Brake);
    
    liftMotor.setSensorPhase(true);
    liftMotor.setSelectedSensorPosition(0);
    liftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);

    liftMotor.configMotionAcceleration(65000, TIMEOUT);
    liftMotor.configMotionCruiseVelocity(65000, TIMEOUT);
    
    liftMotor.configPeakOutputForward(1., TIMEOUT);
    liftMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    liftMotor.configNominalOutputForward(0, TIMEOUT);
    liftMotor.configNominalOutputReverse(0, TIMEOUT);
    
    liftMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    liftMotor.config_kP(0, .15, TIMEOUT);
    liftMotor.config_kI(0, 0, TIMEOUT);
    liftMotor.config_kD(0, 1, TIMEOUT);
    liftMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Lift Motor Initialized");
  }
  
  private void initIntakeMotor(){
    intakeMotor.configFactoryDefault();
    
    intakeMotor.setInverted(true);
    intakeMotor.setNeutralMode(NeutralMode.Brake);
    
    intakeMotor.configPeakOutputForward(1., TIMEOUT);
    intakeMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    intakeMotor.configNominalOutputForward(0, TIMEOUT);
    intakeMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Intake Motor Initialized");
  }
  
  private void initShootMotor(){
    shootMotor.configFactoryDefault();
 
    shootMotor.setInverted(false);
    shootMotor.setNeutralMode(NeutralMode.Brake);
    
    shootMotor.configPeakOutputForward(1., TIMEOUT);
    shootMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    shootMotor.configNominalOutputForward(0, TIMEOUT);
    shootMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Shoot Motor Initialized");
  }
  
  private void initRotateMotor(){
    rotateMotor.configFactoryDefault();
    
    rotateMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    rotateMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    rotateMotor.setInverted(false);
    rotateMotor.setNeutralMode(NeutralMode.Brake);
    
    rotateMotor.setSensorPhase(false);
    rotateMotor.setSelectedSensorPosition(0);
    rotateMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rotateMotor.configMotionAcceleration(30000, TIMEOUT);
    rotateMotor.configMotionCruiseVelocity(30000, TIMEOUT);

    rotateMotor.configPeakOutputForward(1., TIMEOUT);
    rotateMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rotateMotor.configNominalOutputForward(0, TIMEOUT);
    rotateMotor.configNominalOutputReverse(0, TIMEOUT);
    
    rotateMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    
    rotateMotor.config_kP(0, .15, TIMEOUT);
    rotateMotor.config_kI(0, 0, TIMEOUT);
    rotateMotor.config_kD(0, 1, TIMEOUT);
    rotateMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Rotate Motor Initialized");
  }
  
  private void initLeftHatchMotor(){
    leftHatchMotor.configFactoryDefault();
    
    leftHatchMotor.setInverted(false);
    leftHatchMotor.setNeutralMode(NeutralMode.Brake);
    
    leftHatchMotor.configPeakOutputForward(1., TIMEOUT);
    leftHatchMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    leftHatchMotor.configNominalOutputForward(0, TIMEOUT);
    leftHatchMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Left Hatch Motor Initialized");
  }
  
  private void initRightHatchMotor(){
    rightHatchMotor.configFactoryDefault();
  
    rightHatchMotor.setInverted(false);
    rightHatchMotor.setNeutralMode(NeutralMode.Brake);
    
    rightHatchMotor.configPeakOutputForward(1., TIMEOUT);
    rightHatchMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rightHatchMotor.configNominalOutputForward(0, TIMEOUT);
    rightHatchMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Right Hatch Motor Initialized");
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
