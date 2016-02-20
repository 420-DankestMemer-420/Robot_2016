package org.usfirst.frc2832.Robot_2016.commands.autonomous;

import org.usfirst.frc2832.Robot_2016.DriveEncoders;
import org.usfirst.frc2832.Robot_2016.RobotMap;
import org.usfirst.frc2832.Robot_2016.TrajectoryController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class  MoveForward extends Command {
	
	static double dist, initVal, startAngle, correctiveForce;
	static final double TOLERANCE = 0.05, kP = 0.05; //constant of proportion for PID
	static TrajectoryController tc;
	
    public MoveForward(double distance) {
        // Use requires() here to declare subsystem dependencies
        // e.g. requires(chassis);
    	dist = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initVal = DriveEncoders.getAbsoluteValue();
    	tc = new TrajectoryController(dist, 0.4, 0.4, 0.6, 0.9, -0.9); //TO-DO: would be nice to test these numbers!
    	startAngle = RobotMap.imu.getYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	correctiveForce = -kP * (RobotMap.imu.getYaw() - startAngle); //corrects for displaced angles while moving forward (e.g. over Ramparts); sign may be wrong
    	RobotMap.driveTrain.arcadeDrive(-tc.get(DriveEncoders.getAbsoluteValue() - initVal), correctiveForce); //set speed to one given by Trajectory Controller.
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (DriveEncoders.getAbsoluteValue() - initVal) > dist; //TODO: Only works for positive dist
        //Math.abs(DriveEncoders.getAbsoluteValue() - initVal - dist) < TOLERANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
