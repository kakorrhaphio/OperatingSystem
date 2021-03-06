package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;

/**
 * Created by class on 10/13/16.
 */
public class InterruptProcessor {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static boolean interrupt;
    private static ECB current_event = null;
    private static InterruptProcessor instance = new InterruptProcessor();
    private InterruptProcessor(){
        interrupt = false;
    }
    public static InterruptProcessor getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void signalInterrupt(ECB block_in){
        interrupt = true;
        current_event = block_in;
    }

    public static boolean hasInterrupt(){
        return interrupt;
    }

    public static void addEvent(ECB event_in){
        EventQueue.enQueue(event_in);
    }


    public static void are_there_any_interrupts () {
        ECB next_event = EventQueue.has_timer();
        if (next_event != null) {
            signalInterrupt(next_event);
        }
    }

    public static void clean(){
        interrupt = false;
        current_event = null;
    }
}
