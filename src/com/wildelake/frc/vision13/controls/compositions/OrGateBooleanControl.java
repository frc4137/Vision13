package com.wildelake.frc.vision13.controls.compositions;


/**
 * OrGateBooleanControl allows two buttons to be pressed at once and read
 * as a single, different button, but only if they are pressed at the same time
 *
 */
public class OrGateBooleanControl extends BooleanControl {
	private final BooleanControl button1, button2;
	
	public OrGateBooleanControl(BooleanControl button1, BooleanControl button2) {
		this.button1 = button1;
		this.button2 = button2;
	}
	
	public void update() {
		setValue(button1.getValue() || button2.getValue());
	}
}
