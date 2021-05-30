/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivebase.shifter;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drivebase.*;
public class AutoShift extends CommandBase {

  double avgVel;

  public AutoShift() {
    // this subsystem requires shifter, which means that it is interrupted when
    // another command that requires shifter is called
    addRequirements(Robot.shifter);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    DriveBaseMAP.shifty.set(DriveBaseMAP.LOW_GEAR);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {

 
 //   SmartDashboard.putNumber("avgvel", avgVel);

    avgVel =calcAvgVel();

    if (Robot.oi.xbox0.getBumper(Hand.kLeft)) { // check the driver is spindexering down the low gear button
      if (DriveBaseMAP.shifty.get() == DriveBaseMAP.HIGH_GEAR) { //check that robot is not already in low gear
        Robot.shifter.shiftDown();
      }
    } 
    else if (Robot.oi.xbox0.getBumper(Hand.kRight)) { // check the driver is spindexering down the high gear button
      if (DriveBaseMAP.shifty.get() == DriveBaseMAP.LOW_GEAR) { //check that robot is not already in high gear
        Robot.shifter.shiftUp();
      }
    } else if (avgVel > DriveBaseMAP.SHIFT_UP_THRESHOLD) { // if in low, switch to high
        if (DriveBaseMAP.shifty.get() == DriveBaseMAP.LOW_GEAR) {
          Robot.shifter.shiftUp();
        }
    } else if (avgVel < DriveBaseMAP.SHIFT_DOWN_THRESHOLD) {
      if (DriveBaseMAP.shifty.get() == DriveBaseMAP.HIGH_GEAR) {
        Robot.shifter.shiftDown();
      }
    }
  }

  public double calcAvgVel() {
    return (Math.abs(DriveBaseMAP.getNEOEncoderVelocity(DriveBaseMAP.driveMotorL1))
        + Math.abs(DriveBaseMAP.getNEOEncoderVelocity(DriveBaseMAP.driveMotorR1))) / 2; // average absolute velocity of
                                                                                        // the two sides
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true or interrupted
  @Override
  public void end(boolean isInterrupted) {
  } 
}
