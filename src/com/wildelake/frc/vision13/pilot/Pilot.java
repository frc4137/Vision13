package com.wildelake.frc.vision13.pilot;

/**
 * Pilot is used to specify classes which control the physical behavior of the robot directly
 */
public interface Pilot {
	/**
	 * `tick' should be called on every periodic tick of the main loop.
	 */
	public void tick();
}
