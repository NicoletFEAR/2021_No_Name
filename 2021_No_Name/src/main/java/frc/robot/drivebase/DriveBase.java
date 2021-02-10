package frc.robot.drivebase;

import frc.robot.drivebase.*; // imports everything in the drivebase folder - "*" means all in folder

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    /**
     * Creates a new DriveBase.
     */

    // VARIABLES
    public boolean isFront = true;
    double outputLeft = 0.0; // our driving speeds
    double outputRight = 0.0;
    double max;
    double multiplier;

    public void RacingDrive(double robotOutput, double turnAmount) { // range from -1.0 to 1.0
    
		//turnAmount *= DriveBaseMAP.TURN_SCALING; // in case we wanted to scale down all turning
        turnAmount = turnAmount < 0 ? -(turnAmount*turnAmount) : (turnAmount*turnAmount); // gives more precise low speed tunring (0->0, .5->.25, 1->1)
        outputLeft = -robotOutput + turnAmount; // sets the outputs for each side
        outputRight = robotOutput + turnAmount; // based on our desired speed and turning
		max = outputLeft < outputRight ? outputRight : outputLeft; 

        if (max > DriveBaseMAP.SPEED_SCALING)
			multiplier = DriveBaseMAP.SPEED_SCALING / (max);
		else
			multiplier = 1;

		outputLeft *= multiplier;
		outputRight *= multiplier;

		if (!isFront) { // if the robot is backwards, flip left and right
			double temp = outputLeft;
			outputLeft = outputRight;
			outputRight = temp;
		}



        DriveBaseMAP.driveMotorL1.set(outputLeft);    
        DriveBaseMAP.driveMotorR1.set(outputRight);  
        

    }

    public void tankDrive(double leftOutput, double rightOutput) {
        DriveBaseMAP.driveMotorL1.set(-leftOutput);
        DriveBaseMAP.driveMotorR1.set(rightOutput);
    }
  
    public void stop() {
        DriveBaseMAP.driveMotorL1.set(0.0);    
        DriveBaseMAP.driveMotorR1.set(0.0);    
    }

    public boolean isFrontFacing() {
		return isFront;
    }
  
	public void switchFront() {
		isFront = !isFront;
		SmartDashboard.putBoolean("isFront", isFront);
	}
  
    public DriveBase() {
        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}