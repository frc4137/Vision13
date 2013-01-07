package com.wildelake.frc.vision13.utils;

public class Box<T> {

	T value;
	
	public Box() {
		this(null);
	}
	
	public Box(T initialValue) {
		value = initialValue;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

}
