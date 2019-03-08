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
import java.util.Arrays;

public class SwerveDrive {
    private SwerveModule frontRight;
    private SwerveModule frontLeft;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;
    
    private SwerveMath m_swerveMath;
    
    private ArrayList<Double> swerveVectors = new ArrayList<Double>(8);
    private ArrayList<Double> driveDistanceVectors = new ArrayList<Double>(Arrays.asList(0.,0.,0.,0.));
    
    public SwerveDrive(SwerveModule frontRightWheel, SwerveModule frontLeftWheel, SwerveModule rearLeftWheel, SwerveModule rearRightWheel){
        frontRight = frontRightWheel;
        frontLeft = frontLeftWheel;
        rearLeft = rearLeftWheel;
        rearRight = rearRightWheel;
        
        m_swerveMath = new SwerveMath();
    }
    
    public void initMotors(){
        frontRight.initSteerMotor();
        frontLeft.initSteerMotor();
        rearLeft.initSteerMotor();
        rearRight.initSteerMotor();
        
        frontRight.initDriveMotor();
        frontLeft.initDriveMotor();
        rearLeft.initDriveMotor();
        rearRight.initDriveMotor();
    }
    
    public void reset(){
        frontRight.resetSteerEncoder();
        frontLeft.resetSteerEncoder();
        rearLeft.resetSteerEncoder();
        rearRight.resetSteerEncoder();
        m_swerveMath.reset();
    }

    private ArrayList<Double> getDriveMotorPositions() {
        driveDistanceVectors.set(0, (double)frontRight.getDrivePosition());
        driveDistanceVectors.set(1, (double)frontLeft.getDrivePosition());
        driveDistanceVectors.set(2, (double)rearLeft.getDrivePosition());
        driveDistanceVectors.set(3, (double)rearRight.getDrivePosition());
        return driveDistanceVectors;
    }
    
    public void setMotorsForDistance (double fwd, boolean centric, double gyro, boolean reverseEn, boolean snakeMode, double distance){
        driveDistanceVectors = getDriveMotorPositions();

        swerveVectors = m_swerveMath.getVectors(fwd, 0., 0., centric, gyro, reverseEn, snakeMode);
        
        frontRight.setWheelPosition(driveDistanceVectors.get(0)+distance);
        frontLeft.setWheelPosition(driveDistanceVectors.get(1)+distance);
        rearLeft.setWheelPosition(driveDistanceVectors.get(2)+distance);
        rearRight.setWheelPosition(driveDistanceVectors.get(3)+distance);
        
        frontRight.setSteerPosition(swerveVectors.get(1));
        frontLeft.setSteerPosition(swerveVectors.get(3));
        rearLeft.setSteerPosition(swerveVectors.get(5));
        rearRight.setSteerPosition(swerveVectors.get(7));
    
    }
    
    public void setMotors  (double fwd, double str, double rcw, boolean centric, double gyro, boolean reverseEn, boolean snakeMode){
        
        swerveVectors = m_swerveMath.getVectors(fwd, str, rcw, centric, gyro, reverseEn, snakeMode);
        
        // double temp = frontRight.getVelocity();
        // double temp2 = swerveVectors.get(0);
        
        frontRight.setVelocity(swerveVectors.get(0));
        frontLeft.setVelocity(swerveVectors.get(2));
        rearLeft.setVelocity(swerveVectors.get(4));
        rearRight.setVelocity(swerveVectors.get(6));
        
        // System.out.println("Current Velocity: "+temp);
        // System.out.println("Set Velocity: "+temp2);
        
        // double temp = frontRight.getPosition();
        // double temp2 = swerveVectors.get(1);
        
        frontRight.setSteerPosition(swerveVectors.get(1));
        frontLeft.setSteerPosition(swerveVectors.get(3));
        rearLeft.setSteerPosition(swerveVectors.get(5));
        rearRight.setSteerPosition(swerveVectors.get(7));
        
        // System.out.println("Current Position: "+temp);
        // System.out.println("Set position: "+temp2);
    }
    
    public void setCoast(){
        frontRight.setCoast();
        frontLeft.setCoast();
        rearLeft.setCoast();
        rearRight.setCoast();
    }
    
    public void setBrake(){
        frontRight.setBrake();
        frontLeft.setBrake();
        rearLeft.setBrake();
        rearRight.setBrake();
    }
    
    public void stopDriveMotors(){
        setBrake();
        frontRight.setVelocity(0.);
        frontLeft.setVelocity(0.);
        rearLeft.setVelocity(0.);
        rearRight.setVelocity(0.);
        setCoast();
    }
    
    public void emergencyStopMotors(){
        frontRight.stop();
        frontLeft.stop();
        rearLeft.stop();
        rearRight.stop();
    }
    
    //Calls calibration method in SwerveDrive, enable CheckPhase boolean in RobotMap, if a mechanical module is swapped
    public void calSteerMotors(boolean checkPhase){
        if(checkPhase){
            detectSteerEncoderPhase();
            
            System.out.println("**Calibrating Steering");
            frontRight.rotateSteerForCal();
            frontLeft.rotateSteerForCal();
            rearLeft.rotateSteerForCal();
            rearRight.rotateSteerForCal();
            
            detectDriveEncoderPhase();
        }
        else{
            System.out.println("**Calibrating Steering");
            frontRight.rotateSteerForCal();
            frontLeft.rotateSteerForCal();
            rearLeft.rotateSteerForCal();
            rearRight.rotateSteerForCal();
        }
    }
    
    //Check for encoders correctly phased.  Otherwise closed-loop controls for driving won't work.
    //As such this all has to run open-loop and the motors have to be spinning to detect the encoder phase.
    //Drive the bot forward, detect phase, 
    private void detectSteerEncoderPhase(){
        frontRight.setSteerSpeed(.1);
        frontLeft.setSteerSpeed(.1);
        rearLeft.setSteerSpeed(.1);
        rearRight.setSteerSpeed(.1);
        delay(400);
        boolean frPhase = frontRight.detectSteerMotorPhase();
        boolean flPhase = frontLeft.detectSteerMotorPhase();
        boolean rlPhase = rearLeft.detectSteerMotorPhase();
        boolean rrPhase = rearRight.detectSteerMotorPhase();
        delay(100);
        frontRight.setSteerSpeed(-.1);
        frontLeft.setSteerSpeed(-.1);
        rearLeft.setSteerSpeed(-.1);
        rearRight.setSteerSpeed(-.1);
        delay(500);
        frontRight.setSteerSpeed(0);
        frontLeft.setSteerSpeed(0);
        rearLeft.setSteerSpeed(0);
        rearRight.setSteerSpeed(0);
        System.out.println("**Steer Motor Encoder Phase Checking"+
        "\n  --Front Right Steer Encoder Out-of-phase = "+frPhase+
        "\n  --Front Left  Steer Encoder Out-of-phase = "+flPhase+
        "\n  --Rear Left   Steer Encoder Out-of-phase = "+rlPhase+
        "\n  --Rear Right  Steer Encoder Out-of-phase = "+rrPhase);
        // System.out.println("**Steer Motor Encoder Phase Checking"+
        //                  "\n  --Rear Right  Steer Encoder Out-of-phase = "+frPhase);
    }
    
    private void detectDriveEncoderPhase(){
        frontRight.setDriveSpeed(1.);
        frontLeft.setDriveSpeed(1.);
        rearLeft.setDriveSpeed(1.);
        rearRight.setDriveSpeed(1.);
        delay(400);
        boolean frPhase = frontRight.detectDriveMotorPhase();
        boolean flPhase = frontLeft.detectDriveMotorPhase();
        boolean rlPhase = rearLeft.detectDriveMotorPhase();
        boolean rrPhase = rearRight.detectDriveMotorPhase();
        delay(100);
        frontRight.setDriveSpeed(-1.);
        frontLeft.setDriveSpeed(-1.);
        rearLeft.setDriveSpeed(-1.);
        rearRight.setDriveSpeed(-1.);
        delay(500);
        frontRight.setDriveSpeed(0);
        frontLeft.setDriveSpeed(0);
        rearLeft.setDriveSpeed(0);
        rearRight.setDriveSpeed(0);
        System.out.println("**Drive Motor Encoder Phase Checking"+
        "\n  --Front Right Drive Encoder Out-of-phase = "+frPhase+
        "\n  --Front Left  Drive Encoder Out-of-phase = "+flPhase+
        "\n  --Rear Left   Drive Encoder Out-of-phase = "+rlPhase+
        "\n  --Rear Right  Drive Encoder Out-of-phase = "+rrPhase);
        // System.out.println("**Drive Motor Encoder Phase Checking"+
        //                 "\n  --Front Right Drive Encoder Out-of-phase = "+frPhase);
    }
    
    private void delay(int msec){
        try{
            Thread.sleep(msec);
        }
        catch (Exception e){
            System.out.println("Error in Waitloop");
        }
    }
}
