package com.wildelake.frc.vision13;

/**
 * Class AutoPilotEvent
 * object wrapping the type of State objects compatible
 * @author Satyajit DasSarma
 *
 */
public class AutoPilotEvent {
	
	// trivial change by adrusi
	
	private State desired;
	
	public AutoPilotEvent() {
		desired = new State();
	}
	public AutoPilotEvent(MyRobotDrive state) {
		desired = new State(state);
	}
	public AutoPilotEvent(Location state) {
		desired = new State(state);
	}
	public AutoPilotEvent(ControllerManager state) {
		desired = new State(state);
	}
	
	/**
	 * Returns the type of the inner State object
	 * @return
	 */
	public String getType() {
		return desired.getType();
	}
	
	/**
	 * Checks if State from AutoPilot is equal to the target State
	 * @param s = State from AutoPilot
	 * @return is current State = target State
	 */
	public boolean isTriggered(State s) {
		return s.equals(desired);
	}

}
