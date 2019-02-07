/*----------------------------------------------------------------------------*/
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
  private Button btn1 = new JoystickButton(swerveJoy, 1); //Square Button - Robot Centric Toggle - Default Robot
  private Button btn2 = new JoystickButton(swerveJoy, 2); //X Button - Field Centric Toggle - Default Robot
  private Button btn3 = new JoystickButton(swerveJoy, 3); //O Button - Crab Toggle - Default Crab
  private Button btn4 = new JoystickButton(swerveJoy, 4); //Triange Button - Snake Toggle - Default Crab
  private Button btn5 = new JoystickButton(swerveJoy, 5); //L1 Button - Targeting On/Off While Pressed / Normal Drive Camera While !Pressed
  private Button btn6 = new JoystickButton(swerveJoy, 6); //R1 Button - No Key Binding
  private Button btn7 = new JoystickButton(swerveJoy, 7); //L2 Button - No Key Binding
  private Button btn8 = new JoystickButton(swerveJoy, 8); //R2 Button - Stop While Pressed
  private Button btn9 = new JoystickButton(swerveJoy, 9); //Select Button - No Key Binding
  private Button btn10 = new JoystickButton(swerveJoy, 10); //Start Button - No Key Binding
  private Button btn11 = new JoystickButton(swerveJoy, 11); //Left Stick Button - No Key Binding
  private Button btn12 = new JoystickButton(swerveJoy, 12); //Right Stick Button - No Key Binding

  public OI() {
    // Call FieldMode Commmand to toggle Field Centric on/off
    btn1.whenPressed(new RobotMode()); 
    btn2.whenPressed(new FieldMode());
    //Call CrabMode Command to enable Crab Mode, call SnakeMode to toggle snake mode
    btn3.whenPressed(new CrabMode());
    btn4.whenPressed(new SnakeMode());
    //Call TargettingMode to toggle vision targetting while held
    btn5.whileHeld(new TargettingMode());
    btn8.whileHeld(new Brake());
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
