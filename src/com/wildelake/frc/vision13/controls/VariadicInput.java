package com.wildelake.frc.vision13.controls;

import com.wildelake.frc.vision13.controls.compositions.VariadicControl;

public class VariadicInput extends VariadicControl {
	private final Controller controller;
	private final int port;
	
	public VariadicInput(Controller controller, int port) {
		this.controller = controller;
		this.port = port;
	}
	
	public void update() {
		setValue(controller.getVariadicInput(port));
	}
}
