package frc.robot.shooter.turret;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class TurretMAP {

    // Turret motor
    //public static TalonSRX turretMotor;
    public static CANSparkMax turretMotor;

    // Turret encoder
    //public static SensorCollection turretEncoder;
    public static CANEncoder turretEncoder;
    public static double turretInitEncoderZero = 0.0;

    public static double DEFAULT_SPEED = 0.3; //  if we want a default speed for commands
    public static double MAX_SPEED = 0.5; 

    public static int MAX_ENCODER = 1000;
    public static int MIN_ENCODER = -1000;
    

    public static void init() {
        //turretMotor = new TalonSRX(27); 
        turretMotor = new CANSparkMax(27, MotorType.kBrushless);
        turretMotor.setSmartCurrentLimit(40, 40);

        turretMotor.setIdleMode(IdleMode.kBrake);
        //turretMotor.setNeutralMode(NeutralMode.Brake);
        turretMotor.setInverted(false);

        turretEncoder = turretMotor.getEncoder();

        //turretEncoder = turretMotor.getSensorCollection();
        //initEncoderZero = (TurretMAP.turretEncoder.getPulseWidthRiseToFallUs() - 1024) / (8 * 4095);
        turretInitEncoderZero = turretEncoder.getPosition();

        // How You Would Get and Set Encoder Pos From Other Classes:
        //turretEncoder.getQuadraturePosition();
        //turretEncoder.getPulseWidth();
        //turretEncoder.setAnalogPosition(int new position, int timeoutMs);

        //Add Turret PID later
    }
    
}