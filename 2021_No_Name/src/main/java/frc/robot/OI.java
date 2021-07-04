package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.drivebase.shifter.ManualDown;
import frc.robot.drivebase.shifter.ManualUp;
// import frc.robot.drivebase.*
import frc.robot.intake.In;
import frc.robot.intake.Out;
import frc.robot.kicker.KickToShooter;
import frc.robot.shooter.Lob;

/**
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public XboxController xbox0; // the drive controller // input 0 on driver station
    private Button xbox0LeftStick;
    private Button xbox0RightStick;
    private Button xbox0LBumper;
    private Button xbox0RBumper;
    private Button xbox0Start;
    private Button xbox0Back;
    private Button xbox0X;
    private Button xbox0Y;
    private Button xbox0B;
    private Button xbox0A;
    private POVButton xbox0DpadUp;
    private POVButton xbox0DpadRight;
    private POVButton xbox0DpadDown;
    private POVButton xbox0DpadLeft;

    public OI() {

        // Init our driver controller and buttons
        xbox0 = new XboxController(0);

        xbox0A = new JoystickButton(xbox0, 1);
        xbox0B = new JoystickButton(xbox0, 2);
        xbox0X = new JoystickButton(xbox0, 3);
        xbox0Y = new JoystickButton(xbox0, 4);
        xbox0LBumper = new JoystickButton(xbox0, 5);
        xbox0RBumper = new JoystickButton(xbox0, 6);
        xbox0Back = new JoystickButton(xbox0, 7);
        xbox0Start = new JoystickButton(xbox0, 8);
        xbox0LeftStick = new JoystickButton(xbox0, 9);
        xbox0RightStick = new JoystickButton(xbox0, 10);
        xbox0DpadUp = new POVButton(xbox0, 0);
        xbox0DpadLeft = new POVButton(xbox0, 270);
        xbox0DpadRight = new POVButton(xbox0, 90);
        xbox0DpadDown = new POVButton(xbox0, 180);

        // Buttom Map https://docs.google.com/drawings/d/1BF8G-HcMui3Qu7I2f9eXdoeyDQ2lp7zIP9lheYLL0Qc/edit

        // DRIVE ---------------------------------------------

        //xbox0X.whenPressed(() -> Robot.driveBase.switchFront()); 

        xbox0LBumper.whenPressed(new ManualDown()); // shifter
        xbox0RBumper.whenPressed(new ManualUp()); // shifter

        // SHOOTER -------------------------------------------
        
        xbox0Y.whenHeld(new Lob()); // shoot at fixed parade lob

        // KICKER --------------------------------------------

        xbox0B.whenHeld(new KickToShooter()); // for Auto Shooting

        // INTAKE --------------------------------------------
        
        xbox0A.whenHeld(new In());
        xbox0X.whenHeld(new Out());
        
        // D pad up and down for intake piston
        xbox0DpadUp.whenPressed(() -> Robot.intake.deploy());
        xbox0DpadDown.whenPressed(() -> Robot.intake.retract());
        
        // SPINDEXER -----------------------------------------
        
        xbox0DpadRight.whenPressed(() -> Robot.spindexer.spinClockwise());
        xbox0DpadRight.whenReleased(() -> Robot.spindexer.stop());
        xbox0DpadLeft.whenPressed(() -> Robot.spindexer.spinCounterClockwise());
        xbox0DpadLeft.whenReleased(() -> Robot.spindexer.stop());

        // CLIMB ---------------------------------------------
    
    }

    // Driver
    public XboxController getXbox0() {
        return xbox0;
    }


    public double getDriveRightTrigger() {
        return getXbox0().getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getDriveLeftTrigger() {
        return getXbox0().getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public double getDriveSteer() {
        return getXbox0().getX(GenericHID.Hand.kLeft);
    }


}