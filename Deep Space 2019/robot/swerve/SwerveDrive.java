/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/* Robot Swerve Module Assignment
*                        
*              Front/Forward       
*                   ^             
*                   |             
*                   |             
*  front left |-----------| front right     
*             |           |       
*             |           |  l    
*             |           |  e    
*             |           |  n    
*             |           |  g    
*             |           |  t    
*             |           |  h    
*             |           |       
*  rear left  |-----------| rear right     
*                 width           
*
***********************
 * This class pulls together four swerve modules into one object so the 
 * Drivetrain subsystem can make simple calls to set speed, and position.
 * This class aggregates the four modules and their motors.  This class
 * also uses the swerve math object to call the vectors method and get the
 * new speed and position settings.
 */

package frc.robot.swerve;

import java.util.ArrayList;

public class SwerveDrive {
    private SwerveModule frontRight;
    private SwerveModule frontLeft;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;

    private SwerveMath m_swerveMath;

    private ArrayList<Double> swerveVectors = new ArrayList<Double>(8);

    public SwerveDrive(SwerveModule frontRightWheel, SwerveModule frontLeftWheel, SwerveModule rearLeftWheel, SwerveModule rearRightWheel){
        frontRight = frontRightWheel;
        frontLeft = frontLeftWheel;
        rearLeft = rearLeftWheel;
        rearRight = rearRightWheel;

        m_swerveMath = new SwerveMath();
    }

    public void initMotors(){
        frontRight.initSteerMotor();
        //frontLeft.initSteerMotor();
        //rearLeft.initSteerMotor();
        //rearRight.initSteerMotor();

        frontRight.initDriveMotor();
        //frontLeft.initDriveMotor();
        //rearLeft.initDriveMotor();
        //rearRight.initDriveMotor();
    }

    public boolean calSteerMotors(){
        boolean clear = frontRight.rotateSteerForCal();
        // frontLeft.rotateSteerForCal();
        // rearLeft.rotateSteerForCal();
        // rearRight.rotateSteerForCal();
        return clear;
    }

    public void setMotors  (double fwd, double str, double rcw, boolean centric, double gyro, boolean reverseEn){

        swerveVectors = m_swerveMath.getVectors(fwd, str, rcw, centric, gyro, reverseEn);

        // double temp = frontRight.getVelocity();
        // double temp2 = swerveVectors.get(0);

        frontRight.setVelocity(swerveVectors.get(0));
        // frontLeft.setVelocity(swerveVectors.get(2));
        // rearLeft.setVelocity(swerveVectors.get(4));
        // rearRight.setVelocity(swerveVectors.get(6));

        // System.out.println("Current Velocity: "+temp);
        // System.out.println("Set Velocity: "+temp2);

        // double temp = frontRight.getPosition();
        // double temp2 = swerveVectors.get(1);
        
        frontRight.setPosition(swerveVectors.get(1));
        // frontLeft.setPosition(swerveVectors.get(3));
        // rearLeft.setPosition(swerveVectors.get(5));
        // rearRight.setPosition(swerveVectors.get(7));

        // System.out.println("Current Position: "+temp);
        // System.out.println("Set position: "+temp2);
    }

    public void setCoast(){
        frontRight.setCoast();
        //frontLeft.setCoast();
        //rearLeft.setCoast();
        //rearRight.setCoast();
    }

    public void setBrake(){
        frontRight.setBrake();
        //frontLeft.setBrake();
        //rearLeft.setBrake();
        //rearRight.setBrake();
    }

    public void stopMotors(){
        frontRight.stop();
        // frontLeft.stop();
        // rearLeft.stop();
        // rearRight.stop();
    }
}
