package com.wildelake.frc.vision13.controls.compositions;


/**
 * OrGateBooleanControl allows buttons to be pressed at once and read
 * as a single, different button, ORed  together
 *
 */
public class OrGateBooleanControl extends BooleanControl {
	private final BooleanControl[] buttons;
	
	public OrGateBooleanControl(BooleanControl button1, BooleanControl button2) {
		this(new BooleanControl[] {button1, button2});
	}
	public OrGateBooleanControl(BooleanControl[] buttons) {
		this.buttons = buttons;
	}
	
	public void update() {
		setValue(Boolean.FALSE);
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i].getValue()) {
				setValue(Boolean.TRUE);
				break;
			}
	}
}
