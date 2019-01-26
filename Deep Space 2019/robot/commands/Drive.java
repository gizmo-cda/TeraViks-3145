/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//  ************************************************************************  //
// This command gets joystick input and calls Drivetrain.Move to move the     //
// the robot in the desired direction                                         //

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;


public class Drive extends Command {
  private Double fwd; //Forward, Y axis, -1 to 1 from Joystick//
  private Double str; //Strafe, X axis, 1 to -1 from Joystick//
  private Double rcw; //Rotate CW, Z axis, 1 to -1 from Joystick, refernced 1=180 CW -1=-180 CW//

  public Drive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Get joystick inputs, apply the negative value to change the polarity
    // of the Y axis so a positive is forward.  Swerve Math expects normal
    // cartesian coordinates to calculate the directional vectors per wheel.

    // setting fwd values to raw input from the joystick's y axis
    fwd = -Robot.m_oi.getSwerveJoy().getY();

    // setting str values to raw input from the joystick's x axis
    str = Robot.m_oi.getSwerveJoy().getX();

    // setting rcw values to raw input from the joystick's z axis
    rcw = Robot.m_oi.getSwerveJoy().getZ();

    // System.out.println("X: "+fwd);
    // System.out.println("Y: "+str);
    // System.out.println("Z: "+rcw);

    if ((fwd > -RobotMap.X_AXIS_THREASHOLD) && (fwd < RobotMap.X_AXIS_THREASHOLD)) {
      fwd = 0.;
    }

    if ((str > -RobotMap.Y_AXIS_THREASHOLD) && (str < RobotMap.Y_AXIS_THREASHOLD)) {
      str = 0.;
    }

    if ((rcw > -RobotMap.Z_AXIS_THREASHOLD) && (rcw < RobotMap.Z_AXIS_THREASHOLD)) {
      rcw = 0.;
    }

    // squaring to double precision and improves operator control; if-else loops lets us retain the sign values
    if (fwd < 0) {
      fwd *= fwd * -1;
    } else {
      fwd *= fwd;
    }

    if (str < 0) {
      str *= str * -1;
    } else {
      str *= str;
    }

    if (rcw < 0) {
      rcw *= rcw * -1;
    } else {
      rcw *= rcw;
    }

    // Call Drivetrain Subsystem 
    Robot.m_drivetrain.move(fwd, str, rcw);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
