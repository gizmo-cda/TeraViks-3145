/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

public class LED extends SubsystemBase {
  private static AddressableLED m_led;
  private static AddressableLEDBuffer m_ledBuffer;
  private static int m_rainbowFirstPixelHue = 240;
  private int ballCount;
  private int adressableLED;
  private boolean magFull;
  private int r = 0;
  private int g = 0;
  private int b = 0;

  /**
   * Creates a new LED.
   */
  public LED() {
    // PWM port 9
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(0);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(35);
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();
  }

  public void rainbowLED() {
    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;

    m_led.setData(m_ledBuffer);
  }

  public void dispBallCountLED() {
    ballCount = RobotContainer.m_magazine.getBallCount();
    adressableLED = ballCount * 7 - 1;
    magFull = RobotContainer.m_magazine.getMagFull();
    
    if (magFull) {
      r = 240;
      g = 0;
      b = 0;
    } else {
      r = 200;
      g = 100;
      b = 0;
    }

    for (var i = 0; i <= adressableLED ; i++) {
      if (!(i == 6 || i == 7 || i == 13 || i == 14 || i == 20 || i == 21 || i == 27 || i == 28)) {
        m_ledBuffer.setRGB(i, r, g, b);
      }
    }

    m_led.setData(m_ledBuffer);
  }

  public void clearLED() {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
