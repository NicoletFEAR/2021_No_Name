package frc.robot.climb; // the package where this file lives

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbMAP {

    // Speed Controllers/Motors
    public static CANSparkMax climbMotor;

    // Intake Constants
    public static double DEFAULT_SPEED = 0.2; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum intake speed

    // Piston

    public static void init() {

        climbMotor = new CANSparkMax(20, MotorType.kBrushless);

        climbMotor.setSmartCurrentLimit(80, 80);

        climbMotor.setIdleMode(IdleMode.kBrake);



    }

}
