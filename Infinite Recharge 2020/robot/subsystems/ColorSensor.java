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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class ColorSensor extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  // Set the default command for a subsystem here.
  // setDefaultCommand(new MySpecialCommand());

  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final static I2C.Port i2cPort = I2C.Port.kOnboard;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a parameter.
   * The device will be automatically initialized with default parameters.
   */
  private final static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This
   * can be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final static ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * Note: Any example colors should be calibrated as the user needs, these are
   * here as a basic example.
   */
  private final static Color kBlueTarget = ColorMatch.makeColor(0.195, 0.459, 0.345);
  private final static Color kGreenTarget = ColorMatch.makeColor(0.222, 0.512, 0.265);
  private final static Color kRedTarget = ColorMatch.makeColor(0.329, 0.442, 0.229);
  private final static Color kYellowTarget = ColorMatch.makeColor(0.28, 0.521, 0.198);

  private static String currentColor;
  private static String targetColor;

  public static String colorTest() {
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

  public void rotateWheel() {
    targetColor = colorTest();
    int colorCheck = 0;

    while (colorCheck < 7) {
      currentColor = colorTest();
      System.out.println("spinning wheel");
      if (currentColor == targetColor) {
        colorCheck += 1;
        Timer.delay(1.);
        System.out.println("target color found!");
      }
    }
  }

  public void rotateWheelToColor() {
    String gameData;
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

    while (targetColor != currentColor) {
      currentColor = colorTest();
      System.out.println("spinning wheel");
    }

    System.out.println("target color found!");
  }
}
