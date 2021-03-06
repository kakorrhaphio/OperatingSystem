package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class EventQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<ECB> queue;
    private static EventQueue instance = new EventQueue();
    private EventQueue(){
        queue = new ArrayList<>();
    }
    public static EventQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(ECB to_add){
        queue.add(to_add);
    }

    public static void deQueue(ECB to_remove){
        queue.remove(to_remove);
    }

    public static ECB has_timer () {
        ECB to_return = null;
        for (int i = 0; i < queue.size(); i ++) {
            if (System.currentTimeMillis() >=  queue.get(i).time) {
                to_return = queue.get(i);
                deQueue(to_return);
                break;
            }
        }
        return to_return;
    }

    public static String printing(){
        if (queue.isEmpty()){
            return null;
        }
        Object[] arr =  queue.toArray();
        String output = "";
        for (int i = 0; i < arr.length; i ++) {
            output += ProcessManager.toString((PCB)arr[i]) + "\n";
        }
        return output;
    }

    public static void clean(){
        queue = new ArrayList();
    }
}
