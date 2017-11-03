import wpilib

class Soren(wpilib.SampleRobot):
    def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """

        self.stick = wpilib.Joystick(0)
        self.elevator_stick = wpilib.Joystick(1)

        self.wings = wpilib.Jaguar(4)
        self.rightWheel = wpilib.Jaguar(6)
        self.leftWheel = wpilib.Jaguar(7)
        self.elevator_jag = wpilib.Jaguar(5)
        self.brake = wpilib.Relay(0)

        self.robot_drive = wpilib.RobotDrive(0,1,2,3)

        self.timer = wpilib.Timer()

        print("1. Drive to Autozone with Style")
        print("2. Can and Tote")
        self.mode = input("Enter auto self.mode: ")

        try:
            self.mode = int(self.mode)
        except ValueError:
            self.mode = float(self.mode)


    def flap(self):
            # Go forward
            self.timer.start()
            while self.timer.get() <= 1.9:
                self.robot_drive.holonomicDrive(0.5, 0, 0)
            self.timer.reset()

            # Stop
            self.timer.start()
            while self.timer.get() <= 2:
                self.robot_drive.holonomicDrive(0, 0, 0)
            self.timer.reset()

            # Spin
            self.timer.start()
            while self.timer.get() <= 6:
                self.robot_drive.holonomicDrive(0, 0, 0.5)
            self.timer.reset()
            self.robot_drive.holonomicDrive(0, 0, 0)

            # 'Flap' the grabber arms
            while self.isAutonomous():
                self.timer.start()
                while self.timer.get() <= 0.5:
                    self.wings.set(.5)
                self.timer.reset()

                self.timer.start()
                while self.timer.get() <= 0.5:
                    self.wings.set(-.)
                self.timer.reset()

    def can_and_tote(self):

        self.timer.start()

        #Close the wings
        while self.timer.get() <= 0.25:
            self.wings.set(-1)
        self.wings.set(0)
        self.timer.reset()

        # Disengage the brake and move the elevator up
        self.timer.start()
        while self.timer.get() <= 0.5:
            self.brake.set(1)
            self.elevator_jag.set(0.8)

        self.elevator_jag.set(0)
        self.brake.set(0)
        self.timer.reset()

        # Go forward
        self.timer.start()
        while self.timer.get() <= 0.4:
            self.robot_drive.holonomicDrive(0.5, 0, 0)

        self.timer.reset()
        self.robot_drive.holonomicDrive(0, 0, 0)

        # Disengage the brake and move the elevator down
        self.timer.start()
        while self.timer.get() <= 0.5:
            self.brake.set(1)
            self.elevator_jag.set(-0.8)

        self.timer.reset()
        self.brake.set(0)

        # Close the wings
        self.timer.start()
        while self.timer.get() <= 0.25:
            self.wings.set(-1)

        self.wings.set(0)
        self.timer.reset()

        # Disengage the brake and move the elevator up
        self.timer.start()
        while self.timer.get() <= 0.5:
            self.brake.set(1)
            self.elevator_jag.set(0.8)
        self.timer.reset()

        self.elevator_jag.set(0)

        self.timer.start()
        while self.timer.get() <= 0.25:
            self.wings.set(-1)

        self.wings.set(0)
        self.timer.reset()

        # Strafe left
        self.timer.start()
        while self.timer.get() <= 2:
            self.robot_drive.holonomicDrive(0.5, 270, 0)

        # Stop drive
        self.timer.reset()
        self.robot_drive.holonomicDrive(0, 0, 0)

        # Disengage the brake and move the elevator down
        self.timer.start()
        while self.timer.get() <= 0.5:
            self.brake.set(1)
            self.elevator_jag.set(-0.8)

        self.timer.reset()
        self.elevator_jag.set(0)
        self.brake.set(0)

        # Open the flaps
        self.timer.start()
        while self.timer.get() <= 0.5:
            self.wings.set(.5)

        self.timer.reset()
        self.wings.set(0)

        # Move backwards
        self.timer.start()
        while self.timer.get() <= 0.25:
            self.robot_drive.holonomicDrive(-0.5, 0, 0)
        self.timer.reset()

        self.robot_drive.holonomicDrive(0, 0, 0)

    def autonomousInit(self):
        """This function is run once each time the robot enters autonomous self.mode."""


    def autonomous(self):
        """This function is called periodically during autonomous."""


        self.robot_drive.setSafetyEnabled(False)

        if self.mode == 2:
            self.can_and_tote()
        elif self.mode == 1:
            self.flap()
        else:
            print("You made a BIGGGGGGG booboo.")
            self.flap()


    def operatorControl(self):

        """This function is called periodically during operator control."""

        self.robot_drive.setSafetyEnabled(False)

        '''This code adds deadspace to the joystick to make the driving a little
        bit easier and more intuitive.'''

        while self.isOperatorControl() and self.isEnabled():
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


            direction = self.stick.getDirectionDegrees()

            # Go Right
            if self.stick.getRawButton(3):
                self.robot_drive.holonomicDrive(0.25, 80, twist)
            #Go Left
            elif self.stick.getRawButton(1):
                self.robot_drive.holonomicDrive(0.25, 280, twist)
            #Go Forward
            elif self.stick.getRawButton(4):
                self.robot_drive.holonomicDrive(0.25, 0, twist)
            # Go Backward
            elif self.stick.getRawButton(2):
                self.robot_drive.holonomicDrive(0.25, 180, twist)
            # Drive using the joysticks
            else:
                self.robot_drive.holonomicDrive(magnitude, direction, twist)

            # Manually moves the elevator and disengage the brake
            if self.elevator_stick.getRawButton(7):
                self.brake.set(1)
                self.elevator_jag.set(-1*self.elevator_stick.getY())
            else:
                self.brake.set(0)
                self.elevator_jag.set(0)

            # Open the flaps
            if self.elevator_stick.getRawButton(3):
                self.wings.set(1)
            # Close the flaps
            elif self.elevator_stick.getRawButton(1):
                self.wings.set(-1)
            # Cut power so motors are not 'up in smoke'
            else:
                self.wings.set(0)

    def testPeriodic(self):
        """This function is called periodically during test self.mode."""
        wpilib.LiveWindow.run()

if __name__ == "__main__":
    wpilib.run(Soren)
