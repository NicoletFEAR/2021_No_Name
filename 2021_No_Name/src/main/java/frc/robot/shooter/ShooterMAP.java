package frc.robot.shooter; // the package where this file lives

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterMAP {

    // Speed Controllers/Motors
    public static CANSparkMax flywheelMotor; // the big shooter flywheel
    public static CANSparkMax flywheelMotor2;
    //public static CANSparkMax hoodMotor;
    //public static CANSparkMax spindexerMotor;
    public static double setPoint;
  private static double adjustableSpeed;

    // Shooter Constants
    public static double DEFAULT_HOLD_SPEED = 1.0; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum intake speed

    public static void init() {

        flywheelMotor = new CANSparkMax(24, MotorType.kBrushless);
        flywheelMotor2 = new CANSparkMax(25, MotorType.kBrushless);

        // hoodMotor = new CANSparkMax(32, MotorType.kBrushless);
        // spindexerMotor = new CANSparkMax(33, MotorType.kBrushless);
        
        
        flywheelMotor.setSmartCurrentLimit(80, 80);
        flywheelMotor2.setSmartCurrentLimit(80, 80);

        flywheelMotor.setIdleMode(IdleMode.kBrake);
        flywheelMotor2.setIdleMode(IdleMode.kBrake);

        
        flywheelMotor2.follow(flywheelMotor);
        flywheelMotor2.setInverted(true);


        adjustableSpeed = 0.75;
        //hoodSet = 0.0;

        SmartDashboard.putNumber("SHOOT SPD", adjustableSpeed);
        SmartDashboard.putNumber("HOOD SET", 0.0);


        
		// How You Would Get and Set Encoder Pos From Other Classes:
        //turretEncoder.getQuadraturePosition();
        //turretEncoder.getAnalogIn();
        //turretEncoder.setAnalogPosition(int new position, int timeoutMs);
    }



}