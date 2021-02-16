// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autonomous;

import com.google.gson.Gson;
// https://github.com/google/gson/blob/master/UserGuide.md#TOC-Primitives-Examples 

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj2.command.CommandBase;

// to call
// new Command​(String name, double timeout)?
// then u call .start()?
// or actually it might be scheduler.getInstance.add(Command command)
// https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Scheduler.html 


public class Record extends CommandBase {
  /** Creates a new Record. */
  Scheduler localScheduler;

  public Record() {
    // no dependencies so that all of the other normal commands can run
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Gson gson = new Gson();
    //gson.toJson();
    localScheduler = Scheduler.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // plan to implement scheduler stuff later to make more adaptable
    // localScheduler.
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
