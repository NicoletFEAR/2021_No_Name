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
import frc.robot.hold.HoldMAP;

public class AutoShoot extends CommandBase {

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

  /** Creates a new AutoShoot. */
  public AutoShoot() {
    addRequirements(Robot.shooter);
    addRequirements(Robot.turret);
    addRequirements(Robot.hood);
    // addRequirements(Robot.hold);
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

  // each value corresponds to a ten pixel change in height.
  private int[] yLookup = { 0, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000, 3200, 3400, 3600, 3800, 4000, 4200,
      4400, 4600, 4800, 5000, 5200, 5400, 5600, 5800 };

  public int useYLookup(int yPixels) {
    // int lookupIndex = (int) yPixels/2 + 24;
    return 3000;
    // return yLookup[lookupIndex];
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // print encoder value, for testing
    if (DriveBaseMAP.debugMode) {
      SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
      SmartDashboard.putNumber("Turret Encoder",
          (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095));
    }

    // GET LIMELIGHT VALUES
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    // DO MATH TO CALCULATE SETPOINTS
    // x to turret

    // y lookup
    // area lookup
    // average area and y lookup

    // SET HOOD
    // Robot.hood.setHoodTargetPID(useYLookup((int) y));
    // SET FLYWHEEL
    /*
     * 
     * if (y > 13) {speedVal = 0.8;} else if (y > 12) {speedVal = 0.7;} else if (y >
     * 11) {speedVal = 0.65;} else if (y > 10) {speedVal = 0.7;} else if (y > 9)
     * {speedVal = 0.7;} else if (y > 8) {speedVal = 0.7;} else if (y > 7) {speedVal
     * = 0.7;} else if (y > 6) {speedVal = 0.7;} else if (y > 9) {speedVal = 0.7;}
     * else if (y > 8) {speedVal = 0.7;} else if (y > 7) {speedVal = 0.7;} else if
     * (y > 6) {speedVal = 0.7;}
     */
    // switch (y)
    // case y > 13:
    // speedVal = .8;
    // break;
    // case y > 11:
    // speedVal = .75;
    // break;
    // case y > 10:
    // speedVal = 8;
    // break;
    // case y > 9:
    // speedVal = 8;
    // break;
    // case y > 8:
    // speedVal = 8;
    // break;
    // case y > 6:
    // speedVal = 8;
    // break;
    // case y > 4.5:
    // speedVal = 8;
    // break;
    // case y > 1:
    // speedVal = .55;
    // break;
    // case y > -2:
    // speedVal = .62;
    // break;
    // default:
    // speedVal = .63;
    // break;

    double newAdjSpd = SmartDashboard.getNumber("SHOOT SPD", 0.75);
    if ((newAdjSpd != adjustableSpd)) {
      adjustableSpd = newAdjSpd;
    }
    ShooterMAP.flywheelMotor.set(adjustableSpd);
    // Robot.shooter.setPoint = useYLookup((int) y);
    // SmartDashboard.putNumber("setPoint SHOOT", Robot.shooter.setPoint);
    // Robot.shooter.setFlywheelPID(useYLookup((int) y)); // full speed for now
    // SET TURRET
    Robot.turret.addToTurretSetpoint((int) (x));
    HoldMAP.holdMotor.set(HoldMAP.DEFAULT_SPEED);
    // RUN HOLD?
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Robot.shooter.setPoint = 0;
    ShooterMAP.flywheelMotor.set(0.0);
    Robot.turret.stop();
    HoldMAP.holdMotor.set(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
