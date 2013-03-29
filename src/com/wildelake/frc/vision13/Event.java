/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildelake.frc.vision13;

/**
 *
 * @author adrusi
 */
public abstract class Event {
    public int tickStart;
    public int tickEnd;
    private String name;
    protected Object internal;
    public Event(String name, int start, int stop, Object internal) {
        this.name = name;
        this.tickStart = start;
        this.tickEnd = stop;
        this.internal = internal;
    }
    public abstract void event();
    public boolean isTriggered(int tick) {
        return (tickStart <= tick) && (tick <= tickEnd);
    }
    public String toString() {
        return name + "[" + tickStart/VisionIterative.tps + "-" + tickEnd/VisionIterative.tps + "]";
    }
}
