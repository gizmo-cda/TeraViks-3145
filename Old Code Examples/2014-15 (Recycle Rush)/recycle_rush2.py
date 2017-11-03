import wpilib
import time


class MyRobot(wpilib.IterativeRobot):

    def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """
        self.robot_drive = wpilib.RobotDrive(0,1,2,3)

        self.stick = wpilib.Joystick(0)
        self.elevator_stick = wpilib.Joystick(1)

        #self.accelerometer = wpili.BuiltInAccelerometer()

        '''self.talon_0 = wpilib.Talon(0)
        self.talon_1 = wpilib.Talon(1)
        self.talon_2 = wpilib.Talon(2)
        self.talon_3 = wpilib.Talon(3)'''

        self.elevator_jag = wpilib.CANJaguar(1)

        # self.elevator_jag.setPositionModeQuadEncoder(360,80,0.000,4)
        self.elevator_jag.setPercentModeQuadEncoder(360)
        self.elevator_jag.enableControl()
    def autonomousInit(self):
        """This function is run once each time the robot enters autonomous mode."""
        def forward(run_time): # Written by Tim
            wpilib.Timer.start()
            while wpilib.Timer.get()<=run_time:
                self.talon_0.set(1)
                self.talon_1.set(1)
                self.talon_2.set(1)
                self.talon_3.set(1)
            self.talon_0.set(0)
            self.talon_1.set(0)
            self.talon_2.set(0)
            self.talon_3.set(0)
            wpilib.Timer.reset()
            return
        def backward(run_time): # Written by Tim
            wpilib.Timer.start()
            while wpilib.Timer.get()<=run_time:
                self.talon_0.set(-1)
                self.talon_1.set(-1)
                self.talon_2.set(-1)
                self.talon_3.set(-1)
            self.talon_0.set(0)
            self.talon_1.set(0)
            self.talon_2.set(0)
            self.talon_3.set(0)
            wpilib.Timer.reset()
            return
        def strafe_left(run_time): # Written by Tim
            wpilib.Timer.start()
            while wpilib.Timer.get()<=run_time:
                self.talon_0.set(-1)
                self.talon_1.set(1)
                self.talon_2.set(1)
                self.talon_3.set(-1)
            self.talon_0.set(0)
            self.talon_1.set(0)
            self.talon_2.set(0)
            self.talon_3.set(0)
            wpilib.Timer.reset()
            return

        def strafe_left(run_time): # Written by Tim
            wpilib.Timer.start()
            while wpilib.Timer.get()<=run_time:
                self.talon_0.set(1)
                self.talon_1.set(-1)
                self.talon_2.set(-1)
                self.talon_3.set(1)
            self.talon_0.set(0)
            self.talon_1.set(0)
            self.talon_2.set(0)
            self.talon_3.set(0)
            wpilib.Timer.reset()
            return



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
        #self.robot_drive.setInvertedMotor(1, True)
        #self.robot_drive.setInvertedMotor(3, True)

        if 0 <= self.stick.getTwist() <= 0.1 or -0.1 <= self.stick.getTwist() <= 0:
            twist = 0
        elif self.stick.getTwist() > 0.1:
            twist = self.stick.getTwist()/2.5 - 0.1
        elif self.stick.getTwist() < -0.1:
            twist = self.stick.getTwist()/2.5 + 0.1

        if 0 <= self.stick.getMagnitude() <= 0.1 or -0.1 <= self.stick.getMagnitude() <= 0:
            magnitude = 0
        elif self.stick.getMagnitude() > 0.1:
            magnitude = self.stick.getMagnitude()/2 - 0.1
        elif self.stick.getMagnitude() < -0.1:
            magnitude = self.stick.getMagnitude()/2 + 0.1

        self.robot_drive.holonomicDrive(magnitude, self.stick.getDirectionDegrees(), twist)

        #print(self.stick.getY())
        #print("X:", self.stick.getX())
        #self.talon_0.set(-1 * self.stick.getY())
        #self.talon_1.set(-1 * self.stick.getY())
        #self.talon_2.set(-1 * self.stick.getY())
        #self.talon_3.set(-1 * self.stick.getY())


        #Testing code for the elevator
        #self.elevator_jag.set(-10 * self.elevator_stick.getY())

        '''if self.elevator_stick.getRawButton(4):
            self.elevator_jag.set(self.elevator_jag.getPosition() + 0.71)
        if self.elevator_stick.getRawButton(3):
            self.elevator_jag.set(self.elevator_jag.getPosition() - 0.71)
        if self.elevator_stick.getRawButton(5):
            self.elevator_jag.set(3)'''
        if not self.elevator_jag.getForwardLimitOK():
            self.elevator_jag.set(-.075)
        if not self.elevator_jag.getReverseLimitOK():
            self.elevator_jag.set(.15)
        self.elevator_jag.set(.1)

        currentRevs = self.elevator_jag.getPosition()
        if self.elevator_stick.getRawButton(4):
            while self.elevator_jag.getPosition() < .1+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.65)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() < .2+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.1)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() < .7+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.75)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() < .8+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.65)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() < .9+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.35)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() < 1+currentRevs and self.elevator_jag.getForwardLimitOK():
                self.elevator_jag.set(.15)
                print("Elevator: ", self.elevator_jag.getPosition())
                break
            self.elevator_jag.set(.15)
        if self.elevator_stick.getRawButton(3):
            while self.elevator_jag.getPosition() > currentRevs-.1 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.25)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() > currentRevs-.2 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.5)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() > currentRevs-.7 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.75)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() > currentRevs-.8 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.5)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() > currentRevs-.9 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.25)
                print("Elevator: ", self.elevator_jag.getPosition())
            while self.elevator_jag.getPosition() > currentRevs-1 and self.elevator_jag.getReverseLimitOK():
                self.elevator_jag.set(-.15)
                print("Elevator: ", self.elevator_jag.getPosition())
                self.elevator_jag.set(0)
                break
            self.elevator_jag.set(.15)
        print("Elevator: ", self.elevator_jag.getPosition())

        '''if self.stick.getX() > 0:
            self.talon_0.set(self.stick.getX())
            self.talon_1.set(-1 * self.stick.getX())
            self.talon_2.set(-1 * self.stick.getX())
            self.talon_3.set(self.stick.getX())
        elif self.stick.getX() < 0:
            self.talon_0.set(-1 * self.stick.getX())
            self.talon_1.set(self.stick.getX())
            self.talon_2.set(self.stick.getX())
            self.talon_3.set(-1 * self.stick.getX())
        else:
            self.talon_0.set(0)
            self.talon_1.set(0)
            self.talon_2.set(0)
            self.talon_3.set(0)
        '''

        '''try:
            on
        except NameError:
            on = 1

        if self.stick.getRaswButton(4):
            if on == 1:
                self.robot_drive.holonomicDrive(0.2, 0.0, 0.0)
                on = 2
                return
            elif on == 2:
                self.robot_drive.holonomicDrive(0.2, 0.0, 0.0)
                on = 1
                return
            return'''

        #print("Magnitude: ", self.stick.getMagnitude())
        #print("Direction: ", self.stick.getDirectionDegrees())
        #print("Rotation: ", self.stick.getTwist())
    def testPeriodic(self):
        """This function is called periodically during test mode."""
        wpilib.LiveWindow.run()

if __name__ == "__main__":
    wpilib.run(MyRobot)
