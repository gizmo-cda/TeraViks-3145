/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * The RobotMap is where most constants are defined for easy reference
 */

package frc.robot;

public class RobotMap {
  // Constants for Robot Dimensions
  public static final double WHEELBASE_LENGTH = 23.38; // inches
  public static final double WHEELBASE_TRACK_WIDTH = 19.88; // inches
  
  // CAN Bus IDs for Drivetrain Talon SRX controllers
  public static final int FRONT_RIGHT_DRIVE_TalonSRX_CAN_ID = 1;
  public static final int FRONT_LEFT_DRIVE_TalonSRX_CAN_ID  = 2;
  public static final int REAR_LEFT_DRIVE_TalonSRX_CAN_ID   = 3;
  public static final int REAR_RIGHT_DRIVE_TalonSRX_CAN_ID  = 4;
  
  public static final int FRONT_RIGHT_STEER_TalonSRX_CAN_ID = 11;
  public static final int FRONT_LEFT_STEER_TalonSRX_CAN_ID  = 12;
  public static final int REAR_LEFT_STEER_TalonSRX_CAN_ID   = 13;
  public static final int REAR_RIGHT_STEER_TalonSRX_CAN_ID  = 14;

  // CAN Bus IDs for All other Subsystem Talon SRX controllers
  public static final int BOOMERANG_LIFT_TalonSRX_CAN_ID = 15;
  public static final int INTAKE__TalonSRX_CAN_ID = 16;
  public static final int SHOOT_TalonSRX_CAN_ID = 17;
  public static final int BOOMERANG_ROTATE_LEFT_TalonSRX_CAN_ID = 18;
  public static final int BOOMERANG_ROTATE_RIGHT_TalonSRX_CAN_ID = 19;
  public static final int REAR_ROBOT_LIFT_TalonSRX_CAN_ID = 20;
  public static final int REAR_ROBOT_DRIVE_TalonSRX_CAN_ID = 21;
  public static final int HATCH_GRABBER_LEFT_TalonSRX_CAN_ID = 22;
  public static final int HATCH_GRABBER_RIGHT_TalonSRX_CAN_ID = 23;

  // Talon controllers' timeout
  public static final int TalonSRX_TIMEOUT = 1000; //units in msec

  // Gear Ratio Constants.  All Gear Ratios expressed in Motor revs TO Wheel Revs (ie M:W).  10:1 = 10 revs motor for 1 rev of the wheel
  public static final double FINAL_DRIVE_WHEEL_GEAR_RATIO = 10.;        // Motor drives the wheel directly, ratio is based on pully sizes
  public static final double STEER_MOTOR_GEAR_REDUCTION_RATIO = 12.;    // Motor drives gear reduction unit
  public static final double STEER_WHEEL_GEAR_RATIO = 4.6;              // Output of gear reduction drives the steering pully
  public static final double FINAL_STEER_WHEEL_GEAR_RATIO = STEER_MOTOR_GEAR_REDUCTION_RATIO * STEER_WHEEL_GEAR_RATIO;
 
  // Encoder Constants
  public static final double DRIVE_WHEEL_PULSES_PER_100MS = 600.;       // Used for closed loop velocity
  public static final double STEER_MOTOR_PULSES_PER_REVOLUTION = 4096.; // Used for closed loop position
  public static final double STEER_PPR = STEER_MOTOR_PULSES_PER_REVOLUTION * FINAL_STEER_WHEEL_GEAR_RATIO;

  // Encoder Phase Checking Constants
  public static final boolean CHECK_PHASE_DURING_STEERING_CALIBRATION = true;
  
  public static final boolean FRONT_RIGHT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean FRONT_LEFT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean REAR_LEFT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean REAR_RIGHT_DRIVE_TalonSRX_ENCODER_PHASE = true;

  public static final boolean FRONT_RIGHT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean FRONT_LEFT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean REAR_LEFT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean REAR_RIGHT_STEER_TalonSRX_ENCODER_PHASE = false;

  // Locations of the swerve drive index signal for each modle in encoder pulses to get to zero, straight forward
  public static final boolean ENABLE_DRIVETRAIN_CALIBRATION = true;
  public static final double FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR * .375; //Drive Motor needs to be inverted
  public static final double FRONT_LEFT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125;
  public static final double REAR_LEFT_STEER_INDEX_OFFSET_PULSES  = STEER_PPR  * .375;
  public static final double REAR_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125; //Drive Motor needs to be inverted
  
  // Drive Motor Inversion, Flips the polarity of the motor in the Talon Controller
  public static final boolean FRONT_RIGHT_DRIVE_TalonSRX_Invert = true;
  public static final boolean FRONT_LEFT_DRIVE_TalonSRX_Invert = false;
  public static final boolean REAR_LEFT_DRIVE_TalonSRX_Invert = false;
  public static final boolean REAR_RIGHT_DRIVE_TalonSRX_Invert = true;

  // Deadband Joystick Constants
  public static final double X_AXIS_THREASHOLD = 0.1;
  public static final double Y_AXIS_THREASHOLD = 0.1;
  public static final double Z_AXIS_THREASHOLD = 0.1;
  
  // Pitch & Roll Constants
  public static final double PITCH_THRESHOLD = 20.; //Pitch in degrees
  
  // Vision System Constants
  public static final double CAMERA_MOUNTING_ANGLE = 35;  //Units are in degrees and referenced to X axis, with CCW being positive
  public static final double CAMERA_MOUNTING_HEIGHT = 42;  //Units are in inches
  public static final double DOUBLE_STRIPE_REFLECTIVE_TAPE_TARGET_HEIGHT = 29;  //Units are in inches

  public static void init(){
  }
}
