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
  /**
   * Creates a new ShootBall.
   */
  public ShootBall() {
    // Use addRequirements() here to declare subsystem dependencies.
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
    RobotContainer.m_shooter.shootBall(RobotContainer.m_drivetrain.getTargetTrackMode());
    RobotContainer.m_magazine.resetPosition();
    delay(800);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    forceEnd = RobotContainer.m_magazine.emptyMagazine();
    // RobotContainer.m_led.shootLED();
    // RobotContainer.m_magazine.emptyMagazine();
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

  public boolean getForceEnd(){
    return forceEnd;
  }

  private void delay(int msec){
    try{
        Thread.sleep(msec);
    }
    catch (Exception e){
        System.out.println("Error in Waitloop");
    }
  }

}
