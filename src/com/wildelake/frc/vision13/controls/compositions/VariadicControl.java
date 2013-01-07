package com.wildelake.frc.vision13.controls.compositions;

import java.util.ArrayList;

/**
 * A standard event-based interface for composing and using variadic inputs
 */

public class VariadicControl {
	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	public static class Listener {
		public void whilePositive(double value) {}
		public void whileNegative(double value) {}
		public void whileZero() {}
		/**
		 * `always' is called on every tick of the main loop
		 */
		public void always(double value) {}
	}
	
	// Sorry, java ME doesn't support enums
	protected final int WHILE_POSITIVE_EVENT = 0;
	protected final int WHILE_NEGATIVE_EVENT = 1;
	protected final int WHILE_ZERO_EVENT = 2;
	protected final int ALWAYS_EVENT = 3;
	
	protected void triggerEvent(int event, double value) {
		for (Listener listener : listeners) {
			switch (event) {
			case WHILE_POSITIVE_EVENT: listener.whilePositive(value); break;
			case WHILE_NEGATIVE_EVENT: listener.whileNegative(value); break;
			case WHILE_ZERO_EVENT: listener.whileZero(); break;
			case ALWAYS_EVENT:
				listener.always(value);
				if (value > 0) listener.whilePositive(value);
				else if (value < 0) listener.whileNegative(value);
				else listener.whileZero();
			break;
			default: throw new Error("Invalid event for Boolean Control");
			}
		}
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
}
