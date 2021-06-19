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

  // REV PID definitions
  public static CANPIDController m_pidController;
  public static CANEncoder m_encoder;
  public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
  // 

  public static boolean doShoot;

    public static void init() {

        doShoot = true;

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

        // REV PID
        m_pidController = flywheelMotor.getPIDController();
        m_encoder = flywheelMotor.getEncoder();
        // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000156; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // Smart Motion Coefficients
    maxVel = 2000; // rpm
    maxAcc = 1500;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    /**
     * Smart Motion coefficients are set on a CANPIDController object
     * 
     * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
     * the pid controller in Smart Motion mode
     * - setSmartMotionMinOutputVelocity() will put a lower bound in
     * RPM of the pid controller in Smart Motion mode
     * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
     * of the pid controller in Smart Motion mode
     * - setSmartMotionAllowedClosedLoopError() will set the max allowed
     * error for the pid controller in Smart Motion mode
     */
    int smartMotionSlot = 0;
    m_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    m_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    m_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);
        //

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