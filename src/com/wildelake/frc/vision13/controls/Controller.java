package com.wildelake.frc.vision13.controls;

/**
 * Controller is a standard interface for physical controllers such as joysticks
 * it could also be used for other controllers such as a minecraft server or the keyboard
 */

public interface Controller {
	public Object[] getAllInputs();
	public double   getVariadicInput(int port);
	public boolean  getBooleanInput (int port);
}