package frc.robot.shooter.turret;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;


public class TurretMAP {

    // Turret motor
    public static TalonSRX turretMotor;

    // Turret encoder
    public static SensorCollection turretEncoder;

    public static double DEFAULT_SPEED = 0.1; //  if we want a default speed for commands
    public static double MAX_SPEED = 1.0; 

    public static int MAX_ENCODER = 1000;
    public static int MIN_ENCODER = -1000;
    

    public static void init() {
        turretMotor = new TalonSRX(34);  

        turretMotor.setNeutralMode(NeutralMode.Brake);
        turretMotor.setInverted(false);

        turretEncoder = turretMotor.getSensorCollection();

        // How You Would Get and Set Encoder Pos From Other Classes:
        //turretEncoder.getQuadraturePosition();
        //turretEncoder.getAnalogIn();
        //turretEncoder.setAnalogPosition(int new position, int timeoutMs);

        //Add Turret PID later
    }
    
}