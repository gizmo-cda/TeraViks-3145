import wpilib

class myRobot
	
	#initializes all of the parts of the robot
	def robotInit(self):

		#joysticks
		self.joystick_1 = wpilib.Joystick(0)
		self,joystick_2 = wpilib.Joystick(1)

		#drive with 2 parameters, one for eash joystick
		self.roboDrive = wpilib.roboDrive(0,1,2)

		#solenoid for the super shifer (i set it to 0,0 because we dont know what channels we will use)
		self.shifter = wpilib.Solenoid(0,0);

		#solenoid for the catapault
		self.shooterValve = wpilib.Solenoid(1,1)

	#operator controll begins
	def teleopPeriodic(self):

		#make sure the shifter is in high gear when we start
		shifer.set(off)

		#set the drive, make sure squaredinput is set to true
		self.roboDrive.tankDrive(joystick_1, joystick_2, True)

		#shift the robot into low gear
		if self.joystick_1.getRawButton(6):
			if shifter.get() == True:
				shifter.set(False)
			if shifer.get() == False:
				shifer.set(True)

		#firing the catapault
		#
		# we had to clean up
		#


	
if __name__ == '__main__':
	wpilib.run(myRobot):