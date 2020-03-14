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
 * PATH INDEX
 * <p>
 * TargetStart: 
 * {@link TargetStart#twoBallCenter() twoBallCenter},
 * {@link TargetStart#threeBallCenter() threeBallCenter},
 * {@link TargetStart#threeBallTrench() threeBallTrench},
 * {@link TargetStart#fiveBallTrench() fiveBallTrench},
 * {@link TargetStart#twoBallCenterThreeBallTrench() twoBallCenterThreeBallTrench},
 * {@link TargetStart#twoBallCenterFiveBallTrench() twoBallCenterFiveBallTrench}
 * <p>
 * SideStart: 
 * {@link SideStart#twoBallCenter() twoBallCenter},
 * {@link SideStart#threeBallCenter() threeBallCenter},
 * {@link SideStart#twoBallCenterFiveBallTrench() twoBallCenterFiveBallTrench},
 * {@link SideStart#threeBallTrench() threeBallTrench},
 * {@link SideStart#fiveBallTrench() fiveBallTrench},
 * {@link SideStart#threeBallTrenchTwoBallCenter() threeBallTrenchTwoBallCenter}
 * <p>
 * FarSideStart: 
 * {@link FarSideStart#twoBallTrenchThreeBallCenter() twoBallTrenchThreeBallCenter},
 * {@link FarSideStart#twoBallTrenchFiveBallCenter() twoBallTrenchFiveBallCenter}
 */
public class AutoPaths {
    private static double startDegree;

    public AutoPaths() {
    }

    public static boolean driveForward() {
        driveDistance(1., 0., 36.);
        return true;
    }

    public static class TargetStart extends AutoPaths {
        public static boolean twoBallCenter() {
            driveDistance(1., -.158, 120.32);
            driveDistance(.532, 1., 60.8);
            rotateToHeading(.4, 157.);

            startIntake();
            // Intaking
            driveDistance(.3, 0., 35.2);
            stopIntake();

            driveDistance(0., 1., 25.6);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }

        public static boolean threeBallCenter() {
            shoot();
            rotateToHeading(.4, -111.);

            startIntake();
            driveDistance(-.674, 1., -181.76);
            // Intaking
            driveDistance(.3, 0., 51.2);
            stopIntake();

            driveDistance(0., -1., -32.);
            driveDistance(1., 0., 76.8);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }

        public static boolean threeBallTrench() {
            shoot();

            startIntake();
            driveDistance(.384, -1., 140.8);
            // Intaking
            driveDistance(.3, 0., 83.2);
            stopIntake();

            driveDistance(-1., .577, -140.8);
            shoot();
            return true;
        }

        public static boolean fiveBallTrench() {
            shoot();

            startIntake();
            driveDistance(.384, -1., 140.8);
            // Intaking
            driveDistance(.3, 0., 147.3);
            stopIntake();

            driveDistance(-1., 1., -64.);
            driveDistance(-1., .577, -140.8);
            shoot();
            return true;
        }

        public static boolean twoBallCenterThreeBallTrench() {
            twoBallCenter();

            driveDistance(-1., -.424, 99.2);
            startIntake();
            driveDistance(.3, 0., 97.28);
            stopIntake();
            rotateToHeading(.4, -13.);
            shoot();
            return true;
        }

        public static boolean twoBallCenterFiveBallTrench() {
            twoBallCenter();

            driveDistance(-1., -.424, 99.2);
            startIntake();
            driveDistance(.3, 0., 163.84);
            Timer.delay(.3);
            stopIntake();
            driveDistance(-1., 0., -66.56);
            rotateToHeading(.4, -13.);
            shoot();
            return true;
        }
    }

    public static class SideStart extends AutoPaths {
        public static boolean twoBallCenter() {
            driveDistance(.532, 1., 195.2);
            rotateToHeading(.4, 157.);

            startIntake();
            // Intaking
            driveDistance(.3, 0., 35.2);
            stopIntake();

            driveDistance(0., 1., 25.6);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }

        public static boolean threeBallCenter() {
            driveDistance(0., 1., 83.2);
            shoot();
            rotateToHeading(.4, -111.);

            startIntake();
            driveDistance(-.674, 1., -181.76);
            // Intaking
            driveDistance(.3, 0., 51.2);
            stopIntake();

            driveDistance(0., -1., -32.);
            driveDistance(1., 0., 76.8);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }

        public static boolean twoBallCenterFiveBallTrench() {
            twoBallCenter();

            driveDistance(-1., -.424, 99.2);
            startIntake();
            driveDistance(.3, 0., 163.84);
            Timer.delay(.3);
            stopIntake();
            driveDistance(-1., 0., -66.56);
            rotateToHeading(.4, -13.);
            shoot();
            return true;
        }

        public static boolean threeBallTrench() {
            driveDistance(0., 1., 83.2);
            shoot();

            startIntake();
            driveDistance(.384, -1., 140.8);
            // Intaking
            driveDistance(.3, 0., 83.2);
            stopIntake();

            driveDistance(-1., .577, -140.8);
            shoot();
            return true;
        }

        public static boolean fiveBallTrench() {
            driveDistance(0., 1., 83.2);
            shoot();

            startIntake();
            driveDistance(.384, -1., 140.8);
            // Intaking
            driveDistance(.3, 0., 147.3);
            stopIntake();

            driveDistance(-1., 1., -64.);
            driveDistance(-1., .577, -140.8);
            shoot();
            return true;
        }

        public static boolean threeBallTrenchTwoBallCenter() {
            driveDistance(1., .966, 104.96);
            shoot();
            driveDistance(1., -.81, 89.6);

            startIntake();
            driveDistance(3., 0., 87.75);
            stopIntake();

            rotateToHeading(.4, 157.);
            driveDistance(1., -.933, -89.6);

            startIntake();
            // Intaking
            driveDistance(.3, 0., 93.44);
            stopIntake();

            driveDistance(0., -1., 25.6);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }
    }

    public static class FarSideStart extends AutoPaths {

        public static boolean twoBallTrenchThreeBallCenter() {
            startIntake();
            driveDistance(1., .087, 135.68);
            Timer.delay(.3);
            stopIntake();

            driveDistance(-1., 0., -25.6);
            driveDistance(0., -1., -92.8);
            rotateToHeading(.4, -28.);
            shoot();
            rotateToHeading(.4, -111.);

            startIntake();
            driveDistance(0., -1., 33.28);
            // Intaking
            driveDistance(.3, 0., 38.4);
            stopIntake();

            driveDistance(0., -1., 44.8);
            driveDistance(1., 0., 83.2);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }

        public static boolean twoBallTrenchFiveBallCenter() {
            startIntake();
            driveDistance(1., .087, 135.68);
            Timer.delay(.3);
            stopIntake();

            driveDistance(-1., 0., -25.6);
            driveDistance(0., -1., -92.8);
            rotateToHeading(.4, -28.);
            shoot();
            rotateToHeading(.4, -111.);

            startIntake();
            driveDistance(0., -1., 33.28);
            // Intaking
            driveDistance(.3, 0., 41.6);
            rotateToHeading(.4, 159.);
            driveDistance(-1., 0., -51.2);
            driveDistance(.3, 0., 32.);
            stopIntake();

            driveDistance(0., 1., 25.6);
            rotateToHeading(.4, 0.);
            shoot();
            return true;
        }
    }

    // AUTO METHODS
    // just makes everything a litte cleaner and easier to read

    private static void shoot() {
        startDegree = RobotContainer.m_gyro.getYawDeg();

        CommandScheduler.getInstance().schedule(new TargetTrackModeEngage());
        // CommandScheduler.getInstance().run();

        RobotContainer.m_drivetrain.move(0, 0, 0);
        while (RobotContainer.m_drivetrain.getTxAvg() < -1. && RobotContainer.m_drivetrain.getTxAvg() > 1.) {
            RobotContainer.m_drivetrain.move(0, 0, 0);
        }

        CommandScheduler.getInstance().schedule(new TargetTrackModeDisengage());
        // CommandScheduler.getInstance().run();

        CommandScheduler.getInstance().schedule(new ShootBall());
        while (!RobotContainer.m_ShootBall.getForceEnd()) {
            CommandScheduler.getInstance().run();
        }

        rotateToHeading(.4, startDegree);
    }

    private static void startIntake() {
        RobotContainer.m_intake.intakeBall();
    }

    private static void stopIntake() {
        RobotContainer.m_intake.stopIntake();
    }

    /**
     * Tells Drivetrain to move a distance. Distance is in +/- inches, drives the
     * specified distance and returns true once the drive train stops. Can only be
     * used in Autonomous when DRIVE is not on the command stack
     * 
     * @param fwd      forward power (+ forward, - backwards)
     * @param str      strafe power (+ right, - left)
     * @param distance in +/- inches
     */
    private static void driveDistance(double fwd, double str, double distance) {
        RobotContainer.m_drivetrain.driveDistance(fwd, str, distance);
    }

    /**
     * This method rotates to the absolute heading +/-180 and returns true after the
     * robot is stopped Can only be used in Autonomous when DRIVE is not on the
     * command stack
     * 
     * @param rcw     rotate speed
     * @param heading degrees relative to field
     */
    private static void rotateToHeading(double rcw, double heading) {
        RobotContainer.m_drivetrain.rotateToHeading(rcw, heading);
    }
}
