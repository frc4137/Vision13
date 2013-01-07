package com.wildelake.frc.vision13.controls;

public abstract class BooleanControl {
	public static class Listener {
		public void onEnable() {}
		public void onDisable() {}
		public void onChange(boolean value) {
			if (value) onEnable();
			else onDisable();
		}
		public void whileEnabled() {}
		public void whileDisabled() {}
		public void always(boolean value) {
			if (value) whileEnabled();
			else whileDisabled();
		}
	}
	public abstract void addListener(Listener listener);
}
