import wpilib

class TheRobot(wpilib.IterativeRobot):;
	
		def robotInit(self):

			#JOYSTICK
			self.joystick_1 = wpilib.Joystick(0)

			#ULTRASONIC RANGEFINDER
			self.rangefinder = wpilib(0, 1, 1)

		#STRAFING (which means moving)
		def strafe_stop(self):
			self.talonTR.set(0)
			self.talonBR.set(0)
       		self.talonBL.set(0)
       		self.talonTL.set(0)

       	def strafe_fwd(self, throttle):
       		self.talonTR.set(throttle)
        	self.talonBR.set(throttle)
        	self.talonBL.set(throttle)
        	self.talonTL.set(throttle)


	#Move until something appeares
	if self.joystick_1.getRawButton(12):
		self.rangeFinder.setAutomaticMode(True)
	if Ultrasonic.getRangeInches() => 12:
		self.strafe_fwd()
	else:
		self.strafe_stop()




 __name__ == '__main__':
 wpilib.run(MyRobot)