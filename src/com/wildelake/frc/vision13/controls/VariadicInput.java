package com.wildelake.frc.vision13.controls;

/**
 * wraps variadic inputs i.e. the axes of a joystick (-1,1)
 * @author admin
 *
 */
public class VariadicInput extends ControllerInput {

	public VariadicInput(Controller top, int port) {
		super(top, port);
	}
	
	public double getRawValue() {
		return parent.getVariadicInput(portID);
	}
}
