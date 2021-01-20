// package frc.robot.drivebase;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.drivebase.DriveBaseMAP;

// /**
//  *
//  */
// public class Shifter extends SubsystemBase {

// 	public void shiftUp() {
// 		DriveBaseMAP.shifty.set(DriveBaseMAP.HIGH_GEAR); // High = Reverse
// 	}

// 	public void shiftDown() {
// 		DriveBaseMAP.shifty.set(DriveBaseMAP.LOW_GEAR); // Low = Forward
// 	}

// 	// shift the gearbox to the opposite state
// 	public void shift() {

// 		if (DriveBaseMAP.shifty.get() == DriveBaseMAP.HIGH_GEAR) {
// 			shiftDown();
// 		} else {
// 			shiftUp();
// 		}
// 	}

// 	public void stop() {
// 		// DriveBaseMAP.shifty.set(DoubleSolenoid.Value.kOff);
// 	}
// }