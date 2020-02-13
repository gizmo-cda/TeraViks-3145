/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* This class provides methods for setting, getting and init of various motor
* parameters for the drive and steering motors within a Swerve Module object.
*/

package frc.robot.swerve;

import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.Faults;

import java.lang.reflect.Array;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;

public class SwerveModule {
    
    private String name;
    private WPI_TalonFX driveMotor;
    public WPI_TalonFX steerMotor;
    private Faults driveFaults = new Faults();
    private Faults steerFaults = new Faults();

    // Digital Inputs for Callibration
    private DigitalInput calFrontRight = new DigitalInput(RobotMap.FRONT_RIGHT_STEER_CAL);
    private DigitalInput calFrontLeft = new DigitalInput(RobotMap.FRONT_LEFT_STEER_CAL);
    private DigitalInput calRearLeft = new DigitalInput(RobotMap.REAR_LEFT_STEER_CAL);
    private DigitalInput calRearRight = new DigitalInput(RobotMap.REAR_RIGHT_STEER_CAL);

    private static boolean [] calSteerSensors = {false, false, false, false};
    private static int sensorID;

    private int TIMEOUT = RobotMap.TalonFX_TIMEOUT;
    
    public SwerveModule(String wheelName, WPI_TalonFX wheelDriveMotor, WPI_TalonFX wheelSteerMotor){
        name = wheelName;
        driveMotor = wheelDriveMotor;
        steerMotor = wheelSteerMotor;
    }
    
    //Open-Loop Percentage Output Setting -1 to 1
    public void setDriveSpeed(double driveSpeed){
        driveMotor.set(ControlMode.PercentOutput, driveSpeed);
    }
    
    //Open-Loop Percentage Output Setting -1 to 1
    public void setSteerSpeed(double steerSpeed){
        steerMotor.set(ControlMode.PercentOutput, steerSpeed);
    }
    
    //Closed-Loop Velocity Target Setting (+/- speed in pulses per 100 msec)
    public void setVelocity(double wheelVelocity){
        driveMotor.set(ControlMode.Velocity, wheelVelocity);
    }
    
    //Closed-Loop Position Target Setting (+/- pulses per +/-180 degrees)
    public void setSteerPosition(double wheelPosition){
        steerMotor.set(ControlMode.MotionMagic, wheelPosition);
    }

    public void setWheelPosition(double position){
         driveMotor.set(ControlMode.Position, position);
    }
    
    public int getVelocity(){
        return driveMotor.getSelectedSensorVelocity();
    }
    
    public int getSteerPosition(){
        return steerMotor.getSelectedSensorPosition();
    }

    public int getDrivePosition(){
        return driveMotor.getSelectedSensorPosition();
    }

    public boolean[] getCalSensorState(){
        Array.setBoolean(calSteerSensors, 0, calFrontRight.get());
        Array.setBoolean(calSteerSensors, 1, calFrontLeft.get());
        Array.setBoolean(calSteerSensors, 2, calRearLeft.get());
        Array.setBoolean(calSteerSensors, 3, calRearRight.get());
        return calSteerSensors;
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
    
    public WPI_TalonFX getDriveMotor(){
		return driveMotor;
	}
	
	public WPI_TalonFX getSteerMotor(){
		return steerMotor;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean detectDriveMotorPhase(){
        driveMotor.getFaults(driveFaults);
        return driveFaults.SensorOutOfPhase;
    }
    
    public boolean detectSteerMotorPhase(){
        steerMotor.getFaults(steerFaults);
        return steerFaults.SensorOutOfPhase;
    }
    
    public void resetSteerEncoder(){
        steerMotor.setSelectedSensorPosition(0);
    }

    // Steering Motor Calibration Method to find the Index Sensor and set the wheel straight forward
    public void rotateSteerForCal(){
        //Init local variables
        boolean clear = false;
        int currentPos = 0;
        int newPos = 0;
        
        System.out.println("  --Calibrating - "+name);
        
        //Enable encoder clearing so when the index sensor goes active the reset executes.
        // steerMotor.configClearPositionOnQuadIdx(true, TIMEOUT);
        
        //Set current position to a known value and start the motor open-loop, but slow
        steerMotor.setSelectedSensorPosition(currentPos);
        // steerMotor.set(ControlMode.PercentOutput, .3);
        
        // delay(40);
        
        //While the motor is running check to see when the encoder has been reset
        while (!clear) {
            // if (newPos < currentPos){
            //     steerMotor.set(ControlMode.PercentOutput, 0.);
            //     clear = true;
            // }
            // else {
            //     currentPos = newPos;
            // }
            getCalSensorState();

            switch (name){
                case "FrontRightWheel":
                sensorID = 0;
                break;
                case "FrontLeftWheel":
                sensorID = 1;
                break;
                case "RearLeftWheel":
                sensorID = 2;
                break;
                case "RearRightWheel":
                sensorID = 3;
                break;
            }

            if (calSteerSensors[sensorID]) {
                steerMotor.setSelectedSensorPosition(0);
                clear = true;
            }
        }

            
            // delay(20);
            // newPos = steerMotor.getSelectedSensorPosition();
        
        //Give the motor extra time to stop
        delay(60);
        
        //Disabled index clearing and get the encoder position which will always be positive after stopping the open loop run
        // steerMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
        
        //Find the index offset in pulses for the wheel being calibrated
        double offset = 0.;
        
        switch (name){
            case "FrontRightWheel":
            offset = RobotMap.FRONT_RIGHT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FR;
            break;
            case "FrontLeftWheel":
            offset = RobotMap.FRONT_LEFT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_FL;
            break;
            case "RearLeftWheel":
            offset = RobotMap.REAR_LEFT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RL;
            break;
            case "RearRightWheel":
            offset = RobotMap.REAR_RIGHT_STEER_INDEX_OFFSET_PULSES + RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL_RR;
            break;
        }
        
        //Add small increment to offset to account for closed loop error
        //offset += RobotMap.CLOSED_LOOP_STEERING_ERROR_FOR_CAL;

        System.out.println("    -Offset            = "+(int) offset);

        //Set the position off the wheel for the calibrated index correcting for index dection error
        steerMotor.set(ControlMode.Position, offset);
        
        //Delay for the time it will take the wheel to move to the set position, this assumes ~ 2 sec per wheel revolution
        double positionDelay = offset / RobotMap.STEER_PPR * 2000.;
        delay((int) positionDelay);
        
        //Now disable the motor before resetting the encoder to the new calibrated reference
        steerMotor.set(ControlMode.PercentOutput, 0.);
        delay(40);

        //Check for the error in driving to the position and reset the encoder to the new 0 reference
        double error = (double) steerMotor.getSelectedSensorPosition();
        error = Math.abs(error) - Math.abs(offset);
        
        steerMotor.setSelectedSensorPosition(0);
        
        //Delay to make sure it had time to set the reference to 0 before the next command is called
        delay(40);

        //Now set the wheel to position 0 to hold the new calibrated position
        steerMotor.set(ControlMode.Position, 0.);
        
        System.out.println("    -Calibration Error = "+(int) error);
        System.out.println("    -Calibration Completed" + name);
    }

    private void delay(int msec){
        try{
            Thread.sleep(msec);
        }
        catch (Exception e){
            System.out.println("Error in Waitloop");
        }
    }
    
    //Talon configuration for the Steer Motor
    public void initSteerMotor(){
        steerMotor.configFactoryDefault();
        
        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT);
        steerMotor.selectProfileSlot(0, 0); //slot #, PID #

        steerMotor.setInverted(false);
        steerMotor.setNeutralMode(NeutralMode.Brake);

        steerMotor.setSelectedSensorPosition(0);
        steerMotor.configClearPositionOnQuadIdx(false, TIMEOUT);

        steerMotor.configMotionAcceleration(40960, TIMEOUT);  //400 Optical Encoder accel and velocity targets
        steerMotor.configMotionCruiseVelocity(20480, TIMEOUT);

        steerMotor.configPeakOutputForward(1., TIMEOUT);
        steerMotor.configPeakOutputReverse(-1., TIMEOUT);
        
        steerMotor.configNominalOutputForward(0, TIMEOUT);
        steerMotor.configNominalOutputReverse(0, TIMEOUT);
        
        steerMotor.configAllowableClosedloopError(0, 5, TIMEOUT);  //Error for 2048 CTRE Encoder

        steerMotor.config_IntegralZone(0, 100, TIMEOUT); //I-zone limits
        
        steerMotor.config_kP(0, .75, TIMEOUT);  
        steerMotor.config_kI(0, .001, TIMEOUT);
        steerMotor.config_kD(0, 1., TIMEOUT);
        steerMotor.config_kF(0, 0., TIMEOUT);
        
        System.out.println("  --Steer Motor Initialized - "+name);
    }
    
    //Talon configuration for the Drive Motor
    public void initDriveMotor(){
        driveMotor.configFactoryDefault();
        
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, TIMEOUT);
        driveMotor.selectProfileSlot(0, 0); //slot #, PID #

        //Invert the drive motors on one side because the calibration routine will rotate 
        //them after indexing an extra 180 degrees so the pullies face inward on the chassis
        //Also set Encoder Phase
        switch (name){
            case "FrontRightWheel":
            driveMotor.setInverted(RobotMap.FRONT_RIGHT_DRIVE_TalonFX_Invert);
            break;
            case "FrontLeftWheel":
            driveMotor.setInverted(RobotMap.FRONT_LEFT_DRIVE_TalonFX_Invert);
            break;
            case "RearLeftWheel":
            driveMotor.setInverted(RobotMap.REAR_LEFT_DRIVE_TalonFX_Invert);
            break;
            case "RearRightWheel":
            driveMotor.setInverted(RobotMap.REAR_RIGHT_DRIVE_TalonFX_Invert);
            break;
        }
        
        driveMotor.setNeutralMode(NeutralMode.Coast);
        driveMotor.configClosedloopRamp(.5, TIMEOUT);
        
        driveMotor.setSelectedSensorPosition(0);
        
        driveMotor.configPeakOutputForward(1, TIMEOUT);
        driveMotor.configPeakOutputReverse(-1, TIMEOUT);
        
        driveMotor.configNominalOutputForward(0, TIMEOUT);
        driveMotor.configNominalOutputReverse(0, TIMEOUT);
        
        driveMotor.configAllowableClosedloopError(0, 20, TIMEOUT);

        driveMotor.config_IntegralZone(0, 50, TIMEOUT);
        
        driveMotor.config_kP(0, .05, TIMEOUT);
        driveMotor.config_kI(0, 0.0001, TIMEOUT);
        driveMotor.config_kD(0, 2., TIMEOUT);
        driveMotor.config_kF(0, .05, TIMEOUT);
        
        System.out.println("  --Drive Motor Initialized - "+name);
    }
}