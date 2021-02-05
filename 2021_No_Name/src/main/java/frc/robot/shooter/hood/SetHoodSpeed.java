package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class SetHoodSpeed extends InstantCommand {
    /**
    * Creates a new HoodCommand.
    */
    private double speed;

    public SetHoodSpeed(double speed) {
        this.speed = speed; 
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.hood);
    }
    
    // Called when the command is initially scheduled
    @Override
    public void initialize() {        
        Robot.hood.setHoodSpeed(speed);
    }
    
}  