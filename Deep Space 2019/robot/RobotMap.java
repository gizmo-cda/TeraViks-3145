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
  public static final int BOOMERANG_ROTATE_TalonSRX_CAN_ID = 18;
  public static final int REAR_ROBOT_LIFT_TalonSRX_CAN_ID = 19;
  public static final int REAR_ROBOT_LIFT_DRIVE_TalonSRX_CAN_ID = 20;
  public static final int HATCH_GRABBER_LEFT_TalonSRX_CAN_ID = 21;
  public static final int HATCH_GRABBER_RIGHT_TalonSRX_CAN_ID = 22;

  // Talon controllers' timeout
  public static final int TalonSRX_TIMEOUT = 1000; //units in msec

  // Talon Encoder Phase Checking Constants
  public static final boolean CHECK_PHASE_DURING_DRIVETRAIN_CALIBRATION = false;

  public static final boolean FRONT_RIGHT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean FRONT_LEFT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean REAR_LEFT_DRIVE_TalonSRX_ENCODER_PHASE = true;
  public static final boolean REAR_RIGHT_DRIVE_TalonSRX_ENCODER_PHASE = true;

  public static final boolean FRONT_RIGHT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean FRONT_LEFT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean REAR_LEFT_STEER_TalonSRX_ENCODER_PHASE = false;
  public static final boolean REAR_RIGHT_STEER_TalonSRX_ENCODER_PHASE = false;

  // Drivetrain Gear Ratio Constants
  public static final double FINAL_DRIVE_WHEEL_GEAR_RATIO = 10.;        // Motor drives the wheel directly, ratio is based on pully sizes
  public static final double STEER_MOTOR_GEAR_REDUCTION_RATIO = 12.;    // Motor drives gear reduction unit
  public static final double STEER_WHEEL_GEAR_RATIO = 4.612;            // Output of gear reduction drives the steering pully
  public static final double FINAL_STEER_WHEEL_GEAR_RATIO = STEER_MOTOR_GEAR_REDUCTION_RATIO * STEER_WHEEL_GEAR_RATIO;
 
  // Drivetrain Encoder Constants
  public static final double DRIVE_WHEEL_PULSES_PER_100MS = 600.;       // Used for closed loop velocity
  public static final double STEER_MOTOR_PULSES_PER_REVOLUTION = 4096.; // Used for closed loop position
  public static final double STEER_PPR = 226831.;

  // Locations of the swerve drive index signal for each modle in encoder pulses to get to zero, straight forward
  public static final boolean ENABLE_DRIVETRAIN_CALIBRATION = false;
  public static final double FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR * .375; //Drive Motor needs to be inverted
  public static final double FRONT_LEFT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125;
  public static final double REAR_LEFT_STEER_INDEX_OFFSET_PULSES  = STEER_PPR  * .375;
  public static final double REAR_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125; //Drive Motor needs to be inverted
  public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL = 500.; //Small adjustment to cal for error in driving to final cal position & delay in index detection

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
  public static final double PITCH_THRESHOLD = 20.; //Roll in degrees
  public static final double ROLL_THRESHOLD = 20.; //Pitch in degrees
  
  // Vision System Constants
  public static final double CAMERA_MOUNTING_ANGLE = 30.;  //Units are in degrees and referenced to X axis, with CCW being positive
  public static final double CAMERA_MOUNTING_HEIGHT = 36.;  //Units are in inches
  public static final double DOUBLE_STRIPE_REFLECTIVE_TAPE_TARGET_HEIGHT = 25.;  //Units are in inches

  // Boomerang Lift Level Positions
  //public static final double SCOOP_LIFT_LEVEL = 38199;
  public static final double LOW_TARGET_LIFT_LEVEL = 38199.;
  public static final double MID_TARGET_LIFT_LEVEL = 822788.;
  public static final double HIGH_TARGET_LIFT_LEVEL = 1759932.;
  public static final double LEVEL2_PLATFORM_LIFT_LEVEL = 50000.;
  public static final double LEVEL3_PLATRORM_LIFT_LEVEL = 76000.;

  // Boomerang Rotate Positions
  public static final double BOOMERANG_DEPLOYED_POSITION = 1.;
  public static final double BOOMERANG_RETRACTED_POSITION = 1.;

  // Rear Lift Level Positions
  public static final int LEVEL2_PLATFORM_REAR_LIFT_LEVEL = 1;
  public static final int LEVEL3_PLATFORM_REAR_LIFT_LEVEL = 1;

  // Collision Detection for NavX
  public static final double COLLISION_THRESHOLD_Y = .5; // in Gs


  public static void init(){
  }
}
