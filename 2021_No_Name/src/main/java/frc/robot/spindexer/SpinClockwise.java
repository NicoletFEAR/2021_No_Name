package frc.robot.spindexer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SpinClockwise extends CommandBase {
    /**
    * Creates a new IntakeCommand.
    */

    public SpinClockwise() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.spindexer);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.spindexer.spinClockwise(); //?
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.spindexer.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
