package com.wildelake.frc.vision13.controls.compositions;

/**
 * MultiplyVariadicControl is used to scale the input from a variadic
 * control while a specified button is being pressed
 *
 */
public class MultiplyVariadicControl extends VariadicControl {
	private final VariadicControl source;
	private final double multiplier;
	
	public MultiplyVariadicControl(VariadicControl source, double multiplier) {
		this.source = source;
		this.multiplier = multiplier;
	}
	
	public void update() {
		setValue(source.getValue() * multiplier);
	}
}
