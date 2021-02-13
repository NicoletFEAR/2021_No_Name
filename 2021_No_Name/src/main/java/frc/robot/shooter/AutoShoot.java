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


  /** Creates a new AutoShoot. */
  public AutoShoot() {
    addRequirements(Robot.shooter);
    addRequirements(Robot.turret);
    addRequirements(Robot.hood);
    addRequirements(Robot.hold);
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

    
    x = tx.getDouble(25.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    LEDState = ledState.getDouble(3.0);
    ledState.forceSetDouble(3.0);

  }

  // each value corresponds to a ten pixel change in height.
  private int[] yLookup = {1,2,3,4,5,6,7,8,9,10};

  public int useYLookup(int yPixels) {
    int lookup = (int) yPixels/10;
    return yLookup[lookup];
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    //print encoder value, for testing
    SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
    SmartDashboard.putNumber("Turret Encoder", TurretMAP.turretEncoder.getAnalogIn());


    // GET LIMELIGHT VALUES
    x = tx.getDouble(25.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);

    // DO MATH TO CALCULATE SETPOINTS
    // x to turret


    // y lookup
    // area lookup
    //average area and y lookup


    // SET HOOD
    Robot.hood.setHoodTargetPID(useYLookup((int) y));
    // SET FLYWHEEL
    Robot.shooter.setFlywheelPID(1); // full speed for now
    // SET TURRET
    Robot.turret.addToTurretSetpoint((int) (x - 160));
    // RUN HOLD?

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
