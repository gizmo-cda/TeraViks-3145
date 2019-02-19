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
  int dpad;
  double position;

  // potential to increment to a set pos or decrement to a set pos with two different button calls

  public BoomerangLift() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_boomerang);
    //currentLevel = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    position= RobotMap.LOW_TARGET_LIFT_LEVEL;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // set up mechanism to increment or decrement currentLevel (or maybe separate commands for up and down)
  //   if (currentLevel < boomLevels.length) {
  //     currentLevel++;
  //   } else {
  //     currentLevel = 0;
  //   }
  //   int driveLevel = boomLevels[currentLevel];
  //   Robot.m_boomerang.liftLevelBall(driveLevel);
  // }
    dpad = Robot.m_oi.getOperatorDpad();
    
    switch (dpad) {
      case -1:
      break;
      case 0:
      //position = Robot.m_boomerang.getTestLiftPosition();
      break;
      case 90:
      position = RobotMap.HIGH_TARGET_LIFT_LEVEL;
      break;
      case 180:
      position = RobotMap.MID_TARGET_LIFT_LEVEL;
      break;
      case 270:
      position = RobotMap.LOW_TARGET_LIFT_LEVEL;
      break;
    }
      Robot.m_boomerang.setLiftLevelMotionMagic(position);
      Robot.m_boomerang.setDesiredLiftLevel(position);
  }

  public double setTestLiftPostion(){
    return position;
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
