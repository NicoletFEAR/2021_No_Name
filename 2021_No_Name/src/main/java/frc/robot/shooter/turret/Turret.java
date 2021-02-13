// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Turret extends SubsystemBase {
  //Operates Turret

  // Our Methods HERE

  public void setTurretMotorSpeed(double speed) {
    TurretMAP.turretMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setTurretPID(int target) {
    // use can talon srx pid
    if (target < TurretMAP.MAX_ENCODER && target > TurretMAP.MIN_ENCODER) {
      // use target value to set
    } else if (target >= TurretMAP.MAX_ENCODER) {
      //use max encoder value
    } else {
      //use min encoder value
    }
  }

  public void addToTurretSetpoint(int targetChange) {
    // change the setpoint BY a certain value
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
