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
    //public static CANSparkMax hoodMotor;
    //public static CANSparkMax holdMotor;
    public static double setPoint;
  private static double adjustableSpeed;
    // Flywheel PID
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public static CANPIDController flywheelPIDController;
    public static CANEncoder flywheelEncoder;

    // Shooter Constants
    public static double DEFAULT_HOLD_SPEED = 1.0; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum intake speed

    public static void init() {

        flywheelMotor = new CANSparkMax(31, MotorType.kBrushless);
        // hoodMotor = new CANSparkMax(32, MotorType.kBrushless);
        // holdMotor = new CANSparkMax(33, MotorType.kBrushless);
        
        
        flywheelMotor.setSmartCurrentLimit(80, 80);

        flywheelMotor.setIdleMode(IdleMode.kBrake);

        setUpFlywheelPID();

        adjustableSpeed = 0.75;
        //hoodSet = 0.0;

        SmartDashboard.putNumber("SHOOT SPD", adjustableSpeed);
        SmartDashboard.putNumber("HOOD SET", 0.0);


        
		// How You Would Get and Set Encoder Pos From Other Classes:
        //turretEncoder.getQuadraturePosition();
        //turretEncoder.getAnalogIn();
        //turretEncoder.setAnalogPosition(int new position, int timeoutMs);
    }

    public static void setUpFlywheelPID() {
        // flywheel PID
        flywheelPIDController = flywheelMotor.getPIDController();
        flywheelEncoder = flywheelMotor.getEncoder();
        // PID coefficients
        kP = 6e-5; 
      kI = 0;
     kD = 0; 
      kIz = 0; 
    kFF = 0.000015; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

        
    // display PID coefficients on SmartDashboard
    /*
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    */
      
    }

    public static void periodCheckFlywheelPIDTuning() { // puts and sets PID Values from smart dashboard
    
    /*  // PID TUNING FLYWHEEL
        // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { flywheelPIDController.setP(p); kP = p; }
    if((i != kI)) { flywheelPIDController.setI(i); kI = i; }
    if((d != kD)) { flywheelPIDController.setD(d); kD = d; }
    if((iz != kIz)) { flywheelPIDController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { flywheelPIDController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      flywheelPIDController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
      */
    }

    /**
     * PIDController objects are commanded to a set point using the 
     * SetReference() method.
     * 
     * The first parameter is the value of the set point, whose units vary
     * depending on the control type set in the second parameter.
     * 
     * The second parameter is the control type can be set to one of four 
     * parameters:
     *  com.revrobotics.ControlType.kDutyCycle
     *  com.revrobotics.ControlType.kPosition
     *  com.revrobotics.ControlType.kVelocity
     *  com.revrobotics.ControlType.kVoltage
     */
    //double setPoint = m_stick.getY()*maxRPM;
    /*
    flywheelPIDController.setReference(setPoint, ControlType.kVelocity);
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("ProcessVariable", flywheelEncoder.getVelocity());
    
    }
    */

    public static void incrementPID(double incrementValue) {
      setPoint += incrementValue;
    }

}