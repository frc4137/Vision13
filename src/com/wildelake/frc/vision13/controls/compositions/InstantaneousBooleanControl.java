package com.wildelake.frc.vision13.controls.compositions;

public class InstantaneousBooleanControl extends BooleanControl {
	public InstantaneousBooleanControl(BooleanControl src) {
		src.addListener(new BooleanControl.Listener() {
			public void onEnabled() {
				setValue(true);
			}
		});
	}
	
	public void update() {
		if (getValue()) setValue(false);
	}
}
