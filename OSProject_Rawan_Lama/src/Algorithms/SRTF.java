/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;

import java.util.Collections;

import static Algorithms.OSPracticeUI.*;

public class SRTF extends Thread {
    public static ArrayList<Process> processListSRTF = new ArrayList<>();
    public static ArrayList<Process> readyQueueSRTF = new ArrayList<>();
    public static ArrayList<GanttchartNode> ganttChatList = new ArrayList<>();

    @Override
    public void run() {
        if (processListSRTF.size() != 0) {
            processListSRTF.clear();
        }
        if (readyQueueSRTF.size() != 0) {
            processListSRTF.clear();
        }
        if (ganttChatList.size() != 0) {
            ganttChatList.clear();
        }

        for (int i = 0; i < processList.size(); i++) {
            Process temp = new Process(processList.get(i).getPID(),
                    processList.get(i).getArrivalTime(),
                    processList.get(i).getCpuBurstFixed(),
                    processList.get(i).getRepeat(),
                    processList.get(i).getInterval(),
                    processList.get(i).getDeadline());
            processListSRTF.add(temp);
        }

        boolean currentRunning = false; //used to indicate whether the CPU is working on a process or not
        boolean checkPriority = false; //we need to check the shortest remaining job in two cases:
        //1)having a new process entering the ready queue, 2)a process has finished
        //so checkPriority will act as a flag to whether enter the reordering of the ready queue steps or not, and will be set to true in the above two cases.
        int time = 0; //clock demonstration
        int i = 0;
        int counter = 0; //to count the processes that have finished executing
        int ganttCounter = 0;
        while (counter < processListSRTF.size()) {   //as long as there not finished processes, times should keep increasing
            if (currentRunning) {  //need to decrement CPU burst of the running process, as a unit time has elapsed
                ganttCounter++;
                readyQueueSRTF.get(0).setCpuBurstVar(readyQueueSRTF.get(0).getCpuBurstVar() - 1);
            }
            if (currentRunning && readyQueueSRTF.size() != 0 && (readyQueueSRTF.get(0).getCpuBurstVar() == 0)) {  //If a running process has executed all its Bursts: we can store the finish time, and calculate all needed values.
                readyQueueSRTF.get(0).setFinishTime(time);
                counter++;
                readyQueueSRTF.get(0).setTA((int) readyQueueSRTF.get(0).getFinishTime() - readyQueueSRTF.get(0).getArrivalTime());
                readyQueueSRTF.get(0).setWait(readyQueueSRTF.get(0).getTA() - readyQueueSRTF.get(0).getCpuBurstFixed());
                readyQueueSRTF.get(0).setWTA((readyQueueSRTF.get(0).getTA() / readyQueueSRTF.get(0).getCpuBurstFixed()));
                GanttchartNode t = new GanttchartNode(readyQueueSRTF.get(0).getPID(), time - ganttCounter, time);
                ganttChatList.add(t);
                ganttCounter = 0;
                readyQueueSRTF.remove(0);
                checkPriority = true;   //need to check the shortest remaining job
                currentRunning = false; //the CPU can start working on another process
            }
            if (readyQueueSRTF.size() > 0) {
                for (int k = 0; k < processListSRTF.size(); k++) {
                    if (processListSRTF.get(k).getArrivalTime() == time && processListSRTF.get(k).getCpuBurstVar() != 0 && currentRunning) {
                        GanttchartNode t = new GanttchartNode(readyQueueSRTF.get(0).getPID(), time - ganttCounter, time);
                        ganttChatList.add(t);
                        ganttCounter = 0;
                        break;

                    }
                }

            }
            while (i < processListSRTF.size() && time >= processListSRTF.get(i).getArrivalTime()) { //check the arriving processes at a given time
                if (time == processListSRTF.get(i).getArrivalTime()) {  //adding the process to the readyQueue
                    readyQueueSRTF.add(processListSRTF.get(i));
                    reorderingReadyQueue();
                    checkPriority = true;  //need to check which job to schedule next
                }
                i++;
            }
            i = 0;
            if (checkPriority && readyQueueSRTF.size() > 0) {  //if there is a need to check the shortest remaining and the readyQueue is not empty, we perform the following
                reorderingReadyQueue();
                if (readyQueueSRTF.get(0).getStartTime() == -1)
                    readyQueueSRTF.get(0).setStartTime(time);
                currentRunning = true;
                checkPriority = false;
            }
            time++;

        }

    }

    public static void reorderingReadyQueue() {
        Process min = readyQueueSRTF.get(0);
        for (int i = 1; i < readyQueueSRTF.size(); i++) {
            if (readyQueueSRTF.get(i).getCpuBurstVar() < min.getCpuBurstVar()) {   //searching for the minimum remaining time
                min = readyQueueSRTF.get(i);
                Collections.swap(readyQueueSRTF, 0, i);
            }
            if (readyQueueSRTF.get(i).getCpuBurstVar() == min.getCpuBurstVar()) { //if two processes with same remaining time, take the one with the smallest PID
                if (readyQueueSRTF.get(i).getPID() < min.getPID()) {
                    min = readyQueueSRTF.get(i);
                    Collections.swap(readyQueueSRTF, 0, i);
                }
            }
        }
    }
}
