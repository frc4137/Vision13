package com.wildelake.frc.vision13.controls.compositions;

public class RampedVariadicControl extends VariadicControl {
	private final VariadicControl src;
	private final double deltav;
	
	public RampedVariadicControl(VariadicControl src, double deltav) {
		this.src = src;
		this.deltav = deltav;
	}

	public void update() {
		double difference = Math.min(deltav, Math.abs(src.getValue() - getValue()));
		setValue(src.getValue() > getValue() ? getValue() + difference : getValue() - difference);
	}

}
