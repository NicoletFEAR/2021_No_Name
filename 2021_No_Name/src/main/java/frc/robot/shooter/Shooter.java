package frc.robot.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.shooter.*;
import com.revrobotics.ControlType;

public class Shooter extends SubsystemBase {
    /**
     * Operates Shooter.
     */

    // Our Methods HERE

    public void runHold() {
        ShooterMAP.holdMotor.set(ShooterMAP.DEFAULT_HOLD_SPEED);
    }

    public void setHoodPID(int target) {
        //ShooterMAP.holdPIDController.setReference(setPoint, ControlType.kPosition);
        // set to target PID
    }

    public void setFlywheelPID(int target) {
        ShooterMAP.flywheelPIDController.setReference(target, ControlType.kVelocity);
        // set target velocity for flywheel PID
    }

    public void setTurretPID(int target) {
        // use can talon srx pid
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}