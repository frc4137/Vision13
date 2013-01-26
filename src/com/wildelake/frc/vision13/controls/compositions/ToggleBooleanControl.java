package com.wildelake.frc.vision13.controls.compositions;


/**
 * ToggleBooleanControl allows one button to be persistent in state
 *
 */
public class ToggleBooleanControl extends BooleanControl {
	private final BooleanControl button;
	private final boolean[] state;
	
	public ToggleBooleanControl(BooleanControl button) {
		this.state = new boolean[] {false};
		this.button = button;
//		state = button.getValue();
		button.addListener(new BooleanControl.Listener() {
			public void onEnabled() {
				state[0] = !state[0];
				setValue(state[0]);
			}
		});
	}
	
	public void update() {}
}
