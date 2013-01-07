package com.wildelake.frc.vision13.controls;

/**
 * Wraps boolean inputs i.e. buttons
 * @author admin
 *
 */
public class BooleanInput extends ControllerInput {

	public BooleanInput(Controller top, int port) {
		super(top, port);
	}
	
	public boolean getRawValue() {
		return parent.getBooleanInput(portID);
	}
}
