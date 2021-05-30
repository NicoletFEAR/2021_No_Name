/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivebase.shifter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivebase.*;
import frc.robot.*;

public class ShiftUpHold extends CommandBase {
  /**
   * Creates a new ShiftUpSpindexerCMD.
   */
  public ShiftUpHold() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.shifter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.shifter.shiftUp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.shifter.shiftDown();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
