package frc.robot.intake; // the package where this file lives

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbMAP {

    // Speed Controllers/Motors
    public static CANSparkMax climbMotor;

    // Intake Constants
    public static double DEFAULT_SPEED = 0.5; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum intake speed

    // Piston
    public static DoubleSolenoid intakePiston;

    public static void init() {

        intakeMotor = new CANSparkMax(21, MotorType.kBrushless);

        intakeMotor.setSmartCurrentLimit(80, 80);

        intakeMotor.setIdleMode(IdleMode.kBrake);

        intakePiston = new DoubleSolenoid(2, 3);


    }

}
