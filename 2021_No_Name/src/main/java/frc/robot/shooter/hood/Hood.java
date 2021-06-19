// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {

  public double setPoint;
  public double encoderPos;
  public double setSpeed = 0;

  private double movementVal;

  /** Creates a new Hood. */
  public Hood() {
  }

  public void init() {
  }

  // Passed adjusted input from OpenLoopHood
  public void setHoodSpeed(double adjustedInput) {
     HoodMAP.hoodMotor.set(adjustedInput);
    
  }

  public void stop() {
    HoodMAP.hoodMotor.set(0.0);
  }

  public void setHoodSetpoint(double hoodSet) {
    encoderPos = (HoodMAP.hoodEncoder.getPosition() - HoodMAP.hoodInitEncoderZero);
    if (hoodSet > HoodMAP.MAX_ENCODER) {
      hoodSet = HoodMAP.MAX_ENCODER;
    } else if (hoodSet < HoodMAP.MIN_ENCODER) {
      hoodSet = HoodMAP.MIN_ENCODER;
    }
    movementVal = (hoodSet - encoderPos) / 10; // scale
    
    // if (hoodSet > 0 && encoderPos >= HoodMAP.MAX_ENCODER) {
    //   movementVal = 0;
    // } else if (movementVal < 0 && encoderPos <= HoodMAP.MIN_ENCODER) {
    //   movementVal = 0;
    // }
    
    if (Math.abs(movementVal) < 0.05) {
      movementVal = 0;
    }

    if (movementVal >= HoodMAP.MAX_SPEED) {
      movementVal = HoodMAP.MAX_SPEED;
    } else if (movementVal <= -HoodMAP.MAX_SPEED) {
      movementVal = -HoodMAP.MAX_SPEED;
    }

    //SmartDashboard.putNumber("HOOD MVMT VAL", movementVal);
    HoodMAP.hoodMotor.set(movementVal);
  }

  @Override
  public void periodic() {

  }
}
