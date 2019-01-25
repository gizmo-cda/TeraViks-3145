/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // Constants for Robot Dimensions //
  public static final double WHEELBASE_LENGTH = 10.0;
  public static final double WHEELBASE_TRACK_WIDTH = 10.0;
  
  // Adjust wheel speed with this constant, must be <= 1.0//
  public static final double WHEEL_SPEED_SCALE = 1.0; 

  // CAN Bus IDs for Drivetrain Talon SRX controllers //
  public static final int FRONT_RIGHT_DRIVE_TalonSRX_CAN_ID = 1;
  public static final int FRONT_LEFT_DRIVE_TalonSRX_CAN_ID  = 2;
  public static final int REAR_LEFT_DRIVE_TalonSRX_CAN_ID   = 3;
  public static final int REAR_RIGHT_DRIVE_TalonSRX_CAN_ID  = 4;
  
  public static final int FRONT_RIGHT_STEER_TalonSRX_CAN_ID = 11;
  public static final int FRONT_LEFT_STEER_TalonSRX_CAN_ID  = 12;
  public static final int REAR_LEFT_STEER_TalonSRX_CAN_ID   = 13;
  public static final int REAR_RIGHT_STEER_TalonSRX_CAN_ID  = 14;

  // Gear Ratio Constants.  All Gear Ratios expressed in Motor revs TO Wheel Revs (ie M:W).  10:1 = 10 revs motor for 1 rev of the wheel //
  public static final double FINAL_DRIVE_WHEEL_GEAR_RATIO = 10.0/1.0;       // Motor drives the wheel directly, ratio is based on pully sizes //
  public static final double STEER_MOTOR_GEAR_REDUCTION_RATIO = 10.0/1.0;   // Motor drives gear reduction unit //
  public static final double STEER_WHEEL_GEAR_RATIO = 5.0/1.0;              // Output of gear reduction drives the steering pully //
  public static final double FINAL_STEER_WHEEL_GEAR_RATIO = STEER_MOTOR_GEAR_REDUCTION_RATIO * STEER_WHEEL_GEAR_RATIO;

  public static void init(){
  }
}
