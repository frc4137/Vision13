package com.wildelake.frc.vision13.controls;

public interface Controller {
	public double   getVariadicInput(int port);
	public boolean  getBooleanInput (int port);
}