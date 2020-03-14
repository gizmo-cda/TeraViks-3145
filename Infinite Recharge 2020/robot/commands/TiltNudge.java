/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;

public class TiltNudge extends CommandBase {
  private double upDown;
  private double position;
  /**
   * Creates a new TiltNudge.
   */
  public TiltNudge() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     upDown = -RobotContainer.getOperatorY();

    // Dead-band the joystick inputs to remove noise/errors when centered
    if ((upDown > -RobotMap.Y_AXIS_THREASHOLD) && (upDown < RobotMap.Y_AXIS_THREASHOLD)) upDown = 0.;

     position = (double) RobotContainer.m_tilt.getTiltPosition();
    if (upDown > 0) position += -RobotMap.PULSES_PER_DEGREE; 
    if (upDown < 0) position -= -RobotMap.PULSES_PER_DEGREE;

    RobotContainer.m_tilt.setTiltAngle(position);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
