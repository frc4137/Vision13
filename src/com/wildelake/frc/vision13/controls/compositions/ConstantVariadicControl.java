package com.wildelake.frc.vision13.controls.compositions;

/**
 * ConstantVariadicControl is used to have a variadic
 * control which is internally a double.
 *
 */
public class ConstantVariadicControl extends VariadicControl {
	
	public ConstantVariadicControl(double value) {
		setValue(value);
	}
}
