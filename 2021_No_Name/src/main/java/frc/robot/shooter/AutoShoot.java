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
    //addRequirements(Robot.turret);
    //addRequirements(Robot.hood);
    // addRequirements(Robot.hold);
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // table = NetworkTableInstance.getDefault().getTable("limelight");
    // tx = table.getEntry("tx");
    // ty = table.getEntry("ty");
    // ta = table.getEntry("ta");
    // ledState = table.getEntry("ledMode");

    // // 320 x 240
    // x = tx.getDouble(0.0);
    // y = ty.getDouble(0.0);
    // area = ta.getDouble(0.0);

    // LEDState = ledState.getDouble(3.0);
    // ledState.forceSetDouble(3.0);
    // adjustableSpd = 0.75;


  }

 

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // print encoder value, for testing
    // if (DriveBaseMAP.debugMode) {
    //   SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
    //   // SmartDashboard.putNumber("Turret Encoder",
    //   //     (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095));
    //   SmartDashboard.putNumber("Turret Encoder relative", ((TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095) - TurretMAP.initEncoderZero));
    // }

    // // GET LIMELIGHT VALUES
    // x = tx.getDouble(0.0);
    // y = ty.getDouble(0.0);
    // area = ta.getDouble(0.0);

    // // DO MATH TO CALCULATE SETPOINTS
    // // x to turret

    // // y lookup
    // // area lookup
    // // average area and y lookup

    // // SET HOOD
    // // Robot.hood.setHoodTargetPID(useYLookup((int) y));
    // // SET FLYWHEEL

    // if (y > 13) {
    //   speedVal = 0.63;
    // } else if (y > 12) {
    //   speedVal = 0.57;//0.7;
    // } else if (y > 11) {
    //   speedVal = 0.55;//0.65;
    // } else if (y > 10) {
    //   speedVal = 0.53;//0.65;
    // } else if (y > 9) {
    //   speedVal = 0.57;//0.68;
    // } else if (y > 8) {
    //   speedVal = 0.57;//0.7;
    // } else if (y > 7) {
    //   speedVal = 0.56;//0.69;
    // } else if (y > 6) {
    //   speedVal = 0.56;
    // } else if (y > 5) {
    //   speedVal = 0.56;
    // } else if (y > 3) {
    //   speedVal = 0.56;
    // } else if (y > 0) {
    //   speedVal = 0.53;
    // } else if (y > -3) {
    //   speedVal = 0.8;
    // } else if (y > -5) {
    //   speedVal = 1.0;
    // } else if (y > -8) {
    //   speedVal = 1.0;
    // } else {
    //   speedVal = 1.0;
    // }

    // // double newAdjSpd = SmartDashboard.getNumber("SHOOT SPD", 0.75);
    // // if ((newAdjSpd != adjustableSpd)) {
    // // adjustableSpd = newAdjSpd;
    // // }
    // ShooterMAP.flywheelMotor.set(speedVal);
    // // Robot.shooter.setPoint = useYLookup((int) y);
    // // SmartDashboard.putNumber("setPoint SHOOT", Robot.shooter.setPoint);
    // // Robot.shooter.setFlywheelPID(useYLookup((int) y)); // full speed for now
    // // SET TURRET
    // Robot.turret.addToTurretSetpoint((int)x);
    // HoldMAP.holdMotor.set(HoldMAP.DEFAULT_SPEED);
    // // RUN HOLD?
    ShooterMAP.flywheelMotor.set(.75);
    //ShooterMAP.flywheelMotor.set(adjustableSpd);


    // double smartHoodSet = SmartDashboard.getNumber("HOOD SET", 0.0);
    // if ((smartHoodSet != hoodSet)) {
    //   hoodSet = smartHoodSet;
    // }
    //Robot.hood.setHoodSetpoint(-50);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Robot.shooter.setPoint = 0;
    ShooterMAP.flywheelMotor.set(0.0);
    //Robot.turret.stop();
    //HoldMAP.holdMotor.set(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
