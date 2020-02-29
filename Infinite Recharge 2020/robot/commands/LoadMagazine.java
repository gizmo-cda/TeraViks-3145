
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class LoadMagazine extends CommandBase {
  /**
   * Creates a new LoadMagazine.
   */
  public LoadMagazine() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.m_magazine);
  }

  private boolean forceEnd = false;
  // private boolean finished = false;

  // public void cancelCommand(){
  //   if(finished) finished = false;
  //   else finished = true;
  // }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.m_magazine.loadMagazine();
    forceEnd = RobotContainer.m_magazine.getStopLoad();
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
