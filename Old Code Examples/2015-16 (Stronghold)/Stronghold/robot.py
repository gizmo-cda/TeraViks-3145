import wpilib

class myRobot(wpilib.IterativeRobot):
	
	#initializes all of the parts of the robot
	def robotInit(self):
             
		#.___________. _______ .______          ___   ____    ____  __   __  ___      _______.
		#|           ||   ____||   _  \        /   \  \   \  /   / |  | |  |/  /     /       |
		#`---|  |----`|  |__   |  |_)  |      /  ^  \  \   \/   /  |  | |  '  /     |   (----`
		#    |  |     |   __|  |      /      /  /_\  \  \      /   |  | |    <       \   \    
		#    |  |     |  |____ |  |\  \----./  _____  \  \    /    |  | |  .  \  .----)   |   
		#    |__|     |_______|| _| `._____/__/     \__\  \__/     |__| |__|\__\ |_______/    


		#camera
		
		try:
			self.camera_1 = wpilib.USBCamera("cam0")
			self.camServ = wpilib.CameraServer()
			self.camera_1.active = True
			self.camServ.startAutomaticCapture(self.camera_1)
		except:
			camera_1 = None
		

		#drive
		self.roboDrive = wpilib.RobotDrive(0,1) #left =1, right=0

		#joystick
		self.joystick_1 = wpilib.Joystick(0) 
		self.joystick_2 = wpilib.Joystick(1) #drive

		#shifter solenoid
		self.shifterSol = wpilib.Solenoid(0,0)
		self.shifterSol.set(False)

		#solonoid shooters
		self.solonoid_shooter1 = wpilib.Solenoid(0,1)
		self.solonoid_shooter2 = wpilib.Solenoid(0,2)

		self.solonoid_shooter2.set(False)
		self.solonoid_shooter1.set(False)

		#motors for the climbimg arm (arms and hooks)
		#self.armBase_1 = wpilib.Talon(2)
		#self.armJoint_1 = wpilib.Talon(3)

		#self.armBase_2 = wpilib.Talon(4)
		#self.armJoint_2 = wpilib.Talon(5)

		#relays for arm limits
		#self.relayUp_1 = wpilib.DigitalOutput(0)
		#self.relayDown_1 = wpilib.DigitalOutput(1)
		#self.relayUp_2 = wpilib.DigitalOutput(2)
		#self.relayDown_2 = wpilib.DigitalOutput(3)

		#self.relayUp_1.set(False)
		#self.relayDown_1.set(False)
		#self.relayUp_2.set(False)
		#self.relayDown_2.set(False)
		
		#sweeper parts (motor and solenoid)
		self.sweeperMotor = wpilib.Talon(8)
		self.sweeperSolenoid = wpilib.Solenoid(0,3)

		self.sweeperMotor.set(0)
		self.sweeperSolenoid.set(False)
		
		#PC Arm
		self.pcArm = wpilib.Jaguar(6)
		self.pcArm.set(0)

		#winch
		#self.winch = wpilib.Talon(7)

		#timer
		self.timer = wpilib.Timer()
		
	
	#autonomous
	def autonomousPeriodic(self):
		self.autoCount = 0
		if self.autoCount < 100:
			self.shifterSol.set(True)
			self.roboDrive.arcadeDrive(-0.65, 0)
			self.autoCount = self.autoCount + 1
		else:
			self.roboDrive.arcadeDrive(0, 0)


	#disabled
	def disabledInit(self):
		"placeholder"

	#autoInit
	def autonomousInit(self):
		"placeholder"

	#teleopInit
	def teleopInit(self):
		"placeholder"

	#tele-op
	def teleopPeriodic(self):

		self.shifterSol.set(False)

		while self.isOperatorControl() and self.isEnabled():

			#Drive
			self.roboDrive.arcadeDrive(self.joystick_1.getY(), self.joystick_1.getTwist())

			#Shifter Button
			if self.joystick_1.getRawButton(6):
				if self.shifterSol.get():
					self.shifterSol.set(False)
					print("HIGH GEAR")
				else:
					self.shifterSol.set(True)
					print("LOW GEAR")

			#Firing
			if self.joystick_2.getRawButton(2):
				self.sweeperSolenoid.set(True)
				self.sweeperMotor.set(-1)
				self.timer.delay(.5)
				self.solonoid_shooter1.set(True)
				self.solonoid_shooter2.set(True)
				self.timer.delay(1)
				self.solonoid_shooter1.set(False)
				self.solonoid_shooter2.set(False)
				self.timer.delay(.5)
				self.sweeperMotor.set(0)
				self.sweeperSolenoid.set(False)

			#Sweeper
			if self.joystick_2.getRawButton(1):
				self.sweeperSolenoid.set(True)
				self.sweeperMotor.set(-1)
			else:
				self.sweeperSolenoid.set(0)
				self.sweeperMotor.set(0)

			#reverse sweeper roller
			if self.joystick_2.getRawButton(3):
				while self.joystick_2.getRawButton(3):
					self.sweeperMotor.set(1)

			#climbing settings
			#if self.joystick_2.getRawButton(10):
				#timeout relays for 1 second
				#self.armJoint_1.set(.25)
				#self.armJoint_2.set(.25)

				#self.armBase_1.set(.5)
				#self.armBase_2.set(-.5)

				#self.timer.delay(1.25)

				#self.armBase_1.set(0)
				#self.armBase_2.set(0)

				#self.timer.delay(1.5)

				#self.armJoint_1.set(1)
				#self.armJoint_2.set(1)

			#elif self.joystick_2.getRawButton(9):
				#self.armJoint_1.set(-.26)
				#self.armJoint_2.set(-.24)
				#self.armBase_1.set(-.25)
				#self.armBase_2.set(.25)

				#self.armJoint_1.set(-1)
				#self.armJoint_2.set(-1)
			#if self.joystick_2.getRawButton(5):
			#	self.armJoint_1.set(self.joystick_2.getY())
			#elif self.joystick_2.getRawButton(6):
			#	self.armJoint_2.set(self.joystick_2.getY())
			#elif self.joystick_2.getRawButton(7):
			#	self.armBase_1.set(self.joystick_2.getY())
			#elif self.joystick_2.getRawButton(8):
			#	self.armBase_2.set(self.joystick_2.getY())
			#else:
			#	self.armJoint_1.set(0)
			#	self.armJoint_2.set(0)
			#	self.armBase_2.set(0)
			#	self.armBase_1.set(0)

			if self.joystick_2.getRawButton(4):
				self.pcArm.set(self.joystick_2.getY())
			else: self.pcArm.set(0)

			#Winch
			#Select button winch 
			#if self.joystick_2.getRawButton(9):
			#	self.winch.set(.5)
			#	self.armJoint_1.set(1)
			#	self.armJoint_2.set(1)
			#	self.armBase_1.set(-1)
			#	self.armBase_2.set(1)
			#else: self.winch.set(0)
			#Start button winch in
			#elif self.joystick_2.getRawButton(10):
			#	self.winch.set(-1)
			#	self.armJoint_1.set(-0.5)
			#	self.armJoint_2.set(-0.5)
			#	self.armBase_1.set(1)
			#	self.armBase_2.set(1)
			#else: self.winch.set(0)
			
					

if __name__ == '__main__':
	wpilib.run(myRobot)