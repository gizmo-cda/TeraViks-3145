/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class RobotMap {
    // Constants for Robot Dimensions
    public static final double WHEELBASE_LENGTH = 19.625; // inches
    public static final double WHEELBASE_TRACK_WIDTH = 19.625; // inches
  
    // Speed Scale Constants for the Drivetrain
    public static final double HIGH_SPEED_SCALE = 1.;
    public static final double MED_SPEED_SCALE = .3;
    public static final double LOW_SPEED_SCALE = .1;
    public static final double ROTATE_SCALE = .4;

    // Max speed and accelleration in METERS
    public static final double ROBOT_MAX_SPEED = 0.; // m/s
    public static final double ROBOT_MAX_ACCEL = 0.; // m/s^2
    
    // CAN Bus IDs for Drivetrain Talon SRX controllers
    public static final int FRONT_RIGHT_DRIVE_TalonFX_CAN_ID = 1;
    public static final int FRONT_LEFT_DRIVE_TalonFX_CAN_ID  = 2;
    public static final int REAR_LEFT_DRIVE_TalonFX_CAN_ID   = 3;
    public static final int REAR_RIGHT_DRIVE_TalonFX_CAN_ID  = 4;
    
    public static final int FRONT_RIGHT_STEER_TalonFX_CAN_ID = 11;
    public static final int FRONT_LEFT_STEER_TalonFX_CAN_ID  = 12;
    public static final int REAR_LEFT_STEER_TalonFX_CAN_ID   = 13;
    public static final int REAR_RIGHT_STEER_TalonFX_CAN_ID  = 14;
  
    // CAN Bus IDs for All other Subsystem Talon SRX and FX controllers
    public static final int MAGAZINE_TalonFX_CAN_ID = 15;
    public static final int INTAKE_TalonSRX_CAN_ID = 16;
    public static final int SHOOT_TOP_TalonFX_CAN_ID = 17;
    public static final int SHOOT_BOTTOM_TalonFX_CAN_ID = 18;
    public static final int ZIPLINE_AND_COLOR_WHEEL_TalonSRX_CAN_ID = 19;
    public static final int LIFT_TalonFX_CAN_ID = 20;
    public static final int TILT_TalonFX_CAN_ID = 21;
    public static final int BEAVER_TAIL_TalonSRX_CAN_ID = 22;
  
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
    public static final double STEER_PPR = 15360.;                        // Pulses per wheel revolution with 2048 CTRE Mag Encoder + 7.46:1 calculated 15275
  
    // Locations of the swerve drive index signal for each modle in encoder pulses to get to zero, straight forward
    public static final double FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR * -.19681; //Drive Motor needs to be inverted
    public static final double FRONT_LEFT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .464388;
    public static final double REAR_LEFT_STEER_INDEX_OFFSET_PULSES  = STEER_PPR  * -.205404;
    public static final double REAR_RIGHT_STEER_INDEX_OFFSET_PULSES = STEER_PPR  * .463346; //Drive Motor needs to be inverted
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FR = 0.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FL = 0.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RL = 0.; //Small adjustment to cal for error
    public static final double CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RR = 0.; //Small adjustment to cal for error
  
    // Drive Motor Inversion, Flips the polarity of the motor in the Talon Controller
    public static final boolean FRONT_RIGHT_DRIVE_TalonFX_Invert = true;
    public static final boolean FRONT_LEFT_DRIVE_TalonFX_Invert = false;
    public static final boolean REAR_LEFT_DRIVE_TalonFX_Invert = false;
    public static final boolean REAR_RIGHT_DRIVE_TalonFX_Invert = true;
  
    // Deadband Joystick Constants
    public static final double X_AXIS_THREASHOLD = 0.1;
    public static final double Y_AXIS_THREASHOLD = 0.1;
    public static final double Z_AXIS_THREASHOLD = 0.1;
    
    // Pitch & Roll Constants
    public static final double PITCH_THRESHOLD = 15.; //Roll in degrees
    public static final double ROLL_THRESHOLD = 15.; //Pitch in degrees
    
    // Vision System Constants
    public static final double CAMERA_MOUNTING_ANGLE = 30.;  //Units are in degrees and referenced to X axis, with CCW being positive
    public static final double CAMERA_MOUNTING_HEIGHT = 24.;  //Units are in inches
    public static final double TARGET_HEIGHT = 98.; //Units are in inches
    public static final double DIFFERENTIAL_HEIGHT = TARGET_HEIGHT - CAMERA_MOUNTING_HEIGHT;
    public static final double GRAVITY = 386.; //Units are in inches/sec ^2
    public static final double VELOCITY = 785.; //Units are in inches/sec (2500rpm with 6in wheel diameter)
    public static final double CONSTANT_K = (2 * GRAVITY) / (VELOCITY * VELOCITY);

    // Lift Positions
    public static final double LIFT_TOP_POSITION = 0.;

    // Tilt Constants
    public static final double TRACK_LENGTH = 24.; // Inches
    public static final double GEAR_TRAVEL = 2.; // Inches
    public static final double GEAR_RATIO = 20.; // 20:1 gearbox
    public static final double MAGAZINE_LOW= TRACK_LENGTH / GEAR_TRAVEL * GEAR_RATIO * 2048.;
    public static final double MAGAZINE_VERTICAL = 2048.;
    public static final double PULSES_PER_DEGREE = MAGAZINE_LOW / 65.; // 65 degrees of travel
    public static final double PULSES_PER_RADIAN = PULSES_PER_DEGREE * Math.PI / 180.;
    public static final double MAGAZINE_CONTROL_WHEEL = PULSES_PER_DEGREE * 30.;

    // Shooter Constants
    public static final double TOP_SHOOT_WHEEL_PULSES_PER_100MS = 8533.;
    public static final double BOTTOM_SHOOT_WHEEL_PULSES_PER_100MS = 8533.;
    public static final double TOP_SHOOT_WHEEL_WALL_PULSES_PER_100MS = 1000.;
    public static final double BOTTOM_SHOOT_WHEEL_WALL_PULSES_PER_100MS = 1000.;
    
    // DIO Ports
    public static final int BALL_READY_TO_LOAD = 2;
    public static final int BALL_IN_FIRST_POSITION = 3;
    public static final int BALL_IN_FIFTH_POSITION = 4;
    public static final int REAR_RIGHT_STEER_CAL = 6;
    public static final int REAR_LEFT_STEER_CAL = 7;
    public static final int FRONT_LEFT_STEER_CAL = 8;
    public static final int FRONT_RIGHT_STEER_CAL = 9;

    // PWM Ports
    public static final int BALL_COUNT_LED_RIGHT = 0;
    public static final int BALL_COUNT_LED_LEFT = 1;
    public static final int STATUS_LED_RIGHT = 2;
    public static final int STATUS_LED_LEFT = 3;
    public static final int ARM_SERVO_PWM_PORT = 4;

    // Neopixel strip lengths
    public static final int LED_STRIP_LENGTH = 60;   
    
    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  }  
