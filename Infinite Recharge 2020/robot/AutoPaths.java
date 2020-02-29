/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.RobotContainer;
/**
 * Add your docs here.
 */
public class AutoPaths {
    public AutoPaths() {
    }

    public static boolean drivePathA(){
        RobotContainer.m_drivetrain.driveDistance(1., 0., 36.);
        return true;
    }
}
