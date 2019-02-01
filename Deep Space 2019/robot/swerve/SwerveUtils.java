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
    private static double ticks = RobotMap.STEER_MOTOR_PULSES_PER_REVOLUTION;
    private static double steerGearRatio = RobotMap.FINAL_STEER_WHEEL_GEAR_RATIO;

    SwerveUtils(){
    }

    public double normEncoder(int currentPosition){

        int position = currentPosition;
        int rotations;

        rotations = position / (int)(ticks * steerGearRatio); // do math and truncate to int, then implicitly typecast to double for return

        System.out.println(rotations);
        rotations *= (ticks * steerGearRatio);
        System.out.println(rotations);

        return rotations;
    }
}
