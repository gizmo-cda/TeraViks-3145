/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftBoomerang extends Command {
  int boomLevels[] = new int[] {0,100,200,300,400,500,600,700}; // determine at a later point
  int currentLevel;

  // potential to increment to a set pos or decrement to a set pos with two different button calls

  public LiftBoomerang() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_boomerang);
    currentLevel = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // set up mechanism to increment or decrement currentLevel (or maybe separate commands for up and down)
    if (currentLevel < boomLevels.length) {
      currentLevel++;
    } else {
      currentLevel = 0;
    }
    int driveLevel = boomLevels[currentLevel];
    Robot.m_boomerang.liftLevelBall(driveLevel);
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
