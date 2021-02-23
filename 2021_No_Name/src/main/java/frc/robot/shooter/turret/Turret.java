// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Turret extends SubsystemBase {
  //Operates Turret
  double movementVal;
  int encoderPos;
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

  public void addToTurretSetpoint(int targetChange) { // a range of -23 to 23
    encoderPos = (TurretMAP.turretEncoder.getPulseWidthPosition() - TurretMAP.initEncoderZero);

    movementVal = targetChange/2; //scale

    // if (Math.abs(movementVal) < 0.05) {
    //     movementVal = 0;
    
    if (targetChange > 0 && encoderPos >= TurretMAP.MAX_ENCODER) {
        movementVal = 0;
    } else if (movementVal < 0 && encoderPos <= TurretMAP.MIN_ENCODER) {
        movementVal = 0;
    }

    if (movementVal >= 0.28) {movementVal = 0.28;}
    else if (movementVal <= -0.28) {movementVal = -0.28;}

    TurretMAP.turretMotor.set(ControlMode.PercentOutput, movementVal);
  }

  public void turretToZero() {
    encoderPos = (TurretMAP.turretEncoder.getPulseWidthPosition() - TurretMAP.initEncoderZero);
    movementVal = - (encoderPos/2);
    TurretMAP.turretMotor.set(ControlMode.PercentOutput, movementVal);

  }
  public void stop() {
    TurretMAP.turretMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("TurretEncoder", (TurretMAP.turretEncoder.getPulseWidthPosition() - TurretMAP.initEncoderZero));
  }
}
