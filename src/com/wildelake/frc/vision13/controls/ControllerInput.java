package com.wildelake.frc.vision13.controls;

public abstract class ControllerInput {
	protected Controller parent;
	protected int portID;
	public ControllerInput(Controller top, int port) {
		parent = top;
		portID = port;
	}
}
