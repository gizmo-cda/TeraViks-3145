/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.swerve;


import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This class builds a module that consists of the drive motor and steer motor
 * for each wheel.  It provides methods for setting and getting various motor
 * parameters.
 */
public class SwerveModule {

    private String name;
    private WPI_TalonSRX driveMotor;
    private WPI_TalonSRX steerMotor;

    //private double driveMotorRatio = RobotMap.FINAL_DRIVE_WHEEL_GEAR_RATIO;
    private double steerMotorRatio = RobotMap.FINAL_STEER_WHEEL_GEAR_RATIO;

    public SwerveModule(String wheelName, WPI_TalonSRX wheelDriveMotor, WPI_TalonSRX wheelSteerMotor){
        name = wheelName;
        driveMotor = wheelDriveMotor;
        steerMotor = wheelSteerMotor;
    }

    public void setSpeed(double wheelSpeed){
        driveMotor.set(ControlMode.PercentOutput, wheelSpeed);
    }

    public void setAngle(double wheelAngle){
        steerMotor.set(ControlMode.Position,  wheelAngle * steerMotorRatio);
    }
    
    public void stop(){
        driveMotor.stopMotor();
        steerMotor.stopMotor();
	}
    
    public WPI_TalonSRX getDriveMotor(){
		return driveMotor;
	}
	
	public WPI_TalonSRX getSteerMotor(){
		return steerMotor;
    }

    public String getName(){
        return name;
    }

}
