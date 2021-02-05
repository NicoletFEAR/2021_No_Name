package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Lower extends CommandBase {
    /**
    * Creates a new HoodCommand.
    */

    public Lower() { //is this what she means? Ask again
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
        Robot.hood.setHoodSpeed(-0.2);
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
