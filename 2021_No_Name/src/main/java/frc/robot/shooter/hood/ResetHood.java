package frc.robot.shooter.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import com.revrobotics.CANEncoder;
import frc.robot.shooter.hood.*;


public class ResetHood extends SequentialCommandGroup {


    public ResetHood() {
        addCommands(

            //new SetHoodSpeed(-0.2),
    
            //new WaitCommand(2),

            new HoodEncoderReset(),

            new SetHoodSpeed(0.0)
            );
      }         


}