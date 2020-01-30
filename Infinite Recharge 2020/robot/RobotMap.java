/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class RobotMap {
    // Constants for Robot Dimensions
    public static final double WHEELBASE_LENGTH = 23.38; // inches
    public static final double WHEELBASE_TRACK_WIDTH = 19.88; // inches
  
    // Speed Scale Constants for the Drivetrain
    public static final double HIGH_SPEED_SCALE = 1.0;
    public static final double LOW_SPEED_SCALE = .3;
    public static final double ROTATE_SCALE = .67;
    
    // CAN Bus IDs for Drivetrain Talon SRX controllers
    public static final int FRONT_RIGHT_DRIVE_TalonFX_CAN_ID = 1;
    public static final int FRONT_LEFT_DRIVE_TalonFX_CAN_ID  = 2;
    public static final int REAR_LEFT_DRIVE_TalonFX_CAN_ID   = 3;
    public static final int REAR_RIGHT_DRIVE_TalonFX_CAN_ID  = 4;
    
    public static final int FRONT_RIGHT_STEER_TalonFX_CAN_ID = 11;
    public static final int FRONT_LEFT_STEER_TalonFX_CAN_ID  = 12;
    public static final int REAR_LEFT_STEER_TalonFX_CAN_ID   = 13;
    public static final int REAR_RIGHT_STEER_TalonFX_CAN_ID  = 14;
  
    // CAN Bus IDs for All other Subsystem Talon SRX controllers
    public static final int MAGAZINE_TalonFX_CAN_ID = 15;
    public static final int INTAKE_TalonSRX_CAN_ID = 16;
    public static final int SHOOT_TalonSRX_CAN_ID = 17;
    public static final int BOOMERANG_ROTATE_TalonSRX_CAN_ID = 18;
    public static final int REAR_ROBOT_LIFT_TalonSRX_CAN_ID = 19;
    public static final int REAR_ROBOT_LIFT_DRIVE_TalonSRX_CAN_ID = 20;
    public static final int HATCH_GRABBER_TalonSRX_CAN_ID = 21;
    public static final int BOOMERANG_LIFT_BOOSTER_TalonSRX_CAN_ID = 22;
  
    // Talon controllers' timeout
    public static final int TalonFX_TIMEOUT = 1000; //units in msec
    public static final int TalonSRX_TIMEOUT = 1000; //units in msec

    // Drivetrain Gear Ratio Constants
    public static final double FINAL_DRIVE_WHEEL_GEAR_RATIO = 10.;        // Motor drives the wheel directly, ratio is based on pully sizes
    public static final double STEER_MOTOR_GEAR_REDUCTION_RATIO = 12.;    // Motor drives gear reduction unit
    public static final double STEER_WHEEL_GEAR_RATIO = 4.612;            // Output of gear reduction drives the steering pully
    public static final double FINAL_STEER_WHEEL_GEAR_RATIO = STEER_MOTOR_GEAR_REDUCTION_RATIO * STEER_WHEEL_GEAR_RATIO;
   
    // Drivetrain Encoder Constants
    public static final double DRIVE_WHEEL_PULSES_PER_100MS = 21480.;       // Used for closed loop velocity
    public static final double DRIVE_WHEEL_PULSES_PER_INCH = 49.13;       // PPI with 400 Optical Encoder
    public static final double STEER_MOTOR_PULSES_PER_REVOLUTION = 2048;  // 2048 CTRE Mag Encoder
    public static final double STEER_PPR = 15275.;                        // Pulses per wheel revolution with 2048 CTRE Mag Encoder + 7.46:1
  
    // Locations of the swerve drive index signal for each modle in encoder pulses to get to zero, straight forward
    public static final double FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR * .375; //Drive Motor needs to be inverted
    public static final double FRONT_LEFT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125;
    public static final double REAR_LEFT_STEER_INDEX_OFFSET_PULSES  = STEER_PPR  * .375;
    public static final double REAR_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .125; //Drive Motor needs to be inverted
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FR = -281.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FL = -238.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RL = -287.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RR = -323.; //Small adjustment to cal for error
  
    // Drive Motor Inversion, Flips the polarity of the motor in the Talon Controller
    public static final boolean FRONT_RIGHT_DRIVE_TalonFX_Invert = true;
    public static final boolean FRONT_LEFT_DRIVE_TalonFX_Invert = false;
    public static final boolean REAR_LEFT_DRIVE_TalonFX_Invert = false;
    public static final boolean REAR_RIGHT_DRIVE_TalonFX_Invert = true;
  
    // Deadband Joystick Constants
    public static final double X_AXIS_THREASHOLD = 0.05;
    public static final double Y_AXIS_THREASHOLD = 0.05;
    public static final double Z_AXIS_THREASHOLD = 0.05;
    
    // Pitch & Roll Constants
    public static final double PITCH_THRESHOLD = 15.; //Roll in degrees
    public static final double ROLL_THRESHOLD = 15.; //Pitch in degrees
    
    // Vision System Constants - not used
    public static final double CAMERA_MOUNTING_ANGLE = 30.;  //Units are in degrees and referenced to X axis, with CCW being positive
    public static final double CAMERA_MOUNTING_HEIGHT = 62.;  //Units are in inches
    public static final double DOUBLE_STRIPE_REFLECTIVE_TAPE_TARGET_HEIGHT = 28.5;  //Units are in inches
  
    // Boomerang Lift Level Positions - 400 Optical Encoder with 36:1 + 3.25:1
    public static final double NEGATIVE_SLACK_LIFT_LEVEL = -3000.; //-4300 is calculated value. Slack in Boomerang lift that must be taken up to lift the robot onto the platform
    public static final double LOW_TARGET_LIFT_LEVEL = 10000.; 
    public static final double CARGO_SHIP_TARGET_LIFT_LEVEL = 50000.; 
    public static final double LEVEL2_PLATFORM_LIFT_LEVEL = 28000.; 
    public static final double LEVEL3_PLATRORM_LIFT_LEVEL = 65000.; 
    public static final double MID_TARGET_LIFT_LEVEL = 92000.;
    public static final double HIGH_TARGET_LIFT_LEVEL = 191000.; 
  
    // Boomerang Rotate Positions - 400 Optical Encoder with 100:1 + 3.25:1
    public static final double BOOMERANG_DEPLOYED_POSITION = 61192.; //400 Opctical Encoder with 100:1 GB + 3.25:1
  
    // Rear Lift Level Positions - 400 Optical Encoder with 64:1
    public static final int LEVEL2_PLATFORM_REAR_LIFT_LEVEL = 135000;
    public static final int LEVEL3_PLATFORM_REAR_LIFT_LEVEL = 325000;
  
  }  
