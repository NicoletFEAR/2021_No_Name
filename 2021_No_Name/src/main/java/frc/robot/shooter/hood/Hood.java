// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  public Hood() {
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
  }
}
