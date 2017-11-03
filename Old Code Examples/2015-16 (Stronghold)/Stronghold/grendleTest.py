import wpilib

class myRobot(wpilib.IterativeRobot):
	
	#initializes all of the parts of the robot
	def robotInit(self):

		#motors
		self.talonLeft = wpilib.Talon(0)
		self.talonRight = wpilib.Talon(1)

		#joystick
		self.joystick_1 = wpilib.Joystick(0)

		#gyro
		self.gyro = wpilib.AnalogGyro(0, 0, 0)

		#timer
		self.timer = wpilib.Timer()

	#operator controll begins

	def move_forward(self, throttle):
		self.talonRight.set(throttle * -1)
		self.talonLeft.set(throttle)

	def turn_right(self, throttle):
		self.talonLeft.set(throttle)
		self.talonRight.set(throttle)

	def turn_left(self, throttle):
		self.talonRight.set(throttle * -1)
		self.talonLeft.set(throttle * -1)

	def stop_robot(self):
		self.talonRight.set(0)
		self.talonLeft.set(0)

	#autonomous mode
	def autonomousInit(self):
		self.move_forward(-1)
		self.timer.delay(1)
		self.stop_robot()
	

	def teleopPeriodic(self):

		while self.isOperatorControl() and self.isEnabled():

			#Drive
			if self.joystick_1.getRawButton(7) and self.joystick_1.getRawButton(8):
				self.move_forward(self.joystick_1.getY())
			elif self.joystick_1.getRawButton(7):
				self.turn_left(self.joystick_1.getY())
			elif self.joystick_1.getRawButton(8):
				self.turn_right(self.joystick_1.getY())
			else:
				self.stop_robot()

			if self.joystick_1.getRawButton(1):
				print(gyro.getAngle())




if __name__ == '__main__':
	wpilib.run(myRobot)