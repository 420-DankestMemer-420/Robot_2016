package org.usfirst.frc2832.Robot_2016;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class adds new behavior to CANTalon to give better safety
 * If you use this instead of CANTalon, the motor <b>shouldn't catch on fire.</b>
 * @author Brendan
 *
 */
public class CANTalonCurrentSafety extends CANTalon {

	/**
	 * A thread that monitors this motor's current.
	 * It disables the motor temporarily if the current goes too high.
	 * @author Brendan
	 *
	 */
	private class MonitorThread extends Thread {
		@Override
		public void run() {
			double current = 0;
			final double filter = 0.1666667;
			final double max = 30; // 30 Amps should not be violated in most cases
			
			boolean isDisabled = false;
			
			while (!Thread.currentThread().isInterrupted()) {
				// Rolling Avg.
				current = filter * getOutputCurrent() + current * (1 - filter);
				if (current > max * 0.9 && !isDisabled) {
					disableControl();
					isDisabled = true;
				}else if (current < max * 0.80 && isDisabled){
					enableControl();
					isDisabled = false;
				}
				Timer.delay(0.1);
			}
		}
	}
	private Thread th;
	
	public CANTalonCurrentSafety(int deviceNumber) {
		super(deviceNumber);
		th = new MonitorThread();
		th.start();
	}

	@Override
	public String getSmartDashboardType() {
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		th.interrupt();
	}
}
