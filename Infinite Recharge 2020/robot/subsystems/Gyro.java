/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * Add your docs here.
 */

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.*;

public class Gyro extends SubsystemBase {

    public static AHRS ahrs;

    public Gyro() {
        ahrs = new AHRS(SPI.Port.kMXP);
    }

    public void reset(){
        ahrs.reset();
    }

    /** This is total accumulated Yaw angle in degrees and is continuous */
    public double getYawAccumDeg(){
        return ahrs.getAngle();
    }

    /** This is Yaw angle +/- 180 in degrees, this has a discontinuity */
    public double getYawDeg(){
       return ahrs.getYaw();
    }

    /** This is Pitch angle +/- 180 in degrees, robot short axis tilt to front is positive */
    public double getPitchDeg(){
        return ahrs.getPitch();
    }

    /** This is Roll angle +/- 180 in degrees, robot long axis tilt to right is positive */
    public double getRollDeg(){
        return ahrs.getRoll();
    }

    public double getAccelX(){
        return (double)ahrs.getWorldLinearAccelX();
    }

    public double getAccelY(){
        return (double)ahrs.getWorldLinearAccelY();
    }

    /*@Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }*/
}
