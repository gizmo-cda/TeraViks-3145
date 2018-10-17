/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3145.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
	
//	private static final String kDefaultAuto = "Default";
//	private static final String kCustomAuto1 = "Go Straight";
//	private static final String kCustomAuto2 = "Angle right";
//	private static final String kCustomAuto3 = "Angle left";
//	private static final String kCustomAuto4 = "Go straight and place block";
	//private String m_autoSelected;
	//private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//PID talon for arm tilt
	static TalonSRX _armTiltMotor = new TalonSRX(8);
	
	//Controllers
	Joystick _armJoystick = new Joystick(2);
	Joystick _driveJoystick = new Joystick(0);
	
	//Pneumatics
	Compressor _compressor = new Compressor();
	Solenoid _shifterSolenoid = new Solenoid(40, 1);
	
	//timer for autonomous
	Timer _timer = new Timer();
	
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
	WPI_TalonSRX _grabMotor1 = new WPI_TalonSRX(5);
	WPI_TalonSRX _grabMotor2 = new WPI_TalonSRX(6);
	
	//Hardware for grabberator
	Solenoid _grabberator = new Solenoid(40, 4);
//	WPI_TalonSRX _grabMotor = new WPI_TalonSRX(5);
	
	// Scissor lift solenoid stuff
	Solenoid _scissorLiftSolUp = new Solenoid(40, 2);
	Solenoid _scissorLiftSolDown = new Solenoid(40, 0);
	
	// Climb 
	Solenoid _climbSol = new Solenoid(40, 3);

	// Limit switch that is FALSE when the scissor lift is fully retracted
	DigitalInput _armNotRetractedSwitch = new DigitalInput(1);
	
	// True until we hit the top
	DigitalInput _homeSwitch = new DigitalInput(0);
	
	// This is for choosing whether we want to start auto on the right or left side of the alliance zone
	// (still going to place cube OUR switch regardless of where we choose to start from on the field)
	DigitalInput _leftStartingPos = new DigitalInput(2);
	DigitalInput _rightStartingPos = new DigitalInput(3);
	
	double targetPos;

	private boolean hasBeenHomed;

	private String switchPosition;

	private int _upperLimit;

	private int _lowerLimit;
	
	private String startingPos;
		
	//Autonomous
	// Command autonomousCommand;
	// SendableChooser autoChooser;
		
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// stream camera to dashboard
		CameraServer.getInstance().startAutomaticCapture();
		
		//PID INITIALIZATION STUFF FOR THE SCISOR LIFT TIT MOTOR
		
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
		_armTiltMotor.configPeakOutputForward(.5, Constants.kTimeoutMs);
		_armTiltMotor.configPeakOutputReverse(-.5, Constants.kTimeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		_armTiltMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		_armTiltMotor.config_kF(Constants.kSlotIdx, .1, Constants.kTimeoutMs);
		_armTiltMotor.config_kP(Constants.kSlotIdx, 50, Constants.kTimeoutMs);
		_armTiltMotor.config_kI(Constants.kSlotIdx, 0, Constants.kTimeoutMs);
		_armTiltMotor.config_kD(Constants.kSlotIdx, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		_armTiltMotor.configMotionCruiseVelocity(300, Constants.kTimeoutMs);
		_armTiltMotor.configMotionAcceleration(300, Constants.kTimeoutMs);
		
		//initial limits for tilting
		_upperLimit = 0;
		_lowerLimit = 6600;	
	}
	
	@Override
	public void autonomousInit() {
		//home tilt motor by slowly incrementing the position until a switch is activated
		hasBeenHomed = false;
		for (double i = _armTiltMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx); _homeSwitch.get() && ! hasBeenHomed; i -= 0.001) {
			_armTiltMotor.set(ControlMode.MotionMagic, i);
		}	
		hasBeenHomed = true;
		//System.out.println("NOT MOVING");
		/* zero the sensor */
		_armTiltMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		_armTiltMotor.set(ControlMode.MotionMagic, 10);
		
		//Start timer and extend timeout period of the drive motors for autonomous
		_timer.start();
		_drive.setExpiration(.01);
		
		if (DriverStation.getInstance().isFMSAttached()) {
			String gameData = DriverStation.getInstance().getGameSpecificMessage();
			if(gameData.length() > 0) {
				if(gameData.charAt(0) == 'L') {
					switchPosition = "left";
				} else {
					switchPosition = "right";
					}
	        	}
		} else {
			// For testing, if there is no FMS (you are not connected to an official FIRST field)
			// The robot will assume in autonomous mode that our alliance's switch is on the right
			switchPosition = "right";
		}
		
		
		//Set a starting position based off of switch input
		//this is used for autonomous mode
		if (_leftStartingPos.get() && _rightStartingPos.get()) {
			startingPos = "middle";
		} else if (_leftStartingPos.get()) {
			startingPos = "left";
		} else {
			startingPos = "right";
		}
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//System.out.println(startingPos);
		switchPosition = "left";
		_grabberator.set(false);
		//Auto functions for just driving straight forward
		//We only use these when the switch and robot are on the same side
		if(startingPos == "right" & switchPosition == "right") {
			if(_timer.get() < 4) {
				_drive.arcadeDrive(-.8, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			} else {
				_drive.arcadeDrive(0, 0);				
				_grabberator.set(true);
			}
			
		} 
		else if(startingPos == "left" & switchPosition == "left") {
			if(_timer.get() < 2.3) {
				_drive.arcadeDrive(-.85, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			} else if (_timer.get() < 4) {
				_drive.arcadeDrive(0, 0);
				_grabberator.set(true);
			} else if (_timer.get() < 5.5) {
				_drive.arcadeDrive(0.8, 0);
			} else if (_timer.get() < 6.5) {
				_drive.arcadeDrive(0, -.68);
			} else if (_timer.get() < 7.3) {
				_drive.arcadeDrive(0, 0);
			} else if (_timer.get() < 8) {
				_drive.arcadeDrive(-.85, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 6600);
			} else if (_timer.get() < 8.2) {
				_drive.arcadeDrive(0, 0);
			} else if (_timer.get() < 9) {
				_drive.arcadeDrive(0, 0.66);
			} else if (_timer.get() < 9.1) {
				_drive.arcadeDrive(0, 0);
			} else if (_timer.get() < 9.8) {
				_drive.arcadeDrive(-0.85, 0);
				_grabberator.set(true);
				_grabMotor1.set(-1);
				_grabMotor2.set(1);
			} else if (_timer.get() < 9.9) {
				_drive.arcadeDrive(0, 0);
				_grabberator.set(false);
			} else if (_timer.get() < 10.5) {
				_drive.arcadeDrive(0.6, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			} else if (_timer.get() < 10.6) {
				_drive.arcadeDrive(0, 0);
				_grabMotor1.set(0);
				_grabMotor2.set(0);
			} // TODO: Turn left and drive forward then shoot cube in switch and victory!
		}
		
		/*if(startingPos == "right" & switchPosition == "right") {
			if(_timer.get() < 5.2) {
				_drive.arcadeDrive(-.8, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			} else if (_timer.get() < 6) {
				_drive.arcadeDrive(0, .65);;
			} else {
				_drive.arcadeDrive(0, 0);
				_grabberator.set(true);
			}
		}*/
		
		//Auto function for switches on opposite sides of the robot
		if(startingPos == "right" & switchPosition == "left") {
			if(_timer.get() < 1) {
				_drive.arcadeDrive(-.7, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			}else if(_timer.get() < 2) {
				_drive.arcadeDrive(0, .65);
			}else if(_timer.get() < 6.5) {
				_drive.arcadeDrive( -.66, 0);
			} else if(_timer.get() < 7.35) {
				_drive.arcadeDrive(0, -.78);
			} else if(_timer.get() < 10.7) {
				_drive.arcadeDrive(-.8, 0);
			}else {
				_drive.stopMotor();
				_grabberator.set(true);
			}
			
//			if(startingPos == "right" & switchPosition == "left") {
//				if(_timer.get() < 1) {
//					_drive.arcadeDrive(-.8, 0);
//					_armTiltMotor.set(ControlMode.MotionMagic, 3000);
//				}else if(_timer.get() < 2) {
//					_drive.arcadeDrive(0, .65);
			// Increase This (6.5 sec)
//				}else if(_timer.get() < 6.5) {
//					_drive.arcadeDrive( -.66, 0);
//				} else if(_timer.get() < 7.35) {
//					_drive.arcadeDrive(0, -.78);
			// Decrease this (10.7)
//				} else if(_timer.get() < 10.7) {
//					_drive.arcadeDrive(-.8, 0);
//				} else if(_timer.get() < 12.0) {
//			_drive.arcadeDrive(0, -.78);
//				} else {
//					_drive.stopMotor();
//					_grabberator.set(true);
//				}
			
		} else if(startingPos == "left" & switchPosition == "right") {
			if(_timer.get() < 1) {
				_drive.arcadeDrive(-.7, 0);
				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			}else if(_timer.get() < 2) {
				_drive.arcadeDrive(0, -.65);
			}else if(_timer.get() < 6.5) {
				_drive.arcadeDrive( -.66, 0);
			} else if(_timer.get() < 7.35) {
				_drive.arcadeDrive(0, .78);
			} else if(_timer.get() < 10.7) {
				_drive.arcadeDrive(-.8, 0);
			}else {
				_drive.stopMotor();
				_grabberator.set(true);
			}
		}
		
		
		//auto functions for starting in the center

		
		if(startingPos == "middle") {
			if(_timer.get() < 1) {
				_drive.arcadeDrive(-1, 0);
			}
		}
		
//		if(startingPos == "middle" & switchPosition == "right") {
//			if(_timer.get() < 1) {
//				_drive.arcadeDrive(-.8, 0);
//				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
//			}else if(_timer.get() < 2) {
//				_drive.arcadeDrive(0, -.65);
//			}else if(_timer.get() < 4.5) {
//				_drive.arcadeDrive( -.6, 0);
//			} else if(_timer.get() < 5.35) {
//				_drive.arcadeDrive(0, .78);
//			} else if(_timer.get() < 8.5) {
//				_drive.arcadeDrive(-.8, 0);
//			}else {
//				_drive.stopMotor();
//				_grabberator.set(true);
//			}
//		} else if(startingPos == "middle" & switchPosition == "left") {
//			if(_timer.get() < 1) {
//				_drive.arcadeDrive(-.8, 0);
//				_armTiltMotor.set(ControlMode.MotionMagic, 3000);
//			}else if(_timer.get() < 2) {
//				_drive.arcadeDrive(0, .66);
//			}else if(_timer.get() < 4.5) {
//				_drive.arcadeDrive( -.6, 0);
//			} else if(_timer.get() < 5.35) {
//				_drive.arcadeDrive(0, -.78);
//			} else if(_timer.get() < 8.5) {
//				_drive.arcadeDrive(-.8, 0);
//			}else {
//				_drive.stopMotor();
//				_grabberator.set(true);
//			}
//		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	
	@Override
	public void teleopPeriodic() {
		
		// System.out.println("left:" + _leftStartingPos.get());
		// System.out.println("right:" + _rightStartingPos.get());
		
		//encoder position and stick axis
		double encoderPos = _armTiltMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		double leftYstick = _armJoystick.getRawAxis(1);
				
		//Controls for tilting the scissor lift
		//Here we increment a target position which we then command the
		//tilt motor to drive to using motion magic
		
		//only increment if we are out of the dead zone
		if(leftYstick > .05) {
			 
			if(targetPos > _upperLimit + 20) {
				targetPos += -50 * leftYstick;
			} else if(targetPos >= _upperLimit && targetPos <= _upperLimit + 20) {
				targetPos += - 25;
			}
			
		} else if(leftYstick < -.05) {
			if(_lowerLimit - 20 > targetPos) {
				targetPos += -50 * leftYstick;
			} else if(_lowerLimit >= targetPos && targetPos >= _lowerLimit - 20) {
				targetPos += 25;
			}
		}

		//Operates the arm tilt motor with PID
		//Drive the motor to the target position
		_armTiltMotor.set(ControlMode.MotionMagic, targetPos);
		//  System.out.println(encoderPos);
		
		//Some debug stuff
//		System.out.println("Target Position: " + targetPos);		
//		System.out.println("Current Encoder Position: " + encoderPos);
//		System.out.println("Velocity: "+_armTiltMotor.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
		//System.out.println("Axis: " + leftYstick);
				
		//Run the drive train based on the controller input
		_drive.arcadeDrive(_driveJoystick.getY(), -1 * _driveJoystick.getZ());
		
		//Shift into high and low gear
		if (_driveJoystick.getRawButton(7)) {
			_shifterSolenoid.set(false);
		} else if (_driveJoystick.getRawButton(8)) {
			_shifterSolenoid.set(true);
		}
		
		//Open and close grabberator claw
		if (_armJoystick.getRawButton(1)){
			_grabberator.set(true);
		} else {
			_grabberator.set(false);
		}
		
		if (_armJoystick.getRawButton(11)) {
			_armTiltMotor.set(ControlMode.MotionMagic, -800);
		}
//		
		//Drive grabberator motors in and out
		if (_armJoystick.getRawButton(7)) {
			_grabMotor1.set(-1);
			_grabMotor2.set(1);
			//_grabMotor.set(-1);
		} else if (_armJoystick.getRawButton(6)) {
			_grabMotor1.set(1);
			_grabMotor2.set(-1);
//			_grabMotor.set(1);
		} else {
			_grabMotor1.set(0);
			_grabMotor2.set(0);
//			_grabMotor.set(0);
		}
		
		// Scissor Lift Control
		//we set a new lower limit when extended to ensure we stay within the frame perimiter
		
		//retracting
		if (_armJoystick.getRawButton(2)) {	
			_scissorLiftSolDown.set(true);
		} else {
			_scissorLiftSolDown.set(false);
		}
		
		//extending and setting limits
		if (_armJoystick.getRawButton(3)) {
			if (encoderPos < 1000) {
				_lowerLimit = 1000;
				_scissorLiftSolUp.set(true);
			} 
		} else {
			_scissorLiftSolUp.set(false);
		}
		
		
		//reset lower limit when retracted
		if (! _armNotRetractedSwitch.get()) {
			_lowerLimit = 6600;
		}
		
		
		//climbing controls
		if (_armJoystick.getRawButton(8)) {
			_climbSol.set(true);
			_scissorLiftSolDown.set(true);
		}
		
		if (_armJoystick.getRawButton(9)) {
			_climbSol.set(false);
			_scissorLiftSolDown.set(false);
		}
	}
				
	/**
	 * This function is called periodically during test mode.
	 */
	
	@Override
	public void testPeriodic() {
		if (_leftStartingPos.get() && _rightStartingPos.get()) {
			startingPos = "middle";
		} else if (_leftStartingPos.get()) {
			startingPos = "left";
		} else {
			startingPos = "right";
		}
	}
}
