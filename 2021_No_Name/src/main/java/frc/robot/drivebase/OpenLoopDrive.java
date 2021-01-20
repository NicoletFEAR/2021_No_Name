package frc.robot.drivebase;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class OpenLoopDrive extends CommandBase {
  
  double forwardVal;
  double backwardVal; 
  double turnVal;
  double robotOutput;

  public OpenLoopDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.driveBase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    forwardVal = Robot.oi.getDriveRightTrigger();
    backwardVal = Robot.oi.getDriveLeftTrigger();
    turnVal = Robot.oi.getDriveSteer();

    robotOutput = forwardVal - backwardVal;

    // Pass the values into RacingDrive
    Robot.driveBase.RacingDrive(robotOutput, turnVal);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.driveBase.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
