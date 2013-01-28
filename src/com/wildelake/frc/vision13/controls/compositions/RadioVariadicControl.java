package com.wildelake.frc.vision13.controls.compositions;

/**
 * RadioVariadicControl is used to convert a BooleanControl[] into a VariadicControl
 * that toggles between the numeric states of each button
 *
 */
public class RadioVariadicControl extends VariadicControl {
	public RadioVariadicControl(BooleanControl[] toggle, double[] on) {
		this(toggle, on, 0);
	}
	
	public RadioVariadicControl(final BooleanControl[] toggle, final double[] on, double original) {
		final VariadicControl that = this;
		setValue(original);
		for (int i = 0; i < toggle.length; i++) {
			final double value = on[i];
			toggle[i].addListener(new BooleanControl.Listener() {
				public void onEnabled() {
					that.setValue(value);
				}
			});
		}
	}
}
