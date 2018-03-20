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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.ControlMode;
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
	TalonSRX _armTiltMotor = new TalonSRX(8);
	
	//Controllers
	Joystick _armJoystick = new Joystick(2);
	Joystick _driveJoystick = new Joystick(0);
	
	//Pneumatics
	Compressor _compressor = new Compressor();
	Solenoid _shifterSolenoid = new Solenoid(40, 1);
	
//	// Drive Train motors
	WPI_TalonSRX _left1 = new WPI_TalonSRX(3);
	WPI_TalonSRX _left2 = new WPI_TalonSRX(4);
	WPI_TalonSRX _right1 = new WPI_TalonSRX(1);
	WPI_TalonSRX _right2 = new WPI_TalonSRX(2);
	
	//Drive train speed controllers
	SpeedControllerGroup _left = new SpeedControllerGroup(_left1, _left2);
	SpeedControllerGroup _right = new SpeedControllerGroup(_right1, _right2);
	
	//Drive train object using speed controllers
	DifferentialDrive _drive = new DifferentialDrive(_left, _right); 
	
//	//Hardware for grabberator
//	Solenoid _grabberator = new Solenoid(40, 4);
//	WPI_TalonSRX _grabMotor1 = new WPI_TalonSRX(5);
//	WPI_TalonSRX _grabMotor2 = new WPI_TalonSRX(6);
	
	//Hardware for grabberator
	Solenoid _grabberator = new Solenoid(40, 4);
	WPI_TalonSRX _grabMotor = new WPI_TalonSRX(5);
	
	// Scissor lift solenoid stuff
	Solenoid _scissorLiftSolUp = new Solenoid(40, 2);
	Solenoid _scissorLiftSolDown = new Solenoid(40, 0);
	
	// Climb 
	Solenoid _climbSol = new Solenoid(40, 3);

	// Limit switch that is TRUE when the scissor lift is fully retracted
	DigitalInput _limitSwitch = new DigitalInput(1);
	
	// True until we hit the top
	DigitalInput _homeSwitch = new DigitalInput(0);
	
	double targetPos;

	private boolean hasBeenHomed;

	private int counter;

	private String switchPosition;
	
	//Autonomous
	// Command autonomousCommand;
	// SendableChooser autoChooser;
	
	public static void autoDriveAndPlace(String switchPosition, int counter, WPI_TalonSRX _grabMotor, Solenoid _grabberator, DifferentialDrive _drive) {
		int counterLimit = 21000;
		while (counter < counterLimit) {
			// <TODO: Figure out where switches are so we know (turning, distance, height, etc) />
		
			// Distance from alliance zone to switch is 14 ft (~427 cm)
			// Height is 1 ft 3 in (~ cm) above ground
			//Plates are 9 inches above ground
			_drive.arcadeDrive(-0.65, 0);
			//_armTiltMotor.set(ControlMode.MotionMagic, 3000);
			counter ++;
			System.out.println(counter);
		}
//		_grabMotor1.set(-1);
//		_grabMotor2.set(1);
		 while (counter < counterLimit + 10000) {
			 _grabMotor.set(-1);
			 _grabberator.set(true);
			 counter ++;
		 }
		 _grabMotor.set(0);
		 _grabberator.set(false);
	}
		
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// autoChooser = new SendableChooser();
		// autoChooser.addDefault("Default autonomous", new autoDefault());
		// autoChooser.addObject("Autonomous 1", new auto1());
		// SmartDashboard.putData("Auto mode chooser", autoChooser);
		
		// What scissor lift should be set to
		// targetPos = - 1850;
	
		// We know that this works
		CameraServer.getInstance().startAutomaticCapture();

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
		_armTiltMotor.config_kF(Constants.kSlotIdx, .1, Constants.kTimeoutMs);
		_armTiltMotor.config_kP(Constants.kSlotIdx, 50, Constants.kTimeoutMs);
		_armTiltMotor.config_kI(Constants.kSlotIdx, 0, Constants.kTimeoutMs);
		_armTiltMotor.config_kD(Constants.kSlotIdx, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		_armTiltMotor.configMotionCruiseVelocity(220, Constants.kTimeoutMs);
		_armTiltMotor.configMotionAcceleration(220, Constants.kTimeoutMs);
		
		
	}
	
	@Override
	public void autonomousInit() {
		//home tilt motor
		hasBeenHomed = false;
		//for (double i = _armTiltMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx); _homeSwitch.get() && ! hasBeenHomed; i -= 0.001) {
		//	_armTiltMotor.set(ControlMode.MotionMagic, i);
		//}	
		hasBeenHomed = true;
		System.out.println("NOT MOVING");
		/* zero the sensor */
		//_armTiltMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		//_armTiltMotor.set(ControlMode.MotionMagic, 10);
		
		//
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		counter = 0;
		if(gameData.length() > 0) {
			if(gameData.charAt(0) == 'L') {
				switchPosition = "left";
			} else {
				switchPosition = "right";
				}
        	}
		}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		// This is assuming we start at the right!!
		if (switchPosition != null) {
			if (switchPosition == "right") {
				autoDriveAndPlace(switchPosition, counter, _grabMotor, _grabberator, _drive);
			} else {
//				TURNS LEFT 90 DEGREES
				while (counter < 37500) {
					_drive.arcadeDrive(-0.8, .6);
					counter ++;
				}
				for (int i = 0; i < 18000; i ++) {
					// This should drive
					_drive.arcadeDrive(.65, 0);
				}
//				TURNS RIGHT 90 DEGREES
				while (counter < counter + 37500) {
					_drive.arcadeDrive(-0.8, -.6);
					counter ++;
				}
				counter = 0;
				autoDriveAndPlace(switchPosition, counter, _grabMotor, _grabberator, _drive);
			}
		}
		
		
		// Scheduler.getInstance().run();
//		int counter;
//		switch (m_autoSelected) {
//			case kCustomAuto1:
//				// Straight line until counter time ends
//				counter = 0;	
//				// _shifterSolenoid.set(true);
//				if (counter < 100) {
//					_drive.arcadeDrive(.65, 0);
//					counter++;
//					
//				} else {
//					_drive.arcadeDrive(0, 0);
//				}
//				break;
//			case kCustomAuto2:
//				// Angle right and go forward
//				counter = 0;
//				// _shifterSolenoid.set(false); See if we even feel comfortable shifting here because the turn and everything might be super unstable
//				if (counter < 100) {
//					_drive.arcadeDrive(.65, .25); // ? see about the scale of the  z axis when you are trying to turn
//					counter++;
//				} else {
//					_drive.arcadeDrive(0,  0);
//				}
//				break;
//			case kCustomAuto3:
//				// Angle left and go forward
//				counter = 0;
//				// _shifterSolenoid.set(false);
//				if (counter < 100) {
//					_drive.arcadeDrive(.65,  -.25); //Same thing as the turn right, test the magnitude of the z axis when you try to turn left
//					counter++;
//				} else {
//					_drive.arcadeDrive(0, 0);
//				}
//				break;
//			case kCustomAuto4:
//				// Go in a straight line. After you stop, place the block into the switch
//				counter = 0;
//				_armTiltMotor.set(ControlMode.MotionMagic, 0); // we can change this angle once we determine the best angle for "shooting" an placing the block
//				// _shifterSolenoid.set(true);
//				if (counter < 100) {
//					_drive.arcadeDrive(.65, 0);
//				} else {
//					_drive.arcadeDrive(0, 0);
//					for (int i = 0; i < 20; i++) {
//						// Functionality for placing block
//						_grabMotor.set(1);
//						// Competition
//						//_grabMotor1.set(-1);
//						//_grabMotor2.set(1);
//					}
//				}
//				_grabMotor.set(0);
//				//_grabMotor1.set(0);
//				//_grabMotor2.set(0);
//				break;
//			case kDefaultAuto:
//			default:
//				System.out.println("This is the default case; let's see what happens here, I guess. Pretend we drive straight?");
//				break;
		}
	//}

	/**
	 * This function is called periodically during operator control.
	 */
	
	@Override
	public void teleopPeriodic() {
		//Operates the arm tilt motor with PID
		//Get stick position
		
		double encoderPos = _armTiltMotor.getSelectedSensorPosition(Constants.kPIDLoopIdx);
		double leftYstick = _armJoystick.getRawAxis(1);
		
		//Controls for tilting the scissor lift
		if(leftYstick > .05) {
			 
			if(targetPos > 20) {
				targetPos += -50 * leftYstick;
			} else if(targetPos >= 0 && targetPos <= 20) {
				targetPos += - 25;
			}
			
		} else if(leftYstick < -.05) {
			if(6480 > targetPos) {
				targetPos += -50 * leftYstick;
			} else if(6500 >= targetPos && targetPos >= 6480) {
				targetPos += 25;
			}
		}

		
		//Operates the arm tilt motor with PID
				//Set target position
				// double targetPos = leftYstick;
		
	//Drive the motor to the target position
	// _armTiltMotor.set(ControlMode.MotionMagic, targetPos);

		
		System.out.println("Target Position: " + targetPos);		
		System.out.println("Current Encoder Position: " + encoderPos);
		System.out.println("Velocity: "+_armTiltMotor.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
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
		if (_armJoystick.getRawButton(6)) {
//			_grabMotor1.set(-1);
//			_grabMotor2.set(1);
			_grabMotor.set(-1);
		} else if (_armJoystick.getRawButton(7)) {
//			_grabMotor1.set(1);
//			_grabMotor2.set(-1);
			_grabMotor.set(1);
		} else {
//			_grabMotor1.set(0);
//			_grabMotor2.set(0);
			_grabMotor.set(0);
		}
		
		// Scissor Lift Control	
		if (_armJoystick.getRawButton(2)) {	
			_scissorLiftSolDown.set(true);
		} else {
			_scissorLiftSolDown.set(false);
		}
		
		if (_armJoystick.getRawButton(3)) {
			if (_limitSwitch.get()) {
				_scissorLiftSolUp.set(true);
			} 
		}
		else {
			_scissorLiftSolUp.set(false);
		}
		
		if (! _limitSwitch.get()) {
			System.out.println("We are ok to extend fully");
		}
		
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
	}
}
