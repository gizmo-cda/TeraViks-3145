/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gps;

import frc.robot.Robot;

public class Target {
    private double tx;  //Horizontal (relatie to cam) offset from target: left 0<x<27, right -27<x<0
    private double ty;  //Vertical (relative to cam) offset from target: below 0<y<20.5, above -20.5
    private double ta;  //Area of target: 0-100% of image
    private double tv;  //Valid target = 1

    private double gyroAngle;

    public Target (){
    }

    // This is the primary method to move to the target.  It calls multiple private methods.
    public void moveToTarget() {
        Robot.m_drivetrain.crabMode();  //Make sure bot is not in snake mode
        tx = Robot.m_vision.getVisionValues().get("tx");
        tv = Robot.m_vision.getVisionValues().get("tv");

        // Rotate the bot until on Target
        rotateToTarget(tx, tv);

        // Now check the gyro versus the defined target angles to find the desired target and it's angle
        gyroAngle = Robot.m_gyro.getYawDeg();
        double desiredAngle = getDesiredAngle(gyroAngle);

        tx = Robot.m_vision.getVisionValues().get("tx");
        tv = Robot.m_vision.getVisionValues().get("tv");

        // Strafe left/right until perpendicular to the the target, continue to correct for x offset too
        strafeToNormal(0., gyroAngle, desiredAngle, tx, tv);

        strafeToNormal(0., gyroAngle, desiredAngle, tx, tv); // fwd should be some percent of a joystick output, 0.2?
    }

    private void rotateToTarget(double tx, double tv) {
        // Adjust robot position until x = 0
        while ((tx > 1 || tx < -1) && (int)tv == 1) {
            tx = Robot.m_vision.getVisionValues().get("tx");
            tv = Robot.m_vision.getVisionValues().get("tv");

            // Only rotation for movement
            Robot.m_drivetrain.move(0.,0.,0.025*tx); // this acts as a gain, might become constant later
        }
    }

    private double getDesiredAngle(double angle){
        double desiredAngle = 0;
        
        if (angle > -150. && angle <= -105.) {
            desiredAngle = -120.;
        }
        if (angle > -105 && angle <= -75) {
            desiredAngle = -90.;
        }
        if (angle > -75 && angle <= -30) {
            desiredAngle = -60.;
        }
        if (angle > -30 && angle <= 30) {
            desiredAngle = 0.;
        }
        if (angle > 30 && angle <= 75) {
            desiredAngle = 60.;
        }
        if (angle > 75 && angle <= 105) {
            desiredAngle = 90.;
        }
        if (angle > 105 && angle <= 150) {
            desiredAngle = 120.;
        }
        if (angle < -150 || angle > 150) {
            desiredAngle = 180.; // should be the same as -180 dicontinuity TODO: figure out
        }
        return desiredAngle;
    }

    private void strafeToNormal(double forwardToTarget, double gyroAngle, double desiredAngle, double tx, double tv) {
        double deltaAngle = gyroAngle - desiredAngle; // TODO: check order here (sign checking)

        // Adjust robot position until delta Angle ~= 0
        while ((deltaAngle > 1 || deltaAngle < -1) && (int)tv == 1) {
            tx = Robot.m_vision.getVisionValues().get("tx");
            tv = Robot.m_vision.getVisionValues().get("tv");

            gyroAngle = Robot.m_gyro.getYawDeg();
            deltaAngle = gyroAngle - desiredAngle;

            // Strafe while also continuing to monitor x and adjust if necessary
            Robot.m_drivetrain.move(forwardToTarget,0.025*deltaAngle,0.025*tx); // this acts as a gain, might become constant later
        }
    }
}
