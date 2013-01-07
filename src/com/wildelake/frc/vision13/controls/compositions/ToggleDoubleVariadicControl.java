package com.wildelake.frc.vision13.controls.compositions;

import com.wildelake.frc.vision13.utils.Box;

/**
 * ToggleDoubleVariadicControl is used to double the input from a variadic control while
 * a specified button is being pressed
 *
 */
public class ToggleDoubleVariadicControl extends VariadicControl {
	private final Box<Boolean> doubled = new Box<Boolean>();
	
	public ToggleDoubleVariadicControl(BooleanControl toggle, VariadicControl source) {
		toggle.addListener(new BooleanControl.Listener() {
			public void always(boolean value) {
				doubled.setValue(value);
			}
		});
		source.addListener(new VariadicControl.Listener() {
			public void always(double value) {
				double result = value;
				if (doubled.getValue()) {
					result *= 2;
					result = Math.min(1.0, result);
					result = Math.max(-1.0, result);
				}
				triggerEvent(ALWAYS_EVENT, result);
			}
		});
	}

}
