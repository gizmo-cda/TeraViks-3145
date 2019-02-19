/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LiftCountIncrement extends Command {

  double position = RobotMap.LOW_TARGET_LIFT_LEVEL;

  public LiftCountIncrement() {
    requires(Robot.m_boomerang);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    position += 5000.; //Remove after positions are adjusted
    System.out.println("-----Current Boomerang Lift Position ="+Robot.m_boomerang.getLiftPosition());
    System.out.println("     New Boomerang set position will be = "+position);
    Robot.m_boomerang.setTestLiftPosition(position);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
