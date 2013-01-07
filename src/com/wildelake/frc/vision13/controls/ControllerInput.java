package com.wildelake.frc.vision13.controls;

public abstract class ControllerInput {
	private ControllerManager parent;
	public ControllerInput(ControllerManager top) {
		parent = top;
	}
	public abstract int getControllerID();
	public abstract int getPort();
}
