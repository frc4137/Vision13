package com.wildelake.frc.vision13.controls;

public class BooleanInput extends ControllerInput {
	
	public BooleanInput(ControllerManager top) {
		super(top);
	}

	@Override
	public int getControllerID() {
		return 0;
	}

	@Override
	public int getPort() {
		return 0;
	}
	
}
