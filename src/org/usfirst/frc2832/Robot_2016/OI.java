// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by RobotBuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2832.Robot_2016;

import org.usfirst.frc2832.Robot_2016.Aimer.Levels;
import org.usfirst.frc2832.Robot_2016.HID.GamepadState;
import org.usfirst.frc2832.Robot_2016.HID.RecordableGamepad;
import org.usfirst.frc2832.Robot_2016.HID.SavedStates;
import org.usfirst.frc2832.Robot_2016.commands.Expel;
import org.usfirst.frc2832.Robot_2016.commands.GoToPosition;
import org.usfirst.frc2832.Robot_2016.commands.ImagingTest;
import org.usfirst.frc2832.Robot_2016.commands.InterfaceFlip;
import org.usfirst.frc2832.Robot_2016.commands.Kick;
import org.usfirst.frc2832.Robot_2016.commands.MoveAimerDown;
import org.usfirst.frc2832.Robot_2016.commands.MoveAimerUp;
import org.usfirst.frc2832.Robot_2016.commands.Shoot;
import org.usfirst.frc2832.Robot_2016.commands.ShootHighGoalTowerBase;
import org.usfirst.frc2832.Robot_2016.commands.SpinShooterWheels;
import org.usfirst.frc2832.Robot_2016.commands.StopAimer;
import org.usfirst.frc2832.Robot_2016.commands.TurnToImage;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    
	
	public RecordableGamepad gamepad;
	public JoystickButton aButton;
	public JoystickButton bButton;
	public JoystickButton xButton;
	public JoystickButton yButton;
	public JoystickButton leftBumper;
	public JoystickButton rightBumper;
	public JoystickButton backButton;
	
	public SendableChooser index;
	
    public OI() {
    	
    	//GamePad Settings
    	gamepad = new RecordableGamepad(0);
	    
    	aButton = new JoystickButton(gamepad,GamepadState.BUTTON_A);
	    aButton.whenPressed(new InterfaceFlip());
	    
	    yButton = new JoystickButton(gamepad,GamepadState.BUTTON_Y);
	    yButton.whenPressed(new Expel());
	    
	    backButton = new JoystickButton(gamepad, GamepadState.BUTTON_BACK);
	    backButton.whenPressed(new Shoot());
	    
	    bButton = new JoystickButton(gamepad, GamepadState.BUTTON_B);
	    bButton.whenPressed(new ImagingTest());
	    
	    leftBumper = new JoystickButton(gamepad,GamepadState.BUTTON_LB);
	    leftBumper.whenPressed(new GoToPosition(Levels.GROUND));
	    
	    rightBumper = new JoystickButton(gamepad,GamepadState.BUTTON_RB);
	    rightBumper.whenPressed(new GoToPosition(Levels.START));
	    
	    
	    
	    xButton = new JoystickButton(gamepad, GamepadState.BUTTON_X);
	    xButton.whenPressed(new Kick());
	    
	    RecordableGamepad.dashboardSetup();
	    
	    createRecordedAutonIndex();
    	
		
    	
		DashboardOutput.putOneTimeData(); //this method contains all the buttons for commands

    }

	private void createRecordedAutonIndex() {		
		
		index = new SendableChooser();
		
		SavedStates.loadIndex();
		
		for(String key : SavedStates.getIndex())
			index.addObject(key, key);
        
		SmartDashboard.putData("Recorded Selection", index);
	}
    
}

