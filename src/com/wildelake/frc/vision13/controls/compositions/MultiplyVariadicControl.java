package com.wildelake.frc.vision13.controls.compositions;

/**
 * MultiplyVariadicControl is used to scale the input from a variadic
 * control.
 *
 */
public class MultiplyVariadicControl extends VariadicControl {
	private final VariadicControl a;
	private final VariadicControl b;
	
	public MultiplyVariadicControl(VariadicControl source, double multiplier) {
		this(source, new ConstantVariadicControl(multiplier));
	}
	
	public MultiplyVariadicControl(VariadicControl a, VariadicControl b) {
		this.a = a;
		this.b = b;
	}
		
	public void update() {
		setValue(a.getValue() * b.getValue());
	}
}
