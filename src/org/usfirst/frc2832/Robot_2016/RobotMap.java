// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by RobotBuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2832.Robot_2016;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon frontLeftMotor;
    public static CANTalon rearLeftMotor;
    public static CANTalon frontRightMotor;
    public static CANTalon rearRightMotor;
    
    public static CANTalon ballIngestLeft;
    public static CANTalon ballIngestRight;
    
    public static RobotDrive driveTrain;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        frontLeftMotor = new CANTalon(1);
        LiveWindow.addActuator("Drivetrain", "frontLeft", frontLeftMotor);
    
        rearLeftMotor = new CANTalon(2);
        LiveWindow.addActuator("Drivetrain", "rearLeft", rearLeftMotor);
        
        frontRightMotor = new CANTalon(3);
        LiveWindow.addActuator("Drivetrain", "frontRight", frontRightMotor);
        
        rearRightMotor = new CANTalon(4);
        LiveWindow.addActuator("Drivetrain", "rearRight", rearRightMotor);
        
        driveTrain = new RobotDrive(frontLeftMotor, rearLeftMotor,
              frontRightMotor, rearRightMotor);
        
        driveTrain.setSafetyEnabled(true);
        driveTrain.setExpiration(0.1);
        driveTrain.setSensitivity(0.5);
        driveTrain.setMaxOutput(1.0);

        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        
        ballIngestLeft = new CANTalon(5);
        LiveWindow.addActuator("Ball Handler", "ingestLeft", rearRightMotor);
        ballIngestRight = new CANTalon(6);
        LiveWindow.addActuator("Ball Handler", "ingestRight", rearRightMotor);
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
