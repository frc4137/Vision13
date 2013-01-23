package com.wildelake.frc.vision13.controls.compositions;

/**
 * BooleanVariadicControl is used to convert a BooleanControl into a VariadicControl
 * that toggles between two numeric states
 *
 */
public class BooleanVariadicControl extends VariadicControl {
	private final BooleanControl toggle;
	private final double off, on;
	
	public BooleanVariadicControl(BooleanControl toggle, double off, double on) {
		this.toggle = toggle;
		this.off = off;
		this.on = on;
	}
	
	public void update() {
		setValue(toggle.getValue() ? on : off);
	}
}
