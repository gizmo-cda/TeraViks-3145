/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.commands.ShootBall;
import frc.robot.commands.TargetTrackModeDisengage;
import frc.robot.commands.TargetTrackModeEngage;

/**
 * Add your docs here.
 */
public class AutoPaths {
    public AutoPaths() {
    }

    public static boolean driveForward() {
        RobotContainer.m_drivetrain.driveDistance(1., 0., 36.);
        return true;
    }

    // shoot 3, move to intake 1, intake 1, move to intake 1, intake 1, move, shoot
    public static boolean ShootIntake2Shoot() {
        shoot();
        RobotContainer.m_drivetrain.driveDistance(1., .577, 107.);
        startIntake();
        Timer.delay(.5);
        stopIntake();
        // 1 MORE INTAKE
        RobotContainer.m_drivetrain.driveDistance(1., 0., 38.);
        RobotContainer.m_drivetrain.rotateToHeading(1., 45.);
        shoot();
        return true;
    }

    public static boolean EightBallCenter() {
        return true;
    }

    // AUTO METHODS
    // just makes everything a litte cleaner and easier

    private static void shoot() {
        CommandScheduler.getInstance().schedule(new TargetTrackModeEngage());
        while (RobotContainer.m_drivetrain.getTxAvg() > -1. && RobotContainer.m_drivetrain.getTxAvg() < 1.) {
            RobotContainer.m_drivetrain.move(0, 0, 0);
        }
        CommandScheduler.getInstance().schedule(new TargetTrackModeDisengage());
        CommandScheduler.getInstance().schedule(new ShootBall());
        while (!RobotContainer.m_ShootBall.getForceEnd()) {
            CommandScheduler.getInstance().run();
        }
    }

    private static void startIntake() {
        RobotContainer.m_intake.intakeBall();
    }

    private static void stopIntake() {
        RobotContainer.m_intake.stopIntake();
    }
}
