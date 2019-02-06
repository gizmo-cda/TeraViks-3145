/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/* Robot Wheel Assignment 
*                        
*    Front/Forward       
*          ^             
*          |             
*          |             
*  W2|-----------|W1     
*    |           |       
*    |           |  l    
*    |           |  e    
*    |           |  n    
*    |           |  g    
*    |           |  t    
*    |           |  h    
*    |           |       
*  W3|-----------|W4     
*        width           
*
***********************
*  This Class takes the desired forward, strafe, and rotate clockwise inputs and 
*  calculates the wheel speed and wheel angle for each wheel to acheive the desired
*  motion.  It also provides for gyro correction of the joystick inputs for 
*  field centric drive mode.
*/

package frc.robot.swerve;

import frc.robot.RobotMap;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class SwerveMath {

  // Fields of SwerveMath, note they are local (ie private to SwerveMath)
  // These are intitialized as constants to reduce the math cycles in the class' methods
  private static Double halfLength = RobotMap.WHEELBASE_LENGTH / 2; //length of chassis divided by 2
  private static Double halfWidth = RobotMap.WHEELBASE_TRACK_WIDTH / 2; //width of chassis divided by 2
  private static Double pp100msec = RobotMap.DRIVE_WHEEL_PULSES_PER_100MS; //encoder pulses per 100 msec

  //private static Double toDeg = 180./Math.PI;  //convert Radians to Degrees
  private static Double toRad = Math.PI/180.;  //convert Degrees to Radians

  // Pulses per various angles for steering the wheel
  private static Double threeSixty = RobotMap.STEER_PPR;
  //private static Double twoSeventy = threeSixty * .75;
  private static Double oneEighty = threeSixty / 2.;
  private static Double ninety = oneEighty / 2.;

  // toPos is used to convert +/-pi angle to encoder position
  private static Double toPos = oneEighty / Math.PI;

  private static Double fwd; //Forward, Y axis, -1 to 1 from Joystick
  private static Double str; //Strafe, X axis, 1 to -1 from Joystick
  private static Double rcw; //Rotate CW, Z axis, 1 to -1 from Joystick, refernced 1=180 CW -1=-180 CW

  private static Double gyro; //Gyro Input in Degrees

  private static boolean centric; //Field Centric Mode

  private static boolean reverseEn; //Reverse Enable Mode

 //Initialize variables to store previous SwerveMath iteration's wheel position values - used for Reversing
  private static Double wp1Current = 0.;  
  private static Double wp2Current = 0.;  
  private static Double wp3Current = 0.;  
  private static Double wp4Current = 0.;  

  //Initialize variables to track whether wheel direction was reversed in previous SwerveMath iteration - used for Reversing
  private static boolean wp1IsReversed = false;  
  private static boolean wp2IsReversed = false;  
  private static boolean wp3IsReversed = false;  
  private static boolean wp4IsReversed = false;  

  //Initialize variables to track full wheel direction rotations
  private static Double wp1Rotate = 0.;  
  private static Double wp2Rotate = 0.;  
  private static Double wp3Rotate = 0.;  
  private static Double wp4Rotate = 0.;  
  
  // Chose ArrayList type and defined the size because the size will never change and it's easy to modify members 
  private ArrayList<Double> wheelVectors = new ArrayList<Double>(Arrays.asList(0.,0.,0.,0.,0.,0.,0.,0.));
  
  // Null Constructor for SwerveMath for Object Instanitation
  public SwerveMath(){
  }

  // First Method of SwerveMath that returns the vector (speed, position) for each wheel as a list
  public ArrayList<Double> getVectors(double fwdIn, double strIn, double rcwIn, boolean centricIn, double gyroIn, boolean reverseEnIn){
    fwd = fwdIn;
    str = strIn;
    rcw = rcwIn;
    centric = centricIn;
    gyro = gyroIn * toRad;
    reverseEn = reverseEnIn;

    // ********Modify the Joystick Inputs for Centric Mode*******
    if (centric) {
      Double y_f = fwd * Math.cos(gyro); // y component of field
      Double y_s = str * Math.sin(gyro); // y component of strafe
      Double x_f = fwd * Math.sin(gyro); // x component of field
      Double x_s = str * Math.cos(gyro); // x component of strafe

      fwd = y_f + y_s;
      str = -x_f + x_s;
    }
    
    // ********Fundamental Math Block for Wheel Speed and Position*******
    // Define the common elements in wheel vector math
    Double A = str - rcw * halfLength; 
    Double B = str + rcw * halfLength;
    Double C = fwd - rcw * halfWidth;
    Double D = fwd + rcw * halfWidth;

    // Calculate the speed for each wheel, result will be proportional, no units
    Double ws1 = Math.sqrt(B*B + C*C);  // Wheel Speed 1 = front right
    Double ws2 = Math.sqrt(B*B + D*D);  // Wheel Speed 2 = front left
    Double ws3 = Math.sqrt(A*A + D*D);  // Wheel Speed 3 = rear left
    Double ws4 = Math.sqrt(A*A + C*C);  // Wheel Speed 4 = rear right
    
    // Wheel Speed Normalization, Can't have a wheel speed > 1
    Double max = ws1;
    if (ws2 > max) max = ws2;
    if (ws3 > max) max = ws3;
    if (ws4 > max) max = ws4;
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }

    // modify wheel speeds from +/- 1 => pulses per 100 ms
    ws1 *= pp100msec; 
    ws2 *= pp100msec;
    ws3 *= pp100msec;
    ws4 *= pp100msec;

    // Calculate the wheel angle for each wheel in radians: +/-pi referenced to Y axis 
    // Then convert to wheel position, with toPos, to absolute pulses for encoder over +/-pi range
    Double wp1 = Math.atan2(B, C) * toPos; // Wheel Angle 1 = front right
    Double wp2 = Math.atan2(B, D) * toPos; // Wheel Angle 2 = front left
    Double wp3 = Math.atan2(A, D) * toPos; // Wheel Angle 3 = rear left
    Double wp4 = Math.atan2(A, C) * toPos; // Wheel Angle 4 = rear right

    // ********Optimization #1: Continous Steering Rotation and Shortest Path Steering with Uni-Directional Drive Wheel************
    //With the output of the math block above there is a discontinuity from 180 degrees to 181 degrees.
    //The math block will represent 181 degrees as -180 degrees.  This means that anytime the wheel needs
    //to move more than 180 degrees, regardless of where it starts it will have to go the long way around
    //the circle.

    //This code block tests each wheel to see if Swerve Math wants to rotate the wheel more than +/- 180 
    //degrees from the previous iteration.  If yes, then "wp*Rotate" is incremented/decremented by 360 degrees.
    //Note that "wp*Current" is normalized back to the range -180..+180 degrees for the comparison to
    //function properly.  All measurements are done in pulses, but we typically talk in degrees.

    //At this point in the calculation, "wp*" is constrained to the range -180..+180 degrees
    //If the Operator command wants to rotate the wheel outside this range, we must add or subtract 360
    //degrees to "wp*" (depending on direction of rotation) so that it moves smoothly instead of reversing
    //the steering motor to stay within the constrained range.

    //"wp*Rotate" is an accumulator that keeps track of how many full rotations each wheel has made during
    //the session and adds/subtracts the pulses. It is added to "wp*" to update the wheel position in 
    // so it is in alignment with the encoder.
    Double pulseDiff1 = wp1 - (wp1Current - wp1Rotate);
    Double pulseDiff2 = wp2 - (wp2Current - wp2Rotate);
    Double pulseDiff3 = wp3 - (wp3Current - wp3Rotate);
    Double pulseDiff4 = wp4 - (wp4Current - wp4Rotate);

    if (pulseDiff1 < -oneEighty) wp1Rotate += threeSixty; //Change to Clockwise Rotation
    if (pulseDiff1 > oneEighty) wp1Rotate -= threeSixty;  //Change to Counter Clockwise Rotation
    if (pulseDiff2 < -oneEighty) wp2Rotate += threeSixty; 
    if (pulseDiff2 > oneEighty) wp2Rotate -= threeSixty;  
    if (pulseDiff3 < -oneEighty) wp3Rotate += threeSixty; 
    if (pulseDiff3 > oneEighty) wp3Rotate -= threeSixty;  
    if (pulseDiff4 < -oneEighty) wp4Rotate += threeSixty; 
    if (pulseDiff4 > oneEighty) wp4Rotate -= threeSixty;  

    wp1 += wp1Rotate;
    wp2 += wp2Rotate;
    wp3 += wp3Rotate;
    wp4 += wp4Rotate;

    wp1Current = wp1;
    wp2Current = wp2;
    wp3Current = wp3;
    wp4Current = wp4;

    // ******Wheel Reversing for Shortest Path of Rotation********
    // ONLY DO THIS if Reverse is Enabled.  Reversing basic logic: 
    // Check to see if any wheel needs to move more than 90 degrees, in pulses
    // If so add or subtract 180 degrees in pulses to the position and change the polarity of the wheel speed

    // First, if any wheel was reversed by last SwerveMath iteration then reverse it for this iteration
    // so the new comparison is done with the supplemental angles.
    // Nested "if" statements calculate the supplemental angle, making sure the encoder never 'wraps'
    if (reverseEn){
      if (wp1IsReversed) {
        ws1 *= -1.;
        if ( wp1 >= 0) wp1 -= oneEighty; else  wp1 += oneEighty;
      }

      if (wp2IsReversed) {
        ws2 *= -1.;
        if ( wp2 >= 0) wp2 -= oneEighty; else  wp2 += oneEighty;
      }

      if (wp3IsReversed) {
        ws3 *= -1.;
        if ( wp3 >= 0) wp3 -= oneEighty; else  wp3 += oneEighty;
      }

      if (wp4IsReversed) {
        ws4 *= -1.;
        if ( wp4 >= 0) wp4 -= oneEighty; else  wp4 += oneEighty;
      }

      // Now determine whether new wheel position is > 90 degrees, in pulses, from current wheel position
      // If so, then reverse speed, and turn wheel direction +/-180 degrees, in pulses
      // Also reverse the boolean logic of the "IsReversed" variable
      // Nested "if" statements calculate the supplemental angle, making sure the encoder never 'wraps'

      if (Math.abs(wp1 - wp1Current) > ninety){
        ws1 *= -1.;
        wp1IsReversed = !wp1IsReversed;
        if ( wp1 >= 0) wp1 -= oneEighty; else  wp1 += oneEighty;
      }

      if (Math.abs(wp2 - wp2Current) > ninety){
        ws2 *= -1.;
        wp2IsReversed = !wp2IsReversed;
        if ( wp2 >= 0) wp2 -= oneEighty; else  wp2 += oneEighty;
      }

      if (Math.abs(wp3 - wp3Current) > ninety){
        ws3 *= -1.;
        wp3IsReversed = !wp3IsReversed;
        if ( wp3 >= 0) wp3 -= oneEighty; else  wp3 += oneEighty;
      }

      if (Math.abs(wp4 - wp4Current) > ninety){
        ws4 *= -1.;
        wp4IsReversed = !wp4IsReversed;
        if ( wp4 >= 0) wp4 -= oneEighty; else  wp4 += oneEighty;
      }

      //Finally, save modified "wp" position from this SwerveMath iteration to be used in next iteration//
      wp1Current = wp1;
      wp2Current = wp2;
      wp3Current = wp3;
      wp4Current = wp4;
    }

    // Set the vector values (speed, position) for each wheel vector
    wheelVectors.set(0,ws1);
    wheelVectors.set(1,wp1);
    wheelVectors.set(2,ws2);
    wheelVectors.set(3,wp2);
    wheelVectors.set(4,ws3);
    wheelVectors.set(5,wp3);
    wheelVectors.set(6,ws4);
    wheelVectors.set(7,wp4);

    return wheelVectors;
  }
}

