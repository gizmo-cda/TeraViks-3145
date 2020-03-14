/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.HashMap;


public class ShooterCam extends SubsystemBase {
  
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-shooter");
  
  private NetworkTableEntry tx= table.getEntry("tx");  //Horizontal (relatie to cam) offset from target: left 0<x<27, right -27<x<0
  private NetworkTableEntry ty= table.getEntry("ty");  //Vertical (relative to cam) offset from target: below 0<y<20.5, above -20.5<y<0
  private NetworkTableEntry ta= table.getEntry("ta");  //Area of target: 0-100% of image
  private NetworkTableEntry tv= table.getEntry("tv");  //Valid target = 1
  
  private HashMap<String, Double> visionValues = new HashMap<String, Double>();
    
  public ShooterCam() {
  }

  /** 0 is pipeline/tracking, 1 is driver cam (no tracking, higher exposure) */
  public void setCamMode(int visionMode) {
    table.getEntry("camMode").setNumber(visionMode);
  }

  /** 1 is force off, 0 is default for pipeline, 3 is force on */
  public void ledOff() {
    table.getEntry("ledMode").setNumber(1); // 1 is force off, 0 is default for pipeline, 3 is force on
  }
  
  /** 1 is force off, 0 is default for pipeline, 3 is force on */
  public void ledOn() {
    table.getEntry("ledMode").setNumber(3); // 1 is force off, 0 is default for pipeline, 3 is force on
  }
  
  public int getCamMode(){
    return (int) table.getEntry("camMode").getDouble(0.);
  }
  
  public void setPipeline(int newPipeline){
    NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("pipeline").setNumber(newPipeline);   
  }
  
  public double getTv(){
    return getVisionValues().get("tv");
  }
  public double getTx(){
    return getVisionValues().get("tx");
  }

  public double getTy(){
    return getVisionValues().get("ty");
  }
  // Network Table is quite verbose and contains more than needed, so clean it up and make it simple for just what is needed
  public HashMap<String, Double> getVisionValues() {
    visionValues.put("tx", tx.getDouble(0.));  
    visionValues.put("ty", ty.getDouble(0.));  
    visionValues.put("ta", ta.getDouble(0.));  
    visionValues.put("tv", tv.getDouble(0.));  
    
    // test with print statement
    // System.out.println("\nTx: "+visionValues.get("tx")
    // +"\nTy: "+visionValues.get("ty")
    // +"\nTa: "+visionValues.get("ta")
    // +"\nTv: "+visionValues.get("tv"));
    
    return visionValues;
  }
  
  public void updateTableValues() {
    // post to smart dashboard periodically
    // SmartDashboard.putNumber("LimelightX", x);
    // SmartDashboard.putNumber("LimelightY", y);
    // SmartDashboard.putNumber("LimelightArea", area); 
  }
  
  /*@Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }*/
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
