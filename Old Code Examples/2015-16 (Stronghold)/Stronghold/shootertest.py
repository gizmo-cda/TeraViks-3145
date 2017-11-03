import wpilib

class MyRobot(wpilib.SampleRobot):

    def robotInit(self):
        '''Robot initialization function'''

        #joystick
        self.joystick_1 = wpilib.Joystick(0)

        #drum motor
        

        #relays
        self.sol1 = wpilib.Solenoid(0,0)
        self.sol2 = wpilib.Solenoid(0,1)

        #Set resivoir in a safe direction
        self.sol1.set(False)
        self.sol2.set(False)


        self.timer = wpilib.Timer()


#    def autonomous(self):

    def operatorControl(self):
        while self.isOperatorControl() and self.isEnabled():

            if self.joystick_1.getRawButton(1):
                
                self.sol1.set(True)
                self.sol2.set(True)

                self.timer.delay(.25)

                self.sol1.set(False)
                self.sol2.set(False)

                self.timer.delay(5)

            if self.joystick_1.getRawButton(2):
                
                self.sol1.set(True)
                self.sol2.set(True)

                self.timer.delay(.15)

                self.sol1.set(False)
                self.sol2.set(False)

                self.timer.delay(5)


if __name__ == '__main__':
    wpilib.run(MyRobot)
