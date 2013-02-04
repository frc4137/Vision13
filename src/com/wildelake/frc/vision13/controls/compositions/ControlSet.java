package com.wildelake.frc.vision13.controls.compositions;

import java.util.Vector;

/**
 * ControlSet allows for creating multiple sets of controls which can be
 * switched between on the fly.
 */
public abstract class ControlSet {
	private final Vector registry = new Vector();
	
	protected void add(Control control) {
		registry.addElement(control);
	}
	
	public void tickAll() {
		for (int i = 0; i < registry.size(); i++) ((Control) registry.elementAt(i)).tick();
	}
	
	public void tick() {
		tickAll();
		update();
	}
	
	/**
	 * `update' is here for convenience because ControlSets will often also
	 * implement Pilot. It allows ControlSets to specify their own behavior
	 * to execute on every tick without having to worry about calling super.
	 */
	public void update() {}
	public abstract void buildControlSet();

}
