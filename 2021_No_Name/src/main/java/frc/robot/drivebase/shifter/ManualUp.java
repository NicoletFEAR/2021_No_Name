// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivebase.shifter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.drivebase.DriveBaseMAP;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ManualUp extends InstantCommand {
  public ManualUp() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.shifter.shiftUp();
    //DriveBaseMAP.TURN_SCALING = SmartDashboard.getNumber("HIGH TURN", 0.55);
    //DriveBaseMAP.SPEED_SCALING = SmartDashboard.getNumber("HIGH SPEED", 0.6);
    //DriveBaseMAP.TURN_SCALING = 0.1;
    //DriveBaseMAP.SPEED_SCALING = 0.3;
    
    DriveBaseMAP.driveMotorL1.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
    DriveBaseMAP.driveMotorL2.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
    DriveBaseMAP.driveMotorL3.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
    DriveBaseMAP.driveMotorR1.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
    DriveBaseMAP.driveMotorR2.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
    DriveBaseMAP.driveMotorR3.setOpenLoopRampRate(DriveBaseMAP.DRIVE_RAMP_RATE_HIGH);
  }
}
