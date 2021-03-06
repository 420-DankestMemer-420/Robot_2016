package org.usfirst.frc2832.Robot_2016.commands.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

//Class that takes input of arraylist of commands and turns it into a command group (all sequential as of now)
//Intention is to take input from the output of ParseInput and output a commandgroup to run for autonomous
public class ConstructedAutonomous extends CommandGroup {
	
	public ConstructedAutonomous(ArrayList<Command> commands)
	{
		for(Command e:commands)
			addSequential(e);
	}

}
