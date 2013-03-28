package com.wildelake.frc.vision13.components;

import edu.wpi.first.wpilibj.Solenoid;

public class Piston {
	private final Solenoid a, b;
	
	public Piston(Solenoid a, Solenoid b) {
		this.a = a;
		this.b = b;
	}
	
	public void out() {
		b.set(false);
		a.set(true);
	}
	
	public void in() {
		a.set(false);
		b.set(true);
	}
}
