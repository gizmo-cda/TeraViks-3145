/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.commands.LoadMagazine;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Magazine extends SubsystemBase {
  private final WPI_TalonFX magazineMotor = new WPI_TalonFX(RobotMap.MAGAZINE_TalonFX_CAN_ID);

  private final DigitalInput ballReadyToLoad = new DigitalInput(RobotMap.BALL_READY_TO_LOAD);
  private final DigitalInput ballInFirstPos = new DigitalInput(RobotMap.BALL_IN_FIRST_POSITION);
  public final DigitalInput ballInFifthPos = new DigitalInput(RobotMap.BALL_IN_FIFTH_POSITION);

  private int TIMEOUT = RobotMap.TalonFX_TIMEOUT;

  private boolean magFull;
  private boolean ballReady;
  private boolean ballLoaded;
  private boolean isExistingBall;
  private boolean canSeeBall;
  private boolean hasSeenBall = false;
  private int ballCount = 0;
  private int ballReadyCount = 0;
  private boolean stopLoad = false;
  private boolean stopShoot = false;
  private double magPosition;

  /**
   * Creates a new Magazine.
   */
  public Magazine() {

  }

  public void advanceMagazine() {
    magazineMotor.set(ControlMode.PercentOutput, .4);
    System.out.println("advancing mag");
  }

  public void stopMagazine() {
    magazineMotor.set(ControlMode.PercentOutput, 0.);
    System.out.println("stopping mag");
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
      stopLoad = false;
      if (ballReady) {
        ballReadyCount += 1;
      }

      // This conditional is true after the ball is ready to be loaded for .1 seconds
      if (ballReadyCount == 5) {
        // This captures the state of the first ball position when we start the motor so
        // that we know if there was already a ball there.
        advanceMagazine();
      }

      if (ballReadyCount >= 5) {
        // This tests to see if there is no longer an existing ball in the first
        // position
        // if (isExistingBall) {
        // isExistingBall = ballLoaded;
        // }

        // This tests if the new ball has been loaded
        if (!ballLoaded && isExistingBall) {
          stopMagazine();

          if (ballCount < 5) {
            ballCount += 1;
          }

          ballReadyCount = 0;
        }
        isExistingBall = ballLoaded;
      }
      stopLoad = false;
    } else {
      if (ballReadyCount >= 5) {
        stopMagazine();
        ballReadyCount = 0;
        // RobotContainer.m_led.magFullLED();
      }
      stopLoad = true;
    }
  }

  public boolean getStopLoad() {
    return stopLoad;
  }

  // This method uses the end sensor to count the balls as they exit the magazine
  // and stop it when the ball count reaches 0. This method uses local variables
  // to track the state of the balls exiting the magazine.
  public boolean emptyMagazine() {
    stopShoot = false;
    magPosition = magazineMotor.getSelectedSensorPosition();

    if (CommandScheduler.getInstance().isScheduled(new LoadMagazine())) stopLoad = true;

    // This gets the sensor values every cycle of the scheduler
    canSeeBall = !ballInFifthPos.get();
    // This gets the state of the shoot button
    // isShootPressed = RobotContainer.getShootButtonState();

    // This removes the command that loads the magazine from the scheduler stack so
    // that it does not interfere with the magazine being emptied.

    if (hasSeenBall) {
      // This tests whether the ball that was being unloaded has finished unloading.
      if (!canSeeBall) {
        ballCount -= 1;
        hasSeenBall = false;
      }
    } else {
      // This tests if there is a ball currently being unloaded in front of the
      // sensor.
      if (canSeeBall) {
        hasSeenBall = true;
      }
    }

    if (magPosition < RobotMap.MAGAZINE_LENGTH) {
      advanceMagazine();    
    } else {
      stopMagazine();
      RobotContainer.m_shooter.stopMotors();
      CommandScheduler.getInstance().schedule(new LoadMagazine());
      stopShoot = true;
      // RobotContainer.m_led.driveLED();
      
      // RobotContainer.m_led.clearStatusLED();
      RobotContainer.m_tilt.setTiltLow();
      ballCount = 0;
    }
    // This Reschedules the load magazine command and stops the shoot ball command
    // after the mag has finished unloading and the shoot button is no longer being
    // pressed.
    return stopShoot;
  }

  public int getBallCount() {
    return ballCount;
  }

  public boolean getMagFull() {
    return ballInFifthPos.get();
  }

  public boolean getBallLoaded() {
    return ballInFifthPos.get();
  }

  public boolean getBallReady() {
    return !ballReadyToLoad.get();
  }

  public void resetPosition() {
    magazineMotor.setSelectedSensorPosition(0);
  }

  public void init() {
    magazineMotor.configFactoryDefault();

    magazineMotor.setInverted(false);

    magazineMotor.setNeutralMode(NeutralMode.Brake);

    magazineMotor.configPeakOutputForward(1., TIMEOUT);
    magazineMotor.configPeakOutputReverse(-1., TIMEOUT);

    magazineMotor.configNominalOutputForward(0, TIMEOUT);
    magazineMotor.configNominalOutputReverse(0, TIMEOUT);

    System.out.println("  - Magazine Motor Initialized");
  }

  private void delay(int msec){
    try{
        Thread.sleep(msec);
    }
    catch (Exception e){
        System.out.println("Error in Waitloop");
    }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
