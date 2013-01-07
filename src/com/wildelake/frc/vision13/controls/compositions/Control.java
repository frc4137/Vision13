package com.wildelake.frc.vision13.controls.compositions;

import java.util.ArrayList;

/**
 * Control deals with updating controls on every tick of the main loop.
 * Note that by using a registry, controls will never be marked by the GC/
 * DO NOT create controls in any sort of loop, they are meant to last for
 * about the life of the program.
 */
public abstract class Control {
	public abstract void tick();
	
	private static ArrayList<Control> registry;
	
	public Control() {
		registry.add(this);
	}
	
	public static void tickAll() {
		for (Control control: registry) control.tick();
	}
}
