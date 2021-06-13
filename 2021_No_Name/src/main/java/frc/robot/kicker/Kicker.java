// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.kicker;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.kicker.*;

public class Kicker extends SubsystemBase {
  /** Creates a new Spindexer. */
  public Kicker() {}
  

  public void spin() {
    KickerMAP.kickerMotor.set(KickerMAP.DEFAULT_SPEED);
  }

  public void spinBackwards() {
    KickerMAP.kickerMotor.set(-KickerMAP.DEFAULT_SPEED);
  }


  public void variableKicker(double speed) {
    if (Math.abs(speed) < Math.abs(KickerMAP.MAX_SPEED)) {
      KickerMAP.kickerMotor.set(speed);
    }
     else {
       if (speed > 0){
        KickerMAP.kickerMotor.set(KickerMAP.MAX_SPEED);
       } else {
        KickerMAP.kickerMotor.set(-KickerMAP.MAX_SPEED);
       }
    }
  }

  public void stop() {
    KickerMAP.kickerMotor.set(0.0);
  }


@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
