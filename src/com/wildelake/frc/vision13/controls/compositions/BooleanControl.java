package com.wildelake.frc.vision13.controls.compositions;

import java.util.Vector;

/**
 * 
 * @author Adrian
 *
 */
public abstract class BooleanControl extends Control {
	private Vector listeners = new Vector();
	private boolean value;
	
	public static class Listener {
		public void onEnabled() {}
		public void onDisabled() {}
		public void onChange(boolean value) {}
		public void whileEnabled() {}
		public void whileDisabled() {}
		public void always(boolean value) {}
	}
	
	public final void tick() {
		update();
		triggerEvent(ALWAYS_EVENT);
	}
	
	public abstract void update();
	
	public boolean getValue() {
		return value;
	}
	/**
	 * please don't call setValue from another control
	 * TODO make the compiler enforce this if possible
	 */
	protected void setValue(boolean value) {
		boolean oldValue = this.value;
		this.value = value;
		if (oldValue != value) triggerEvent(ON_CHANGE_EVENT);
	}
	
	protected final int ON_ENABLED_EVENT = 0;
	protected final int ON_DISABLED_EVENT = 1;
	protected final int ON_CHANGE_EVENT = 2;
	protected final int WHILE_ENABLED_EVENT = 3;
	protected final int WHILE_DISABLED_EVENT = 4;
	protected final int ALWAYS_EVENT = 5;
	
	private void triggerEvent(int event) {
		for (int i = 0; i < listeners.size(); i++) {
			Listener listener = (Listener)listeners.elementAt(i);
			switch (event) {
			case ON_ENABLED_EVENT: listener.onEnabled(); break;
			case ON_DISABLED_EVENT: listener.onDisabled(); break;
			case ON_CHANGE_EVENT:
				listener.onChange(value);
				if (value) listener.onEnabled();
				else listener.onDisabled();
			break;
			case WHILE_ENABLED_EVENT: listener.whileEnabled(); break;
			case WHILE_DISABLED_EVENT: listener.whileDisabled(); break;
			case ALWAYS_EVENT:
				listener.always(value);
				if (value) listener.whileEnabled();
				else listener.whileDisabled();
			break;
			default: throw new Error("Invalid event for Boolean Control");
			}
		}
	}
	
	public void addListener(Listener listener) {
		listeners.addElement(listener);
	}
}
