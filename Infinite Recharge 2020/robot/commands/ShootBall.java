/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootBall extends CommandBase {
  private boolean finished = false;
  /**
   * Creates a new ShootBall.
   */
  public ShootBall() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public void cancelCommand(){
    if(finished) finished = false;
    else finished = true;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("shoot command init");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // RobotContainer.m_shooter.shootBall(RobotContainer.m_drivetrain.getTargetTrackMode());
    RobotContainer.m_magazine.emptyMagazine();
    RobotContainer.m_led.shootLED();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      System.out.println("shoot end");
      finished = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
