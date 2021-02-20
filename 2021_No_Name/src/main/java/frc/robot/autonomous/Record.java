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

    if (OverwriteModeisTrue || !(Files.isRegularFile(Paths.get("/c/" + newPlayName + ".json")))) { 
      try {
        writer = new FileWriter("/c/" + newPlayName + ".json");
      } catch (IOException e) {
        System.out.println("IOException FileWriter bad");
        System.out.println(e);
      }
      System.out.println("successfully made writer");
    } else {
      System.out.println("file name already existed and overwrite mode is false. Will not record");
      isRecording = false;
      //this.cancel();
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
    if (lastLine[6] == 0) {thisLine[6] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0);}
    else if (lastLine[6] == 3) {thisLine[6] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0);}
    else {thisLine[6] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    if (lastLine[7] == 0) {thisLine[7] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0);}
    else if (lastLine[7] == 3) {thisLine[7] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0);}
    else {thisLine[7] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    // coloured buttons:
    // X
    if (lastLine[8] == 0) {thisLine[8] = (Robot.oi.getXbox0().getXButton() ? 1.0 : 0.0);}
    else if (lastLine[8] == 3) {thisLine[8] = (Robot.oi.getXbox0().getXButton() ? 1.0 : 0.0);}
    else {thisLine[8] = (Robot.oi.getXbox0().getXButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
    // Y
    if (lastLine[9] == 0) {thisLine[9] = (Robot.oi.getXbox0().getYButton() ? 1.0 : 0.0);}
    else if (lastLine[9] == 3) {thisLine[9] = (Robot.oi.getXbox0().getYButton() ? 1.0 : 0.0);}
    else {thisLine[9] = (Robot.oi.getXbox0().getYButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
    // A
    if (lastLine[10] == 0) {thisLine[10] = (Robot.oi.getXbox0().getAButton() ? 1.0 : 0.0);}
    else if (lastLine[10] == 3) {thisLine[10] = (Robot.oi.getXbox0().getAButton() ? 1.0 : 0.0);}
    else {thisLine[10] = (Robot.oi.getXbox0().getAButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
    // B
    if (lastLine[11] == 0) {thisLine[11] = (Robot.oi.getXbox0().getBButton() ? 1.0 : 0.0);}
    else if (lastLine[11] == 3) {thisLine[11] = (Robot.oi.getXbox0().getBButton() ? 1.0 : 0.0);}
    else {thisLine[11] = (Robot.oi.getXbox0().getBButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    // middle small buttons:
    //start
    if (lastLine[12] == 0) {thisLine[12] = (Robot.oi.getXbox0().getStartButton() ? 1.0 : 0.0);}
    else if (lastLine[12] == 3) {thisLine[12] = (Robot.oi.getXbox0().getStartButton() ? 1.0 : 0.0);}
    else {thisLine[12] = (Robot.oi.getXbox0().getStartButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    //back
    if (lastLine[13] == 0) {thisLine[13] = (Robot.oi.getXbox0().getBackButton() ? 1.0 : 0.0);}
    else if (lastLine[13] == 3) {thisLine[13] = (Robot.oi.getXbox0().getBackButton() ? 1.0 : 0.0);}
    else {thisLine[13] = (Robot.oi.getXbox0().getBackButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    // D Pad Buttons (4 cardinal directions)
    //Up
    if (lastLine[14] == 0) {thisLine[14] = (Robot.oi.getXbox0().getPOV() == 0  ? 1.0 : 0.0);}
    else if (lastLine[14] == 3) {thisLine[14] = (Robot.oi.getXbox0().getPOV() == 0  ? 1.0 : 0.0);}
    else {thisLine[14] = (Robot.oi.getXbox0().getPOV() == 0  ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    //Right
    if (lastLine[15] == 0) {thisLine[15] = (Robot.oi.getXbox0().getPOV() == 90 ? 1.0 : 0.0);}
    else if (lastLine[15] == 3) {thisLine[15] = (Robot.oi.getXbox0().getPOV() == 90 ? 1.0 : 0.0);}
    else {thisLine[15] = (Robot.oi.getXbox0().getPOV() == 90 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    //Down
    if (lastLine[16] == 0) {thisLine[16] = (Robot.oi.getXbox0().getPOV() == 180 ? 1.0 : 0.0);}
    else if (lastLine[16] == 3) {thisLine[16] = (Robot.oi.getXbox0().getPOV() == 180 ? 1.0 : 0.0);}
    else {thisLine[16] = (Robot.oi.getXbox0().getPOV() == 180 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    //Left
    if (lastLine[17] == 0) {thisLine[17] = (Robot.oi.getXbox0().getPOV() == 270 ? 1.0 : 0.0);}
    else if (lastLine[17] == 3) {thisLine[17] = (Robot.oi.getXbox0().getPOV() == 270 ? 1.0 : 0.0);}
    else {thisLine[17] = (Robot.oi.getXbox0().getPOV() == 270 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    // Joystick button clickers
    //Left Joystick Btn
    if (lastLine[18] == 0) {thisLine[18] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0);}
    else if (lastLine[18] == 3) {thisLine[18] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0);}
    else {thisLine[18] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

    //Right Joystick Btn
    if (lastLine[19] == 0) {thisLine[19] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0);}
    else if (lastLine[19] == 3) {thisLine[19] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0);}
    else {thisLine[19] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
    
    //XBOX2
    
   // joysticks:
   thisLine[20] = Robot.oi.getXbox0().getX(GenericHID.Hand.kLeft);
   thisLine[21] = Robot.oi.getXbox0().getY(GenericHID.Hand.kLeft);
   thisLine[22] = Robot.oi.getXbox0().getX(GenericHID.Hand.kRight);
   thisLine[23] = Robot.oi.getXbox0().getY(GenericHID.Hand.kRight);
   // triggers:
   thisLine[24] = Robot.oi.getXbox0().getTriggerAxis(GenericHID.Hand.kLeft);
   thisLine[25] = Robot.oi.getXbox0().getTriggerAxis(GenericHID.Hand.kRight);
   // bumpers:
   if (lastLine[26] == 0) {thisLine[26] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0);}
   else if (lastLine[26] == 3) {thisLine[26] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 1.0 : 0.0);}
   else {thisLine[26] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kLeft) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   if (lastLine[27] == 0) {thisLine[27] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0);}
   else if (lastLine[27] == 3) {thisLine[27] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 1.0 : 0.0);}
   else {thisLine[27] = (Robot.oi.getXbox0().getBumper(GenericHID.Hand.kRight) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   // coloured buttons:
   // X
   if (lastLine[28] == 0) {thisLine[28] = (Robot.oi.getXbox0().getXButton() ? 1.0 : 0.0);}
   else if (lastLine[28] == 3) {thisLine[28] = (Robot.oi.getXbox0().getXButton() ? 1.0 : 0.0);}
   else {thisLine[28] = (Robot.oi.getXbox0().getXButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
   // Y
   if (lastLine[29] == 0) {thisLine[29] = (Robot.oi.getXbox0().getYButton() ? 1.0 : 0.0);}
   else if (lastLine[29] == 3) {thisLine[29] = (Robot.oi.getXbox0().getYButton() ? 1.0 : 0.0);}
   else {thisLine[29] = (Robot.oi.getXbox0().getYButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
   // A
   if (lastLine[30] == 0) {thisLine[30] = (Robot.oi.getXbox0().getAButton() ? 1.0 : 0.0);}
   else if (lastLine[30] == 3) {thisLine[30] = (Robot.oi.getXbox0().getAButton() ? 1.0 : 0.0);}
   else {thisLine[30] = (Robot.oi.getXbox0().getAButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3
   // B
   if (lastLine[31] == 0) {thisLine[31] = (Robot.oi.getXbox0().getBButton() ? 1.0 : 0.0);}
   else if (lastLine[31] == 3) {thisLine[31] = (Robot.oi.getXbox0().getBButton() ? 1.0 : 0.0);}
   else {thisLine[31] = (Robot.oi.getXbox0().getBButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   // middle small buttons:
   //start
   if (lastLine[32] == 0) {thisLine[32] = (Robot.oi.getXbox0().getStartButton() ? 1.0 : 0.0);}
   else if (lastLine[32] == 3) {thisLine[32] = (Robot.oi.getXbox0().getStartButton() ? 1.0 : 0.0);}
   else {thisLine[32] = (Robot.oi.getXbox0().getStartButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   //back
   if (lastLine[33] == 0) {thisLine[33] = (Robot.oi.getXbox0().getBackButton() ? 1.0 : 0.0);}
   else if (lastLine[33] == 3) {thisLine[33] = (Robot.oi.getXbox0().getBackButton() ? 1.0 : 0.0);}
   else {thisLine[33] = (Robot.oi.getXbox0().getBackButton() ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   // D Pad Buttons (4 cardinal directions)
   //Up
   if (lastLine[34] == 0) {thisLine[34] = (Robot.oi.getXbox0().getPOV() == 0  ? 1.0 : 0.0);}
   else if (lastLine[34] == 3) {thisLine[34] = (Robot.oi.getXbox0().getPOV() == 0  ? 1.0 : 0.0);}
   else {thisLine[34] = (Robot.oi.getXbox0().getPOV() == 0  ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   //Right
   if (lastLine[35] == 0) {thisLine[35] = (Robot.oi.getXbox0().getPOV() == 90 ? 1.0 : 0.0);}
   else if (lastLine[35] == 3) {thisLine[35] = (Robot.oi.getXbox0().getPOV() == 90 ? 1.0 : 0.0);}
   else {thisLine[35] = (Robot.oi.getXbox0().getPOV() == 90 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   //Down
   if (lastLine[36] == 0) {thisLine[36] = (Robot.oi.getXbox0().getPOV() == 180 ? 1.0 : 0.0);}
   else if (lastLine[36] == 3) {thisLine[36] = (Robot.oi.getXbox0().getPOV() == 180 ? 1.0 : 0.0);}
   else {thisLine[36] = (Robot.oi.getXbox0().getPOV() == 180 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   //Left
   if (lastLine[37] == 0) {thisLine[37] = (Robot.oi.getXbox0().getPOV() == 270 ? 1.0 : 0.0);}
   else if (lastLine[37] == 3) {thisLine[37] = (Robot.oi.getXbox0().getPOV() == 270 ? 1.0 : 0.0);}
   else {thisLine[37] = (Robot.oi.getXbox0().getPOV() == 270 ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   // Joystick button clickers
   //Left Joystick Btn
   if (lastLine[38] == 0) {thisLine[38] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0);}
   else if (lastLine[38] == 3) {thisLine[38] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 1.0 : 0.0);}
   else {thisLine[38] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kLeft) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

   //Right Joystick Btn
   if (lastLine[39] == 0) {thisLine[39] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0);}
   else if (lastLine[39] == 3) {thisLine[39] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 1.0 : 0.0);}
   else {thisLine[39] = (Robot.oi.getXbox0().getStickButtonPressed(Hand.kRight) ? 2.0 : 3.0);} // if 1 or 2, goes to a 2 or 3

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
    currentLine++;

    if (Robot.oi.getXbox0().getBackButton() && currentLine >= 50) { // if you pressed the record
      // button again (after 1s)
      System.out.println("finished due to button press");
      isFinished = true;
    }



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    gson.toJson(arrayOfLines, writer);
    System.out.println("saved to json recording");
    try {writer.close();} catch (IOException e) {System.out.println(e);}

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    

    if (isFinished) {
      isRecording = false;
    SmartDashboard.putBoolean("isRecording", isRecording);

      return true;
    } else {
    return false;
    }
  }
}
