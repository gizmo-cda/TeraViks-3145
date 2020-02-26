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
  private static Double radius = Math.sqrt((halfLength * halfLength) + (halfWidth * halfWidth));  //radius of rotational center to each wheel
  private static Double halfLengthNorm = halfLength / radius;  //Length normalized by radius
  private static Double halfWidthNorm = halfWidth / radius;  //Width normalized by radius

  private static Double pp100msec = RobotMap.DRIVE_WHEEL_PULSES_PER_100MS; //encoder pulses per 100 msec

  //private static Double toDeg = 180./Math.PI;  //convert Radians to Degrees
  private static Double toRad = Math.PI/180.;  //convert Degrees to Radians

  // Pulses per various angles for steering the wheel
  private static Double threeSixty = RobotMap.STEER_PPR;
  private static Double twoSeventy = threeSixty * .75;
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

  private static boolean snakeMode; //Crab = false, Snake = true

  private static String hiLo; //Adjust wheel velocity scalar for high speed or low speed

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

  //Only for testing, don't use this method
  public void reset(){
    wp1Current = 0.;  
    wp2Current = 0.;  
    wp3Current = 0.;  
    wp4Current = 0.;  

    wp1IsReversed = false;  
    wp2IsReversed = false;  
    wp3IsReversed = false;  
    wp4IsReversed = false;  

    wp1Rotate = 0.;  
    wp2Rotate = 0.;  
    wp3Rotate = 0.;  
    wp4Rotate = 0.;  
    System.out.println("  --Swerve Math Reset");
  }  

  // First Method of SwerveMath that returns the vector (speed, position) for each wheel as a list
  public ArrayList<Double> getVectors(double fwdIn, double strIn, double rcwIn, boolean centricIn, double gyroIn, boolean reverseEnIn, boolean snakeModeIn, String hiLoIn){
    hiLo = hiLoIn;
    fwd = fwdIn;
    str = strIn;
    // if(hiLo) rcw = rcwIn * RobotMap.ROTATE_SCALE; else rcw = rcwIn; // Wheel speeds are scaled down after math block, if in low speed rotate is slow enough
    if(hiLo == "high") rcw = rcwIn * RobotMap.ROTATE_SCALE; else rcw = rcwIn; // Wheel speeds are scaled down after math block, if in low speed rotate is slow enough
    centric = centricIn;
    gyro = gyroIn * toRad;
    reverseEn = reverseEnIn;
    snakeMode = snakeModeIn;

    // ********Modify the Joystick Inputs for Snake Mode*********
    if (snakeMode) {
      rcw = str;
      str = 0.;
    }
    
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
    Double A = str - rcw * halfLengthNorm; 
    Double B = str + rcw * halfLengthNorm;
    Double C = fwd - rcw * halfWidthNorm;
    Double D = fwd + rcw * halfWidthNorm;

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

    // modify wheel speeds from <=1 ---> pulses per 100 ms
    ws1 *= pp100msec; 
    ws2 *= pp100msec;
    ws3 *= pp100msec;
    ws4 *= pp100msec;

    // modify wheel speeds to scale value <=1
    // if (hiLo) {
    //   ws1 *= RobotMap.HIGH_SPEED_SCALE; 
    //   ws2 *= RobotMap.HIGH_SPEED_SCALE;
    //   ws3 *= RobotMap.HIGH_SPEED_SCALE;
    //   ws4 *= RobotMap.HIGH_SPEED_SCALE;
    // }
    // else {
    //   ws1 *= RobotMap.LOW_SPEED_SCALE; 
    //   ws2 *= RobotMap.LOW_SPEED_SCALE;
    //   ws3 *= RobotMap.LOW_SPEED_SCALE;
    //   ws4 *= RobotMap.LOW_SPEED_SCALE; 
    // }

    switch(hiLo){
      case "high":
        ws1 *= RobotMap.HIGH_SPEED_SCALE; 
        ws2 *= RobotMap.HIGH_SPEED_SCALE;
        ws3 *= RobotMap.HIGH_SPEED_SCALE;
        ws4 *= RobotMap.HIGH_SPEED_SCALE;
      break;

      case "medium":
        ws1 *= RobotMap.MED_SPEED_SCALE; 
        ws2 *= RobotMap.MED_SPEED_SCALE;
        ws3 *= RobotMap.MED_SPEED_SCALE;
        ws4 *= RobotMap.MED_SPEED_SCALE; 
      break;

      case "low":
        ws1 *= RobotMap.LOW_SPEED_SCALE; 
        ws2 *= RobotMap.LOW_SPEED_SCALE;
        ws3 *= RobotMap.LOW_SPEED_SCALE;
        ws4 *= RobotMap.LOW_SPEED_SCALE; 
      break;
    }

    // Calculate the wheel angle for each wheel in radians: +/-pi referenced to Y axis 
    // Then convert to wheel position, with toPos, to absolute pulses for encoder over +/-pi range
    Double wp1 = Math.atan2(B, C) * toPos; // Wheel Angle 1 = front right
    Double wp2 = Math.atan2(B, D) * toPos; // Wheel Angle 2 = front left
    Double wp3 = Math.atan2(A, D) * toPos; // Wheel Angle 3 = rear left
    Double wp4 = Math.atan2(A, C) * toPos; // Wheel Angle 4 = rear right

    // ********Continous Steering Rotation and Shortest Path Steering with Reversing************
    // With the output of the math block above there is a discontinuity from 180 degrees to 181 degrees.
  
    // This code block tests each wheel to see if it needs to move > +/- 90 degrees.  If so, it will add/sub
    // 180 degrees to provide the supplemental angle and reverse the drive wheel.  If the wheel needs to 
    // traverse the discontinuity at +/- 180 it will also track the 360 degree rotation necessary.  
    // If the steering motion is greater than +/- 270 degrees then no reversing will make sense and the 
    // code will add/sub 360 degrees and track the 360 degree rotation through the discontinuity, again
    // for smooth wheel travel and shortest path steering.
   
    // First, if any wheel was reversed by the last SwerveMath iteration then reverse it for this iteration
    // so the new comparison is done with the supplemental angles. Nested "if" statements calculate 
    // the supplemental angle.
    
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
    }

    // Now look to see if the wheel needs to move more than 90 degrees or more than 270 degrees
    // Do this for each wheel position
    switch (pulseDiff(wp1, wp1Current, wp1Rotate)){
      case "< -90":  //If true then reverse the wheel
        ws1 *= -1.;
        wp1IsReversed = !wp1IsReversed;
        if (wp1 >= 0) {
          wp1 -= oneEighty;
          wp1Rotate += threeSixty;  //Rotate throught the discontinuity at +/- 180
        } 
        else {
          wp1 += oneEighty; 
        }
      break;

      case "> 90":  //If true then reverse the wheel
        ws1 *= -1.;
        wp1IsReversed = !wp1IsReversed;
        if (wp1 >= 0) {
          wp1 -= oneEighty;
        } 
        else {
          wp1 += oneEighty; 
          wp1Rotate -= threeSixty;  //Rotate throught the discontinuity at +/- 180
        }
      break;
      
      case "< -270":  //If true rotate through the discontinuity at +/- 180
        wp1Rotate += threeSixty; 
      break;
      
      case "> 270":  //If true rotate through the discontinuity at +/- 180
        wp1Rotate -= threeSixty;
      break;
    }

    switch (pulseDiff(wp2, wp2Current, wp2Rotate)){
      case "< -90":  
        ws2 *= -1.;
        wp2IsReversed = !wp2IsReversed;
        if (wp2 >= 0) {
          wp2 -= oneEighty;
          wp2Rotate += threeSixty; 
        } 
        else {
          wp2 += oneEighty; 
        }
      break;

      case "> 90": 
        ws2 *= -1.;
        wp2IsReversed = !wp2IsReversed;
        if (wp2 >= 0) {
          wp2 -= oneEighty;
        } 
        else {
          wp2 += oneEighty; 
          wp2Rotate -= threeSixty;  
        }
      break;

      case "< -270":
        wp2Rotate += threeSixty; 
      break;

      case "> 270":
        wp2Rotate -= threeSixty;
      break;
    }

    switch (pulseDiff(wp3, wp3Current, wp3Rotate)){
      case "< -90": 
        ws3 *= -1.;
        wp3IsReversed = !wp3IsReversed;
        if (wp3 >= 0) {
          wp3 -= oneEighty;
          wp3Rotate += threeSixty;  
        } 
        else {
          wp3 += oneEighty; 
        }
      break;

      case "> 90":  
        ws3 *= -1.;
        wp3IsReversed = !wp3IsReversed;
        if (wp3 >= 0) {
          wp3 -= oneEighty;
        } 
        else {
          wp3 += oneEighty; 
          wp3Rotate -= threeSixty;  
        }
      break;

      case "< -270":
        wp3Rotate += threeSixty; 
      break;

      case "> 270":
        wp3Rotate -= threeSixty;
      break;
    }

    switch (pulseDiff(wp4, wp4Current, wp4Rotate)){
      case "< -90":  
        ws4 *= -1.;
        wp4IsReversed = !wp4IsReversed;
        if (wp4 >= 0) {
          wp4 -= oneEighty;
          wp4Rotate += threeSixty;  
        } 
        else {
          wp4 += oneEighty; 
        }
      break;

      case "> 90":
        ws4 *= -1.;
        wp4IsReversed = !wp4IsReversed;
        if (wp4 >= 0) {
          wp4 -= oneEighty;
        } 
        else {
          wp4 += oneEighty; 
          wp4Rotate -= threeSixty;
        }
      break;

      case "< -270":
        wp4Rotate += threeSixty; 
      break;

      case "> 270":
        wp4Rotate -= threeSixty;
      break;
    }

    // Add the rotation pulse accumulator to the wheel position to normalize to the encoder position
    wp1 += wp1Rotate;
    wp2 += wp2Rotate;
    wp3 += wp3Rotate;
    wp4 += wp4Rotate;
 
    //Finally, save modified "wp" position from this SwerveMath iteration to be used in next iteration//
    wp1Current = wp1;
    wp2Current = wp2;
    wp3Current = wp3;
    wp4Current = wp4;

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

  // This is a private method to simplify angle finding and make the code more readable
  private String pulseDiff(Double wp, Double wpCurrent, Double wpRotate){
    Double diff = wp - (wpCurrent - wpRotate); //Find the rotation requested, backing out the rotate pulse accumulator
    String angle = "";

    if (diff < -twoSeventy) angle = "< -270"; else if ((diff < -ninety) && reverseEn) angle = "< -90";
    if (diff > twoSeventy) angle = "> 270"; else if ((diff > ninety) && reverseEn) angle = "> 90";

    return angle;
  }
}

