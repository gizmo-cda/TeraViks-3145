/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.gps;

import frc.robot.Robot;
import frc.robot.RobotMap;
import java.lang.Math;

public class Target {
    private double tx;  //Horizontal (relatie to cam) offset from target: left 0<x<27, right -27<x<0
    private double ty;  //Vertical (relative to cam) offset from target: below 0<y<20.5, above -20.5
    private double ta;  //Area of target: 0-100% of image
    private double tv;  //Valid target = 1

    private double camAngle = RobotMap.CAMERA_MOUNTING_ANGLE;
    private double camHeight = RobotMap.CAMERA_MOUNTING_HEIGHT;
    private double targetHeight = RobotMap.DOUBLE_STRIPE_REFLECTIVE_TAPE_TARGET_HEIGHT;

    private String status;

    public Target (){
    }

    // This is the primary method to move to the target.  It calls multiple private methods.
    public void moveToTarget() {
        Robot.m_drivetrain.turnOffCentric();  //Make sure bot is driving relative to itself
        Robot.m_drivetrain.setCrabMode();  //Make sure bot is not in snake mode

        // Rotate the bot until on Target
        System.out.println("Rotating to Target");
        status = rotateToTarget(0.);
        System.out.println(status);

        // Now check the gyro versus the defined target angles to find the desired target and its angle
        double gyroAngle = Robot.m_gyro.getYawDeg();
        double desiredAngle = getTargetAngle(gyroAngle);

        // Strafe left/right until perpendicular to the the target, continue to correct for x offset too
        System.out.println("Strafing to Target");
        status = strafeToNormal(0., desiredAngle);
        System.out.println(status);

        // Drive forward to the target and continue to adjust strafe, but not rotation
        System.out.println("Forward to Target");
        status = forwardToTarget(0., desiredAngle);
        System.out.println(status);
    }

    private String rotateToTarget(double fwd) {
        tx = Robot.m_vision.getVisionValues().get("tx");
        tv = Robot.m_vision.getVisionValues().get("tv");
        String statMsg =""; 

        // Adjust robot position until x = 0
        while ((tx > 1 || tx < -1) && (int) tv == 1) {
            tx = Robot.m_vision.getVisionValues().get("tx");
            tv = Robot.m_vision.getVisionValues().get("tv");

            // Only rotation for movement
            Robot.m_drivetrain.move(fwd, 0., 0.025*tx); // this acts as a gain, might become constant later
        }
        if ((int) tv == 0) statMsg = "Lost Target Lock"; else statMsg = "Rotated to Target";
        return statMsg;
    }

    private double getTargetAngle(double botAngle){
        double targetAngle = 0;
        
        if (botAngle > -150. && botAngle <= -105.) {
            targetAngle = -120.;
        }
        if (botAngle > -105 && botAngle <= -75) {
            targetAngle = -90.;
        }
        if (botAngle > -75 && botAngle <= -30) {
            targetAngle = -60.;
        }
        if (botAngle > -30 && botAngle <= 30) {
            targetAngle = 0.;
        }
        if (botAngle > 30 && botAngle <= 75) {
            targetAngle = 60.;
        }
        if (botAngle > 75 && botAngle <= 105) {
            targetAngle = 90.;
        }
        if (botAngle > 105 && botAngle <= 150) {
            targetAngle = 120.;
        }
        if (botAngle < -150 || botAngle > 150) {
            targetAngle = 180.; // should be the same as -180 dicontinuity TODO: figure out
        }
        return targetAngle;
    }

    private String strafeToNormal(double fwd, double targetAngle) {
        double botAngle = Robot.m_gyro.getYawDeg();
        double deltaAngle = botAngle - targetAngle; // Check: check order here (sign checking)
        tx = Robot.m_vision.getVisionValues().get("tx");
        tv = Robot.m_vision.getVisionValues().get("tv");
        String statMsg =""; 

        // Adjust robot position until delta Angle ~= 0
        while ((deltaAngle > 1 || deltaAngle < -1) && (int) tv == 1) {
            tx = Robot.m_vision.getVisionValues().get("tx");
            tv = Robot.m_vision.getVisionValues().get("tv");

            botAngle = Robot.m_gyro.getYawDeg();
            deltaAngle = botAngle - targetAngle;

            // Strafe while also continuing to monitor x and adjust if necessary
            Robot.m_drivetrain.move(fwd, 0.025*deltaAngle, 0.025*tx); // this acts as a gain, might become constant later
        }

        if ((int) tv == 0) statMsg = "Lost Target Lock"; else statMsg = "Strafed to Target";
        return statMsg;
    }

    private String forwardToTarget(double fwd, double targetAngle) {
        double botAngle = Robot.m_gyro.getYawDeg();
        double deltaAngle = botAngle - targetAngle;  //Check: check order here (sign checking)
        double distToTarget = Math.tan(camAngle+ty)/(camHeight-targetHeight);
        String statMsg =""; 

        tx = Robot.m_vision.getVisionValues().get("tx");
        tv = Robot.m_vision.getVisionValues().get("tv");

        // Adjust robot position until distance to target is <= 20 inches A STARTING PLACE
        while ((distToTarget > 20) && (int) tv == 1) {
            ty = Robot.m_vision.getVisionValues().get("ty");
            tv = Robot.m_vision.getVisionValues().get("tv");

            botAngle = Robot.m_gyro.getYawDeg();
            deltaAngle = botAngle - targetAngle; 
            distToTarget = Math.tan(camAngle+ty)/(camHeight-targetHeight);
           
            // Strafe while also continuing to monitor x and adjust if necessary
            Robot.m_drivetrain.move(fwd, 0.025*deltaAngle, 0.); // this acts as a gain, might become constant later
        }

        if ((int) tv == 0) statMsg = "Lost Target Lock"; else statMsg = "Forwarded to Target";
        return statMsg;
    }
}
