package org.usfirst.frc2832.Robot_2016.HID;

import java.io.Serializable;

import edu.wpi.first.wpilibj.Joystick;

/**
 * <p>
 * An immutable gamepad object.  Treat this as a gamepad "frozen in time".
 * This is to be used instead of the regular WPIlib Joystick or GamepadDeadzoned
 * classes, for use with recordable autonomous.
 * </p>
 * @author Brendan
 *
 */
public class GamepadState implements Serializable {
	private static final long serialVersionUID = 9198171031103864921L;
	
	/**
	 * Indices for Xbox-based controllers
	 */
	
	// Note that buttons start at 1, not zero - accessing the array should use these minus 1
	public static final transient int
		BUTTON_A  = 1,
		BUTTON_B  = 2,
		BUTTON_X  = 3,
		BUTTON_Y  = 4,
		BUTTON_LB = 5,
		BUTTON_RB = 6,
		BUTTON_BACK = 7,
		BUTTON_START = 8,
		
		AXIS_LX = 0,
		AXIS_LY = 1,
		AXIS_LT = 2,
		AXIS_RT = 3,
		AXIS_RX = 4,
		AXIS_RY = 5;
	
	public final double axes[];
	public final boolean buttons[];
	public final int pov;
	public final long timestamp;
	
	/**
	 * Creates a new gamepad state based off of raw data. You probably don't want to use this, use makeState instead.
	 * @param axes
	 * @param buttons
	 * @param pov
	 * @see makeState
	 */
	public GamepadState(double axes[], boolean buttons[], int pov, long timestamp) {
		this.axes = axes.clone();
		this.buttons = buttons.clone();
		this.pov = pov;
		this.timestamp = timestamp;
	}
	
	/**
	 * Creates a new GamepadState based off of a gamepad's current state.
	 * @param j The gamepad object
	 * @return A gamepad state
	 */
	public static GamepadState makeState(Joystick j) {
		double[] axes = new double[j.getAxisCount()];
		for(int i = 0; i < axes.length; i ++)
			axes[i] = j.getRawAxis(i);
		
		boolean[] buttons = new boolean[j.getButtonCount()];
		for(int i = 0; i < buttons.length; i ++)
			buttons[i] = j.getRawButton(i + 1);
		
		return (new GamepadState(axes, buttons, j.getPOV(), System.currentTimeMillis()));
	}
}
