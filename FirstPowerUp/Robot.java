/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3145.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//PID talon for arm tilt
	TalonSRX _armTiltMotor = new TalonSRX(8);
	
	//Controllers
	Joystick _armJoystick = new Joystick(2);
	Joystick _driveJoystick = new Joystick(0);
	
	//Pneumatics
	Compressor _compressor = new Compressor();
	Solenoid _shifterSolenoid = new Solenoid(40, 1);
	
	// Drive Train motors
	WPI_TalonSRX _left1 = new WPI_TalonSRX(3);
	WPI_TalonSRX _left2 = new WPI_TalonSRX(4);
	WPI_TalonSRX _right1 = new WPI_TalonSRX(1);
	WPI_TalonSRX _right2 = new WPI_TalonSRX(2);
	
	//Drive train speed controllers
	SpeedControllerGroup _left = new SpeedControllerGroup(_left1, _left2);
	SpeedControllerGroup _right = new SpeedControllerGroup(_right1, _right2);
	
	//Drive train object using speed controllers
	DifferentialDrive _drive = new DifferentialDrive(_left, _right); 
	
	//Hardware for grabberator
	Solenoid _grabberator = new Solenoid(40, 4);
	WPI_TalonSRX _grabMotor1 = new WPI_TalonSRX(5);
	WPI_TalonSRX _grabMotor2 = new WPI_TalonSRX(6);
	
	// Scissor lift solenoid stuff
	Solenoid _scissorLiftSolUp = new Solenoid(40, 2);
	Solenoid _scissorLiftSolUpDown = new Solenoid(40, 0);
	
	// Climb 
	Solenoid _climbSol = new Solenoid(40, 3);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// We know that this works
		CameraServer.getInstance().startAutomaticCapture();
		
		/* zero the sensor */
		_armTiltMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

		
		/* first choose the sensor */
		_armTiltMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
		Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		_armTiltMotor.setSensorPhase(true);
		_armTiltMotor.setInverted(false);
		/* Set relevant frame periods to be at least as fast as periodic rate*/
		_armTiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
		Constants.kTimeoutMs);
		_armTiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10,
		Constants.kTimeoutMs);
		/* set the peak and nominal outputs */
		_armTiltMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		_armTiltMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_armTiltMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		_armTiltMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		_armTiltMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		_armTiltMotor.config_kF(0, 0, Constants.kTimeoutMs);
		_armTiltMotor.config_kP(0, 2, Constants.kTimeoutMs);
		_armTiltMotor.config_kI(0, 0, Constants.kTimeoutMs);
		_armTiltMotor.config_kD(0, 4, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		_armTiltMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
		_armTiltMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);

		
//		// _armTiltMotor.setSelectedSensorPosition(261, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
//		
////		new Thread(() -> {
////            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
////            camera.setResolution(320, 240);
////            
////            CvSink cvSink = CameraServer.getInstance().getVideo();
////            CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 320, 240);
////            
////            Mat source = new Mat();
////            Mat output = new Mat();
////            
////            while(!Thread.interrupted()) {
////                cvSink.grabFrame(source);
////                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
////                outputStream.putFrame(output);
////            }
////        }).start();
//		
//		//Shift into low gear for safety
//		_shifterSolenoid.set(false);
//		
//		m_chooser.addDefault("Default Auto", kDefaultAuto);
//		m_chooser.addObject("My Auto", kCustomAuto);
//		SmartDashboard.putData("Auto choices", m_chooser);
//		
//		_armTiltMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
//		_armTiltMotor.setSensorPhase(true);
//		_armTiltMotor.setInverted(false);
//
//		/* Set relevant frame periods to be at least as fast as periodic rate */
//		_armTiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
//		_armTiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
//
//		/* set the peak and nominal outputs */
//		_armTiltMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
//		_armTiltMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
//		_armTiltMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
//		_armTiltMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
//
//		/* set closed loop gains in slot0 - see documentation */
//		_armTiltMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
//		_armTiltMotor.config_kF(0, 0.2, Constants.kTimeoutMs);
//		_armTiltMotor.config_kP(0, 1, Constants.kTimeoutMs);
//		_armTiltMotor.config_kI(0, 0, Constants.kTimeoutMs);
//		_armTiltMotor.config_kD(0, 0, Constants.kTimeoutMs);
//		/* set acceleration and vcruise velocity - see documentation */
//		_armTiltMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
//		_armTiltMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);
//		
//		_armTiltMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}

	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	
	@Override
	public void teleopPeriodic() {
		
		//Operates the arm tilt motor with PID
		//Get stick position
		double leftYstick = _armJoystick.getRawAxis(1);
		//Set target position
		double targetPos = leftYstick * 1300;
		// Motor will only turn in the "negative" direction (when joystick is forward, not backward)
		if (targetPos >= 0) {
			targetPos = 0;
		} else {
			_armTiltMotor.set(ControlMode.MotionMagic, targetPos);
		}
		
		//Drive the motor to the target position
		_armTiltMotor.set(ControlMode.MotionMagic, targetPos);

		System.out.println("Target Position: " + targetPos);
		System.out.println("Current Encoder Position: " + _armTiltMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx));
		
		
		//Run the drive train based on the controller input
		_drive.arcadeDrive(_driveJoystick.getY(), -1 * _driveJoystick.getZ());
		
		
		//Shift into high and low gear
		if (_driveJoystick.getRawButton(7)) {
			_shifterSolenoid.set(true);
		} else if (_driveJoystick.getRawButton(8)) {
			_shifterSolenoid.set(false);
		}
		
		
		//Open and close grabberator claw
		if (_armJoystick.getRawButton(1)){
			_grabberator.set(true);
		} else {
			_grabberator.set(false);
		}
		
		//Drive grabberator motors in and out
		if (_armJoystick.getRawButton(6)) {
			_grabMotor1.set(-1);
			_grabMotor2.set(1);
		} else if (_armJoystick.getRawButton(7)) {
			_grabMotor1.set(1);
			_grabMotor2.set(-1);
		} else {
			_grabMotor1.set(0);
			_grabMotor2.set(0);
		}
		// Scissor Lift Control
		if (_armJoystick.getRawButton(2)) {	
			_scissorLiftSolUpDown.set(true);
		} else {
			_scissorLiftSolUpDown.set(false);
		}
		if (_armJoystick.getRawButton(3)) {
			_scissorLiftSolUp.set(true);
		} else {
			_scissorLiftSolUp.set(false);
		}
	}
				
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
