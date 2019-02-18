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

public class CollisionSensor extends Command {
  double prevAccelY = 0.;
  
  public CollisionSensor() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_drivetrain);
  }
  
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean collisionDetected = false;
    
    while (!collisionDetected) {
      double currentAccelY = Robot.m_gyro.getAccelY();
      
      double currentJerkY = currentAccelY - prevAccelY;
      prevAccelY = currentAccelY;
      
      if ( Math.abs(currentJerkY) > RobotMap.COLLISION_THRESHOLD_Y) {
        collisionDetected = true;
      }

      Robot.m_drivetrain.move(.5, 0., 0.);
    } 

    Robot.m_drivetrain.move(0., 0., 0.);
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
