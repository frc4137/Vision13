package com.wildelake.frc.vision13;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class AutoPilot
 * helps to pilot the robot
 * TODO events are not persistent
 * Requires AutoPilotEvent
 * Provides getEvents, TODO respondEvents
 * @author Satyajit DasSarma
 *
 */
public class AutoPilot {
	
	private Location loc;
	private ArrayList<AutoPilotListener> events;
	private MyRobotDrive drive;
	private ControllerManager controls;
	
	public AutoPilot() {
		loc = new Location(0, 0, 0);
		drive = null;
		controls = null;
		events = new HashMap<String, AutoPilotListenner>();
	}
	public AutoPilot(MyRobotDrive drive) {
		loc = drive.getLocation();
		this.drive = drive;
		controls = null;
		events = new HashMap<String, AutoPilotListenner>();
	}
	public AutoPilot(MyRobotDrive drive, ControllerManager controls) {
		loc = drive.getLocation();
		this.drive = drive;
		this.controls = controls;
		events = new HashMap<String, AutoPilotListenner>();
	}
	public AutoPilot(ControllerManager controls) {
		loc = null;
		drive = null;
		this.controls = controls;
		events = new HashMap<String, AutoPilotListenner>();
	}
	
	/**
	 * Adds a controller to the AutoPilot
	 * @param cm = ControllerManager object
	 */
	public void attachController(ControllerManager controls) {
		this.controls = controls;
	}
	
	/**
	 * Checks for each AutoPilotEvent
	 * @return String[] of events triggered right now
	 */
	public String[] getEvents() {
		ArrayList<String> eventListString = new ArrayList<String>();
		for (int i = 0; i < events.size(); i++) {
			AutoPilotListenner evt = events.get(events.keySet().toArray()[i]);
			if ((evt.getType().equals("Location")     && evt.equals(loc))
			||  (evt.getType().equals("MyRobotDrive") && evt.equals(drive))
			||   evt.equals(controls)) {
				eventListString.add((String) events.keySet().toArray()[i]);
			}
		}
		return eventListString.toArray(new String[]{});
	}
	
	public void addEvent(String name, AutoPilotListenner event) {
		events.put(name, event);
	}
	public void addEvent(String name, String type) {
		if (name.equals("")) {
			// TODO handle error case
			return;
		}
		// TODO add event
	}
}
