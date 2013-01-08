package com.wildelake.frc.vision13.controls.compositions;

import java.util.ArrayList;

/**
 * ControlSet allows for creating multiple sets of controls which can be
 * switched between on the fly.
 */
public abstract class ControlSet {
	private final ArrayList<Control> registry = new ArrayList<Control>();
	
	protected void add(Control control) {
		registry.add(control);
	}
	
	public void tickAll() {
		for (Control control : registry) control.tick();
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
