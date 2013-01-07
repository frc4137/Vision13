package com.wildelake.frc.vision13.controls.compositions;

import com.wildelake.frc.vision13.utils.Box;

/**
 * AndGateBooleanControl allows two buttons to be pressed at once and read
 * as a single, different button, but only if they are pressed at the same time
 *
 */
public class AndGateBooleanControl extends BooleanControl {
	private final Box<Boolean> button1value = new Box<Boolean>();
	
	public AndGateBooleanControl(BooleanControl button1, BooleanControl button2) {
		button1.addListener(new BooleanControl.Listener() {
			public void always(boolean value) {
				button1value.setValue(value);
			}
		});
		button2.addListener(new BooleanControl.Listener() {
			public void always(boolean value) {
				triggerEvent(ALWAYS_EVENT, button1value.getValue() && value);
			}
		});
		
	}

}
