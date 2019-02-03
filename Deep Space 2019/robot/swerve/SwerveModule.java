/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.swerve;


import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.Faults;
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
    private Faults driveMotorFaults;
    private Faults steerMotorFaults;

    private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;


    public SwerveModule(String wheelName, WPI_TalonSRX wheelDriveMotor, WPI_TalonSRX wheelSteerMotor){
        name = wheelName;
        driveMotor = wheelDriveMotor;
        steerMotor = wheelSteerMotor;
        steerMotorFaults = new Faults();
        driveMotorFaults = new Faults();
    }

    public void setSpeed(double wheelSpeed){
        driveMotor.set(ControlMode.Velocity, wheelSpeed);
    }

    public void setPosition(double wheelPosition){
        steerMotor.set(ControlMode.Position, wheelPosition);
    }

    public int getSpeed(){
        return driveMotor.getSelectedSensorVelocity();
    }

    public int getPosition(){
        return steerMotor.getSelectedSensorPosition();
    }
    
    public void setBrake(){
        driveMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoast(){
        driveMotor.setNeutralMode(NeutralMode.Coast);
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

    public void initSteerMotor(){
        steerMotor.configFactoryDefault();

        steerMotor.setInverted(false);

        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    	steerMotor.selectProfileSlot(0, 0); //slot #, PID #

        steerMotor.setSensorPhase(false);

        steerMotor.configPeakOutputForward(.3, TIMEOUT);
    	steerMotor.configPeakOutputReverse(-.3, TIMEOUT);
    	
    	steerMotor.configNominalOutputForward(0, TIMEOUT);
    	steerMotor.configNominalOutputReverse(0, TIMEOUT);
    	
    	steerMotor.setNeutralMode(NeutralMode.Brake);
    	
        steerMotor.configAllowableClosedloopError(0, 100, TIMEOUT);
    	
    	steerMotor.config_kP(0, .15, TIMEOUT);
    	steerMotor.config_kI(0, 0, TIMEOUT);
    	steerMotor.config_kD(0, 1, TIMEOUT);
        steerMotor.config_kF(0, 0, TIMEOUT);

        steerMotor.setSelectedSensorPosition(0);

        // steerMotor.set(ControlMode.PercentOutput, 0.1);
        // steerMotor.set(ControlMode.PercentOutput, 0.0);

        // System.out.println("Steer Sensor Out Of Phase - "+steerMotorFaults.SensorOutOfPhase);

        // steerMotor.getFaults(steerMotorFaults);

        // if (steerMotorFaults.SensorOutOfPhase){
        //     steerMotor.setSensorPhase(true);
        // }
        // System.out.println("Steer Sensor Out Of Phase - "+steerMotorFaults.SensorOutOfPhase);

        // steerMotor.setSelectedSensorPosition(0);
        
        System.out.println("Steer Motor Initialized - "+steerMotor.getName());
    }

    public void initDriveMotor(){
        driveMotor.configFactoryDefault();

        driveMotor.setInverted(false);

        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    	driveMotor.selectProfileSlot(0, 0); //slot #, PID #

        driveMotor.configPeakOutputForward(1, TIMEOUT);
        driveMotor.configPeakOutputReverse(-1, TIMEOUT);
        
    	driveMotor.configNominalOutputForward(0, TIMEOUT);
        driveMotor.configNominalOutputReverse(0, TIMEOUT);
        
        driveMotor.setNeutralMode(NeutralMode.Coast);
        
        driveMotor.configAllowableClosedloopError(0, 4, TIMEOUT);
        
    	driveMotor.config_kP(0, .5, TIMEOUT);
    	driveMotor.config_kI(0, 0.0001, TIMEOUT);
    	driveMotor.config_kD(0, 1, TIMEOUT);
        driveMotor.config_kF(0, 1.624, TIMEOUT);

        driveMotor.setSelectedSensorPosition(0);
        
        //driveMotor.set(ControlMode.Position, 240);
        // driveMotor.set(ControlMode)

        driveMotor.setSensorPhase(true);
        

        // PROCEDURE TO REVERSE SENSOR PHASE, NEEDS WORK
        // try {
        //     System.out.println("start Try");
        //     driveMotor.set(ControlMode.PercentOutput, 0.5);
        //     Thread.sleep(2000l);
        //     driveMotor.getFaults(driveMotorFaults);
        //     System.out.println("Drive Sensor Out Of Phase - "+driveMotorFaults.SensorOutOfPhase);
            
        //     System.out.println("it worked");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        // driveMotor.set(ControlMode.PercentOutput, 0.0);

        // if (driveMotorFaults.SensorOutOfPhase){
        //     driveMotor.setSensorPhase(true);
        //     System.out.println("Sensor phase flipped");
        // }

        // System.out.println("Drive Sensor Out Of Phase - "+driveMotorFaults.SensorOutOfPhase);

    }

}
