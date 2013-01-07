package com.wildelake.frc.vision13;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
	private ArrayList<Object> desired;
	private String type;
	public State() {
		desired = null;
		type = null;
	}
	public State(MyRobotDrive drive) {
		desired = robotDriveToArray(drive);
		type = "RobotDrive";
	}
	public State(ControllerManager controlsystem) {
		desired = controllerManagerToArray(controlsystem);
		type = "ControllerManager";
	}
	public State(Location l) {
		desired = new ArrayList<Object>(Arrays.asList(l.getCartesian()));
		type = "Location";
	}
	public boolean equals(State other) {
		if (other.getType().equals(this.getType()) && other.getDesired().equals(this.getDesired())) {
			return true;
		} else {
			return false;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String t) {
		type = t;
	}
	public ArrayList<Object> getDesired() {
		return desired;
	}
	public ArrayList<Object> robotDriveToArray(MyRobotDrive drive) {
		return new ArrayList<Object>(Arrays.asList(drive.getMotorSpeed()));
	}
	public ArrayList<Object> controllerManagerToArray(ControllerManager cm) {
		desired = new ArrayList<Object>();
		for (int i = 0; i < cm.controllers.length; i++) {
			desired.add(cm.controllers[i].getAllInputs());
		}
		return desired;
	}

}
