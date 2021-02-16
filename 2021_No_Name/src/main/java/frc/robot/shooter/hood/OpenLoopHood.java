package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


// Command for manual control of the hood via joystick
// used mostly for tuning & testing purposes
public class OpenLoopHood extends CommandBase {
    double movementVal;
    
    public OpenLoopHood() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.hood);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        movementVal = Robot.oi.getHoodAxis(); //Get Y input from left joystick on mech driver xbox1
        // if (movementVal > 0.3) {
        //     Robot.hood.setPoint += 1.0;
        // } else if (movementVal < -0.3) {
        //     Robot.hood.setPoint -= 1.0;
        // }
        //Might need to tune multiplier value
        Robot.hood.setHoodSpeed(movementVal); //Pass adjusted joystick input to move method
        
        // leave this:
        // long term we really want this joystick input to
        // be changing the setpoint for the Sparkmax built in PID
        // so it would be a change pid thing, but for now 
        // its just manually setting speed
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.hood.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}