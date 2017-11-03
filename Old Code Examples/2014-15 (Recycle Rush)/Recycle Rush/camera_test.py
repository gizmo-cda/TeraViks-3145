import wpilib
import time


class MyRobot(wpilib.IterativeRobot):

    def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """
        self.robot_drive = wpilib.RobotDrive(0,1,2,3)
        self.camera = wpilib._impl.USBCamera(cam0)
        wpilib._impl.CameraServer.getInstance()
        self.stick = wpilib.Joystick(0)

        #self.accelerometer = wpili.BuiltInAccelerometer()

        self.talon_0 = wpilib.Talon(0)
        self.talon_1 = wpilib.Talon(1)
        self.talon_2 = wpilib.Talon(2)
        self.talon_3 = wpilib.Talon(2)
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
        self.robot_drive.holonomicDrive(self.stick.getMagnitude(), self.stick.getDirectionDegrees(), self.stick.getTwist())

        #print(self.stick.getY())
        print(self.stick.getX())
        #self.talon_0.set(-1 * self.stick.getY())
        #self.talon_1.set(-1 * self.stick.getY())
        #self.talon_2.set(-1 * self.stick.getY())
        #self.talon_3.set(-1 * self.stick.getY())

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
