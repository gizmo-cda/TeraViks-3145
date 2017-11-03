import wpilib


class TheRobot(wpilib.IterativeRobot):
    joystick_1 = None
    roboDrive = None
  
    #initializes the parts

    def robotInit(self):
        global joystick_1

        self.camera = wpilib.USBCamera()
        self.camServ = wpilib.CameraServer()
        self.camera.active = True
        self.camServ.startAutomaticCapture(self.camera)

        #joystick
        self.joystick_1 = wpilib.Joystick(0)

        #ultrasonic
        self.rangeFinder = wpilib.Ultrasonic(0,1,1)

        #drum motor
        self.drumMotor = wpilib.Jaguar(4)

        #relays
        self.relayRotate = wpilib.Relay(0,1)
        self.solReservoir = wpilib.Solenoid(0,0)
        self.solAirValve = wpilib.Solenoid(0,1)

        #Set resivoir in a safe direction
        #False sets the solenoid to on, CLOSED
        self.solAirValve.set(False)
        self.solReservoir.set(False)

        #timer
        self.timer = wpilib.Timer()

        #talons for strafing
        #top right, bottom right, top left, and bottom left
        self.talonTR = wpilib.Talon(0)
        self.talonBR = wpilib.Talon(1)
        self.talonTL = wpilib.Talon(2)
        self.talonBL = wpilib.Talon(3)

    #strafing functions
    def strafe_stop(self):
        self.talonTR.set(0)
        self.talonBR.set(0)
        self.talonBL.set(0)
        self.talonTL.set(0)

    def strafe_left(self, throttle):
        self.talonBR.set(-1 * throttle)
        self.talonTR.set(throttle)
        self.talonBL.set(throttle)
        self.talonTL.set(-1 * throttle)

    def strafe_right(self, throttle):
        self.talonTR.set(-1 * throttle)
        self.talonBR.set(throttle)
        self.talonBL.set(-1 * throttle)
        self.talonTL.set(throttle)

    def strafe_fwd(self, throttle):
        self.talonTR.set(throttle * 1.25)
        self.talonBR.set(throttle)
        self.talonBL.set(throttle)
        self.talonTL.set(throttle)

    def strafe_back(self, throttle):
        self.talonTR.set(-1 * throttle)
        self.talonBR.set(-1 * throttle)
        self.talonBL.set(-1 * throttle)
        self.talonTL.set(-1 * throttle)

    def twist_clockWise(self, throttle):
        self.talonTR.set(-1 * throttle)
        self.talonBR.set(-1 * throttle)
        self.talonBL.set(throttle)
        self.talonTL.set(throttle)

    def twist_counterClockWise(self, throttle):
        self.talonTR.set(throttle)
        self.talonBR.set(throttle)
        self.talonBL.set(-1 * throttle)
        self.talonTL.set(-1 * throttle)

    #cubic function to scale input
    def scale_input(self, throttle, scaleFactor):
        val = throttle * throttle * throttle
        val = val * scaleFactor
        val = val + ((1 - scaleFactor) * throttle)
        return val

    #tele-op

    def teleopPeriodic(self):


        while self.isOperatorControl() and self.isEnabled():

#           Set speed of drum motor
            self.drumMotor.set(.15)

            #stop motors so we dont kill anyone
            self.strafe_stop()

            #drive with buttons and sticks
            if self.joystick_1.getRawButton(1):
                while self.joystick_1.getRawButton(1):
                    self.strafe_left(self.joystick_1.getY())

            elif self.joystick_1.getRawButton(2):
                while self.joystick_1.getRawButton(2):
                    self.strafe_back(self.joystick_1.getY())

            elif self.joystick_1.getRawButton(3):
                while self.joystick_1.getRawButton(3):
                    self.strafe_right(self.joystick_1.getY())

            elif self.joystick_1.getRawButton(4):
                while self.joystick_1.getRawButton(4):
                    self.strafe_fwd(self.joystick_1.getY())

            elif self.joystick_1.getRawButton(5):
                while self.joystick_1.getRawButton(5):
                    self.twist_counterClockWise(self.joystick_1.getY())

            elif self.joystick_1.getRawButton(6):
                while self.joystick_1.getRawButton(6):
                    self.twist_clockWise(self.joystick_1.getY())

            else:
                self.strafe_stop()

            #ultrasonic
            if self.joystick_1.getRawButton(12):
                self.rangeFinder.setAutomaticMode(True)
                dist = self.rangeFinder.getRangeInches()
                print(dist)
                print("this worked")

            #firing sequence
            if self.joystick_1.getRawButton(8):
                
                self.timer.start()

                #stop motors so we dont lose control
                self.strafe_stop()


                while ((self.timer.get() >= 0) & (self.timer.get() <=7)):
                    self.solReservoir.set(True)
                    while ((self.timer.get() >= 1) & (self.timer.get() <=7)):
                        self.solAirValve.set(True)
                        while ((self.timer.get() >= 2) & (self.timer.get() <=7)):
                            self.solAirValve.set(False)
                            self.solReservoir.set(True)
                            while ((self.timer.get() >= 3) & (self.timer.get() <=7)):
                                self.relayRotate.set(1)
                                self.solReservoir.set(False)
                                while ((self.timer.get() >= 3.5) & (self.timer.get() <=7)):
                                    self.relayRotate.set(0)
                self.timer.reset()



    
  

if __name__ == '__main__':
    wpilib.run(TheRobot)