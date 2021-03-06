package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import io.github.kakorrhaphio.operatingsystem.model.static_objects.ProcessManager;
import io.github.kakorrhaphio.operatingsystem.view.Log;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */

//TODO: implement ageing <- scheduling algorithms
public class PCB {

    public final int pid;
    public final int kernel_bit;
    public final int priority;

    public int state;
    private int cycles_left;

    public int memory_head;
    public final int memory_size;

    private Quantum[] executable_code;
    private int quantum_counter;


    public PCB(int kernel_bit_in, int priority_in, int memory_size_in, Quantum[] executable_code_in){
        pid = ProcessManager.gen_pid();
        kernel_bit = kernel_bit_in;
        priority = priority_in;
        state = V.NEW;
        cycles_left = 0;
        for (int i = 0; i < executable_code_in.length; i++) {
            cycles_left += executable_code_in[i].size();
        }
        memory_head = -1;
        memory_size = memory_size_in;
        executable_code = executable_code_in;
    }

    public int run () {
        if (quantum_counter == executable_code.length) {
            Log.e("PCB", "Trying to run " + ProcessManager.toString(this) + "with no quantums left");
            return 0;
        } else {
            // TODO: May add a catch statement in case this fails
            if (executable_code[quantum_counter] == null) {
                int cycles_executed = executable_code[quantum_counter].size();
                cycles_left -= cycles_executed;
                quantum_counter ++;
                return cycles_executed;
            }
            executable_code[quantum_counter].run(memory_head);
            int cycles_executed = executable_code[quantum_counter].size();
            cycles_left -= cycles_executed;
            quantum_counter ++;
            return cycles_executed;
        }
    }

    public int getCycles_left () {
        return cycles_left;
    }

    public PCB set_code (Quantum[] code_in) {
        if (executable_code == null) {
            executable_code = code_in;
        }
        return this;
    }
}
