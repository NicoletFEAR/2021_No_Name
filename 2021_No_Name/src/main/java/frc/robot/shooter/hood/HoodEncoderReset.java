package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class HoodEncoderReset extends InstantCommand {

    public HoodEncoderReset() {
        addRequirements(Robot.hood);
    }

    @Override
    public void initialize() {
        //HoodMAP.hoodEncoder.setPosition(0.0);
        HoodMAP.initEncoderZero = HoodMAP.hoodEncoder.getPosition();

    }

}