/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.*;



/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // Declare Subsystem Objects
  public static Drivetrain m_drivetrain = new Drivetrain();
  public static Gyro m_gyro = new Gyro();
  public static Vision m_vision = new Vision();
  public static ColorSensor m_color = new ColorSensor();
  public static LED m_led = new LED();
  public static Magazine m_magazine = new Magazine();

  // Instantiate Command Objects
  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final CalibrateDriveTrain m_CalibrateDriveTrain = new CalibrateDriveTrain();
  private final CrabMode m_CrabMode = new CrabMode();
  private final Drive m_Drive = new Drive();
  private final FieldCentric m_FieldCentric = new FieldCentric();
  private final Flip180CCW m_Flip180CCW = new Flip180CCW();
  private final Flip180CW m_Flip180CW = new Flip180CW();
  private final GetColor m_GetColor = new GetColor();
  private final HighSpeedDrive m_HighSpeedDrive = new HighSpeedDrive();
  private final LowSpeedDrive m_LowSpeedDrive = new LowSpeedDrive();
  private final RobotCentric m_RobotCentric = new RobotCentric();
  private final SnakeMode m_SnakeMode = new SnakeMode();
  // private final Command m_autoCommand;
  private final SpinWheelToColor m_spinWheelToColor = new SpinWheelToColor();
  private final SpinWheel3Rev m_spinWheel3Rev = new SpinWheel3Rev();

  //Instantiate the Object, SwerveJoy, the joystick that controls the swervedrive
  private static Joystick swerveJoy = new Joystick(0);

  //Instantiate the buttons 0-11
  private JoystickButton btnSquare = new JoystickButton(swerveJoy, 1); //Square Button - Rotate 180 CCW
  private JoystickButton btnX = new JoystickButton(swerveJoy, 2); //X Button
  private JoystickButton btnO = new JoystickButton(swerveJoy, 3); //O Button - Rotate 180 CW
  private JoystickButton btnTriangle = new JoystickButton(swerveJoy, 4); //Triange Button
  private JoystickButton btnL1 = new JoystickButton(swerveJoy, 5); //L1 Button - Ball Targetting / Normal Drive Camera While !Pressed
  private JoystickButton btnR1 = new JoystickButton(swerveJoy, 6); //R1 Button - Field Centric
  private JoystickButton btnL2 = new JoystickButton(swerveJoy, 7); //L2 Button - Hatch Targetting / Normal Drive Camera While !Pressed
  private JoystickButton btnR2 = new JoystickButton(swerveJoy, 8); //R2 Button - Robot Centric
  private JoystickButton btnSelect = new JoystickButton(swerveJoy, 9); //Select Button - Level 2 Climb
  private JoystickButton btnStart = new JoystickButton(swerveJoy, 10); //Start Button - Level 3 Climb
  private JoystickButton btnLeftStick = new JoystickButton(swerveJoy, 11); //Left Stick Button
  private JoystickButton btnRightStick = new JoystickButton(swerveJoy, 12); //Right Stick Button

  //Instantiate the Object, operatorJoy, the joystick that controls the grabber/arm
  public static Joystick operatorJoy = new Joystick(1);

  //Instantiate the buttons 0-11
  private JoystickButton obtnX = new JoystickButton(operatorJoy, 1); //X Button - Lift to Middle Goal
  private JoystickButton obtnA = new JoystickButton(operatorJoy, 2); //A Button - Lift to Low Goal
  private JoystickButton obtnB = new JoystickButton(operatorJoy, 3); //B Button - Lift to Cargo Ship
  private JoystickButton obtnY = new JoystickButton(operatorJoy, 4); //Y Button - Lift to High Goal
  private JoystickButton obtnLB = new JoystickButton(operatorJoy, 5); //LB Button - Hatch Grab
  private JoystickButton obtnRB = new JoystickButton(operatorJoy, 6); //RB Button - Hatch Release
  private JoystickButton obtnLT = new JoystickButton(operatorJoy, 7); //LT Button - Ball Intake
  private JoystickButton obtnRT = new JoystickButton(operatorJoy, 8); //RT Button - Ball Shoot
  private JoystickButton obtnBack = new JoystickButton(operatorJoy, 9); //Back Button - Level 2 climb boomerang level
  private JoystickButton obtnStart = new JoystickButton(operatorJoy, 10); //Start Button  - Level 3 climb boomerang level
  private JoystickButton obtnLeftStick = new JoystickButton(operatorJoy, 11); //Left Stick Button
  private JoystickButton obtnRightStick = new JoystickButton(operatorJoy, 12); //Right Stick Button - Reverse Ball Intake

  
  //***** The container for the robot.  Contains subsystems, OI devices, and commands.
  
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings(); 
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Driver Buttons
      
    btnSquare.whenPressed(m_Flip180CCW, false);
    btnO.whenPressed(m_Flip180CW, false);
    btnR1.whenPressed(m_FieldCentric, false);
    btnR2.whenPressed(m_LowSpeedDrive, false); 
    btnR2.whenReleased(m_HighSpeedDrive, false);

    btnStart.whenPressed(m_Drive, false);

    // Operator Buttons
    obtnX.whenPressed(m_spinWheelToColor, false);
    obtnB.whenPressed(m_spinWheel3Rev, false);
  }
  
  public static double getDriverX(){
  return swerveJoy.getX();
  }

  public static double getDriverY(){
    return swerveJoy.getY();
  }

  public static double getDriverZ(){
    return swerveJoy.getZ();
  }
  
  public static double getOperatorY(){
    return operatorJoy.getY();
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  /*public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }*/
}
