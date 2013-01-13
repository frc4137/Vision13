package com.wildelake.frc.vision13.controls.compositions;

public class SummedVariadicControl extends VariadicControl {
	private final VariadicControl a, b;
	
	public SummedVariadicControl(VariadicControl a, VariadicControl b) {
		this.a = a;
		this.b = b;
	}

	public void update() {
		setValue(a.getValue() + b.getValue());
	}

}
