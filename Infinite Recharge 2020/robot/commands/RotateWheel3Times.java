/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RotateWheel3Times extends CommandBase {
  private boolean forceEnd = false;

  /**
   * Creates a new RotateWheel3Times.
   */
  public RotateWheel3Times() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.m_colorAndZipline.startZipline();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forceEnd = RobotContainer.m_colorAndZipline.rotateWheel();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.m_colorAndZipline.stopZipline();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return forceEnd;
  }
}
