// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.hold;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hold extends SubsystemBase {
  /** Creates a new Hold. */
  public Hold() {}
  
  public void toShooter() {
    HoldMAP.holdMotor.set(HoldMAP.DEFAULT_SPEED);
  }

  public void eject() {
    HoldMAP.holdMotor.set(-HoldMAP.DEFAULT_SPEED);
  }

  public void variableHold(double speed) {
    if (speed*-1 > HoldMAP.MAX_SPEED) {
      HoldMAP.holdMotor.set(speed);
    }
     else {
      HoldMAP.holdMotor.set(HoldMAP.MAX_SPEED);
    }
  }

  public void stop() {
    HoldMAP.holdMotor.set(0.0);
  }


@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
