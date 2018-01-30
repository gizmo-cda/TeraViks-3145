import wpilib
import wpilib.drive
import ctre
import wpilib.buttons



class Robot(wpilib.IterativeRobot):
  	

	def robotInit(self):

		self.talonL1 = ctre.wpi_talonsrx.WPI_TalonSRX(4)
		self.talonL2 = ctre.wpi_talonsrx.WPI_TalonSRX(3)
		self.talonR1 = ctre.wpi_talonsrx.WPI_TalonSRX(2)
		self.talonR2 = ctre.wpi_talonsrx.WPI_TalonSRX(1)

		self.leftTalons = wpilib.SpeedControllerGroup(self.talonL1, self.talonL2)
		self.rightTalons = wpilib.SpeedControllerGroup(self.talonR1, self.talonR2)

		self.drive = wpilib.drive.DifferentialDrive(self.leftTalons, self.rightTalons)
		self.joystick_1 = wpilib.Joystick(0)

		self.highGearTrigger = wpilib.buttons.JoystickButton(self.joystick_1, 8)
		self.lowGearTrigger = wpilib.buttons.JoystickButton(self.joystick_1, 7)

		self.compresser = wpilib.Compressor()

		self.solenoid1 = wpilib.Solenoid(40, 1)
		self.solenoid1.set(False)


		# wpilib.CameraServer.launch()

		wpilib.CameraServer.launch('vision.py:main')

	#def autonomousInit(self):

	#def autonomousPeriodic(self):


	def teleopPeriodic(self):
		while self.isOperatorControl and self.isEnabled:
			self.drive.arcadeDrive(self.joystick_1.getY(), -1 * self.joystick_1.getTwist())

			#button 1 #solenoid 3
			if self.highGearTrigger.get():
				self.solenoid1.set(True)
			elif self.lowGearTrigger.get():
				self.solenoid1.set(False)


if __name__ == "__main__":
    wpilib.run(Robot)