package org.usfirst.frc2832.Robot_2016.commands;

import org.usfirst.frc2832.Robot_2016.Kicker;
import org.usfirst.frc2832.Robot_2016.Robot;

import edu.wpi.first.wpilibj.command.Command;
//runs the kicker
public class Kick extends Command {
//Kicks out kicker and then resets it after timeout
private static long timeStart;
private static final long TIMEOUT = 1500;


	public Kick() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		timeStart = System.currentTimeMillis();
		Kicker.launch();
		Robot.isShooting = true;
	}

	@Override
	protected void execute() {
		//Kicker.launch();

	}

	@Override
	protected boolean isFinished() {
		return (timeStart + TIMEOUT < System.currentTimeMillis());
	}	




	@Override
	protected void end() {
		Kicker.resetAfterLaunch();
		Robot.isShooting = false;
	}

	@Override
	protected void interrupted() {
		Kicker.reset();
		Robot.isShooting = false;
	}
}