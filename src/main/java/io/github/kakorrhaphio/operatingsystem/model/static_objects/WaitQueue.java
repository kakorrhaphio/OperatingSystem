package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;

import java.util.PriorityQueue;

/**
 * Created by class on 10/13/16.
 */
public class WaitQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static PriorityQueue<PCB> queue;
    private static WaitQueue instance = new WaitQueue();
    private WaitQueue(){
        queue = new PriorityQueue<>(10, new PCBComparator());
    }
    public static WaitQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(PCB to_add){
        queue.add(to_add);
    }

    // will only dequeue if there is enough memory to add current block
    public static PCB deQueue(){
        if (isEmpty()) {
            return null;
        }
        if (MemoryManager.add_process(queue.peek())) {
            return queue.poll();
        }
        return null;
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
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
        queue = new PriorityQueue<>(10, new PCBComparator());
    }

}
