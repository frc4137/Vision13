package com.wildelake.frc.vision13.controls.compositions;

public class ReboundedVariadicControl extends VariadicControl {
	private final VariadicControl src;
	private final double m, b;

	public ReboundedVariadicControl(VariadicControl src, double oldLower, double oldUpper, double newLower, double newUpper) {
		// new - newLower = 
		this.src = src;
		m = (newUpper - oldUpper) / (newLower - oldLower);
		b = newLower - oldUpper;
	}
	
	public ReboundedVariadicControl(VariadicControl src, double newLower, double newUpper) {
		this(src, -1, 1, newLower, newUpper);
	}

	public void update() {
		setValue(src.getValue() * m + b);
	}
	
}
