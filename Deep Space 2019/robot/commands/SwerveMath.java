/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

import java.lang.Math;
//import edu.wpi.first.wpilibj.Joystick;

public class SwerveMath extends Command {
  private static final Double l = 10.; // length of chassis
  private static final Double w = 10.; // width of chassis

  Double fwd;
  Double str;
  Double rcw;

  public SwerveMath() {

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // joystick inputs
    fwd = -Robot.m_oi.getswerveJoy().getY();
    str = Robot.m_oi.getswerveJoy().getX();
    rcw = Robot.m_oi.getswerveJoy().getZ();

    //Double fwd = OI.x_value;
    Double A = str - rcw * l/2; 
    Double B = str + rcw * l/2;
    Double C = fwd - rcw * w/2;
    Double D = fwd + rcw * w/2;

    Double ws1 = Math.sqrt(B*B + C*C); // Wheel Speed 1 = front right
    Double ws2 = Math.sqrt(B*B + D*D); // Wheel Speed 2 = front left
    Double ws3 = Math.sqrt(A*A + D*D); // Wheel Speed 3 = rear left
    Double ws4 = Math.sqrt (A*A + C*C); // Wheel Speed 4 = rear right

    // System.out.println("Raw Wheel Speed 1: " + Double.toString(ws1));
    // System.out.println("Raw Wheel Speed 2: " + Double.toString(ws2));
    // System.out.println("Raw Wheel Speed 3: " + Double.toString(ws3));
    // System.out.println("Raw Wheel Speed 4: " + Double.toString(ws4));

    Double rads = 180./Math.PI;

    Double wa1 = Math.atan2(B, C) * rads; // Wheel Angle 1 = front right
    Double wa2 = Math.atan2(B, D) * rads; // Wheel Angle 2 = front left
    Double wa3 = Math.atan2(A, D) * rads; // Wheel Angle 3 = rear left
    Double wa4 = Math.atan2(A, C) * rads; // Wheel Angle 4 = rear right

    // Wheel Speed Normalization
    Double max = ws1;
    if (ws2 > max) {
      max = ws2;
    }
    if (ws3 > max) {
      max = ws3;
    }
    if (ws4 > max) {
      max = ws4;
    }
    if (max > 1) {
      ws1/=max;
      ws2/=max;
      ws3/=max;
      ws4/=max;
    }


    // MotorSafety improves safety when motors are updated in loops
    // but is disabled here because motor updates are not looped in
    // this autonomous mode.

    // System.out.println("Wheel Speed 1: " + Double.toString(ws1));
    // System.out.println("Wheel Speed 2: " + Double.toString(ws2));
    // System.out.println("Wheel Speed 3: " + Double.toString(ws3));
    // System.out.println("Wheel Speed 4: " + Double.toString(ws4));

    // System.out.println("Wheel Angle 1: " + Double.toString(wa1));
    // System.out.println("Wheel Angle 2: " + Double.toString(wa2));
    // System.out.println("Wheel Angle 3: " + Double.toString(wa3));
    // System.out.println("Wheel Angle 4: " + Double.toString(wa4));
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
