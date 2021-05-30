// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.spindexer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Spindexer extends SubsystemBase {
  /** Creates a new Spindexer. */
  public Spindexer() {}
  

  public void spinClockwise() {
    SpindexerMAP.spindexerMotor.set(SpindexerMAP.DEFAULT_SPEED);
  }

  public void spinCounterClockwise() {
    SpindexerMAP.spindexerMotor.set(-SpindexerMAP.DEFAULT_SPEED);
  }

  public void variableSpindexer(double speed) {
    if (Math.abs(speed) < Math.abs(SpindexerMAP.MAX_SPEED)) {
      SpindexerMAP.spindexerMotor.set(speed);
    }
     else {
       if (speed > 0){
        SpindexerMAP.spindexerMotor.set(SpindexerMAP.MAX_SPEED);
       } else {
        SpindexerMAP.spindexerMotor.set(-SpindexerMAP.MAX_SPEED);
       }
    }
  }

  public void stop() {
    SpindexerMAP.spindexerMotor.set(0.0);
  }


@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
