// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.shooter.hood.HoodMAP;
import frc.robot.shooter.turret.TurretMAP;

public class ManualTurretHoodFlywheel extends CommandBase {
  double movementVal;
  double turretMovementVal;
  double encoderPos;
  double shooterMovementVal;

  /** Creates a new ManualTurretHoodFlywheel. */
  public ManualTurretHoodFlywheel() {
    addRequirements(Robot.shooter, Robot.hood, Robot.turret);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    shooterMovementVal = Robot.oi.getShooterAxis(); //Get Y input from left joystick on mech driver xbox1
        
        //Might need to tune multiplier value
        Robot.shooter.setShootSpeed(shooterMovementVal); //Pass adjusted joystick input to move method
        
    
    
    turretMovementVal = Robot.oi.getTurretAxis(); //Get X input from left joystick on mech driver xbox1
    //encoderPos = (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8*4095);
    encoderPos = ((TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095) - TurretMAP.initEncoderZero);

    if (Math.abs(turretMovementVal) < 0.05) {
      turretMovementVal = 0;
    } else if (turretMovementVal > 0 && encoderPos > TurretMAP.MAX_ENCODER) {
      turretMovementVal = 0;
    } else if (turretMovementVal < 0 && encoderPos < TurretMAP.MIN_ENCODER) {
      turretMovementVal = 0;
    }
    Robot.turret.setTurretMotorSpeed(movementVal*0.2);
    
    
    movementVal = Robot.oi.getHoodAxis(); //Get Y input from left joystick on mech driver xbox1
    if (Math.abs(movementVal) < 0.05) {   
        movementVal = 0; 
    }
    System.out.println(movementVal);

    movementVal *= HoodMAP.HOOD_MULTIPLIER;
            
      if (Math.abs(movementVal) > HoodMAP.MAX_SPEED) {
        if (movementVal > 0) {
          Robot.hood.setHoodSpeed(HoodMAP.MAX_SPEED);
        } else {
          Robot.hood.setHoodSpeed(-HoodMAP.MAX_SPEED);
        }
        Robot.hood.setHoodSpeed(HoodMAP.MAX_SPEED);
      } else {
        Robot.hood.setHoodSpeed(movementVal); //Pass adjusted joystick input to move method
      }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    Robot.hood.stop();
    Robot.shooter.stop();
    Robot.turret.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
