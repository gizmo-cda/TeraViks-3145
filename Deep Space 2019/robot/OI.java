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
  public int getOperatorDpad(){
    return operatorJoy.getPOV();
  }

  //Instantiate the buttons 0-11
  private Button btnht = new JoystickButton(swerveJoy, 1); //Square Button
  private Button btnbt = new JoystickButton(swerveJoy, 2); //X Button
  private Button btncm = new JoystickButton(swerveJoy, 3); //O Button - Crab Mode - Default Crab
  private Button btnsm = new JoystickButton(swerveJoy, 4); //Triange Button - Snake Mode - Default Crab
  private Button btn5 = new JoystickButton(swerveJoy, 5); //L1 Button
  private Button btnfc = new JoystickButton(swerveJoy, 6); //R1 Button - Field Centric
  private Button btn7 = new JoystickButton(swerveJoy, 7); //L2 Button - Targeting On While Pressed / Normal Drive Camera While !Pressed
  private Button btnrc = new JoystickButton(swerveJoy, 8); //R2 Button - Robot Centric
  private Button btn9 = new JoystickButton(swerveJoy, 9); //Select Button
  private Button btn10 = new JoystickButton(swerveJoy, 10); //Start Button
  private Button btn11 = new JoystickButton(swerveJoy, 11); //Left Stick Button
  private Button btn12 = new JoystickButton(swerveJoy, 12); //Right Stick Button

  //Instantiate the Object, operatorJoy, the joystick that controls the grabber/arm
  public Joystick operatorJoy = new Joystick(1);

  //Instantiate the buttons 0-11
  private Button obtnlm = new JoystickButton(operatorJoy, 1); //X Button - Lift to Middle Goal
  private Button obtnll = new JoystickButton(operatorJoy, 2); //A Button - Lift to Low Goal
  private Button obtn3 = new JoystickButton(operatorJoy, 3); //B Button - Increment up DURING TESTING
  private Button obtnlh = new JoystickButton(operatorJoy, 4); //Y Button - Lift to High Goal
  private Button obtnhg = new JoystickButton(operatorJoy, 5); //LB Button - Hatch Grab
  private Button obtnhr = new JoystickButton(operatorJoy, 6); //RB Button - Hatch Release
  private Button obtnbi = new JoystickButton(operatorJoy, 7); //LT Button - Ball Intake
  private Button obtnbs = new JoystickButton(operatorJoy, 8); //RT Button - Ball Shoot
  private Button obtnl3 = new JoystickButton(operatorJoy, 9); //Back Button - Level 3 climb
  private Button obtnl2 = new JoystickButton(operatorJoy, 10); //Start Button  - Level 2 climb
  private Button obtnbr = new JoystickButton(operatorJoy, 11); //Left Stick Button - Reverse Ball Intake
  private Button obtn12 = new JoystickButton(operatorJoy, 12); //Right Stick Button 

  public OI() {
    // Driver Buttons
    btnrc.whenPressed(new RobotCentric()); 
    btnfc.whenPressed(new FieldCentric());
    // these disrupt the drive train, use crab mode inherently instead
    // btncm.whenPressed(new CrabMode());

    // testing purposes /////////////////////////////////
    btncm.whenPressed(new HatchTrackModeEngage());
    btncm.whenReleased(new HatchTrackModeDisengage());
    /////////////////////////////////////////////////////

    // btnsm.whenPressed(new SnakeMode());
    btn5.whenPressed(new BallTrackModeEngage());
    btn5.whenReleased(new BallTrackModeDisengage());
    // btntm.whenPressed(new TargetingMode());
    btn7.whenPressed(new HatchTrackModeEngage());
    btn7.whenReleased(new HatchTrackModeDisengage());
    btnbt.whenPressed(new BallTargetMode());
    btnht.whenPressed(new HatchTargetMode());
    // btn10.whenPressed(new BoomerangRotate()); //For testing purposes only

    // btnsm.whenPressed(new GPSMode());
    // btnsm.whenReleased(new GPSModeDisengage());

    // Operator Buttons
    obtnhg.whenPressed(new HatchGrab());
    obtnhg.whenReleased(new HatchGrabHold());
    obtnhr.whenPressed(new HatchRelease());
    obtnhr.whenReleased(new HatchReleaseHold());
    obtnbi.whenPressed(new BallIntake());
    obtnbi.whenReleased(new BallStop());
    obtnbs.whenPressed(new BallShoot());
    obtnbs.whenReleased(new BallStop());
    obtnbr.whenPressed(new BallIntakeReverse());
    obtnbr.whenReleased(new BallStop());
    obtnll.whenPressed(new BoomerangLift(RobotMap.LOW_TARGET_LIFT_LEVEL));
    obtnlm.whenPressed(new BoomerangLift(RobotMap.MID_TARGET_LIFT_LEVEL));
    obtnlh.whenPressed(new BoomerangLift(RobotMap.HIGH_TARGET_LIFT_LEVEL));
    obtn3.whenPressed(new BoomerangLift(RobotMap.CARGO_SHIP_TARGET_LIFT_LEVEL));
    obtnl3.whenPressed(new Level3Boomerang());
    obtnl2.whenPressed(new Level3Group());

    if (getOperatorDpad() == 0){
      Robot.m_rearLift.setLiftPosition(0.);
    }

    // test
    // obtn3.whenPressed(new LiftCountIncrement());
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
