/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Magazine extends SubsystemBase {
  private final WPI_TalonFX magazineMotor = new WPI_TalonFX(RobotMap.MAGAZINE_TalonFX_CAN_ID);

  private final DigitalInput ballReadyToLoad = new DigitalInput(RobotMap.BALL_READY_TO_LOAD);
  private final DigitalInput ballInFirstPos = new DigitalInput(RobotMap.BALL_IN_FIRST_POSITION);
  private final DigitalInput ballInFifthPos = new DigitalInput(RobotMap.BALL_IN_FIFTH_POSITION);

  private boolean magFull;
  private boolean ballReady;
  private boolean ballLoaded;
  private boolean isExistingBall;
  private int ballCount = 0;
  private int ballReadyCount = 0;

  /**
   * Creates a new Magazine.
   */
  public Magazine() {

  }

  public void advanceMagazine() {
    // magazineMotor.set(ControlMode.PercentOutput, 1.);
    System.out.println("Motor Running");
  }

  public void stopMagazine() {
    // magazineMotor.set(ControlMode.PercentOutput, 0.);
    System.out.println("Motor Stopped");
  }

  // *** a command is put on the stack that calls this method every cycle of the
  // scheduler. Because of this, there are local variables to track the state of
  // the ball loading into the magazine.
  public void loadMagazine() {
    // These get updated every shedular cycle.
    ballReady = !ballReadyToLoad.get();
    magFull = !ballInFifthPos.get();
    ballLoaded = !ballInFirstPos.get();

    if (!magFull) {
      if (ballReady) {
        ballReadyCount += 1;
      }

      // This conditional is true after the ball is ready to be loaded for .1 seconds
      if (ballReadyCount == 5) {
        // This captures the state of the first ball position when we start the motor so
        // that we know if there was already a ball there.
        isExistingBall = ballLoaded;
        advanceMagazine();
      }

      if (ballReadyCount >= 5) {
        // This tests to see if there is no longer an existing ball in the first
        // position
        if (isExistingBall) {
          isExistingBall = ballLoaded;
        }

        // This tests if the new ball has been loaded
        if (ballLoaded && !isExistingBall) {
          stopMagazine();
          ballCount += 1;
          ballReadyCount = 0;
        }
      }
    } else if(ballReadyCount >= 5) {
        stopMagazine();
        ballReadyCount = 0;
    }
  }

  public void shootBall() {

  }

  public void init() {
    /*
     * magazineMotor.configFactoryDefault();
     * 
     * magazineMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,
     * 0, TIMEOUT); magazineMotor.selectProfileSlot(0, 0); // slot #, PID #
     * 
     * magazineMotor.setInverted(false);
     * 
     * magazineMotor.setSelectedSensorPosition(0);
     * magazineMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
     * 
     * magazineMotor.configPeakOutputForward(1., TIMEOUT);
     * magazineMotor.configPeakOutputReverse(-1., TIMEOUT);
     * 
     * magazineMotor.configNominalOutputForward(0, TIMEOUT);
     * magazineMotor.configNominalOutputReverse(0, TIMEOUT);
     */

    System.out.println("  - Magazine Motor Initialized");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}