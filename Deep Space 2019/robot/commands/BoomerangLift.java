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

public class BoomerangLift extends Command {
  // int boomLevels[] = new int[] {0,100,200,300,400,500,600,700}; // determine at a later point
  // int currentLevel;
  double position = 0.;

  // potential to increment to a set pos or decrement to a set pos with two different button calls

  public BoomerangLift(double level) {
    requires(Robot.m_boomerang);
    position = level;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Robot.m_boomerang.setLiftLevelMotionMagic(position);
      Robot.m_boomerang.setDesiredLiftLevel(position);
  }

  public double setTestLiftPostion(){
    return position;
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
