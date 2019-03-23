/*--------------------------------------------------------------------`--------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import frc.robot.commands.*;

public class OI {

  //Instantiate the Object, SwerveJoy, the joystick that controls the swervedrive
  private Joystick swerveJoy = new Joystick(0);

  //Instantiate the buttons 0-11
  private Button btnSquare = new JoystickButton(swerveJoy, 1); //Square Button - Rotate 180 CCW
  private Button btnX = new JoystickButton(swerveJoy, 2); //X Button
  private Button btnO = new JoystickButton(swerveJoy, 3); //O Button - Rotate 180 CW
  private Button btnTriangle = new JoystickButton(swerveJoy, 4); //Triange Button
  private Button btnL1 = new JoystickButton(swerveJoy, 5); //L1 Button - Ball Targetting / Normal Drive Camera While !Pressed
  private Button btnR1 = new JoystickButton(swerveJoy, 6); //R1 Button - Field Centric
  private Button btnL2 = new JoystickButton(swerveJoy, 7); //L2 Button - Hatch Targetting / Normal Drive Camera While !Pressed
  private Button btnR2 = new JoystickButton(swerveJoy, 8); //R2 Button - Robot Centric
  private Button btnSelect = new JoystickButton(swerveJoy, 9); //Select Button - Level 2 Climb
  private Button btnStart = new JoystickButton(swerveJoy, 10); //Start Button - Level 3 Climb
  private Button btnLeftStick = new JoystickButton(swerveJoy, 11); //Left Stick Button
  private Button btnRightStick = new JoystickButton(swerveJoy, 12); //Right Stick Button

  //Instantiate the Object, operatorJoy, the joystick that controls the grabber/arm
  public Joystick operatorJoy = new Joystick(1);

  //Instantiate the buttons 0-11
  private Button obtnX = new JoystickButton(operatorJoy, 1); //X Button - Lift to Middle Goal
  private Button obtnA = new JoystickButton(operatorJoy, 2); //A Button - Lift to Low Goal
  private Button obtnB = new JoystickButton(operatorJoy, 3); //B Button - Lift to Cargo Ship
  private Button obtnY = new JoystickButton(operatorJoy, 4); //Y Button - Lift to High Goal
  private Button obtnLB = new JoystickButton(operatorJoy, 5); //LB Button - Hatch Grab
  private Button obtnRB = new JoystickButton(operatorJoy, 6); //RB Button - Hatch Release
  private Button obtnLT = new JoystickButton(operatorJoy, 7); //LT Button - Ball Intake
  private Button obtnRT = new JoystickButton(operatorJoy, 8); //RT Button - Ball Shoot
  private Button obtnBack = new JoystickButton(operatorJoy, 9); //Back Button - Level 2 climb boomerang level
  private Button obtnStart = new JoystickButton(operatorJoy, 10); //Start Button  - Level 3 climb boomerang level
  private Button obtnLeftStick = new JoystickButton(operatorJoy, 11); //Left Stick Button
  private Button obtnRightStick = new JoystickButton(operatorJoy, 12); //Right Stick Button - Reverse Ball Intake

  public OI() {
    // Driver Buttons
    btnSquare.whenPressed(new Flip180CCW());
    btnO.whenPressed(new Flip180CW());
    btnL1.whenPressed(new BallTrackModeEngage());
    btnL1.whenReleased(new BallTrackModeDisengage());
    btnL2.whenPressed(new HatchTrackModeEngage());
    btnL2.whenReleased(new HatchTrackModeDisengage());
    btnR1.whenPressed(new FieldCentric());
    btnR2.whenPressed(new LowSpeedDrive()); 
    btnR2.whenReleased(new HighSpeedDrive());
    btnSelect.whenPressed(new Level2Group());
    btnStart.whenPressed(new Level3Group());

    // Operator Buttons
    obtnLB.whenPressed(new HatchGrab());
    obtnLB.whenReleased(new HatchGrabHold());
    obtnRB.whenPressed(new HatchRelease());
    obtnRB.whenReleased(new HatchReleaseHold());
    obtnLT.whenPressed(new BallIntake());
    obtnLT.whenReleased(new BallStop());
    obtnRT.whenPressed(new BallShoot());
    obtnRT.whenReleased(new BallStop());
    obtnLeftStick.whenPressed(new BoomerangNudge());
    obtnRightStick.whenPressed(new BallIntakeReverse());
    obtnRightStick.whenReleased(new BallStop());
    obtnA.whenPressed(new BoomerangLift(RobotMap.LOW_TARGET_LIFT_LEVEL));
    obtnX.whenPressed(new BoomerangLift(RobotMap.MID_TARGET_LIFT_LEVEL));
    obtnY.whenPressed(new BoomerangLift(RobotMap.HIGH_TARGET_LIFT_LEVEL));
    obtnB.whenPressed(new BoomerangLift(RobotMap.CARGO_SHIP_TARGET_LIFT_LEVEL));
    obtnBack.whenPressed(new Level2Boomerang());
    obtnStart.whenPressed(new Level3Boomerang());

  }
  
  public double getDriverX(){
  return swerveJoy.getX();
  }

  public double getDriverY(){
    return swerveJoy.getY();
  }

  public double getDriverZ(){
    return swerveJoy.getZ();
  }
  
  public double getOperatorY(){
    return operatorJoy.getY();
  }

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Button button = new JoystickButton(stick, 0);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());
  

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
