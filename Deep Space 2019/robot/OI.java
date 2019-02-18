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
  private Button btnrc = new JoystickButton(swerveJoy, 1); //Square Button - Robot Centric - Default Robot
  private Button btnfc = new JoystickButton(swerveJoy, 2); //X Button - Field Centric - Default Robot
  private Button btncm = new JoystickButton(swerveJoy, 3); //O Button - Crab Mode - Default Crab
  private Button btnsm = new JoystickButton(swerveJoy, 4); //Triange Button - Snake Mode - Default Crab
  private Button btntm = new JoystickButton(swerveJoy, 5); //L1 Button - Targeting On While Pressed / Normal Drive Camera While !Pressed
  private Button btn6 = new JoystickButton(swerveJoy, 6); //R1 Button - No Key Binding
  private Button btn7 = new JoystickButton(swerveJoy, 7); //L2 Button - No Key Binding
  private Button btnbk = new JoystickButton(swerveJoy, 8); //R2 Button - Stop While Pressed
  private Button btn9 = new JoystickButton(swerveJoy, 9); //Select Button - No Key Binding
  private Button btn10 = new JoystickButton(swerveJoy, 10); //Start Button - No Key Binding
  private Button btn11 = new JoystickButton(swerveJoy, 11); //Left Stick Button - No Key Binding
  private Button btn12 = new JoystickButton(swerveJoy, 12); //Right Stick Button - No Key Binding

  //Instantiate the Object, operatorJoy, the joystick that controls the grabber/arm
  private Joystick operatorJoy = new Joystick(1);

  //Instantiate the buttons 0-11
  private Button obtn2 = new JoystickButton(operatorJoy, 1); //A Button
  private Button obtnbt = new JoystickButton(operatorJoy, 2); //B Button - Ball Target
  private Button obtnht = new JoystickButton(operatorJoy, 3); //X Button - Hatch Target
  private Button obtn4 = new JoystickButton(operatorJoy, 4); //Y Button
  private Button obtnhg = new JoystickButton(operatorJoy, 5); //LB Button - Hatch Grab
  private Button obtnhr = new JoystickButton(operatorJoy, 6); //RB Button - Hatch Release
  private Button obtnbi = new JoystickButton(operatorJoy, 7); //LT Button - Ball Intake
  private Button obtnbr = new JoystickButton(operatorJoy, 8); //RT Button - Ball Shoot
  private Button obtn9 = new JoystickButton(operatorJoy, 9); //Select Button
  private Button obtn10 = new JoystickButton(operatorJoy, 10); //Start Button
  private Button obtn11 = new JoystickButton(operatorJoy, 11); //Left Stick Button
  private Button obtn12 = new JoystickButton(operatorJoy, 12); //Right Stick Button

  public OI() {
    // Driver Buttons
    btnrc.whenPressed(new RobotCentric()); 
    btnfc.whenPressed(new FieldCentric());
    btncm.whenPressed(new CrabMode());
    btnsm.whenPressed(new SnakeMode());
    btntm.whenPressed(new TargetingMode());
    btnbk.whenPressed(new Brake());

    // Operator Buttons
    obtnbt.whenPressed(new BallTargetMode());
    obtnht.whenPressed(new HatchTargetMode());
    obtnhg.whenPressed(new HatchGrab());
    obtnhg.whenReleased(new HatchGrabHold());
    obtnhr.whenPressed(new HatchRelease());
    obtnhr.whenReleased(new HatchReleaseHold());
    obtnbi.whenPressed(new BallIntake());
    obtnbr.whenReleased(new BallShoot());
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

  public int getOperatorDpad(){
    return operatorJoy.getPOV();
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
