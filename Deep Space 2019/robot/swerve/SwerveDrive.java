/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.swerve;

//import frc.robot.swerve.SwerveModule;
import java.util.ArrayList;

/**
 * Add your docs here.
 */
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

    public void initSteerMotors(){
        frontRight.initSteerMotor();
        //frontLeft.initSteerMotor();
        //rearLeft.initSteerMotor();
        //rearRight.initSteerMotor();
    }

    public void initDriveMotors(){
        frontRight.initDriveMotor();
        //frontLeft.initDriveMotor();
        //rearLeft.initDriveMotor();
        //rearRight.initDriveMotor();
    }
    public void setMotors(double fwd, double str, double rcw, boolean centric, double gyro){
        swerveVectors = m_swerveMath.getVectors(fwd, str, rcw, centric, gyro);
       
        frontRight.setSpeed(swerveVectors.get(0));
        // frontLeft.setSpeed(swerveVectors.get(2));
        // rearLeft.setSpeed(swerveVectors.get(4));
        // rearRight.setSpeed(swerveVectors.get(6));

        // double temp = frontRight.getPosition();
        // double temp2 = swerveVectors.get(1);

        double temp = frontRight.getSpeed();
        double temp2 = swerveVectors.get(0);

        frontRight.setPosition(swerveVectors.get(1));

        // System.out.println("Current Position: "+temp);
        // System.out.println("Set position: "+temp2);

        // System.out.println("Current Velocity: "+temp);
        // System.out.println("Set Velocity: "+temp2);

        // frontLeft.setAngle(swerveVectors.get(3));
        // rearLeft.setAngle(swerveVectors.get(5));
        // rearRight.setAngle(swerveVectors.get(7));
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
