package com.wildelake.frc.vision13.controls.compositions;


/**
 * ToggleBooleanControl allows one button to be persistent in state
 *
 */
public class ToggleBooleanControl extends BooleanControl {
	public ToggleBooleanControl(BooleanControl button) {
		final boolean[] state = {false};
		button.addListener(new BooleanControl.Listener() {
			public void onEnabled() {
				state[0] = !state[0];
				setValue(state[0]);
			}
		});
	}
}
