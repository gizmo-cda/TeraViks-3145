/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.swerve;

import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class SwerveUtils {
    private static double steerGearRatio = RobotMap.FINAL_STEER_WHEEL_GEAR_RATIO;

    SwerveUtils(){
    }

    public double normEncoder(int currentPosition){

        int position = currentPosition;
        int rotations;

        rotations = currentPosition / 4096;

        if (rotations > 0) {
         
        }
    }
}
