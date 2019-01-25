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
import edu.wpi.first.wpilibj.command.Command;


public class Drive extends Command {
  private Double fwd; //Forward, Y axis, -1 to 1 from Joystick//
  private Double str; //Strafe, X axis, 1 to -1 from Joystick//
  private Double rcw; //Rotate CW, Z axis, 1 to -1 from Joystick, refernced 1=180 CW -1=-180 CW//

  public Drive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //requires(Robot.m_drivetrain);
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
    fwd = -Robot.m_oi.getSwerveJoy().getY();
    str = Robot.m_oi.getSwerveJoy().getX();
    rcw = Robot.m_oi.getSwerveJoy().getZ();

    // Call Drivetrain Subsystem //
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
