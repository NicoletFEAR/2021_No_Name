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
import frc.robot.shooter.AutoShoot;
import frc.robot.drivebase.DriveBaseMAP;

public class Player extends CommandBase {

  double thisLine[] = new double[14];
  // double thisLine[] = new double[28]; // two controllers

  int currentLine = 0;
  public boolean isPlaying = false;

  //public Scanner scanner = null;
  long startTime;

  boolean onTime = true;
  double nextDouble;
  String autoToPlay;
  Gson gson;

  boolean isFinished;
  FileReader fileReader;

  AutoShoot shooty;

  ArrayList<double[]> allLines;
  /** Creates a new Player. */
  public Player() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.driveBase);
    //addRequirements(Robot.shifter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gson = new Gson();
    currentLine = 0;
    isFinished = false;
    autoToPlay = "defaultEmpty";

    isPlaying = true;
    
    SmartDashboard.putBoolean("isPlaying", isPlaying);
    autoToPlay = SmartDashboard.getString("autoToPlay", "defaultEmpty");
    System.out.println("playing auto " + autoToPlay);
    //String file = "src/test/resources/myFile.json";
    // writer = new FileWriter("/c/recordings" + newPlayName + ".json");
    try {fileReader = new FileReader("/c/" + autoToPlay + ".json");}
    catch (Exception e) {
      isFinished = true;
      isPlaying = false;
      System.out.println("could not create FileReader");
      System.out.println(e);
    }
    //String stringJson = String(Files.readAllBytes(Paths.get(file)));
    if (allLines != null) {
      allLines.clear();
    }
    
    try {
      allLines = gson.fromJson(fileReader, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file");
    } catch (Exception e) {
      System.out.println("failed to open read file");
      System.out.println(e);

      //Robot.player.endPlaying();
    }
		
		//let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
    
    isPlaying = true;
    SmartDashboard.putBoolean("isPlaying", isPlaying);
		
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

     // DRIVE:
     Robot.driveBase.RacingDrive(thisLine[5]-thisLine[4], thisLine[0] * DriveBaseMAP.TURN_SCALING);

     // SWITCH FRONT
     if (thisLine[8] == 1.0) {Robot.driveBase.switchFront();} 
    
     // SHIFTER (IGNORE)

     // SPIN INTAKE
     if (thisLine[10] == 1.0) {Robot.intake.intake();}
     if (thisLine[10] == 3.0) {Robot.intake.stop();}

     // SPIN HOLD TO SHOOT
     if (thisLine[11] == 1.0) {Robot.hold.toShooter();}
     if (thisLine[11] == 3.0) {Robot.hold.stop();}

     // AUTO AIM
     if (thisLine[9] == 1.0) {shooty = new AutoShoot(); shooty.schedule();} else if (thisLine[9] == 3.0) {shooty.cancel();}

     // INTAKE UP
    if (thisLine[14] == 1.0) {Robot.intake.up();}

     // INTAKE DOWN
     if (thisLine[16] == 1.0) {Robot.intake.down();}







    // END RUN LINE

    currentLine ++;

    if (currentLine >= allLines.size() - 1) {
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isPlaying = false;
    
    SmartDashboard.putBoolean("isPlaying", isPlaying);
    try {fileReader.close();}
    catch(Exception e) {}
  }

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
