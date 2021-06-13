// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.spindexer;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SpinSmart extends CommandBase {
  /** Creates a new SpinSmart. */

  public boolean directionCC = true;
  private CANSparkMax motor = SpindexerMAP.spindexerMotor;
  private int counter = 0;

  public SpinSmart() {
    addRequirements(Robot.spindexer);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    directionCC = true;
    counter = 0;
    Robot.spindexer.spinClockwise();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    counter++;

    SmartDashboard.putNumber("spin_outputCurrent", motor.getOutputCurrent());
    SmartDashboard.putNumber("spin_velocity", motor.getEncoder().getVelocity());

    if (motor.getOutputCurrent() >= 200 & motor.getEncoder().getVelocity() <= 0 & counter > 10) {
      directionCC = !directionCC;
      if (directionCC) {Robot.spindexer.spinClockwise();}
      else {Robot.spindexer.spinCounterClockwise();}
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.spindexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
