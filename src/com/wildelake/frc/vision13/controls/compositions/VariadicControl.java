package com.wildelake.frc.vision13.controls.compositions;

import java.util.Vector;

/**
 * A standard event-based interface for compositioning and using variadic inputs
 */

public abstract class VariadicControl extends Control {
	private Vector listeners = new Vector();
	private double value;
	
	public static class Listener {
		public void whilePositive(double value) {}
		public void whileNegative(double value) {}
		public void whileZero() {}
		/**
		 * `always' is called on every tick of the main loop
		 */
		public void always(double value) {}
	}
	
	public final void tick() {
		update();
		triggerEvent(ALWAYS_EVENT);
	}
	
	public void update() {}
	
	public double getValue() {
		return value;
	}
	/**
	 * please don't call setValue from another control
	 * TODO make the compiler enforce this if possible
	 */
	protected void setValue(double value) {
		value = Math.min(value, 1);
		value = Math.max(value, -1);
		this.value = value;
	}
	
	// Sorry, java ME doesn't support enums
	protected final int WHILE_POSITIVE_EVENT = 0;
	protected final int WHILE_NEGATIVE_EVENT = 1;
	protected final int WHILE_ZERO_EVENT = 2;
	protected final int ALWAYS_EVENT = 3;
	
	protected void triggerEvent(int event) {
		for (int i = 0; i < listeners.size(); i++) {
			Listener listener = (Listener) listeners.elementAt(i);
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
		listeners.addElement(listener);
	}
}
