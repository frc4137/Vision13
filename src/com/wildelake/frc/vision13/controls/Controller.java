package com.wildelake.frc.vision13.controls;

public interface Controller {
	public Object[] getAllInputs();
	public double   getVariadicInput(int port);
	public boolean  getBooleanInput (int port);
}