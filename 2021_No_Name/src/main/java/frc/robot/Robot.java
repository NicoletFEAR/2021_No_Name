// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory o
package frc.robot;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ejml.simple.AutomaticSimpleMatrixConvert;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autonomous.PlayerPreLoaded;
import frc.robot.drivebase.DriveBase;
import frc.robot.drivebase.DriveBaseMAP;
import frc.robot.drivebase.OpenLoopDrive;
import frc.robot.drivebase.shifter.Shifter;
import frc.robot.spindexer.Spindexer;
import frc.robot.spindexer.SpindexerMAP;
import frc.robot.intake.Intake;
import frc.robot.intake.IntakeMAP;
import frc.robot.shooter.Shooter;
import frc.robot.shooter.ShooterMAP;
import frc.robot.shooter.hood.Hood;
import frc.robot.shooter.hood.HoodMAP;
import frc.robot.shooter.turret.Turret;
import frc.robot.shooter.turret.TurretMAP;
import frc.robot.kicker.*;
import frc.robot.climb.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // our subsystems
  public static OI oi;
  public static DriveBase driveBase;
  public static Shifter shifter;
  public static Intake intake;
  public static Shooter shooter;
  public static Spindexer spindexer;
  public static Hood hood;
  public static Turret turret;
  public static Kicker kicker;
  public static Climb climb;


   // Enable or disable printing of diagnostics to smart dashboard
    // Also being used in the ShooterMAP to enable/disable putting of various PID
    // diagnostics

  // some default auto stuff, may be removed later
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public ArrayList<double[]> autonomousArray;
  
  ArrayList<double[]> redA;
  ArrayList<double[]> redB;
  ArrayList<double[]> blueA;
  ArrayList<double[]> blueB;
  ArrayList<double[]> currentChallenge;

  NetworkTable table;
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   * 
   */
  @Override
  public void robotInit() {


    // RobotMAP.init(); // we might not need this
    DriveBaseMAP.init();
    ShooterMAP.init();
    IntakeMAP.init();
    HoodMAP.init();
    SpindexerMAP.init();
    TurretMAP.init();
    KickerMAP.init();
    ClimbMAP.init();
  
    driveBase = new DriveBase();
    SmartDashboard.putBoolean("isFront", driveBase.isFrontFacing());

    shifter = new Shifter();
    intake = new Intake();
    shooter = new Shooter();
    spindexer = new Spindexer();
    hood = new Hood();
    turret = new Turret();
    kicker = new Kicker();
    climb = new Climb();

    oi = new OI(); // this comes after the subsystems!

    DriveBaseMAP.debugMode = false; //See Above

    driveBase.setDefaultCommand(new OpenLoopDrive()); // means OpenLoopDrive runs
    //shifter.setDefaultCommand(new AutoShift());

    // ResetHood resetHood = new ResetHood();
    // resetHood.schedule();

    //hood.setDefaultCommand(new OpenLoopHood()); //Only happening when left joystick is being held down
    //turret.setDefaultCommand(new OpenLoopTurret());
    SmartDashboard.putString("newPlayName", "defaultEmpty"); // should be in robotInit
    SmartDashboard.putBoolean("OverwriteModeisTrue", false); // should be in robotinit
    SmartDashboard.putString("autoToPlay", "defaultEmpty");

    // PreLoading auto arraylists
    Gson gson = new Gson();

    try { // RED A
      FileReader fileReader_RedA = new FileReader("/c/" + "RedA" + ".json");
      redA = gson.fromJson(fileReader_RedA, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file RedA");
      fileReader_RedA.close();
    }
    catch (Exception e) {
      System.out.println("could not create FileReader RedA");
      System.out.println(e);
    } // END RED A

    try { // RED B
      FileReader fileReader_RedB = new FileReader("/c/" + "RedB" + ".json");
      redB = gson.fromJson(fileReader_RedB, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file redB");
      fileReader_RedB.close();
    }
    catch (Exception e) {
      System.out.println("could not create FileReader RedB");
      System.out.println(e);
    } // END RED B
    
    try { // BLUE A
      FileReader fileReader_BlueA = new FileReader("/c/" + "BlueA" + ".json");
      blueA = gson.fromJson(fileReader_BlueA, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file BlueA");
      fileReader_BlueA.close();
    }
    catch (Exception e) {
      System.out.println("could not create FileReader BlueA");
      System.out.println(e);
    } // END BLUE A
    
    try { // BLUE B
      FileReader fileReader_BlueB = new FileReader("/c/" + "BlueB" + ".json");
      blueB = gson.fromJson(fileReader_BlueB, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file BlueB");
      fileReader_BlueB.close();
    }
    catch (Exception e) {
      System.out.println("could not create FileReader BlueB");
      System.out.println(e);
    } // END BLUE B

    try { // CURRENT CHALLENGE
      FileReader fileReader_CurrentChallenge = new FileReader("/c/" + "slalomnew" + ".json");
      currentChallenge = gson.fromJson(fileReader_CurrentChallenge, new TypeToken<List<double[]>>(){}.getType());
      System.out.println("opened read file CURRENT CHALLENGE");
      fileReader_CurrentChallenge.close();
    }
    catch (Exception e) {
      System.out.println("could not create FileReader CURRENT CHALLENGE");
      System.out.println(e);
    } 
    
    table = NetworkTableInstance.getDefault().getTable("limelight");

    // END CURRENT CHALLENGE
//    try{
//      TurretMAP.turretEncoder.setPulseWidthPosition(0, 100);
//    } catch (Exception e) {
//      System.out.println(e.toString());
//    }
//    TurretMAP.initEncoderZero = TurretMAP.turretEncoder.getPulseWidthPosition();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
    //HoodMAP.hoodMotor.set(0.3);
    //TurretMAP.turretMotor.set(ControlMode.PercentOutput, 0.3);

    // to reduce prints
    if (DriveBaseMAP.debugMode) {
      //SmartDashboard.putNumber("Turret Encoder: ", TurretMAP.turretEncoder.getPulseWidthPosition());
      //SmartDashboard.putNumber("Turret Encoder: ", (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095));
      //SmartDashboard.putNumber("Turret Init Position: ", TurretMAP.initEncoderZero);
      //SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() { // start of auto
    // DECIDE WHICH AUTO TO PLAY
    double tx = table.getEntry("tx").getDouble(0.0);
    double ty = table.getEntry("ty").getDouble(0.0);

    // check where the ball is (by distance and by left/right)
    if (ty > 0) { // run blue
      if (tx < 0) {
        autonomousArray = blueB; // B // far away, on the left
      } else {
        autonomousArray = blueA; // A // far away, on the right
      }
    }
    else { // run red
    if (tx < 0) {
      autonomousArray = redB; // B // close, on the left
    } else {
      autonomousArray = redA; // A // far away, on the right
    }
    }
    autonomousArray = currentChallenge;
    CommandBase autoCommand = new PlayerPreLoaded(autonomousArray);
    autoCommand.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //If you are reading this and things are going poorly with vision it's probably because of this.
  //  TurretMAP.initEncoderZero = TurretMAP.turretEncoder.getPulseWidthPosition();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
