package frc.robot.kicker;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.shooter.ShooterMAP;

public class KickToShooter extends CommandBase {
    /**
    * Creates a new IntakeCommand.
    */

    public KickToShooter() {
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
        if (ShooterMAP.doShoot == true) {
            Robot.kicker.spin(); //?
        } else {
            Robot.kicker.stop();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.kicker.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
