/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DigitalInput;

public class DIO extends SubsystemBase {
  private static final DigitalInput test = new DigitalInput(0);
  /**
   * Creates a new DIO.
   */
  public DIO() {

  }

  public static void test(){
    System.out.println(test.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
