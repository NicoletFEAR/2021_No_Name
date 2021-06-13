package frc.robot.climb;

import frc.robot.climb.*; // imports everything in the drivebase folder - "*" means all in folder

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climb extends SubsystemBase {
    /**
     * Operates Climb.
     */

    // Our Methods HERE
    public void runClimb() { // 
        ClimbMAP.climbMotor.set(ClimbMAP.DEFAULT_SPEED);
    }

    public void reverse() { // reverse of default direction at a constant speed
        ClimbMAP.climbMotor.set(-ClimbMAP.DEFAULT_SPEED);
    }

    public void variableClimb(double speed) {
        if (speed < ClimbMAP.MAX_SPEED) {
            ClimbMAP.climbMotor.set(speed);
        } else {
            ClimbMAP.climbMotor.set(ClimbMAP.MAX_SPEED);
        }
        
    }


    public void stop() {
        ClimbMAP.climbMotor.set(0.0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
    

