package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.shooter.*; //if its in the shooter package does it still need this import?
import frc.robot.shooter.turret.TurretMAP;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.ControlType;

public class Shooter extends SubsystemBase {
    /**
     * Operates Shooter.
     */

    // Our Methods HERE

    public void stop() {
        ShooterMAP.flywheelMotor.set(0.0);
    }

    public void setShootSpeed(double adjustedSpeed) {
        ShooterMAP.flywheelMotor.set(adjustedSpeed);
    }

    public void setHoodPID(int target) {
        // ShooterMAP.holdPIDController.setReference(setPoint, ControlType.kPosition);
        // set to target PID
    }

    public void setFlywheelPID(int target) {
        ShooterMAP.flywheelPIDController.setReference(target, ControlType.kVelocity);
        // set target velocity for flywheel PID
    }
 

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}