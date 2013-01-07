package com.wildelake.frc.vision13.controls;

/**
 * A ControllerInput is a object that wraps the raw input from a Controller
 * @author admin
 *
 */
public abstract class ControllerInput {
	protected Controller parent; // the controller which is being referenced
	protected int portID;
	public ControllerInput(Controller top, int port) {
		parent = top;
		portID = port;
	}
}
