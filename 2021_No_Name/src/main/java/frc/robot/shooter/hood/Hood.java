// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

public class Hood extends SubsystemBase {

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
  public double setPoint;
  
  /** Creates a new Hood. */
  public Hood() {
  }

  public void init() {
    //Get motor from HoodMAP
    //HoodMAP.hoodMotor
    //HoodMAP.hoodEncoder
    //HoodMAP.hoodController

    setPoint = 0; //Target number of ticks
    
     // PID coefficients
     kP = 5e-5; 
     kI = 1e-6;
     kD = 0; 
     kIz = 0; 
     kFF = 0.000156; 
     kMaxOutput = 1; 
     kMinOutput = -1;
     maxRPM = 5700;

     // Smart Motion Coefficients
    maxVel = 2000; // rpm
    maxAcc = 1500;

     // set PID coefficients
     HoodMAP.hoodController.setP(kP);
     HoodMAP.hoodController.setI(kI);
     HoodMAP.hoodController.setD(kD);
     HoodMAP.hoodController.setIZone(kIz);
     HoodMAP.hoodController.setFF(kFF);
     HoodMAP.hoodController.setOutputRange(kMinOutput, kMaxOutput);

     /**
     * Smart Motion coefficients are set on a CANPIDController object
     * 
     * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
     * the pid controller in Smart Motion mode
     * - setSmartMotionMinOutputVelocity() will put a lower bound in
     * RPM of the pid controller in Smart Motion mode
     * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
     * of the pid controller in Smart Motion mode
     * - setSmartMotionAllowedClosedLoopError() will set the max allowed
     * error for the pid controller in Smart Motion mode
     */

    int smartMotionSlot = 0;
    HoodMAP.hoodController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    HoodMAP.hoodController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    HoodMAP.hoodController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    HoodMAP.hoodController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);


    if (Robot.debugMode) {
      // display PID coefficients on SmartDashboard
      SmartDashboard.putNumber("Hood P Gain", kP);
      SmartDashboard.putNumber("Hood I Gain", kI);
      SmartDashboard.putNumber("Hood D Gain", kD);
      SmartDashboard.putNumber("Hood I Zone", kIz);
      SmartDashboard.putNumber("Hood Feed Forward", kFF);
      SmartDashboard.putNumber("Hood Max Output", kMaxOutput);
      SmartDashboard.putNumber("Hood Min Output", kMinOutput);

      // display Smart Motion coefficients
      SmartDashboard.putNumber("Hood Max Velocity", maxVel);
      SmartDashboard.putNumber("Hood Min Velocity", minVel);
      SmartDashboard.putNumber("Hood Max Acceleration", maxAcc);
      SmartDashboard.putNumber("Hood Allowed Closed Loop Error", allowedErr);
      SmartDashboard.putNumber("Hood Set Position", 0);
      SmartDashboard.putNumber("Hood Set Velocity", 0);

      // button to toggle between velocity and smart motion modes
      SmartDashboard.putBoolean("Hood Mode", true);
    }
  }

  //Passed adjusted input from OpenLoopHood
  public void setHoodSpeed(double adjustedInput) {
    HoodMAP.hoodMotor.set(adjustedInput);
  }

  public void stop() {
    HoodMAP.hoodMotor.set(0.0);
  }

  // will finish later
  public void setHoodTargetPID(int targetVal) {
    // set the built in SparkMax PID
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // read PID coefficients from SmartDashboard
    if (Robot.debugMode) {
      double p = SmartDashboard.getNumber("Hood P Gain", 0);
      double i = SmartDashboard.getNumber("Hood I Gain", 0);
      double d = SmartDashboard.getNumber("Hood D Gain", 0);
      double iz = SmartDashboard.getNumber("Hood I Zone", 0);
      double ff = SmartDashboard.getNumber("Hood Feed Forward", 0);
      double max = SmartDashboard.getNumber("Hood Max Output", 0);
      double min = SmartDashboard.getNumber("Hood Min Output", 0);
      double maxV = SmartDashboard.getNumber("Hood Max Velocity", 0);
      double minV = SmartDashboard.getNumber("Hood Min Velocity", 0);
      double maxA = SmartDashboard.getNumber("Hood Max Acceleration", 0);
      double allE = SmartDashboard.getNumber("Hood Allowed Closed Loop Error", 0);

      // if PID coefficients on SmartDashboard have changed, write new values to controller
      if((p != kP)) { HoodMAP.hoodController.setP(p); kP = p; }
      if((i != kI)) { HoodMAP.hoodController.setI(i); kI = i; }
      if((d != kD)) { HoodMAP.hoodController.setD(d); kD = d; }
      if((iz != kIz)) { HoodMAP.hoodController.setIZone(iz); kIz = iz; }
      if((ff != kFF)) { HoodMAP.hoodController.setFF(ff); kFF = ff; }
      if((max != kMaxOutput) || (min != kMinOutput)) { 
        HoodMAP.hoodController.setOutputRange(min, max); 
        kMinOutput = min; kMaxOutput = max; 
      }

      if((maxV != maxVel)) { HoodMAP.hoodController.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
      if((minV != minVel)) { HoodMAP.hoodController.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
      if((maxA != maxAcc)) { HoodMAP.hoodController.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
      if((allE != allowedErr)) { HoodMAP.hoodController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }
    }
    
    double processVariable;
    boolean mode = SmartDashboard.getBoolean("Hood Mode", false);
    if(mode) {
      //setPoint = SmartDashboard.getNumber("Hood Set Velocity", 0);
      HoodMAP.hoodController.setReference(setPoint, ControlType.kVelocity);
      processVariable = HoodMAP.hoodEncoder.getVelocity();
    } else {
      //setPoint = SmartDashboard.getNumber("Hood Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      HoodMAP.hoodController.setReference(setPoint, ControlType.kSmartMotion);
      processVariable = HoodMAP.hoodEncoder.getPosition();
    }
    
    SmartDashboard.putNumber("Hood SetPoint", setPoint);
    SmartDashboard.putNumber("Hood Process Variable", processVariable);
    SmartDashboard.putNumber("Hood Output", HoodMAP.hoodMotor.getAppliedOutput());
  }
}
