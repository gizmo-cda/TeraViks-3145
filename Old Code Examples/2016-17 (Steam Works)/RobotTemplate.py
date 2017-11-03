#MAKE SURE TO NAME THE FILE "robot.py"
#Import the FRC Python3 library

#DOCS
#http://robotpy.readthedocs.io/en/latest/wpilib/Joystick.html

import wpilib

#create a class for the robot
class MyRobot(wpilib.IterativeRobot):
    #We have 5 methods for the states of our robot

    def robotInit(self):
        #Called at the start of the program
        #we always declare hardware here
        #this is a simple robot drive
        self.drive = wpilib.RobotDrive(0, 1)

        self.joystick_1 = wpilib.Joystick(0)

    def autonomousInit(self):
        #Run once a robot enters autonomous mode
        self.auto_loop_counter = 0

    def autonomousPeriodic(self):
        #Called periodically thoughout autonomous, the "meat and potatoes"
        # Check if we have completed 100 loops (2 Seconds)
        if self.auto_loop_counter < 100:
            self.auto_loop_counter += 1     #Signify that we have done a loop
        else:
            self.robot_drive.drive(0, 0)    #Stop robot

    def teleopPeriodic(self):
        #Run periodically throughout Teleop
        #This is where we will create the controlls of the robot
        #You will spend most of your time here

        #Here are some quick safety features
        self.drive.setSafetyEnabled(True)

        while self.isOperatorControl() and self.isEnabled():
            self.drive.setSafetyEnabled(False)

            #THIS IS WHERE WE DO CONTROLLS
            self.roboDrive.arcadeDrive(self.joystick_1)

    def testPeriodic(self):
        #Run periodically during test mode

#run the robot class
if __name__ == "__main__":
    wpilib.run(MyRobot)
