// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autonomous;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
// https://github.com/google/gson/blob/master/UserGuide.md#TOC-Primitives-Examples 

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

// to call
// new Command​(String name, double timeout)?
// then u call .start()?
// or actually it might be scheduler.getInstance.add(Command command)
// https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/Scheduler.html 


// String(Files.readAllBytes(Paths.get(file)))
// String file = "src/test/resources/myFile.json";
 

public class Record extends CommandBase {
  /** Creates a new Record. */
  private boolean isFinished;
  
  public int currentLine;
  long startTime;
  boolean OverwriteModeisTrue = false;
  String newPlayName = "defaultName";
  public Gson gson;

  public boolean isRecording;
  FileWriter writer;

  private ArrayList<double[]> arrayOfLines = new ArrayList<double[]>();
  private double lastLine[] = new double[40];
  //double thisLine[] = new double[40]; // for two controllers
  // double thisLine[] = new double[14];
  // 14 buttons 6 axes

  public Record() {
    // no dependencies so that all of the other normal commands can run
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.shifter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // SmartDashboard.putString("newPlayName", "defaultEmpty"); // should be in
    // robotInit
    // SmartDashboard.putBoolean("OverwriteModeisTrue", false); // should be in
    // robotinit
    // SmartDashboard.putString("autoToPlay", "backupAuto"); // robotinit

    // set last array to 0



    gson = new Gson();
    // gson.toJson();
    // localScheduler = Scheduler.getInstance();

    newPlayName = SmartDashboard.getString("newPlayName", "defaultEmpty");
    System.out.println("recording auto " + newPlayName);

    OverwriteModeisTrue = SmartDashboard.getBoolean("OverwriteModeisTrue", false);

    if (OverwriteModeisTrue || !(Files.isRegularFile(Paths.get("/u/recordings" + newPlayName + ".json")))) { 
      try {
        writer = new FileWriter("/u/recordings" + newPlayName + ".json");
      } catch (IOException e) {
        System.out.println("IOException FileWriter bad");
      }
      System.out.println("successfully made writer");
    } else {
      System.out.println("file name already existed and overwrite mode is false. Will not record");
      isRecording = false;
      this.cancel();
    }

    currentLine = 0;
    isRecording = true;
    SmartDashboard.putBoolean("isRecording", isRecording);

  }

  public double[] checkButtons() {
    double thisLine[] = new double[40];
    
    // joysticks:
    thisLine[0] = Robot.oi.getXbox0().getX(GenericHID.Hand.kLeft);
    thisLine[1] = Robot.oi.getXbox0().getY(GenericHID.Hand.kLeft);
    thisLine[2] = Robot.oi.getXbox0().getX(GenericHID.Hand.kRight);
    thisLine[3] = Robot.oi.getXbox0().getY(GenericHID.Hand.kRight);
    // triggers:
    thisLine[4] = Robot.oi.getXbox0().getTriggerAxis(GenericHID.Hand.kLeft);
    thisLine[5] = Robot.oi.getXbox0().getTriggerAxis(GenericHID.Hand.kRight);
    // bumpers:
    thisLine[6] = (lastLine[6] == 0) ? (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 2.0 : 0.0);
    thisLine[7] = (lastLine[7] == 0) ? (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 2.0 : 0.0);
    // coloured buttons:
    thisLine[8] = (lastLine[8] == 0) ? (Robot.oi.getXbox0().getXButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getXButton() ? 2.0 : 0.0); // X
    thisLine[9] = (lastLine[9] == 0) ? (Robot.oi.getXbox0().getYButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getYButton() ? 2.0 : 0.0); // Y
    thisLine[10] = (lastLine[10] == 0) ? (Robot.oi.getXbox0().getAButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getAButton() ? 2.0 : 0.0); // A
    thisLine[11] = (lastLine[11] == 0) ? (Robot.oi.getXbox0().getBButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getBButton() ? 2.0 : 0.0); // B
    // middle small buttons:
    thisLine[12] = (lastLine[12] == 0) ? (Robot.oi.getXbox0().getStartButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getStartButton() ? 2.0 : 0.0);
    thisLine[13] = (lastLine[13] == 0) ? (Robot.oi.getXbox0().getBackButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox0().getBackButton() ? 2.0 : 0.0);
    
    // D Pad Buttons (4 cardinal directions)
    thisLine[14] = (lastLine[14] == 0) ? (Robot.oi.getXbox0().getPOV() == 0 ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getPOV() == 0 ? 2.0 : 0.0);
    thisLine[15] = (lastLine[15] == 0) ? (Robot.oi.getXbox0().getPOV() == 90 ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getPOV() == 90 ? 2.0 : 0.0);
    thisLine[16] = (lastLine[16] == 0) ? (Robot.oi.getXbox0().getPOV() == 180 ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getPOV() == 180 ? 2.0 : 0.0);
    thisLine[17] = (lastLine[17] == 0) ? (Robot.oi.getXbox0().getPOV() == 270 ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getPOV() == 270 ? 2.0 : 0.0);

    // Joystick button clickers
    thisLine[18] = (lastLine[18] == 0) ? (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 2.0 : 0.0);
    thisLine[19] = (lastLine[19] == 0) ? (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0)
      : (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 2.0 : 0.0);
    
    //XBOX2
    
    // joysticks:
    thisLine[20] = Robot.oi.getXbox1().getX(GenericHID.Hand.kLeft);
    thisLine[21] = Robot.oi.getXbox1().getY(GenericHID.Hand.kLeft);
    thisLine[22] = Robot.oi.getXbox1().getX(GenericHID.Hand.kRight);
    thisLine[23] = Robot.oi.getXbox1().getY(GenericHID.Hand.kRight);
    // triggers:
    thisLine[24] = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kLeft);
    thisLine[25] = Robot.oi.getXbox1().getTriggerAxis(GenericHID.Hand.kRight);
    // bumpers:
    thisLine[26] = (lastLine[26] == 0) ? (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kLeft) ? 2.0 : 0.0);
    thisLine[27] = (lastLine[27] == 0) ? (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getBumper(GenericHID.Hand.kRight) ? 2.0 : 0.0);
    // coloured buttons:
    thisLine[28] = (lastLine[28] == 0) ? (Robot.oi.getXbox1().getXButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getXButton() ? 2.0 : 0.0); // X
    thisLine[29] = (lastLine[29] == 0) ? (Robot.oi.getXbox1().getYButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getYButton() ? 2.0 : 0.0); // Y
    thisLine[30] = (lastLine[30] == 0) ? (Robot.oi.getXbox1().getAButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getAButton() ? 2.0 : 0.0); // A
    thisLine[31] = (lastLine[31] == 0) ? (Robot.oi.getXbox1().getBButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getBButton() ? 2.0 : 0.0); // B
    // middle small buttons:
    thisLine[32] = (lastLine[32] == 0) ? (Robot.oi.getXbox1().getStartButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getStartButton() ? 2.0 : 0.0);
    thisLine[33] = (lastLine[33] == 0) ? (Robot.oi.getXbox1().getBackButton() ? 1.0 : 0.0)
        : (Robot.oi.getXbox1().getBackButton() ? 2.0 : 0.0);
    
    // D Pad Buttons (4 cardinal directions)
    thisLine[34] = (lastLine[34] == 0) ? (Robot.oi.getXbox1().getPOV() == 0 ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getPOV() == 0 ? 2.0 : 0.0);
    thisLine[35] = (lastLine[35] == 0) ? (Robot.oi.getXbox1().getPOV() == 90 ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getPOV() == 90 ? 2.0 : 0.0);
    thisLine[36] = (lastLine[36] == 0) ? (Robot.oi.getXbox1().getPOV() == 180 ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getPOV() == 180 ? 2.0 : 0.0);
    thisLine[37] = (lastLine[37] == 0) ? (Robot.oi.getXbox1().getPOV() == 270 ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getPOV() == 270 ? 2.0 : 0.0);

    // Joystick button clickers
    thisLine[38] = (lastLine[38] == 0) ? (Robot.oi.getXbox1().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getStickButtonPressed(Hand.kLeft) ? 2.0 : 0.0);
    thisLine[39] = (lastLine[39] == 0) ? (Robot.oi.getXbox1().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0)
      : (Robot.oi.getXbox1().getStickButtonPressed(Hand.kRight) ? 2.0 : 0.0);

    lastLine = thisLine.clone();
    return thisLine;
  }



  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // plan to implement scheduler stuff later to make more adaptable
    // localScheduler.
    
    double[] theLine = checkButtons();
    arrayOfLines.add(theLine);

    if (Robot.oi.getXbox0().getBumper(Hand.kRight) && currentLine >= 50) { // if you pressed the record
      // button again (after 1s)
      System.out.println("finished due to button press");
      isFinished = true;
    }



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    gson.toJson(arrayOfLines, writer);
    try {writer.close();} catch (IOException e) {System.out.println(e);}

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    isRecording = false;
    SmartDashboard.putBoolean("isRecording", isRecording);

    if (isFinished) {
      return true;
    } else {
    return false;
    }
  }
}
