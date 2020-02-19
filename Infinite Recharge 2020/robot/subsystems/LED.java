/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LED extends SubsystemBase {
  // Create LED Objects
  private static AddressableLED m_ballCountLEDR;
  private static AddressableLED m_ballCountLEDL;
  private static AddressableLED m_statusLEDR;
  private static AddressableLED m_statusLEDL;

  // Create LED Buffer Objects
  private static AddressableLEDBuffer m_ballCountLEDBufferR;
  private static AddressableLEDBuffer m_ballCountLEDBufferL;
  private static AddressableLEDBuffer m_statusLEDBufferR;
  private static AddressableLEDBuffer m_statusLEDBufferL;

  // Used for rainbow
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
    // Must be a PWM header, not MXP or DIO
    // m_ballCountLEDR = new AddressableLED(RobotMap.BALL_COUNT_LED_RIGHT);
    // m_ballCountLEDL = new AddressableLED(RobotMap.BALL_COUNT_LED_LEFT);
    m_statusLEDR = new AddressableLED(RobotMap.STATUS_LED_RIGHT);
    // m_statusLEDL = new AddressableLED(RobotMap.STATUS_LED_LEFT);

    // Reuse buffer
    // Length is set from a constant in RobotMap
    // Length is expensive to set, so only set it once, then just update data
    // m_ballCountLEDBufferR = new AddressableLEDBuffer(RobotMap.LED_STRIP_LENGTH);
    // m_ballCountLEDBufferL = new AddressableLEDBuffer(RobotMap.LED_STRIP_LENGTH);
    m_statusLEDBufferR = new AddressableLEDBuffer(RobotMap.LED_STRIP_LENGTH);
    // m_statusLEDBufferL = new AddressableLEDBuffer(RobotMap.LED_STRIP_LENGTH);
    // m_ballCountLEDR.setLength(m_ballCountLEDBufferR.getLength());
    // m_ballCountLEDL.setLength(m_ballCountLEDBufferL.getLength());
    m_statusLEDR.setLength(m_statusLEDBufferR.getLength());
    // m_statusLEDL.setLength(m_statusLEDBufferL.getLength());

    // Set the data
    // m_ballCountLEDR.setData(m_ballCountLEDBufferR);
    // m_ballCountLEDR.start();
    // m_ballCountLEDL.setData(m_ballCountLEDBufferL);
    // m_ballCountLEDL.start();
    m_statusLEDR.setData(m_statusLEDBufferR);
    m_statusLEDR.start();
    // m_statusLEDL.setData(m_statusLEDBufferL);
    // m_statusLEDL.start();
  }

  public void rainbowLED() {
    // For every pixel
    for (var i = 0; i < m_statusLEDBufferR.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_statusLEDBufferR.getLength())) % 180;
      // Set the value
      // m_ballCountLEDBufferR.setHSV(i, hue, 255, 128);
      // m_ballCountLEDBufferL.setHSV(i, hue, 255, 128);
      m_statusLEDBufferR.setHSV(i, hue, 255, 128);
      // m_statusLEDBufferL.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;

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
        // m_ballCountLEDBufferR.setRGB(i, r, g, b);
        // m_ballCountLEDBufferL.setRGB(i, r, g, b);
      }
    }
  }

  public void intakeLED(){
    for (var i = 0; i < RobotMap.LED_STRIP_LENGTH; i++) {
      m_statusLEDBufferR.setRGB(i, 0, 0, 255);
      // m_statusLEDBufferL.setRGB(i, 0, 0, 255);
    }
    m_statusLEDR.setData(m_statusLEDBufferR);
    // m_statusLEDR.setData(m_statusLEDBufferR);
  }

  public void climbLED(){
    for (var i = 0; i < RobotMap.LED_STRIP_LENGTH; i++) {
      m_statusLEDBufferR.setRGB(i, 100, 0, 100);
      // m_statusLEDBufferL.setRGB(i, 150, 0, 200);
    }
  }

  public void shootLED(){
    for (var i = 0; i < RobotMap.LED_STRIP_LENGTH; i++) {
      m_statusLEDBufferR.setRGB(i, 0, 255, 0);
      // m_statusLEDBufferL.setRGB(i, 0, 180, 50);
    }
  }

  public void clearBallCountLED(){
    for (var i = 0; i < RobotMap.LED_STRIP_LENGTH; i++) {
      // m_ballCountLEDBufferR.setRGB(i, 0, 0, 0);
      // m_ballCountLEDBufferL.setRGB(i, 0, 0, 0);
    }
  }

  public void clearStatusLED() {
    for (var i = 0; i < RobotMap.LED_STRIP_LENGTH; i++) {
      m_statusLEDBufferR.setRGB(i, 0, 0, 0);
      // m_statusLEDBufferL.setRGB(i, 0, 0, 0);
    }
  }

  public void clearLED(){
    clearBallCountLED();
    clearStatusLED();
  }

  public void testLED(){
    for (var i = 0; i < 60; i++) {
      // m_ballCountLEDBufferR.setRGB(i, 100, 50, 0);
    }

    for (var i = 0; i < 0; i++) {
      // m_ballCountLEDBufferR.setRGB(i, 200, 100, 0);
    }
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // m_ballCountLEDR.setData(m_ballCountLEDBufferR);
    // m_ballCountLEDL.setData(m_ballCountLEDBufferL);
    m_statusLEDR.setData(m_statusLEDBufferR);
    // m_statusLEDL.setData(m_statusLEDBufferL);
  }
}
