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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.usfirst.frc2832.Robot_2016.HID.GamepadState;
import org.usfirst.frc2832.Robot_2016.commands.Intake;
import org.usfirst.frc2832.Robot_2016.commands.InterfaceFlip;
import org.usfirst.frc2832.Robot_2016.commands.MoveAimerDown;
import org.usfirst.frc2832.Robot_2016.commands.MoveAimerUp;
import org.usfirst.frc2832.Robot_2016.commands.Shoot;
import org.usfirst.frc2832.Robot_2016.commands.SpinShooterWheels;
import org.usfirst.frc2832.Robot_2016.commands.StopAimer;
import org.usfirst.frc2832.Robot_2016.commands.StopBallMotors;
import org.usfirst.frc2832.Robot_2016.commands.autonomous.ConstructedAutonomous;
import org.usfirst.frc2832.Robot_2016.commands.autonomous.MoveForward;
import org.usfirst.frc2832.Robot_2016.commands.autonomous.ParseInput;
import org.usfirst.frc2832.Robot_2016.commands.autonomous.RotateAngle;
import org.usfirst.frc2832.Robot_2016.vision.CameraServer2832;

import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.can.CANJNI;
import edu.wpi.first.wpilibj.can.CANExceptionFactory;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Enumeration;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    private static boolean recordedAuton = false;

	Command autonomousCommand;
    public static NetworkTable table;
    
    public static OI oi;
    public static BallMotors ballMotors = new BallMotors();
    public static Aimer aimer = new Aimer();
    public static double defaultAngle;
    public static USBCamera camera1, camera2;
    public boolean leftTriggerPressed = false;
    public boolean rightTriggerPressed = false;
    public boolean shooterNotActive = true;
    public boolean povPressed = false;
    private long lastRunTime = System.currentTimeMillis();
    private long timer = 0;
	private String recordedID;
    public static SendableChooser auto_Movement, auto_Reverse, auto_isHighGoal;
//    public static int gameMode; // 0 is autonDrive, 1 teleopDrive, 2 ingesting, 3 shooting
//    public static boolean isAuton;
    public static boolean isBlue;
    public static boolean isSpinning;
    public static boolean isShooting;
    public static boolean isExpelling;
    public static boolean isIngesting;

    private IntBuffer CAN_status = ByteBuffer.allocateDirect(4).asIntBuffer();
    private IntBuffer CAN_messageId = ByteBuffer.allocateDirect(4).asIntBuffer();
    private ByteBuffer CAN_data = ByteBuffer.allocateDirect(8);
    private ByteBuffer CAN_timestamp = ByteBuffer.allocate(4);

    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands 
        //(which it very likely will), subsystems are not guaranteed to be 
        // constructed yet. Thus, their requires() statements may grab null 
        // pointers. Bad news. Don't move it.
        
        oi = new OI();
        try {
	        camera1 = new USBCamera("cam0");
	        camera2 = new USBCamera("cam1");
	        
	        camera1.setFPS(15);
	        camera1.setSize(320, 240);
	        
	        camera2.setFPS(15);
	        camera2.setSize(320, 240);
	        CameraServer2832 cameraServer = CameraServer2832.getInstance();
	        cameraServer.startAutomaticCapture(camera1, camera2);
        } catch (VisionException e) {
        	e.printStackTrace();
        }

        auto_Movement = new SendableChooser();
        auto_Movement.addObject("Do nothing at all", "0");
        auto_Movement.addObject("Rotate 45", "r45");
        auto_Movement.addObject("Rotate -45", "r-45");
        auto_Movement.addObject("Move Forward 3", "f3");
        auto_Movement.addDefault("Move Forward 5", "f5");
        auto_Movement.addObject("Move Forward 6.5", "f6.5");
        auto_Movement.addObject("Spy Bot", "s");
        SmartDashboard.putData("Autonomous Selection", auto_Movement);
        
        auto_Reverse = new SendableChooser();
        auto_Reverse.addDefault("Just move forward", false);
        auto_Reverse.addObject("Go backwards after", true);
        SmartDashboard.putData("Add backwards motion", auto_Reverse);
        
        auto_isHighGoal = new SendableChooser();
        auto_isHighGoal.addDefault("No shot", 0);
        auto_isHighGoal.addObject("Low Goal", 1);
        auto_isHighGoal.addObject("High Goal", 2);
        SmartDashboard.putData("Shooting", auto_isHighGoal);
        
        
        isBlue = false;
        isSpinning = false;
        isShooting = false;
        isExpelling = false;
        isIngesting = false;

        RobotMap.winchMotor.setEncPosition(0);
        Aimer.loadPreferences();
        
        table = NetworkTable.getTable("GRIP/contours");
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	if (recordedAuton)
    		oi.gamepad.loadVirtualGamepad(recordedID);
    	RobotMap.winchMotor.setEncPosition(0);
    	RobotMap.winchMotor.enableBrakeMode(false);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        
        recordedID = (String) (oi.index.getSelected());
        recordedAuton = SmartDashboard.getBoolean("Use Recorded Autonomous");
        
        Aimer.toPositionMode();
        RobotMap.winchMotor.setEncPosition(0);
        RobotMap.winchMotor.setPosition(0);
        RobotMap.winchMotor.set(0);
        DashboardOutput.putPeriodicData();
    
        isBlue = (DriverStation.getInstance().getAlliance() == Alliance.Blue);
          
        sendStateToLights(false, false);
    }

    public void autonomousInit() {
    	RobotMap.winchMotor.enableBrakeMode(true);
    	if (recordedAuton) {
    		oi.gamepad.startVirtualGamepad();
    	} else {
		    // schedule the autonomous command (example)	
			autonomousCommand = (CommandGroup) new ConstructedAutonomous(ParseInput.takeInput((String)auto_Movement.getSelected(), 
					(boolean)auto_Reverse.getSelected(), (int)auto_isHighGoal.getSelected()));
			if(autonomousCommand != null)
				autonomousCommand.start();
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    	if (recordedAuton)
    		handleInput(oi.gamepad);
    	
        Scheduler.getInstance().run();
        DashboardOutput.putPeriodicData();//this is a method to contain all the "putNumber" crap we put to the Dashboard

        sendStateToLights(true, true);
    }

    public void teleopInit() {
    	RobotMap.winchMotor.enableBrakeMode(true);
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	// Recordable Autonomous
    	oi.gamepad.updateRecordStatus();
    	
    	DashboardOutput.putPeriodicData();
    	
    	handleInput(oi.gamepad);
    	
        Scheduler.getInstance().run();
        
        sendStateToLights(true, false);
        
      //D-Pad Controls
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private void handleInput(GenericHID g) {
    	RobotMap.driveTrain.arcadeDrive(
    			g.getRawAxis(GamepadState.AXIS_LY) * (InterfaceFlip.isFlipped ? 1 : -1), 
    			g.getRawAxis(GamepadState.AXIS_RX));
    	
    	//D-Pad Controls

        switch (oi.gamepad.getPOV()) {
		//D-pad right
		case 90:
			if (!povPressed) {
				Scheduler.getInstance().add(null);
			}
			povPressed = true;
			break;
		//D-pad left
		case 270:
			if (!povPressed) {
				Scheduler.getInstance().add(null);
			}
			povPressed = true;
			break;
		//D-pad up
		case 0:
			// Use speed mode if not currently used
			if (!povPressed) {
				Scheduler.getInstance().add(new MoveAimerUp());
			}
			povPressed = true;
			break;
		//D-pad down
		case 180:
			if (!povPressed) {
				Scheduler.getInstance().add(new MoveAimerDown());
			}
			povPressed = true;
			break;
		case -1:
			if (povPressed == true) {
			Scheduler.getInstance().add(new StopAimer());
			}
			povPressed = false;
			break;
		}
        
       //left Trigger Code
        if (oi.gamepad.getRawAxis(GamepadState.AXIS_LT) >= .2) {
        	Scheduler.getInstance().add(new Intake());
        	
			leftTriggerPressed = true;
		} else {
			leftTriggerPressed = false;
		}
        //right Trigger Code
        if (oi.gamepad.getRawAxis(GamepadState.AXIS_RT) >= .2) {  
        	Scheduler.getInstance().add(new SpinShooterWheels());
        	
        	rightTriggerPressed = true;
		} else if (rightTriggerPressed){
			rightTriggerPressed = false;
			shooterNotActive = true;
			Scheduler.getInstance().add(new StopBallMotors());
			
		}
      //  if (rightTriggerPressed && shooterNotActive) {
        //	gameMode = 3;
        //	Scheduler.getInstance().add(new SpinShooterWheels());
        //	shooterNotActive = false;
       // }
        
        
    }
 
    
    
    
    void sendStateToLights(boolean isEnabled, boolean isAutonomous)
    {
 //   	final int MSGID_FOR_LIGHTS = CANJNI.LM_API_ICTRL_T_SET | 0x30; // ID=59

    	// LM_API_ICTRL_T_SET =((0x00020000 | 0x02000000 | 0x00001000) | (7 << 6));
    	// Final ID: 0x020211FB
    	// (7 < 6) => 111000000 => 0x1C0
    	// Decoded on Ardinio as 0x1F02033B
    	// Random: 0x2041441
    	
    	// DO NOT CHANGE THIS NUMBER
    	// Doug and Justin worked for a long while to find an ID that works.
    	// We are using CAN ID 16 (0x10) Bigger IDs don't seem to work.
    	final int MSGID_FOR_LIGHTS = 0x02021450;
    			
    	timer = System.currentTimeMillis();
    	if ( timer > lastRunTime + 100 ) // At least 100 ms difference.
    	{
    		lastRunTime = timer;

    		CAN_data.put(0, (byte)(isAutonomous ? 0 : 1) );
    		CAN_data.put(1, (byte)(isBlue ? 0 : 1) );
    		CAN_data.put(2, (byte)(isEnabled ? 1 : 0) );
    		CAN_data.put(3, (byte)(isSpinning ? 1 : 0) );
    		CAN_data.put(4, (byte)(isShooting ? 1 : 0) );
    		CAN_data.put(5, (byte)(isExpelling ? 1 : 0) );
    		CAN_data.put(6, (byte)(isIngesting ? 1 : 0) );
    		CAN_data.put(7, (byte)0);
    		
    		try 
    		{
    			CANJNI.FRCNetworkCommunicationCANSessionMuxSendMessage(MSGID_FOR_LIGHTS, CAN_data, CANJNI.CAN_SEND_PERIOD_NO_REPEAT);
    		} 
    		catch (Exception e) 
    		{
//    			e.printStackTrace();
    		}
    		
    	}
    }
  
    public static String GetMacAddress()
    {
    	String address = null;
    	InetAddress lanIp = null;

    	try 
    	{
    		String ipAddress = null;
    		Enumeration<NetworkInterface> net = null;
    		net = NetworkInterface.getNetworkInterfaces();
    		while(net.hasMoreElements())
    		{
    			NetworkInterface element = net.nextElement();
    			Enumeration<InetAddress> addresses = element.getInetAddresses();
    			while (addresses.hasMoreElements())
    			{
    				InetAddress ip = addresses.nextElement();
    				if (ip instanceof Inet4Address)
    				{
    					if (ip.isSiteLocalAddress())
    					{
    						ipAddress = ip.getHostAddress();
    						lanIp = InetAddress.getByName( ipAddress );
    					}
    				}
    			}
    		}

    		if( lanIp != null )
    		{
    			NetworkInterface network = NetworkInterface.getByInetAddress( lanIp );
    			byte[] mac = network.getHardwareAddress();

    			StringBuilder sb = new StringBuilder();
    			for (int i = 0; i < mac.length; i++) 
    			{
    				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
    			}
    			address = sb.toString();
    		}
    	} 
    	catch (Exception e)
    	{
//    		e.printStackTrace();
    	}

    	return address;
    }
}
