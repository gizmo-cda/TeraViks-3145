import wpilib
from ctre import WPI_TalonSRX
import wpilib.buttons

class Robot(wpilib.IterativeRobot):

	kSlotIdx = 0
	kPIDLoopIdx = 0
	kTimeoutMs = 10

	def robotInit(self):

		self.talon = WPI_TalonSRX(0)

		self.joystick = wpilib.Joystick(0)

		self.loops = 0
		self.timesInMotionMagic = 0

		#Choosing Sensor
		self.talon.configSelectedFeedbackSensor(WPI_TalonSRX.FeedbackDevice.CTRE_MagEncoder_Relative, self.kPIDLoopIdx, self.kTimeoutMs)
		self.talon.setSensorPhase(True)
		self.talon.setInverted(False)

		#Set relevant frame periods to be at least as fast as periodic rate
		self.talon.setStatusFramePeriod(WPI_TalonSRX.StatusFrameEnhanced.Status_13_Base_PIDF0, 10, self.kTimeoutMs)
		self.talon.setStatusFramePeriod(WPI_TalonSRX.StatusFrameEnhanced.Status_10_MotionMagic, 10, self.kTimeoutMs)

		# set the peak and nominal outputs
		self.talon.configNominalOutputForward(0, self.kTimeoutMs)
		self.talon.configNominalOutputReverse(0, self.kTimeoutMs)
		self.talon.configPeakOutputForward(1, self.kTimeoutMs)
		self.talon.configPeakOutputReverse(-1, self.kTimeoutMs)

		# set closed loop gains in slot0 - see documentation */
		self.talon.selectProfileSlot(self.kSlotIdx, self.kPIDLoopIdx)
		self.talon.config_kF(0, 0.2, self.kTimeoutMs)
		self.talon.config_kP(0, 0.2, self.kTimeoutMs)
		self.talon.config_kI(0, 0, self.kTimeoutMs)
		self.talon.config_kD(0, 0, self.kTimeoutMs)
		# set acceleration and vcruise velocity - see documentation
		self.talon.configMotionCruiseVelocity(15000, self.kTimeoutMs)
		self.talon.configMotionAcceleration(6000, self.kTimeoutMs)
		# zero the sensor
		self.talon.setSelectedSensorPosition(0, self.kPIDLoopIdx, self.kTimeoutMs)

		self.button =  wpilib.buttons.JoystickButton(self.joystick, 3)
		self.button2 = wpilib.buttons.JoystickButton(self.joystick, 2)



		#self.pidController = wpilib.PIDController()

		# self.setpoint = 1024

		# # PID Values
		# self.P = 1
		# self.I = 1
		# self.D = 1

		# self.integral = 0
		# self.previous_error = 0
		#self.talon.setSelectedSensorPosition(0, 0, 0)


	# def setSetpoint(self, setpoint):
	# 	self.setpoint = setpoint

	# def PID(self):
	# 	print("PID for angle control")
	# 	error = self.setpoint - self.talon.getSelectedSensorPosition() # Error = Target - Actual
	# 	self.integral = integral + (error*.02)
	# 	derivative = (error - self.previous_error) / .02
	# 	self.rcw = self.P*error + self.I*self.integral + self.D*derivative


	# def execute(self):
	# 	print("Called every iteration of teleopPeriodic")
	# 	self.PID()
	# 	self.talon.set(self.rcw)

	def teleopPeriodic(self):
		#targetPos = self.joystick.getY * 10.0; 
		while self.isOperatorControl and self.isEnabled:
			leftYstick = -1.0 * self.joystick.getY()
			#calculate motor output
			motorOutput = self.talon.getMotorOutputPercent()

			if self.joy.getRawButton(1):
				# Motion Magic - 4096 ticks/rev * 10 Rotations in either direction
				targetPos = leftYstick * 4096 * 10.0
				self.talon.set(WPI_TalonSRX.ControlMode.MotionMagic, targetPos)

			else:
				# Percent voltage mode
				self.talon.set(WPI_TalonSRX.ControlMode.PercentOutput, leftYstick)


			# self.talon.set(0)
			# self.talon.setQuadraturePosition(0)

			# self.talon.configSelectedFeedbackSensor(0,0,0)
			# self.talon.configMotionAcceleration(self.talon.getQuadraturePosition(), 0)
			# self.talon.configMotionCruiseVelocity(self.talon.getQuadraturePosition(), 0)
			# self.talon.set(ctre.wpi_talonsrx.ControlMode.MotionMagicArc, ctre.wpi_talonsrx.MotionMagicArc.PercentOutput)

			# self.talon.set()

			# if self.joystick.getY != 0:
			# 	if self.targetPos < 1025:
			# 		self.talon.set(targetPos)
			# 




			#self.talon.pidWrite(0.01)

			#self.pidController.set(0.1, 0.001, 0.0)
			# #self.talon.setQuadraturePosition(0)
			# if self.joystick.getY() < -0.1:
			# 	if self.talon.getSelectedSensorPosition(0) <= 1025:
			# 	#if self.talon.getQuadraturePosition() <= 1025:
			# 		self.talon.pidWrite(.08)
			# 		print(self.talon.getSelectedSensorPosition(0))
			# 		#print(self.talon.getQuadraturePosition())
			# 	else:
			# 		self.talon.pidWrite(0)
			# elif self.joystick.getY() > 0.1:
			# 	if self.talon.getSelectedSensorPosition(0) >= 0:
			# 	#if self.talon.getQuadraturePosition() >= 0: 
			# 		self.talon.pidWrite(-.08)
			# 		print(self.talon.getSelectedSensorPosition(0))
			# 		#print(self.talon.getQuadraturePosition())
			# 	else:
			# 		self.talon.pidWrite(0)
			# else:
			# 	self.talon.pidWrite(0)

			# if self.button.get():
			# 	# if self.talon.getQuadraturePosition() <= 1025:
			# 	self.talon.pidWrite(1)
			# 	print(self.talon.getQuadraturePosition())
			# 	# else:
			# 	# 	self.talon.pidWrite(0)
			# elif self.button2.get():
			# 	# if self.talon.getQuadraturePosition() >= 0:
			# 	self.talon.pidWrite(-1)
			# 	print(self.talon.getQuadraturePosition())
			# else:
			# 	self.talon.pidWrite(0)

if __name__ == "__main__":
	wpilib.run(Robot)
