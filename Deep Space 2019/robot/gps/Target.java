/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gps;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.ArrayList;
import java.util.Arrays;
import frc.robot.Robot;
import frc.robot.subsystems.*;

public class Target {
    private ArrayList<Double> targetVals;
    private double tx;
    private double ty;
    private double ta;
    private double tv;

    public Target (){
    }

    public boolean aquireTarget(double tx, double ty, double ta, double tv) {
        if ((int)tv == 1) return true; else return false;
    }

    private void rotateToTarget(double tx, double tv) {
        while ((tx < 0.1) && (tx > -0.1) && ((int)tv == 1)) {
            targetVals = Robot.m_vision.getVisionTableValues();
            tx = targetVals.get(0);
            tv = targetVals.get(3);
            // adjust robot position until x = 0
            Robot.m_drivetrain.move(0.,0.,0.025*tx); // this acts as a gain, might become constant later
        }
    }

    private double getDesiredAngle(double gyroAngle){
        double desiredAngle = 0;
        
        if (gyroAngle > -150. && gyroAngle <= -105.) {
            desiredAngle = -120.;
        }
        if (gyroAngle > -105 && gyroAngle <= -75) {
            desiredAngle = -90.;
        }
        if (gyroAngle > -75 && gyroAngle <= -30) {
            desiredAngle = -60.;
        }
        if (gyroAngle > -30 && gyroAngle <= 30) {
            desiredAngle = 0.;
        }
        if (gyroAngle > 30 && gyroAngle <= 75) {
            desiredAngle = 60.;
        }
        if (gyroAngle > 75 && gyroAngle <= 105) {
            desiredAngle = 90.;
        }
        if (gyroAngle > 105 && gyroAngle <= 150) {
            desiredAngle = 120.;
        }
        if (gyroAngle < -150 || gyroAngle > 150) {
            desiredAngle = 180.; // should be the same as -180 dicontinuity TODO: figure out
        }
        return desiredAngle;
    }

    private void strafeToNormal(double forwardToTarget, double gyroAngle, double desiredAngle, double tx, double tv) {
        double deltaAngle = gyroAngle - desiredAngle; // TODO: check order here (sign checking)

        while ((deltaAngle < 0.1) && (deltaAngle > -0.1) && ((int)tv == 1)) {
            targetVals = Robot.m_vision.getVisionTableValues();
            tx = targetVals.get(0);
            tv = targetVals.get(3);
            // adjust robot position until x = 0
            Robot.m_drivetrain.move(forwardToTarget,0.025*deltaAngle,0.025*tx); // this acts as a gain, might become constant later
        }
    }

    public void moveToTarget(double gyroAngle) {
        targetVals = Robot.m_vision.getVisionTableValues();
        tx = targetVals.get(0);
        tv = targetVals.get(3);

        rotateToTarget(tx, tv);

        double desiredAngle = getDesiredAngle(gyroAngle);

        // TODO: make this into its own submethod
        targetVals = Robot.m_vision.getVisionTableValues();
        tx = targetVals.get(0);
        tv = targetVals.get(3);

        strafeToNormal(0., gyroAngle, desiredAngle, tx, tv);

        strafeToNormal(0., gyroAngle, desiredAngle, tx, tv); // fwd should be some percent of a joystick output, 0.2?

    }
}
