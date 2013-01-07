package com.wildelake.frc.vision13.controls;

public class VariadicInput extends ControllerInput {

	public VariadicInput(Controller top, int port) {
		super(top, port);
	}
	
	public double getRawValue() {
		return parent.getVariadicInput(portID);
	}
}
