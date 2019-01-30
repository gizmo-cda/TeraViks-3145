/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

  public Joystick swerveJoy;

  //De buttons 0-11
  public Button btn0; //Square Button
  public Button btn1; //X Button
  public Button btn2; //O Button
  public Button btn3; //Triange Button
  public Button btn4; //L1 Button
  public Button btn5; //R1 Button
  public Button btn6; //L2 Button
  public Button btn7; //R2 Button
  public Button btn8; //Select Button
  public Button btn9; //Start Button
  public Button btn10; //Left Stick Button
  public Button btn11; //Right Stick Button


  public OI() {
    //Instantiate the Object, SwerveJoy)
    swerveJoy = new Joystick(0);
    //Instantiate the buttons 0-11
    btn0 = new JoystickButton(swerveJoy, 0); //Square Button
    btn1 = new JoystickButton(swerveJoy, 1); //X Button
    btn2 = new JoystickButton(swerveJoy, 2); //O Button
    btn3 = new JoystickButton(swerveJoy, 3); //Triange Button
    btn4 = new JoystickButton(swerveJoy, 4); //L1 Button
    btn5 = new JoystickButton(swerveJoy, 5); //R1 Button
    btn6 = new JoystickButton(swerveJoy, 6); //L2 Button
    btn7 = new JoystickButton(swerveJoy, 7); //R2 Button
    btn8 = new JoystickButton(swerveJoy, 8); //Select Button
    btn9 = new JoystickButton(swerveJoy, 9); //Start Button
    btn10 = new JoystickButton(swerveJoy, 10); //Left Stick Button
    btn11 = new JoystickButton(swerveJoy, 11); //Right Stick Button

    // toggle field centric
    btn0.whenPressed(new FieldMode());

  }
  //First Method of OI to get all the Joystick inputs//
  public Joystick getSwerveJoy(){
    return swerveJoy;
  }

  public Button getBtn0(){return btn0;} //Square Button
  public Button getBtn1(){return btn1;} //X Button
  public Button getBtn2(){return btn2;} //O Button
  public Button getBtn3(){return btn3;} //Triange Button
  public Button getBtn4(){return btn4;} //L1 Button
  public Button getBtn5(){return btn5;} //R1 Button
  public Button getBtn6(){return btn6;} //L2 Button
  public Button getBtn7(){return btn7;} //R2 Button
  public Button getBtn8(){return btn8;} //Select Button
  public Button getBtn9(){return btn9;} //Start Button
  public Button getBtn10(){return btn10;} //Left Stick Button
  public Button getBtn11(){return btn11;} //Right Stick Button

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
