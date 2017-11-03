import wpilib
import hal
import time

def forward (self): # Written by Tim
    self.tal
class MyRobot(wpilib.IterativeRobot):
    '''def define_shifter(self):
        global shifter
        shifter = 1'''
    def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """
        self.compressor = wpilib.Compressor(0)
        self.robot_drive = wpilib.RobotDrive(1,2)
        self.stick = wpilib.Joystick(0)
        #self.shift_solenoid = wpilib.DoubleSolenoid(0,1)
        self.shift_solenoid_single = wpilib.Solenoid(0,1)
        self.robot_drive.setInvertedMotor(0, True)
        self.compressor.start()
        self.jaguar = wpilib.CANJaguar(1)
        self.jaguar.setPercentModeEncoder(360)
        self.jaguar.enableControl()

        #self.jaguar = wpilib.Jaguar(3)
    def autonomousInit(self):
        """This function is run once each time the robot enters autonomous mode."""
        self.auto_loop_counter = 0

    def autonomousPeriodic(self):
        """This function is called periodically during autonomous."""
        
        # Check if we've completed 100 loops (approximately 2 seconds)
        if self.auto_loop_counter < 100:
            self.robot_drive.drive(-0.5, 0) # Drive forwards at half speed
            self.auto_loop_counter += 1
        else:
            self.robot_drive.drive(0, 0)    #Stop robot

    def teleopPeriodic(self):
        """This function is called periodically during operator control."""
        
        self.robot_drive.arcadeDrive(self.stick)
		
        try:
            shifter
        except NameError:
            shifter = 1
			
        if self.stick.getRawButton(2):
            if shifter == 1:
                self.shift_solenoid_single.set(True)
                shifter = 2
                return
            if shifter == 2:
                self.shift_solenoid_single.set(False)
                shifter = 1
                return
        if self.stick.getRawButton(3):
            self.jaguar.set(1)
        print(self.jaguar.getPosition())	     
            
    def testPeriodic(self):
        """This function is called periodically during test mode."""
        wpilib.LiveWindow.run()

if __name__ == "__main__":
    wpilib.run(MyRobot)
