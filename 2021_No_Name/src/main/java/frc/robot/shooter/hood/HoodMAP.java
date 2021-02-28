package frc.robot.shooter.hood; // the package where this file lives

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class HoodMAP {

    // Speed Controllers/Motors
    public static CANSparkMax hoodMotor;
    public static CANEncoder hoodEncoder; // to be easier to access
    public static CANPIDController hoodController;

    // Hold Constants
    public static double DEFAULT_SPEED = 0.1; //  if we want a default speed for commands
    public static double HOOD_MULTIPLIER = 0.5;
    public static double MAX_SPEED = 0.4; // set a maximum hood movement speed

    public static double MAX_ENCODER = 108;
    public static double MIN_ENCODER = 0;

    public static double initEncoderZero = 0.0;

    public static void init() {

        hoodMotor = new CANSparkMax(32, MotorType.kBrushless); 
        
        hoodMotor.setSmartCurrentLimit(40, 40);

        hoodMotor.setIdleMode(IdleMode.kBrake);

        hoodEncoder = hoodMotor.getEncoder();

        hoodController = hoodMotor.getPIDController();

    }



}
