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
    private double frontRightPosition;
    private double frontLeftPosition;
    private double rearLeftPosition;
    private double rearRightPosition;

    private SwerveModule frontRight;
    private SwerveModule frontLeft;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;

    private SwerveMath m_swerveMath;
    private SwerveUtils m_swerveUtils;

    private ArrayList<Double> swerveVectors = new ArrayList<Double>(8);

    public SwerveDrive(SwerveModule frontRightWheel, SwerveModule frontLeftWheel, SwerveModule rearLeftWheel, SwerveModule rearRightWheel){
        frontRight = frontRightWheel;
        frontLeft = frontLeftWheel;
        rearLeft = rearLeftWheel;
        rearRight = rearRightWheel;

        m_swerveMath = new SwerveMath();
        m_swerveUtils = new SwerveUtils();
    }

    public void initSteerMotors(){
        frontRight.initSteerMotor();
        //frontLeft.initSteerMotor();
        //rearLeft.initSteerMotor();
        //rearRight.initSteerMotor();
    }

    public void setMotors(double fwd, double str, double rcw, boolean centric, double gyro){
        swerveVectors = m_swerveMath.getVectors(fwd, str, rcw, centric, gyro);
        // System.out.println(swerveVectors);
        frontRightPosition = m_swerveUtils.normEncoder(frontRight.getPosition());
        //frontLeftPosition = frontLeft.getPosition();
        //rearLeftPosition = rearLeft.getPosition();
        //rearRightPosition = rearRight.getPosition();
        double temp = frontRight.getPosition();
        frontRight.setSpeed(swerveVectors.get(0));
        double temp2 = swerveVectors.get(1)+frontRightPosition;
        frontRight.setPosition(temp2);

        System.out.println("Get raw: "+temp);
        System.out.println("Set swerve vectors: "+temp2);
        // frontLeft.setSpeed(swerveVectors.get(2));
        // frontLeft.setAngle(swerveVectors.get(3));
        // rearLeft.setSpeed(swerveVectors.get(4));
        // rearLeft.setAngle(swerveVectors.get(5));
        // rearRight.setSpeed(swerveVectors.get(6));
        // rearRight.setAngle(swerveVectors.get(7));
    }

    public void stopMotors(){
        frontRight.stop();
        // frontLeft.stop();
        // rearLeft.stop();
        // rearRight.stop();
    }
}
