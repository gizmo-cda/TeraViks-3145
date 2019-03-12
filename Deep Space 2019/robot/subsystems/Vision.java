/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.gps.Target;

import java.util.HashMap;


public class Vision extends Subsystem {
  
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  
  private NetworkTableEntry tx= table.getEntry("tx");  //Horizontal (relatie to cam) offset from target: left 0<x<27, right -27<x<0
  private NetworkTableEntry ty= table.getEntry("ty");  //Vertical (relative to cam) offset from target: below 0<y<20.5, above -20.5<y<0
  private NetworkTableEntry ta= table.getEntry("ta");  //Area of target: 0-100% of image
  private NetworkTableEntry tv= table.getEntry("tv");  //Valid target = 1
  
  private HashMap<String, Double> visionValues = new HashMap<String, Double>();
  
  private Target m_target = new Target();
  
  public Vision() {
  }
  
  public void setCamMode(int visionMode) {
    table.getEntry("camMode").setNumber(visionMode);
  }

  public void ledOff() {
    table.getEntry("ledMode").setNumber(1); // 1 is force off, 0 is default for pipeline, 3 is force on
  }

  public void ledOn() {
    table.getEntry("ledMode").setNumber(3); // 1 is force off, 0 is default for pipeline, 3 is force on
  }
  
  public int getCamMode(){
    return (int) table.getEntry("camMode").getDouble(0.);
  }
  
  public void setPipeline(int newPipeline){
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(newPipeline);   
  }
  
  public boolean acquireTarget() {
    Timer.delay(0.5);
    double lock = getTv();
    System.out.println("tv:" + lock);
    // double lock = 1.;
    System.out.println("////Aquire Target////");
    
    if ((int) lock == 1) {
      System.out.println("////Move to target////");
      m_target.moveToTarget();
      return true; 
    } 
    else return false;
  }
  
  public double getTv(){
    return getVisionValues().get("tv");
  }
  public double getTx(){
    return getVisionValues().get("tx");
  }
  // Network Table is quite verbose and contains more than needed, so clean it up and make it simple for just what is needed
  public HashMap<String, Double> getVisionValues() {
    // tx = table.getEntry("tx");
    // ty = table.getEntry("ty");
    // ta = table.getEntry("ta");
    // tv = table.getEntry("tv");
    
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
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
