// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.drivebase;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Robot;
// import frc.robot.drivebase.*;

// public class TurnToTarget extends CommandBase {
//   // private double startTime;

//   /**
//    * Creates a new TurnToTargetCMD.
//    */
//   public TurnToTarget() {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(Robot.driveBase);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {
//     // startTime = System.currentTimeMillis();
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     // Horizonatal Offset
//     double hOffset = Robot.driveBase.getHorizontalOffset(); // -90 to 90 ish

//     double maxOutputSpeed = 0.2;
//     double output = (Math.abs(hOffset) / 90.0) * 0.03 + .03;
//     if (output > maxOutputSpeed) {
//       output = maxOutputSpeed;
//     }

//     SmartDashboard.putNumber("VisionTurnOutput", output);

//     if (hOffset < -DriveBaseMAP.VISION_HORIZONTAL_OFFSET_TOLERANCE) {
//       Robot.driveBase.manualDrive(-output, output);
//     } else if (hOffset > DriveBaseMAP.VISION_HORIZONTAL_OFFSET_TOLERANCE) {
//       Robot.driveBase.manualDrive(output, -output);
//     } else {
//       Robot.driveBase.stop();
//     }
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//     Robot.driveBase.stop();
//   }

//   // Returns true when the command should end
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }