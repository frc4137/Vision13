package com.wildelake.frc.vision13.controls.compositions;

/**
 * ToggleMultiplyVariadicControl is used to multiply the input from a VariadicControl while
 * a specified button is being pressed
 *
 */
public class ToggleMultiplyVariadicControl extends VariadicControl {
	private final BooleanControl toggle;
	private final VariadicControl source;
	private final double multiplier;
	
	public ToggleMultiplyVariadicControl(BooleanControl toggle, VariadicControl source, double multiplier) {
		this.toggle = toggle;
		this.source = source;
		this.multiplier = multiplier;
	}
	
	public void update() {
		setValue(toggle.getValue() ? source.getValue() * multiplier : source.getValue());
	}
}
