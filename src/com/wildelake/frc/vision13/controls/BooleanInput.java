package com.wildelake.frc.vision13.controls;

public class BooleanInput extends ControllerInput {

	public BooleanInput(Controller top, int port) {
		super(top, port);
	}
	
	public boolean getRawValue() {
		return parent.getBooleanInput(portID);
	}
}
