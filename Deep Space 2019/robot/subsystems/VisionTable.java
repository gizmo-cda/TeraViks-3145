/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.ArrayList;


public class VisionTable extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Double x, y, area;
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx, ty, ta;
  private ArrayList<Double> visionTableValues = new ArrayList<Double>(3);

  public VisionTable() {
  }

  // Called repeatedly when this Command is scheduled to run
  public ArrayList<Double> getVisionTableValues() {
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");

    // read table values periodically
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    visionTableValues.set(0, x);
    visionTableValues.set(1, y);
    visionTableValues.set(2, area);

    // test with print statement
    System.out.println("X: "+x+"\nY: "+y+"\nArea: "+area);   

    return visionTableValues;
    }

    public void updateTableValues() {
        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area); 
    }

    public void setCamMode(int visionMode) {
        table.getEntry("camMode").setNumber(visionMode);
      }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
