package com.wildelake.frc.vision13.controls;

import com.wildelake.frc.vision13.controls.compositions.BooleanControl;

public class BooleanInput extends BooleanControl {
	private final Controller controller;
	private final int port;
	
	public BooleanInput(Controller controller, int port) {
		this.controller = controller;
		this.port = port;
	}
	
	public void update() {
		setValue(controller.getBooleanInput(port));
//		setValue(true);
	}
}
