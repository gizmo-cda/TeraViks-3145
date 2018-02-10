/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3145.robot;

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
	TalonSRX _talon = new TalonSRX(0);
	Joystick _armJoystick = new Joystick(2);
	Joystick _driveJoystick = new Joystick(0);
	
	Compressor _compressor = new Compressor();
	Solenoid _solenoid = new Solenoid(40, 1);
	
	// Drive Train
	WPI_TalonSRX _left1 = new WPI_TalonSRX(3);
	WPI_TalonSRX _left2 = new WPI_TalonSRX(4);
	WPI_TalonSRX _right1 = new WPI_TalonSRX(1);
	WPI_TalonSRX _right2 = new WPI_TalonSRX(2);

	SpeedControllerGroup _left = new SpeedControllerGroup(_left1, _left2);
	SpeedControllerGroup _right = new SpeedControllerGroup(_right1, _right2);
	
	DifferentialDrive _drive = new DifferentialDrive(_left, _right); 
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		_solenoid.set(false);
		
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		_talon.setSensorPhase(true);
		_talon.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		_talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		_talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		_talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		_talon.config_kF(0, 0.2, Constants.kTimeoutMs);
		_talon.config_kP(0, 1, Constants.kTimeoutMs);
		_talon.config_kI(0, 0, Constants.kTimeoutMs);
		_talon.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		_talon.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
		_talon.configMotionAcceleration(6000, Constants.kTimeoutMs);
		/* zero the sensor */
		_talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
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
	
//		_talon.set(ControlMode.PercentOutput, 1.0);
		double leftYstick = _armJoystick.getRawAxis(1);
		//double motorOutput = _talon.getMotorOutputPercent();
	
		double targetPos = leftYstick * 980;
		// _talon.set(ControlMode.MotionMagic, targetPos);
		
		_drive.arcadeDrive(_driveJoystick.getY(), -1 * _driveJoystick.getZ());
		
		if (_driveJoystick.getRawButton(7)) {
			_solenoid.set(true);
		} else if (_driveJoystick.getRawButton(8)) {
			_solenoid.set(false);
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
