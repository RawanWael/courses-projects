/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;


import java.util.ArrayList;

import static Algorithms.OSPracticeUI.processList;


public class FCFS extends Thread {
    public static ArrayList<Process> processListFCFS = new ArrayList<>();
    public static ArrayList<Process> readyQueueFCFS = new ArrayList<>();

    @Override
    public void run() {
        if (processListFCFS.size() != 0) {
            processListFCFS.clear();
        }
        if (readyQueueFCFS.size() != 0) {
            processListFCFS.clear();
        }
        for (int i = 0; i < processList.size(); i++) {
            Process temp = new Process(processList.get(i).getPID(),
                    processList.get(i).getArrivalTime(),
                    processList.get(i).getCpuBurstFixed(),
                    processList.get(i).getRepeat(),
                    processList.get(i).getInterval(),
                    processList.get(i).getDeadline());
            processListFCFS.add(temp);
        }
        boolean currentRunning = false; //to track the status of the running state
        int time = 0; //clock demonstration
        int i = 0; //processes index in ArrayList

        while (processListFCFS.get(processListFCFS.size() - 1).getFinishTime() == 0) {   //Work until the last process finishes
            /* If there is a process running and it has reached its end time. Set finish time and calculate values*/
            if (currentRunning && readyQueueFCFS.size() != 0 && ((time - readyQueueFCFS.get(0).getStartTime()) == readyQueueFCFS.get(0).getCpuBurstFixed())) {
                readyQueueFCFS.get(0).setFinishTime(time);
                readyQueueFCFS.get(0).setTA((int) readyQueueFCFS.get(0).getFinishTime() - readyQueueFCFS.get(0).getArrivalTime());
                readyQueueFCFS.get(0).setWait(readyQueueFCFS.get(0).getTA() - readyQueueFCFS.get(0).getCpuBurstFixed());
                readyQueueFCFS.get(0).setWTA((double) readyQueueFCFS.get(0).getTA() / readyQueueFCFS.get(0).getCpuBurstFixed());
                readyQueueFCFS.remove(0);
                if (readyQueueFCFS.size() != 0) { // Execute the next process on the ready queue, it exists.
                    readyQueueFCFS.get(0).setStartTime(time);
                } else
                    currentRunning = false; //Else,no process is currently running
            }
            /* Work on all processes to check if its arrival time is now. Ignore any process that has arrival time value > current time*/
            while (i < processListFCFS.size() && time >= processListFCFS.get(i).getArrivalTime()) {
                /* If the current time is the arrival time of the this process and there is no other process executing, execute it*/
                if (time == processListFCFS.get(i).getArrivalTime() && currentRunning == false) {
                    readyQueueFCFS.add(processListFCFS.get(i));
                    processListFCFS.get(i).setStartTime(time);
                    currentRunning = true;
                }
                /* If the current time is the arrival time of the this process and there is another process executing, just add it to queue*/
                else if (time == processListFCFS.get(i).getArrivalTime() && currentRunning) {
                    readyQueueFCFS.add(processListFCFS.get(i));
                }
                i++;
            }
            i = 0; //To start over the next time to check for new processes

            time++; //Time passes
        }
    }
}
