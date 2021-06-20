// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter;

import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.drivebase.DriveBaseMAP;
import frc.robot.shooter.hood.HoodMAP;

public class AutoFlywheel extends CommandBase {

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
  public double setPoint, processVariable;
  public boolean mode = SmartDashboard.getBoolean("Mode", false);

  /** Creates a new AutoShoot. */
  public AutoFlywheel() {
    addRequirements(Robot.shooter);
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //ShooterMAP.doShoot = false;

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

    // REV PID

    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double maxV = SmartDashboard.getNumber("Max Velocity", 0);
    double minV = SmartDashboard.getNumber("Min Velocity", 0);
    double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
    double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);
    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    if ((p != ShooterMAP.kP)) {
      ShooterMAP.m_pidController.setP(p);
      ShooterMAP.kP = p;
    }
    if ((i != ShooterMAP.kI)) {
      ShooterMAP.m_pidController.setI(i);
      ShooterMAP.kI = i;
    }
    if ((d != ShooterMAP.kD)) {
      ShooterMAP.m_pidController.setD(d);
      ShooterMAP.kD = d;
    }
    if ((iz != ShooterMAP.kIz)) {
      ShooterMAP.m_pidController.setIZone(iz);
      ShooterMAP.kIz = iz;
    }
    if ((ff != ShooterMAP.kFF)) {
      ShooterMAP.m_pidController.setFF(ff);
      ShooterMAP.kFF = ff;
    }
    if ((max != ShooterMAP.kMaxOutput) || (min != ShooterMAP.kMinOutput)) {
      ShooterMAP.m_pidController.setOutputRange(min, max);
      ShooterMAP.kMinOutput = min;
      ShooterMAP.kMaxOutput = max;
    }
    if ((maxV != ShooterMAP.maxVel)) {
      ShooterMAP.m_pidController.setSmartMotionMaxVelocity(maxV, 0);
      ShooterMAP.maxVel = maxV;
    }
    if ((minV != ShooterMAP.minVel)) {
      ShooterMAP.m_pidController.setSmartMotionMinOutputVelocity(minV, 0);
      ShooterMAP.minVel = minV;
    }
    if ((maxA != ShooterMAP.maxAcc)) {
      ShooterMAP.m_pidController.setSmartMotionMaxAccel(maxA, 0);
      ShooterMAP.maxAcc = maxA;
    }
    if ((allE != ShooterMAP.allowedErr)) {
      ShooterMAP.m_pidController.setSmartMotionAllowedClosedLoopError(allE, 0);
      ShooterMAP.allowedErr = allE;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // print encoder value, for testing
    if (DriveBaseMAP.debugMode) {
      SmartDashboard.putNumber("Hood Encoder: ", HoodMAP.hoodEncoder.getPosition());
      // SmartDashboard.putNumber("Turret Encoder",
      // (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095));
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
      speedVal = 100.0;
    } else if (y > 18) {
      speedVal = 100.0;// 0.7;
    } else if (y > 17) {
      speedVal = 100.0;// 0.7;
    } else if (y > 15) {
      speedVal = 100.0;// 0.7;
    } else if (y > 13) {
      speedVal = 100.0;
    } else if (y > 12) {
      speedVal = 100.0;// 0.7;
    } else if (y > 11) {
      speedVal = 100.0;
    } else if (y > 10) {
      speedVal = 100.0;
    } else if (y > 9) {
      speedVal = 100.0;
    } else if (y > 8) {
      speedVal = 100.0;
    } else if (y > 7) {
      speedVal = 100.0;
    } else if (y > 6) {
      speedVal = 100.0;
    } else if (y > 5) {
      speedVal = 100.0;
    } else if (y > 3) {
      speedVal = 100.0;
    } else if (y > 0) {
      speedVal = 100.0;
    } else if (y > -3) {
      speedVal = 100.0;
    } else if (y > -5) {
      speedVal = 100.0;
    } else if (y > -8) {
      speedVal = 100.0;
    } else {
      speedVal = 100.0;
    }

    // UNCOMMENT THIS FOR SMARTDASHBOARD TUNING
    // double newAdjSpd = SmartDashboard.getNumber("SHOOT SPD", 0.75);
    // if ((newAdjSpd != adjustableSpd)) {
    // adjustableSpd = newAdjSpd;
    // }
    // speedVal = adjustableSpd;

    // ShooterMAP.flywheelMotor.set(adjustableSpd); //IGNORE IF PID

    // REV PID
    if (true) {
      // if(mode) { // check velocity or position
      setPoint = speedVal;
      // setPoint = SmartDashboard.getNumber("Set Velocity", 0);
      ShooterMAP.m_pidController.setReference(setPoint, ControlType.kVelocity);
      processVariable = ShooterMAP.m_encoder.getVelocity();
      //if (Math.abs(setPoint - processVariable) > 100) {
      //   ShooterMAP.doShoot = false;
      // } else {
      //   ShooterMAP.doShoot = true;
      // }
    } else {
      setPoint = SmartDashboard.getNumber("Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the setReference
       * method on an existing pid object and setting the control type to kSmartMotion
       */
      ShooterMAP.m_pidController.setReference(setPoint, ControlType.kSmartMotion);
      processVariable = ShooterMAP.m_encoder.getPosition();
    }

    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", ShooterMAP.flywheelMotor.getAppliedOutput());

    // ShooterMAP.flywheelMotor.set(speedVal);

    // Robot.shooter.setPoint = useYLookup((int) y);
    // SmartDashboard.putNumber("setPoint SHOOT", Robot.shooter.setPoint);
    // Robot.shooter.setFlywheelPID(useYLookup((int) y)); // full speed for now

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Robot.shooter.setPoint = 0;
    ShooterMAP.doShoot = true;
    ShooterMAP.m_pidController.setReference(0, ControlType.kVelocity);

    ShooterMAP.flywheelMotor.set(0.0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}