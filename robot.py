import wpilib
import ctre
import wpilib.buttons

class Robot(wpilib.IterativeRobot):
  	

	def robotInit(self):

		self.talon = ctre.wpi_talonsrx.WPI_TalonSRX(0)
		self.joystick = wpilib.Joystick(0)
		self.button =  wpilib.buttons.JoystickButton(self.joystick, 3)
		self.button2 = wpilib.buttons.JoystickButton(self.joystick, 2)


	def teleopPeriodic(self):
		while self.isOperatorControl and self.isEnabled:
			if self.button.get():
				if self.talon.getQuadraturePosition() <= 20:
					self.talon.set(.05)
					print(self.talon.getQuadraturePosition())
				else:
					self.talon.set(0)
			elif self.button2.get():
				if self.talon.getQuadraturePosition() >= 0:
					self.talon.set(-.05)
					print(self.talon.getQuadraturePosition())
				else:
					self.talon.set(0)
			

if __name__ == "__main__":
    wpilib.run(Robot)