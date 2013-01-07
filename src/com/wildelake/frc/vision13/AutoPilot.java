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
	
	private Location l;
	private HashMap<String, AutoPilotEvent> events;
	private MyRobotDrive r;
	private ControllerManager c;
	
	public AutoPilot() {
		l = new Location(0, 0, 0);
		r = null;
		c = null;
		events = new HashMap<String,AutoPilotEvent>();
	}
	public AutoPilot(MyRobotDrive d) {
		l = d.getLocation();
		r = d;
		c = null;
		events = new HashMap<String,AutoPilotEvent>();
	}
	public AutoPilot(MyRobotDrive d, ControllerManager cm) {
		l = d.getLocation();
		r = d;
		c = cm;
		events = new HashMap<String,AutoPilotEvent>();
	}
	public AutoPilot(ControllerManager cm) {
		l = null;
		r = null;
		c = cm;
		events = new HashMap<String,AutoPilotEvent>();
	}
	
	/**
	 * Adds a controller to the AutoPilot
	 * @param cm = ControllerManager object
	 */
	public void attachController(ControllerManager cm) {
		c = cm;
	}
	
	/**
	 * Checks for each AutoPilotEvent
	 * @return String[] of events triggered right now
	 */
	public String[] getEvents() {
		ArrayList<String> eventListString = new ArrayList<String>();
		for (int i = 0; i < events.size(); i++) {
			AutoPilotEvent e = events.get(events.keySet().toArray()[i]);
			boolean on;
			switch (e.getType()) {
				case "Location": on = e.equals(l); break;
				case "MyRobotDrive": on = e.equals(r); break;
				default: on = e.equals(c); break;
			}
			if (on) {
				eventListString.add((String) events.keySet().toArray()[i]);
			}
		}
		return eventListString.toArray(new String[]{});
	}
	
	public void addEvent(String name, AutoPilotEvent event) {
		events.put(name, event);
	}
	public void addEvent(String name, String type) {
		switch(name) {
		case "":
			break;
		default:
			break;
		}
	}
}
