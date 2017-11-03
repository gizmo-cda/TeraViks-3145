import wpilib

class MyRobot(wpilib.SampleRobot):

    def robotInit(self):
        '''Robot initialization function'''

#    def autonomous(self):

    def operatorControl(self):
        '''Runs the motors with tank steering'''

        self.robot_drive.setSafetyEnabled(True)

        while self.isOperatorControl() and self.isEnabled():
            self.robot_drive.setSafetyEnabled(False)

#           Set speed of tee-shirt drummotor
            self.drummotor.set(.15)

#           Make sure relay for reservoir is standby
            self.relayreservoir.set(0)

#            twist = self.gamecontroller.getTwist()
#            magnitude = self.gamecontroller.getMagnitude()
#            direction = self.gamecontroller.getDirectionDegrees()
#            self.robot_drive.holonomicDrive(magnitude, direction, twist)

#           Set drive
            self.robot_drive.mecanumDrive_Cartesian(self.gamecontroller.getX(), self.gamecontroller.getY(), self.gamecontroller.getTwist(), 0)


            if self.gamecontroller.getRawButton(1):
                self.timer.start()
                while ((self.timer.get() >= 0) & (self.timer.get() <=7)):
                     self.relayreservoir.setDirection(1)
                     self.relayreservoir.set(1)
                     while ((self.timer.get() >= 1) & (self.timer.get() <=7)):
                          self.relayairvalve.set(1)
                          while ((self.timer.get() >= 2) & (self.timer.get() <=7)):
                               self.relayairvalve.set(0)
                               self.relayreservoir.setDirection(2)
                               self.relayreservoir.set(1)
                               while ((self.timer.get() >= 3) & (self.timer.get() <=7)):
                                    self.relayrotate.set(1)
                                    self.relayreservoir.set(0)
                                    while ((self.timer.get() >= 3.5) & (self.timer.get() <=7)):
                                         self.relayrotate.set(0)
                self.timer.reset()


if __name__ == '__main__':
    wpilib.run(MyRobot)
