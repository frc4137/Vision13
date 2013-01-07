package com.wildelake.frc.vision13.controls;

public abstract class VariadicControl {
	public static class Listener {
		public void whilePositive(double value) {}
		public void whileNegative(double value) {}
		public void whileZero() {}
		public void always(double value) {
			if (value > 0) whilePositive(value);
			else if (value < 0) whileNegative(value);
			else whileZero();
		}
	}
	public abstract void addListener(Listener listener);
}
