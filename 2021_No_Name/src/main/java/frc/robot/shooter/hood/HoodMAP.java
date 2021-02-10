package frc.robot.shooter.hood; // the package where this file lives

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;


public class HoodMAP {

    // Speed Controllers/Motors
    public static CANSparkMax hoodMotor;
    public static CANEncoder hoodEncoder; // to be easier to access
    public static CANPIDController hoodController;

    // Hold Constants
    public static double DEFAULT_SPEED = 0.1; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum hood movement speed


    public static void init() {

        hoodMotor = new CANSparkMax(32, MotorType.kBrushless); 
        hoodEncoder = hoodMotor.getEncoder();

    }

    

}