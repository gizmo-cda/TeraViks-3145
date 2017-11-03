import wpilib

class TheRobot(wpilib.IterativeRobot):
    joystick_1 = None
    roboDrive = None

    #initializes the parts
    def robotInit(self):
        global joystick_1

        #joystick
        self.joystick_1 = wpilib.Joystick(0)

        #drive
        self.roboDrive = wpilib.RobotDrive(0,1,2,3)

        #drum motor
        self.drumMotor = wpilib.Jaguar(4)

        #relays
        self.relayRotate = wpilib.Relay(0,1)
        self.relayReservoir = wpilib.Relay(1,0)
        self.relayAirValve = wpilib.Relay(2,1)

        #Set resivoir in a safe direction
        self.relayReservoir.setDirection(2)
        self.relayReservoir.set(1)

        #timer
        self.timer = wpilib.Timer()

    #tele-op
    def teleopPeriodic(self):

        self.roboDrive.setSafetyEnabled(True)

        while self.isOperatorControl() and self.isEnabled():
            self.roboDrive.setSafetyEnabled(False)

            #Set speed of drum motor
            self.drumMotor.set(.15)

            #Relay is on standby
            self.relayReservoir.set(0)

            #Set drive to move with the joysticks
            self.roboDrive.holonomicDrive(self.joystick_1.getY(), self.joystick_1.getX(), self.joystick_1.getTwist())

            #firing sequence
            if self.joystick_1.getRawButton(1):

                self.timer.start()

                #stop motors so we dont lose control
                self.roboDrive.stopMotor()

                while ((self.timer.get() >= 0) & (self.timer.get() <=7)):
                     self.relayReservoir.setDirection(1)
                     self.relayReservoir.set(1)
                     while ((self.timer.get() >= 1) & (self.timer.get() <=7)):
                          self.relayAirValve.set(1)
                          while ((self.timer.get() >= 2) & (self.timer.get() <=7)):
                               self.relayAirValve.set(0)
                               self.relayReservoir.setDirection(2)
                               self.relayReservoir.set(1)
                               while ((self.timer.get() >= 3) & (self.timer.get() <=7)):
                                    self.relayRotate.set(1)
                                    self.relayReservoir.set(0)
                                    while ((self.timer.get() >= 3.5) & (self.timer.get() <=7)):
                                         self.relayRotate.set(0)
                self.timer.reset()




if __name__ == '__main__':
  wpilib.run(TheRobot)
