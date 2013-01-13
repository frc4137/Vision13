package com.wildelake.frc.vision13.controls.compositions;


/**
 * InvertBooleanControl allows reversing on and off buttons
 */
public class InvertBooleanControl extends BooleanControl {
	private final BooleanControl button;
	
	public InvertBooleanControl(BooleanControl button) {
		this.button = button;
	}
	
	public void update() {
		setValue(!button.getValue());
	}
}
