// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// String(Files.readAllBytes(Paths.get(file)))
// String file = "src/test/resources/myFile.json";

package frc.robot.autonomous;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Player extends CommandBase {

  double thisLine[] = new double[14];
  // double thisLine[] = new double[28]; // two controllers

  int currentLine = 0;
  public boolean isPlaying = false;

  //public Scanner scanner = null;
  long startTime;

  boolean onTime = true;
  double nextDouble;
  String autoToPlay = "BackupAuto";
  Gson gson;

  boolean isFinished = false;

  ArrayList<double[]> allLines;
  /** Creates a new Player. */
  public Player() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.driveBase);
    addRequirements(Robot.shifter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gson = new Gson();

    //String file = "src/test/resources/myFile.json";
    // writer = new FileWriter("/u/recordings" + newPlayName + ".json");

    //String stringJson = String(Files.readAllBytes(Paths.get(file)));
    autoToPlay = SmartDashboard.getString("autoToPlay", "backupAuto");
    System.out.println("playing auto " + autoToPlay);

    
    try {
      allLines = gson.fromJson(new FileReader("/u/recordings" + autoToPlay + ".json"), new TypeToken<List<double[]>>(){}.getType());

    } catch (Exception e) {
      System.out.println("failed to open read file");
      //Robot.player.endPlaying();
    }
		
		//let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
    
    isPlaying = true;
		
		//lets set start time to the current time you begin autonomous
    //startTime = System.currentTimeMillis();
    //Robot.shifter.isPlayingShift = true;

    

  }

  // Called every time the scheduler runs while the command is scheduled.
  /* current layout:
    XBOX0 (0-19), XBOX1(20-39) 
      joysticks 0-3
        0 = left x, 1 = left y
        2 = right x, 3 = right y
      4 = left trigger
      5 = right trigger
      6 = left bumper
      7 = right bumper
      coloured buttons 8-11
        8 = x
        9 = y
        10 = a
        11 = b
      12 = start
      13 = back
      14 = DPAD Up
      15 = DPad Right
      16 = DPad Down
      17 = DPad Left
      18 = Left Joy Btn
      19 = Right Joy Btn

    */
  @Override
  public void execute() {

    
    double[] thisLine = allLines.get(currentLine);
    
    // RUN LINE
    


    // END RUN LINE

    currentLine ++;

    if (currentLine >= allLines.size()) {
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (isFinished){
      return true;
    } else { 
      return false;
    }
  }
}
