package com.wildelake.frc.vision13.controls.compositions;

/**
 * ToggleDoubleVariadicControl is used to double the input from a variadic control while
 * a specified button is being pressed
 *
 */
public class ToggleDoubleVariadicControl extends VariadicControl {
	private final BooleanControl toggle;
	private final VariadicControl source;
	
	public ToggleDoubleVariadicControl(BooleanControl toggle, VariadicControl source) {
		this.toggle = toggle;
		this.source = source;
	}
	
	public void update() {
		double result = source.getValue();
		if (toggle.getValue()) {
			result *= 2;
			result = Math.min(1.0, result);
			result = Math.max(-1.0, result);
		}
		setValue(result);
	}
}
