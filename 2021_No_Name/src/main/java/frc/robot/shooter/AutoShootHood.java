// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.shooter.*;
import frc.robot.shooter.hood.*;
import frc.robot.shooter.turret.TurretMAP;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.drivebase.*;
import frc.robot.spindexer.SpindexerMAP;

public class AutoShootHood extends CommandBase {

  private NetworkTable table;
  private NetworkTableEntry tx;
  private NetworkTableEntry ty;
  private NetworkTableEntry ta;
  private NetworkTableEntry ledState;

  private double x;
  private double y;
  private double area;
  private double LEDState;

  public double adjustableSpd;
  public double speedVal;
  public double hoodSet;

  /** Creates a new AutoShoot. */
  public AutoShootHood() {
    //addRequirements(Robot.shooter);
    addRequirements(Robot.turret);
    addRequirements(Robot.hood);
    // addRequirements(Robot.spindexer);
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    ledState = table.getEntry("ledMode");

    // 320 x 240
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    LEDState = ledState.getDouble(3.0);
    ledState.forceSetDouble(3.0);
    adjustableSpd = 0.75;

    //
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // print encoder value, for testing
    if (DriveBaseMAP.debugMode) {
      SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
 //     SmartDashboard.putNumber("Turret Encoder",
 //         (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095));
    }

    // GET LIMELIGHT VALUES
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);
    if (y > 17.5) {
      x -= 2.0;
    }
    // DO MATH TO CALCULATE SETPOINTS
    // x to turret

    // y lookup
    // area lookup
    // average area and y lookup

    // SET HOOD
    // Robot.hood.setHoodTargetPID(useYLookup((int) y));
    // SET FLYWHEEL

    // FOR FLYWHEEL:
    
    if (y > 20) {
      speedVal = 0.75;
    } else if (y > 18) {
      speedVal = 0.65;//0.7;
    } else if (y > 17) {
      speedVal = 0.65;//0.7;
    } else if (y > 15) {
      speedVal = 0.55;//0.7;
    } else if (y > 13) {
      speedVal = 0.58;//0.7;
    } else if (y > 12) {
      speedVal = 0.58;//0.7;
    } else if (y > 11) {
      speedVal = 0.6;//0.65;
    } else if (y > 10) {
      speedVal = 0.65;//0.65;
    } else if (y > 9) {
      speedVal = 0.6;//0.68;
    } else if (y > 8) {
      speedVal = 0.53;//0.7;
    } else if (y > 7) {
      speedVal = 0.58;//0.69;
    } else if (y > 6) {
      speedVal = 0.62;
    } else if (y > 5) {
      speedVal = 0.62;
    } else if (y > 3) {
      speedVal = 0.5;
    } else if (y > 0) {
      speedVal = 0.5;
    } else if (y > -3) {
      speedVal = 0.55;
    } else if (y > -5) {
      speedVal = 0.55;
    } else if (y > -8) {
      speedVal = 0.55;
    } else {
      speedVal = 0.55;
    }
    

    // FOR HOOD:
    
    if (y > 20) {
      hoodSet = -50;
    } else if (y > 17) {
      hoodSet = -55;//0.7;
    } else if (y > 16) {
      hoodSet = -60;//0.7;
    } else if (y > 15) {
      hoodSet = -65;//0.65;
    } else if (y > 13) {
      hoodSet = -75;//0.65;
    } else if (y > 12) {
      hoodSet = -75;//0.68;
    } else if (y > 8) {
      hoodSet = -80;//0.7;
    } else if (y > 6) {
      hoodSet = -90;//0.69;
    } else if (y > 4) {
      hoodSet = -90;
    } else if (y > 3) {
      hoodSet = -72;
    } else if (y > 2) {
      hoodSet = -60;
    } else if (y > 1) {
      hoodSet = -66;
    } else if (y > 0) {
      hoodSet = -76;
    } else if (y > -3) {
      hoodSet = -88;
    } else if (y > -5) {
      hoodSet = -73;
    } else if (y > -6) {
      hoodSet = -73;
    } else if (y > -8) {
      hoodSet = -70;
    } else {
      hoodSet = -50;
    }
    
    
    // double newAdjSpd = SmartDashboard.getNumber("SHOOT SPD", 0.75);
    // if ((newAdjSpd != adjustableSpd)) {
    //   adjustableSpd = newAdjSpd;
    // }
    
    //ShooterMAP.flywheelMotor.set(speedVal);
    //ShooterMAP.flywheelMotor.set(adjustableSpd);


    // double smartHoodSet = SmartDashboard.getNumber("HOOD SET", 0.0);
    // if ((smartHoodSet != hoodSet)) {
    //   hoodSet = smartHoodSet;
    // }
    //Robot.hood.setHoodSetpoint(hoodSet);


    // Robot.shooter.setPoint = useYLookup((int) y);
    // SmartDashboard.putNumber("setPoint SHOOT", Robot.shooter.setPoint);
    // Robot.shooter.setFlywheelPID(useYLookup((int) y)); // full speed for now
    ShooterMAP.flywheelMotor.set(.53);

    // SET TURRET
    Robot.turret.addToTurretSetpoint((int) (x));
    //SpindexerMAP.spindexerMotor.set(SpindexerMAP.DEFAULT_SPEED);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Robot.shooter.setPoint = 0;
    
    ShooterMAP.flywheelMotor.set(0.0);
    Robot.turret.stop();
    //SpindexerMAP.spindexerMotor.set(0.0);
    HoodMAP.hoodMotor.set(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
