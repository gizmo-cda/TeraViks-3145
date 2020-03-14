// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LED extends SubsystemBase {
  // // Create LED Objects
  // private static AddressableLED frontRightLED;
  // private static AddressableLED frontLeftLED;
  // private static AddressableLED rearLeftLED;
  // private static AddressableLED rearRightLED;
  // private static AddressableLED magLED;

  // // Create LED Buffer Objects
  // private static AddressableLEDBuffer frontRightBuffer;
  // private static AddressableLEDBuffer frontLeftBuffer;
  // private static AddressableLEDBuffer rearRightBuffer;
  // private static AddressableLEDBuffer rearLeftBuffer;
  // private static AddressableLEDBuffer magBuffer;

  // // Used for rainbow
  // private static int m_rainbowFirstPixelHue = 240;

  // /**
  //  * Creates a new LED.
  //  */
  // public LED() {
  //   // Must be a PWM header, not MXP or DIO
  //   frontRightLED = new AddressableLED(RobotMap.FRONT_RIGHT_LED);
  //   frontLeftLED = new AddressableLED(RobotMap.FRONT_LEFT_LED);
  //   rearLeftLED = new AddressableLED(RobotMap.REAR_LEFT_LED);
  //   rearRightLED = new AddressableLED(RobotMap.REAR_RIGHT_LED);
  //   magLED = new AddressableLED(RobotMap.MAGAZINE_LED);

  //   // Reuse buffer
  //   // Length is set from a constant in RobotMap
  //   // Length is expensive to set, so only set it once, then just update data
  //   frontRightBuffer = new AddressableLEDBuffer(RobotMap.DRIVE_LED_LENGTH);
  //   frontLeftBuffer = new AddressableLEDBuffer(RobotMap.DRIVE_LED_LENGTH);
  //   rearLeftBuffer = new AddressableLEDBuffer(RobotMap.DRIVE_LED_LENGTH);
  //   rearRightBuffer = new AddressableLEDBuffer(RobotMap.DRIVE_LED_LENGTH);
  //   magBuffer = new AddressableLEDBuffer(RobotMap.MAG_LED_LENGTH);

  //   frontRightLED.setLength(frontRightBuffer.getLength());
  //   frontLeftLED.setLength(frontLeftBuffer.getLength());
  //   rearLeftLED.setLength(rearLeftBuffer.getLength());
  //   rearRightLED.setLength(rearRightBuffer.getLength());
  //   magLED.setLength(magBuffer.getLength());

  //   // Set the data
  //   frontRightLED.setData(frontRightBuffer);
  //   frontRightLED.start();
  //   frontLeftLED.setData(frontLeftBuffer);
  //   frontLeftLED.start();
  //   rearLeftLED.setData(rearLeftBuffer);
  //   rearLeftLED.start();
  //   rearRightLED.setData(rearRightBuffer);
  //   rearRightLED.start();
  // }

  // public void rainbowLED() {
  //   // For every pixel
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     // Calculate the hue - hue is easier for rainbows because the color
  //     // shape is a circle so only one value needs to precess
  //     final var hue = (m_rainbowFirstPixelHue + (i * 180 / RobotMap.DRIVE_LED_LENGTH)) % 180;
  //     // Set the value
  //     frontRightBuffer.setHSV(i, hue, 255, 128);
  //     frontLeftBuffer.setHSV(i, hue, 255, 128);
  //     rearLeftBuffer.setHSV(i, hue, 255, 128);
  //     rearRightBuffer.setHSV(i, hue, 255, 128);
  //   }
  //   // Increase by to make the rainbow "move"
  //   m_rainbowFirstPixelHue += 3;
  //   // Check bounds
  //   m_rainbowFirstPixelHue %= 180;

  // }

  // public boolean isDriveInverted(){
  //   return RobotContainer.m_drivetrain.getDriveInverted();
  // }

  // public void intakeLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     if(isDriveInverted()){
  //       frontRightBuffer.setRGB(i, 0, 0, 255);
  //       frontLeftBuffer.setRGB(i, 0, 0, 255);
  //       rearLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearRightBuffer.setRGB(i, 255, 255, 255);
  //     } else {
  //       frontRightBuffer.setRGB(i, 255, 255, 255);
  //       frontLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearLeftBuffer.setRGB(i, 0, 0, 255);
  //       rearRightBuffer.setRGB(i, 0, 0, 255);
  //     }
  //   }
  //   frontRightLED.setData(frontRightBuffer);
  //   frontLeftLED.setData(frontLeftBuffer);
  //   rearLeftLED.setData(rearLeftBuffer);
  //   rearRightLED.setData(rearRightBuffer);
  // }

  // public void climbLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     if(isDriveInverted()){
  //       frontRightBuffer.setRGB(i, 100, 0, 100);
  //       frontLeftBuffer.setRGB(i, 100, 0, 100);
  //       rearLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearRightBuffer.setRGB(i, 255, 255, 255);
  //     } else {
  //       frontRightBuffer.setRGB(i, 255, 255, 255);
  //       frontLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearLeftBuffer.setRGB(i, 100, 0, 100);
  //       rearRightBuffer.setRGB(i, 100, 0, 100);
  //     }
  //   }
  //   frontRightLED.setData(frontRightBuffer);
  //   frontLeftLED.setData(frontLeftBuffer);
  //   rearLeftLED.setData(rearLeftBuffer);
  //   rearRightLED.setData(rearRightBuffer);
  // }

  // public void shootLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     if(isDriveInverted()){
  //       frontRightBuffer.setRGB(i, 0, 180, 50);
  //       frontLeftBuffer.setRGB(i, 0, 180, 50);
  //       rearLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearRightBuffer.setRGB(i, 255, 255, 255);
  //     } else {
  //       frontRightBuffer.setRGB(i, 255, 255, 255);
  //       frontLeftBuffer.setRGB(i, 255, 255, 255);
  //       rearLeftBuffer.setRGB(i, 0, 180, 50);
  //       rearRightBuffer.setRGB(i, 0, 180, 50);
  //     }
  //   }
  //   frontRightLED.setData(frontRightBuffer);
  //   frontLeftLED.setData(frontLeftBuffer);
  //   rearLeftLED.setData(rearLeftBuffer);
  //   rearRightLED.setData(rearRightBuffer);
  // }

  // public void driveLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //       if(isDriveInverted()){
  //         frontRightBuffer.setRGB(i, 0, 0, 0);
  //         frontLeftBuffer.setRGB(i, 0, 0, 0);
  //         rearLeftBuffer.setRGB(i, 255, 255, 255);
  //         rearRightBuffer.setRGB(i, 255, 255, 255);
  //       } else {
  //         frontRightBuffer.setRGB(i, 255, 255, 255);
  //         frontLeftBuffer.setRGB(i, 255, 255, 255);
  //         rearLeftBuffer.setRGB(i, 0, 0, 0);
  //         rearRightBuffer.setRGB(i, 0, 0, 0);
  //       }
  //     }
  //     frontRightLED.setData(frontRightBuffer);
  //     frontLeftLED.setData(frontLeftBuffer);
  //     rearLeftLED.setData(rearLeftBuffer);
  //     rearRightLED.setData(rearRightBuffer);
  // }

  // public void magFullLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //       magBuffer.setRGB(i, 255, 0, 0);
  //   }
  //   magLED.setData(magBuffer);
  // }

  // public void clearMagLED(){
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     magBuffer.setRGB(i, 0, 0, 0);
  //   }
  //   magLED.setData(magBuffer);
  // }

  // public void clearDriveLED() {
  //   for (var i = 0; i < RobotMap.DRIVE_LED_LENGTH; i++) {
  //     frontRightBuffer.setRGB(i, 0, 0, 0);
  //     frontLeftBuffer.setRGB(i, 0, 0, 0);
  //     rearLeftBuffer.setRGB(i, 0, 0, 0);
  //     rearRightBuffer.setRGB(i, 0, 0, 0);
  //   }
  //   frontRightLED.setData(frontRightBuffer);
  //   frontLeftLED.setData(frontLeftBuffer);
  //   rearLeftLED.setData(rearLeftBuffer);
  //   rearRightLED.setData(rearRightBuffer);
  // }

  // public void clearLED(){
  //   clearMagLED();
  //   clearDriveLED();
  // }
 
  // @Override
  // public void periodic() {
  //   // This method will be called once per scheduler run
  // }
}
