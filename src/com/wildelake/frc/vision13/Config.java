package com.wildelake.frc.vision13;

/**
 * Allows different runtime settings to be specified for different environments
 */
public final class Config {
	private static boolean configured = false;
	
	/**
	 * marchOn tells whether to let the robot crash when it isn't absolutely necessary to prevent damage or injury.
	 */
	private static boolean marchOn;
	public static boolean getMarchOn() { return marchOn; }
	
	public static void development() {
		if (configured) return;
		configured = true;
		
		marchOn = false;
	}
	
	public static void production() {
		if (configured) return;
		configured = true;
		
		marchOn = true;
	}
}
