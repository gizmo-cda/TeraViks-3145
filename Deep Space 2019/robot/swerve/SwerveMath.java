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
*  This Class takes the desired forward, straif, and rotate clockwise inputs and 
*  calculates the wheel speed and wheel angle for each wheel to acheive the desired
*  motion.
*/

package frc.robot.swerve;

import frc.robot.RobotMap;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class SwerveMath {

  // Fields of SwerveMath, note they are local (ie private to SwerveMath) //
  // These are intitialized as constants to reduce the math cycles in the class' methods
  private static Double halfLength = RobotMap.WHEELBASE_LENGTH/2; // length of chassis divided by 2
  private static Double halfWidth = RobotMap.WHEELBASE_TRACK_WIDTH/2; // width of chassis divided by 2
  private static Double ss = RobotMap.WHEEL_SPEED_SCALE; // speed scale

  private static Double toDeg = 180./Math.PI;  //convert Radians to Degrees
  private static Double toRad = Math.PI/180.;  //convert Degrees to Radians

  private static Double fwd; //Forward, Y axis, -1 to 1 from Joystick//
  private static Double str; //Strafe, X axis, 1 to -1 from Joystick//
  private static Double rcw; //Rotate CW, Z axis, 1 to -1 from Joystick, refernced 1=180 CW -1=-180 CW//

  private static Double gyro;

  private static boolean centric;

  // Chose ArrayList type and defined the size because the size will never change and it's easy to modify members //
  private ArrayList<Double> wheelVectors = new ArrayList<Double>(Arrays.asList(0.,0.,0.,0.,0.,0.,0.,0.));
  
  // Null Constructor for SwerveMath for Object Instanitation //
  public SwerveMath(){
    }

  // First Method of SwerveMath that returns the vector (speed, angle) for each wheel as a list//
  public ArrayList<Double> getVectors(double fwdIn, double strIn, double rcwIn, boolean centricIn, double gyroIn){

    fwd = fwdIn;
    str = strIn;
    rcw = rcwIn;
    centric = centricIn;
    gyro = gyroIn * toRad;

    if (centric) {

      double y_f = fwd * Math.cos(gyro); // y component of field
      double y_s = str * Math.sin(gyro); // y component of strafe
      double x_f = fwd * Math.sin(gyro); // x component of field
      double x_s = str * Math.cos(gyro); // x component of strafe

      fwd = y_f + y_s;
      str = -x_f + x_s;

    }
    
    // Define common elements in wheel vector math //
    Double A = str - rcw * halfLength; 
    Double B = str + rcw * halfLength;
    Double C = fwd - rcw * halfWidth;
    Double D = fwd + rcw * halfWidth;

    // Calculate the WS for each wheel //
    Double ws1 = Math.sqrt(B*B + C*C);  // Wheel Speed 1 = front right
    Double ws2 = Math.sqrt(B*B + D*D);  // Wheel Speed 2 = front left
    Double ws3 = Math.sqrt(A*A + D*D);  // Wheel Speed 3 = rear left
    Double ws4 = Math.sqrt(A*A + C*C); // Wheel Speed 4 = rear right

    // System.out.println("Raw Wheel Speed 1: " + Double.toString(ws1));
    // System.out.println("Raw Wheel Speed 2: " + Double.toString(ws2));
    // System.out.println("Raw Wheel Speed 3: " + Double.toString(ws3));
    // System.out.println("Raw Wheel Speed 4: " + Double.toString(ws4));

    // Calculate the wheel angle for each wheel //
    Double wa1 = Math.atan2(B, C) * toDeg; // Wheel Angle 1 = front right
    Double wa2 = Math.atan2(B, D) * toDeg; // Wheel Angle 2 = front left
    Double wa3 = Math.atan2(A, D) * toDeg; // Wheel Angle 3 = rear left
    Double wa4 = Math.atan2(A, C) * toDeg; // Wheel Angle 4 = rear right

    // Wheel Speed Normalization, Can't have a wheel speed > 1 //
    Double max = ws1;
    if (ws2 > max) {
      max = ws2;
    }
    if (ws3 > max) {
      max = ws3;
    }
    if (ws4 > max) {
      max = ws4;
    }
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }

    // Scale the wheel speeds based on the constant Defined in RobotMap //
    ws1 *= ss;
    ws2 *= ss;
    ws3 *= ss;
    ws4 *= ss;

    // System.out.println("Wheel Speed 1: " + Double.toString(ws1));
    // System.out.println("Wheel Speed 2: " + Double.toString(ws2));
    // System.out.println("Wheel Speed 3: " + Double.toString(ws3));
    // System.out.println("Wheel Speed 4: " + Double.toString(ws4));

    // System.out.println("Wheel Angle 1: " + Double.toString(wa1));
    // System.out.println("Wheel Angle 2: " + Double.toString(wa2));
    // System.out.println("Wheel Angle 3: " + Double.toString(wa3));
    // System.out.println("Wheel Angle 4: " + Double.toString(wa4));

    // Set the vector values (speed, angle) for each wheel vector//
    /* Because this method will be running constantly speed is 
    *  important and since this is a very simple array the 
    *  SwerveDrive class can reference it quickly.  The indices
    *  are self documented here
    */ 
    wheelVectors.set(0,ws1);
    wheelVectors.set(1,wa1);
    wheelVectors.set(2,ws2);
    wheelVectors.set(3,wa2);
    wheelVectors.set(4,ws3);
    wheelVectors.set(5,wa3);
    wheelVectors.set(6,ws4);
    wheelVectors.set(7,wa4);

    // System.out.println(wheelVectors+":\n"+wheelVectors.size());

    return wheelVectors;
  }
}

