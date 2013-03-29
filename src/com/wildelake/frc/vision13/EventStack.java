package com.wildelake.frc.vision13;

import java.util.Stack;

/**
 *
 * @author adrusi
 */
public class EventStack extends Stack {
    public Event getCurrentEvent(int tick) {
        Event e = (Event) this.peek();
        if (e.isTriggered(tick)) {
            return e;
        } else {
            this.pop();
            e = (Event) this.peek();
            return e;
        }
    }
    
}
