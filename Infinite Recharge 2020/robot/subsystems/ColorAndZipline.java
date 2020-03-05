/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.RobotMap;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */

public class ColorAndZipline extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  // Set the default command for a subsystem here.
  // setDefaultCommand(new MySpecialCommand());

  private WPI_TalonSRX ziplineMotor = new WPI_TalonSRX(RobotMap.ZIPLINE_AND_COLOR_WHEEL_TalonSRX_CAN_ID);
  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a parameter.
   * The device will be automatically initialized with default parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This
   * can be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * Note: Any example colors should be calibrated as the user needs, these are
   * here as a basic example.
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.195, 0.459, 0.345);
  private final Color kGreenTarget = ColorMatch.makeColor(0.222, 0.512, 0.265);
  private final Color kRedTarget = ColorMatch.makeColor(0.329, 0.442, 0.229);
  private final Color kYellowTarget = ColorMatch.makeColor(0.28, 0.521, 0.198);

  private String currentColor;
  private String targetColor;
  private String gameData;
  private boolean endCommand;
  private int colorCheck;
  private boolean hasSeenColor;

  public String getColor() {
    /**
     * The method GetColor() returns a normalized color value from the sensor and
     * can be useful if outputting the color to an RGB LED or similar. To read the
     * raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in well
     * lit conditions (the built in LED is a big help here!). The farther an object
     * is the more light from the surroundings will bleed into the measurements and
     * make it difficult to accurately determine its color.
     */

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the sensor.
     */
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    return colorString;
  }

  public void resetColorCheck() {
    colorCheck = 0;
  }

  public boolean rotateWheel() {
    targetColor = getColor();
    currentColor = getColor();


    if (colorCheck < 7) {
      endCommand = false;

      if (currentColor == targetColor && !hasSeenColor) {
        colorCheck ++;
        hasSeenColor = true;
      } else hasSeenColor = false;
    } else endCommand = true;

    return endCommand;
  }
  
  public boolean rotateWheelToColor() {
    getColor();
    currentColor = getColor();

    if (targetColor != currentColor) {
      endCommand = false;
    } else {
      stopZipline();
      endCommand = true;
    }

    return endCommand;
  }

  public void getGameData(){
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    if (gameData.length() > 0) {
      switch (gameData.charAt(0)) {
      case 'B':
        // Blue case code
        targetColor = "Blue";
        break;
      case 'G':
        // Green case code
        targetColor = "Green";
        break;
      case 'R':
        // Red case code
        targetColor = "Red";
        break;
      case 'Y':
        // Yellow case code
        targetColor = "Yellow";
        break;
      default:
        // This is corrupt data
        break;
      }
    } else {
      // Code for no data received yet
      targetColor = "null";
    }
  }

  // ZIPLINE MOTOR COMMANDS
  // the zipline and color wheel motors are attatched to the same controller due
  // to running out of space on the pdp

  public void startZipline() {
    ziplineMotor.set(ControlMode.PercentOutput, .3);
  }

  public void moveZipline(double operatorX) {
    ziplineMotor.set(ControlMode.PercentOutput, operatorX);
  }

  public void stopZipline() {
    ziplineMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void init(){
    ziplineMotor.configFactoryDefault();
    
    ziplineMotor.setInverted(false);
        
    ziplineMotor.configPeakOutputForward(1., TIMEOUT);
    ziplineMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    ziplineMotor.configNominalOutputForward(0, TIMEOUT);
    ziplineMotor.configNominalOutputReverse(0, TIMEOUT);

    System.out.println("  - zipline Motor Initialized");
  }

}
