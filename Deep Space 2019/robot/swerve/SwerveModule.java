/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.swerve;


import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
    private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;

    public SwerveModule(String wheelName, WPI_TalonSRX wheelDriveMotor, WPI_TalonSRX wheelSteerMotor){
        name = wheelName;
        driveMotor = wheelDriveMotor;
        steerMotor = wheelSteerMotor;
    }

    public void setSpeed(double wheelSpeed){
        driveMotor.set(ControlMode.PercentOutput, wheelSpeed);
    }

    public void setPosition(double wheelPosition){
        steerMotor.set(ControlMode.Position,  wheelPosition);
    }

    public int getPosition(){
        return steerMotor.getSelectedSensorPosition();
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

    public void initSteerMotor()
    {
        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
        //steerMotor.configSelectedFeedbackCoefficient(1/STEER_GEAR_RATIO, 0, TIMEOUT);
    	
    	steerMotor.selectProfileSlot(0, 0); //slot #, PID #
    	
        steerMotor.setSelectedSensorPosition(0);
        //steerMotor.configPulseWidthPeriod_EdgesPerRot(4096*STEER_GEAR_RATIO, TIMEOUT);
    	steerMotor.configPeakOutputForward(1, TIMEOUT);
    	steerMotor.configPeakOutputReverse(-1, TIMEOUT);
    	
    	steerMotor.configNominalOutputForward(0, TIMEOUT);
    	steerMotor.configNominalOutputReverse(0, TIMEOUT);
    	
    	steerMotor.setNeutralMode(NeutralMode.Brake);
    	
    	steerMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
    	
    	steerMotor.config_kP(0, 10, TIMEOUT);
    	steerMotor.config_kI(0, 0, TIMEOUT);
    	steerMotor.config_kD(0, 0, TIMEOUT);
    	steerMotor.config_kF(0, 0, TIMEOUT);
    }
}
