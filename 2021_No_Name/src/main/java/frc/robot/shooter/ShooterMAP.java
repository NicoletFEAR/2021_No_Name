package frc.robot.shooter; // the package where this file lives

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import frc.robot.Robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Robot;


public class ShooterMAP {

    // Speed Controllers/Motors
    public static CANSparkMax flywheelMotor; // the big shooter flywheel
    //public static CANSparkMax hoodMotor;
    //public static CANSparkMax holdMotor;
    public static TalonSRX turretMotor;

    // Flywheel PID
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;
    public static CANPIDController flywheelPIDController;
    public static CANEncoder flywheelEncoder;

    // Turret encoder
    public static SensorCollection turretEncoder;

    // Shooter Constants
    public static double DEFAULT_HOLD_SPEED = 1.0; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; // if we want to set a maximum intake speed

    public static void init() {

        flywheelMotor = new CANSparkMax(31, MotorType.kBrushless);
        // hoodMotor = new CANSparkMax(32, MotorType.kBrushless);
        // holdMotor = new CANSparkMax(33, MotorType.kBrushless);
        turretMotor = new TalonSRX(34);  
        
        setUpFlywheelPID();

        turretMotor.setNeutralMode(NeutralMode.Brake);
        turretMotor.setInverted(false);

        turretEncoder = turretMotor.getSensorCollection();
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
        flywheelPIDController.setP(kP);
        flywheelPIDController.setI(kI);
        flywheelPIDController.setD(kD);
        flywheelPIDController.setIZone(kIz);
        flywheelPIDController.setFF(kFF);
        flywheelPIDController.setOutputRange(kMinOutput, kMaxOutput);
        int smartMotionSlot = 0;
        flywheelPIDController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        flywheelPIDController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        flywheelPIDController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        flywheelPIDController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        // if (Robot.debugMode) { 
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
        // }
          // button to toggle between velocity and smart motion modes
          SmartDashboard.putBoolean("Mode", true);
      
    }

    public static void periodCheckFlywheelPIDTuning() { // puts and sets PID Values from smart dashboard
        // PID TUNING FLYWHEEL
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        double maxV = SmartDashboard.getNumber("Max Velocity", 0);
        double minV = SmartDashboard.getNumber("Min Velocity", 0);
        double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
        double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { flywheelPIDController.setP(p); kP = p; }
    if((i != kI)) { flywheelPIDController.setI(i); kI = i; }
    if((d != kD)) { flywheelPIDController.setD(d); kD = d; }
    if((iz != kIz)) { flywheelPIDController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { flywheelPIDController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      flywheelPIDController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }
    if((maxV != maxVel)) { flywheelPIDController.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
    if((minV != minVel)) { flywheelPIDController.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
    if((maxA != maxAcc)) { flywheelPIDController.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
    if((allE != allowedErr)) { flywheelPIDController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }

    double setPoint, processVariable;
    boolean mode = SmartDashboard.getBoolean("Mode", false);
    if(mode) {
      setPoint = SmartDashboard.getNumber("Set Velocity", 0);
      //flywheelPIDController.setReference(setPoint, ControlType.kVelocity); // THIS BIT ACTUALLY SETS THE PID CONTROLLER SET POINT
      processVariable = flywheelEncoder.getVelocity();
    } else {
      setPoint = SmartDashboard.getNumber("Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      //flywheelPIDController.setReference(setPoint, ControlType.kSmartMotion); // THIS BIT ACTUALLY SETS THE PID CONTROLLER SET POINT
      processVariable = flywheelEncoder.getPosition();
    }
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", flywheelMotor.getAppliedOutput());
    }

}
