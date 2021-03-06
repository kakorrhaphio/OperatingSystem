package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;
import io.github.kakorrhaphio.operatingsystem.view.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by class on 10/13/16.
 */
public class ExecutionQueue {

    // example robin algorithm values
    private static final int FIRST_ROBIN_SIZE_LIMIT = 20;
    private static final int FIRST_ROBIN_AGE_LIMIT = 100;

    private static final int SECOND_ROBIN_SIZE_LIMIT = 8;
    private static final int SECOND_ROBIN_AGE_LIMIT = 200;

    //TODO: include seperate kernal queue


    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> robin_user; //three stages, short rr, long rr, fifo
    private static int robin_age;
    private static ExecutionQueue instance = new ExecutionQueue();
    private ExecutionQueue(){}
    public static ExecutionQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(PCB to_add){
        if (robin_user == null){
            Log.i("ExecutionQueue","Attempting to enqueue to a null Execution Queue");
            robin_user = new ArrayList<>();
        }
        robin_user.add(to_add);
    }

    public static PCB deQueue(){
        if (robin_user == null){
            Log.e("ExecutionQueue","Attempting to dequeue from an empty Execution Queue");
            return null;
        }
        if (robin_user.isEmpty()){
            Log.i("ExecutionQueue","Execution Queue is empty, deleting instance");
            robin_user = null;
            return null;
        }
        return robin_user.remove(0);
    }

    // this makes the execution algorithm two stage...
    // first stage is round robin with 10 cycles per process
    // second stage is "fifo", complete process till finish
    public static int cycleAllocation(int current_process_cycles) {
        if (robin_user.size() < SECOND_ROBIN_SIZE_LIMIT || robin_age > SECOND_ROBIN_AGE_LIMIT) {
            return current_process_cycles;
        } else if (robin_user.size() < FIRST_ROBIN_SIZE_LIMIT || robin_age > FIRST_ROBIN_AGE_LIMIT){
            return 20;
        } else{
            return 10;
        }
    }

    public static boolean isEmpty () {
        if (robin_user == null || robin_user.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String printing(){
        if (robin_user == null){
            return null;
        }
        Object[] arr =  robin_user.toArray();
        String output = "";
        for (int i = 0; i < arr.length; i ++) {
            output += ProcessManager.toString((PCB)arr[i]) + "\n";
        }
        return output;
    }

    public static void clean(){
        robin_user = null;
    }
}
