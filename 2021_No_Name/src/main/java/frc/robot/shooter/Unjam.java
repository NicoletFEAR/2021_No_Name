// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drivebase.DriveBaseMAP;
import frc.robot.shooter.hood.HoodMAP;

public class Unjam extends CommandBase {



  /** Creates a new Unjam. */
  public Unjam() {
    addRequirements(Robot.shooter);
    addRequirements(Robot.spindexer);
    addRequirements(Robot.kicker);
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //ShooterMAP.doShoot = false;
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    Robot.kicker.spinBackwards();
    Robot.spindexer.variableSpindexer(0.08);
    ShooterMAP.m_pidController.setReference(-10, ControlType.kVelocity);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    ShooterMAP.m_pidController.setReference(0, ControlType.kVelocity);
    ShooterMAP.doShoot = false;
    Robot.kicker.stop();
    Robot.shooter.stop();
    Robot.spindexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
