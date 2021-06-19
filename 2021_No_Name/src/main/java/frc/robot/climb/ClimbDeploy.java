// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.climb.*;
import frc.robot.Robot;

public class ClimbDeploy extends CommandBase {
  /** Creates a new ClimbDeploy. */
  public ClimbDeploy() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.climb.runClimb();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    // if encoder past the top, return true;
    if (1 > 2) {
      return true;
    }
    return false;
  }
}
