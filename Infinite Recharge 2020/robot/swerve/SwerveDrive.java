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

import frc.robot.RobotMap;
import java.util.ArrayList;
import java.util.Arrays;
import edu.wpi.first.wpilibj.DigitalInput;

public class SwerveDrive {
    private SwerveModule frontRight;
    private SwerveModule frontLeft;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;
    
    private SwerveMath m_swerveMath;
    
    // Digital Inputs for Callibration
    private DigitalInput calSensorFrontRight = new DigitalInput(RobotMap.FRONT_RIGHT_STEER_CAL);
    private DigitalInput calSensorFrontLeft = new DigitalInput(RobotMap.FRONT_LEFT_STEER_CAL);
    private DigitalInput calSensorRearLeft = new DigitalInput(RobotMap.REAR_LEFT_STEER_CAL);
    private DigitalInput calSensorRearRight = new DigitalInput(RobotMap.REAR_RIGHT_STEER_CAL);

    private ArrayList<Double> swerveVectors = new ArrayList<Double>(8);
    private ArrayList<Double> driveDistanceVectors = new ArrayList<Double>(Arrays.asList(0.,0.,0.,0.));
    private int velocity = 0;
    
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
    
    //Only used for testing, DON'T USE THIS METHOD
    public void reset(){
        frontRight.resetSteerEncoder();
        frontLeft.resetSteerEncoder();
        rearLeft.resetSteerEncoder();
        rearRight.resetSteerEncoder();
        m_swerveMath.reset();
    }

    public boolean isDriveTrainStopped() {
        velocity = frontRight.getVelocity() + frontLeft.getVelocity() + rearLeft.getVelocity() + rearRight.getVelocity();
        if (velocity < 1000) return true; else return false;
    }

    private ArrayList<Double> getDriveMotorPositions() {
        driveDistanceVectors.set(0, (double)frontRight.getDrivePosition());
        driveDistanceVectors.set(1, (double)frontLeft.getDrivePosition());
        driveDistanceVectors.set(2, (double)rearLeft.getDrivePosition());
        driveDistanceVectors.set(3, (double)rearRight.getDrivePosition());
        return driveDistanceVectors;
    }
    
    public void setMotorsForDistance (double fwd, double str, boolean centric, double gyro, boolean reverseEn, boolean snakeMode, String hiLo, double distance){
        driveDistanceVectors = getDriveMotorPositions();

        swerveVectors = m_swerveMath.getVectors(fwd, str, 0., centric, gyro, reverseEn, snakeMode, hiLo);
        
        frontRight.setWheelPosition(driveDistanceVectors.get(0)+distance);
        frontLeft.setWheelPosition(driveDistanceVectors.get(1)+distance);
        rearLeft.setWheelPosition(driveDistanceVectors.get(2)+distance);
        rearRight.setWheelPosition(driveDistanceVectors.get(3)+distance);
        
        frontRight.setSteerPosition(swerveVectors.get(1));
        frontLeft.setSteerPosition(swerveVectors.get(3));
        rearLeft.setSteerPosition(swerveVectors.get(5));
        rearRight.setSteerPosition(swerveVectors.get(7));
    }
    
    public void setMotors (double fwd, double str, double rcw, boolean centric, double gyro, boolean reverseEn, boolean snakeMode, String hiLo){
        
        swerveVectors = m_swerveMath.getVectors(fwd, str, rcw, centric, gyro, reverseEn, snakeMode, hiLo);
        
        frontRight.setVelocity(swerveVectors.get(0));
        frontLeft.setVelocity(swerveVectors.get(2));
        rearLeft.setVelocity(swerveVectors.get(4));
        rearRight.setVelocity(swerveVectors.get(6));
         
        frontRight.setSteerPosition(swerveVectors.get(1));
        frontLeft.setSteerPosition(swerveVectors.get(3));
        rearLeft.setSteerPosition(swerveVectors.get(5));
        rearRight.setSteerPosition(swerveVectors.get(7));
    }
    
    public void setMaxDriveMotorPower(double power) {
        frontRight.setMaxDrivePower(power);
        frontLeft.setMaxDrivePower(power);
        rearLeft.setMaxDrivePower(power);
        rearRight.setMaxDrivePower(power);
    }

    public void stopDriveMotors(){
        frontRight.setVelocity(0.);
        frontLeft.setVelocity(0.);
        rearLeft.setVelocity(0.);
        rearRight.setVelocity(0.);
    }
    
    public void emergencyStopMotors(){
        frontRight.stop();
        frontLeft.stop();
        rearLeft.stop();
        rearRight.stop();
    }

    // Steering Motor Calibration Method to find the Index Sensor and set the wheel straight forward
    public void calSteerMotors(){
        System.out.println("**Calibrating Steering");
        
        //Init local variables
        boolean rotate = true;
        boolean rotateFrontRight = true;
        boolean rotateFrontLeft = true;
        boolean rotateRearLeft = true;
        boolean rotateRearRight = true;
     
        // Start all four steer motors
        frontRight.setSteerSpeed(.2);
        frontLeft.setSteerSpeed(.2);
        rearLeft.setSteerSpeed(.2);
        rearRight.setSteerSpeed(.2);   
 
        //While the motors are running check to see when the encoder has been reset and stop the motor
        while (rotate) {
            
            if (calSensorFrontRight.get() && rotateFrontRight) {
                frontRight.setSteerSpeed(0.);
                rotateFrontRight = false;
            }
            
            if (calSensorFrontLeft.get() && rotateFrontLeft) {
                frontLeft.setSteerSpeed(0.);
                rotateFrontLeft = false;
            }
            
            if (calSensorRearLeft.get() && rotateRearLeft) {
                rearLeft.setSteerSpeed(0.);
                rotateRearLeft = false;
            }
            
            if (calSensorRearRight.get() && rotateRearRight) {
                rearRight.setSteerSpeed(0.);
                rotateRearRight = false;      
            }

            if (!rotateFrontRight && !rotateFrontLeft && !rotateRearLeft && !rotateRearRight) {
                rotate = false;
            }
        }

        //Re Init local variables for second loop
        rotate = true;
        rotateFrontRight = true;
        rotateFrontLeft = true;
        rotateRearLeft = true;
        rotateRearRight = true;

        delay(100);

        // Start all four steer motors again now that we know where they are
        frontRight.setSteerSpeed(.2);
        frontLeft.setSteerSpeed(.2);
        rearLeft.setSteerSpeed(.2);
        rearRight.setSteerSpeed(.2);   

        // Delay to insure we are off the magnet for the cal sensor
        delay(100);

        while (rotate) {
            if (calSensorFrontRight.get() && rotateFrontRight) {
                frontRight.steerMotor.setSelectedSensorPosition(0);
                rotateFrontRight = false;
            }
            
            if (calSensorFrontLeft.get() && rotateFrontLeft) {
                frontLeft.steerMotor.setSelectedSensorPosition(0);
                rotateFrontLeft = false;
            }
            
            if (calSensorRearLeft.get() && rotateRearLeft) {
                rearLeft.steerMotor.setSelectedSensorPosition(0);     
                rotateRearLeft = false;
            }
            
            if (calSensorRearRight.get() && rotateRearRight) {
                rearRight.steerMotor.setSelectedSensorPosition(0);
                rotateRearRight = false;      
            }

            if (!rotateFrontRight && !rotateFrontLeft && !rotateRearLeft && !rotateRearRight) {
                rotate = false;
            }
        }

        // Allow the last motor to rotate past the new zero location
        delay(100);

        // Stop all four motors
        frontRight.setSteerSpeed(0.);
        frontLeft.setSteerSpeed(0.);
        rearLeft.setSteerSpeed(0.);
        rearRight.setSteerSpeed(0.);  
       
        // Allow the steering to settle after being stopped
        delay(100);

        // Drive all four steer motors to the Zero Position
        frontRight.setSteerPosition(0.);
        frontLeft.setSteerPosition(0.);
        rearLeft.setSteerPosition(0.);
        rearRight.setSteerPosition(0.);

        // Allow the steering to settle at zero
        delay(100);
      
        // frontRight.adjustSteeringCalOffsets();
        // frontLeft.adjustSteeringCalOffsets();
        // rearLeft.adjustSteeringCalOffsets();
        // rearRight.adjustSteeringCalOffsets();

        System.out.println("    -Applying Offsets");   

         //Set the position off the wheel for the calibrated index correcting for index dection error
         frontRight.setSteerPosition(RobotMap.FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FR);
         frontLeft.setSteerPosition(RobotMap.FRONT_LEFT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FL);
         rearLeft.setSteerPosition(RobotMap.REAR_LEFT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RL);
         rearRight.setSteerPosition(RobotMap.REAR_RIGHT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RR);
        
         //Delay for the time it will take the wheel to move to the set position, this assumes ~ 2 sec per wheel revolution
         delay(300);
         
         //Now disable the motor before resetting the encoder to the new calibrated reference
         frontRight.steerMotor.setSelectedSensorPosition(0);
         frontLeft.steerMotor.setSelectedSensorPosition(0);
         rearLeft.steerMotor.setSelectedSensorPosition(0);
         rearRight.steerMotor.setSelectedSensorPosition(0);

         //Delay to make sure it had time to set the reference to 0 before the next command is called
         delay(100);
 
         //Now set the wheel to position 0 to hold the new calibrated position
         frontRight.setSteerPosition(0.);
         frontLeft.setSteerPosition(0.);
         rearLeft.setSteerPosition(0.);
         rearRight.setSteerPosition(0.);

         System.out.println("    -Calibration Completed");
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
