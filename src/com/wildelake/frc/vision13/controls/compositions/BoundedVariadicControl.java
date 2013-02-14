package com.wildelake.frc.vision13.controls.compositions;

/**
 * BoundedVariadicControl is used to require a VariadicControl's
 * getValue() to between certain bounds
 *
 */
public class BoundedVariadicControl extends VariadicControl {
	private final VariadicControl source;
	private double lower, upper;
	
	public BoundedVariadicControl(VariadicControl source, double lower, double upper) {
		this.source = source;
		this.lower = lower;
		this.upper = upper;
	}
	
	public BoundedVariadicControl(VariadicControl source) {
		this(source, -1, 1);
	}
	
	public void update() {
		setValue(Math.max(Math.min(source.getValue(), upper), lower));
	}
}
