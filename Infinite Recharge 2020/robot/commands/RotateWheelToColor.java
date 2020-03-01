/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class RotateWheelToColor extends CommandBase {
  private boolean forceEnd = false;

  /**
   * Creates a new RotateWheelToColor.
   */
  public RotateWheelToColor() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.m_colorAndZipline.startZipline();
    RobotContainer.m_colorAndZipline.getGameData();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forceEnd = RobotContainer.m_colorAndZipline.rotateWheelToColor();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return forceEnd;
  }
}
