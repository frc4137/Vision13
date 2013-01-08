package com.wildelake.frc.vision13.controls.compositions;



/**
 * Control adds facilities for conveniently binding a Control to a ControlSet
 */
public abstract class Control {
	public abstract void tick();
	
	public Control() {
		synchronized (set) {
			if (set != null) set.add(this);
		}
	}
	
	private static ControlSet set = null;
	
	/**
	 * `buildControlSet' does all the interesting stuff here. It's ugly, but it works, and
	 * its ugliness shouldn't matter since there's no real reason to use multithreading on
	 * the cRIO.
	 */
	public static synchronized void buildControlSet(ControlSet set) {
		Control.set = set;
		set.buildControlSet();
		Control.set = null;
	}
}
