/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotMap;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends CommandBase {

  private Double fwd; //Forward, Y axis, -1 to 1 from Joystick
  private Double str; //Strafe, X axis, 1 to -1 from Joystick
  private Double rcw; //Rotate CW, Z axis, 1 to -1 from Joystick, refernced 1=180 CW -1=-180 CW

  private boolean forceEnd = false;

  /**
   * Creates a new Drive.
   */
  public Drive() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Get joystick inputs, apply the negative value to change the polarity
    // of the Y axis so a positive is forward.  Swerve Math expects normal
    // cartesian coordinates to calculate the directional vectors per wheel.

    // getting fwd values from raw input from the joystick's y axis
    fwd = -RobotContainer.getDriverY();

    // getting str values from raw input from the joystick's x axis
    str = RobotContainer.getDriverX();

    // getting rcw values from raw input from the joystick's z axis
    rcw = RobotContainer.getDriverZ();

    // System.out.println("X: "+fwd);
    // System.out.println("Y: "+str);
    // System.out.println("Z: "+rcw);

    // Dead-band the joystick inputs to remove noise/errors when centered
    if ((fwd > -RobotMap.X_AXIS_THREASHOLD) && (fwd < RobotMap.X_AXIS_THREASHOLD)) fwd = 0.;

    if ((str > -RobotMap.Y_AXIS_THREASHOLD) && (str < RobotMap.Y_AXIS_THREASHOLD)) str = 0.;

    if ((rcw > -RobotMap.Z_AXIS_THREASHOLD) && (rcw < RobotMap.Z_AXIS_THREASHOLD)) rcw = 0.;

    // Squaring to double precision and improve operator control; if-else loops retain the sign values
    if (fwd < 0) fwd *= fwd * -1.; else fwd *= fwd;

    if (str < 0) str *= str * -1.; else str *= str;

    if (rcw < 0) rcw *= rcw * -1.; else rcw *= rcw;

    // Call Drivetrain Subsystem to move
    RobotContainer.m_drivetrain.move(fwd, str, rcw);

    forceEnd = RobotContainer.m_lift.getStopDrive();
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
