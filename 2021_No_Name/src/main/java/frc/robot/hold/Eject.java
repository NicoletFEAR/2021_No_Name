package frc.robot.hold;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Eject extends CommandBase {
    /**
    * Creates a new IntakeCommand.
    */

    public Eject() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.hold);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.hold.eject(); //?
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.hold.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
