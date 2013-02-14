
package com.wildelake.frc.vision13.controls.compositions;

/**
 * BooleanVariadicControl converts three BoolanControls into a VariadicControl
 *
 */
public class CounterVariadicControl extends VariadicControl {
	private final double[] current = new double[1];
	
	public CounterVariadicControl(BooleanControl up, BooleanControl rst, BooleanControl down, final double step) {
		up.addListener(new BooleanControl.Listener() {
			public void onEnabled() {
				current[0] += step;
			}
		});
		rst.addListener(new BooleanControl.Listener() {
			public void whileEnabled() {
				current[0] = 0;
			}
		});
		down.addListener(new BooleanControl.Listener() {
			public void onEnabled() {
				current[0] -= step;
			}
		});
	}
	
	public void update() {
		setValue(current[0]);
	}
}
