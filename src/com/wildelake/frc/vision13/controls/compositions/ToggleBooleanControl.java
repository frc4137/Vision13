package com.wildelake.frc.vision13.controls.compositions;


/**
 * ToggleBooleanControl allows one button to be persistent in state
 *
 */
public class ToggleBooleanControl extends BooleanControl {
	private final BooleanControl button;
	private boolean state;
	
	public ToggleBooleanControl(BooleanControl button) {
		this.button = button;
//		state = button.getValue();
		button.addListener(new BooleanControl.Listener() {
			public void onChange() {
				state = !state;
				setValue(state);
			}
		});
	}
	
	public void update() {}
}
