package com.wildelake.frc.vision13.controls.compositions;

/**
 * ToggleDoubleVariadicControl is used to double the input from a variadic control while
 * a specified button is being pressed
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
		double result = source.getValue() * multiplier;
		result = Math.min(1.0, result);
		result = Math.max(-1.0, result);
		setValue(result);
	}
}
