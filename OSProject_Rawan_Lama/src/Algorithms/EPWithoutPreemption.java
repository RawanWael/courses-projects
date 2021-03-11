/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;

import static Algorithms.OSPracticeUI.processList;


import java.util.Collections;

public class EPWithoutPreemption extends Thread {
    public static ArrayList<GanttchartNode> ganttChatList = new ArrayList<>();
    public static ArrayList<Process> processListEPWithoutPreemption = new ArrayList<>();
    public static ArrayList<Process> readyQueueEPWithoutPreemption = new ArrayList<>();

    @Override
    public void run() {
        if (processListEPWithoutPreemption.size() != 0) {
            processListEPWithoutPreemption.clear();
        }
        if (readyQueueEPWithoutPreemption.size() != 0) {
            processListEPWithoutPreemption.clear();
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
            processListEPWithoutPreemption.add(temp);
        }

        boolean currentRunning = false; //used to indicate whether the CPU is working on a process or not
        boolean checkPriority = false; //we need to check the shortest remaining job in two cases:
        //1)having a new process entering the ready queue, 2)a process has finished
        //so checkPriority will act as a flag to whether enter the reordering of the ready queue steps or not, and will be set to true in the above two cases.
        int time = 0; //clock demonstration
        int i = 0;
        int counter = 0; //to count the processes that have finished executing
        boolean start = true;
        while (counter < processListEPWithoutPreemption.size()) {   //as long as there not finished processes, times should keep increasing

            if (currentRunning && readyQueueEPWithoutPreemption.size() != 0 && ((time - readyQueueEPWithoutPreemption.get(0).getStartTime()) == readyQueueEPWithoutPreemption.get(0).getCpuBurstFixed())) {  //If a running process has executed all its Bursts: we can store the finish
                readyQueueEPWithoutPreemption.get(0).setFinishTime(time);
                GanttchartNode tempNode = new GanttchartNode(readyQueueEPWithoutPreemption.get(0).getPID(), time - readyQueueEPWithoutPreemption.get(0).getCpuBurstFixed(), time);
                ganttChatList.add(tempNode);
                counter++;
                readyQueueEPWithoutPreemption.get(0).setTA((int) readyQueueEPWithoutPreemption.get(0).getFinishTime() - readyQueueEPWithoutPreemption.get(0).getArrivalTime());
                readyQueueEPWithoutPreemption.get(0).setWait(readyQueueEPWithoutPreemption.get(0).getTA() - readyQueueEPWithoutPreemption.get(0).getCpuBurstFixed());
                readyQueueEPWithoutPreemption.get(0).setWTA((double) readyQueueEPWithoutPreemption.get(0).getTA() / readyQueueEPWithoutPreemption.get(0).getCpuBurstFixed());
                readyQueueEPWithoutPreemption.remove(0);
                checkPriority = true;   //need to check the shortest remaining job
                currentRunning = false; //the CPU can start working on another process
            }
            while (i < processListEPWithoutPreemption.size() && time >= processListEPWithoutPreemption.get(i).getArrivalTime()) { //check the arriving processes at a given time
                if (time == processListEPWithoutPreemption.get(i).getArrivalTime()) {  //adding the process to the readyQueue
                    readyQueueEPWithoutPreemption.add(processListEPWithoutPreemption.get(i));
                }
                i++;
            }
            i = 0;
            if (readyQueueEPWithoutPreemption.size() > 0 && (checkPriority || start)) {  //if there is a need to check the shortest remaining and the readyQueue is not empty, we perform the following:
                reorderingReadyQueue();
                Aging();
                if (readyQueueEPWithoutPreemption.get(0).getStartTime() == -1)
                    readyQueueEPWithoutPreemption.get(0).setStartTime(time);
                currentRunning = true;
                checkPriority = false;
                start = false;
            }

            time++;
        }


    }

    public static void Aging() {
        //aging is not implemented for the process that has been executing on the CPU
        for (int i = 1; i < readyQueueEPWithoutPreemption.size(); i++) {
            readyQueueEPWithoutPreemption.get(i).setPriority(readyQueueEPWithoutPreemption.get(i).getPriority() - 0.25);
        }
    }

    public static void reorderingReadyQueue() {
        Process min = readyQueueEPWithoutPreemption.get(0);
        for (int i = 1; i < readyQueueEPWithoutPreemption.size(); i++) {
            if (readyQueueEPWithoutPreemption.get(i).getPriority() < min.getPriority()) {   //searching for the minimum priority
                min = readyQueueEPWithoutPreemption.get(i);
                Collections.swap(readyQueueEPWithoutPreemption, 0, i);
            }
            if (readyQueueEPWithoutPreemption.get(i).getPriority() == min.getPriority()) { //if two processes with same priority, take the one with the lower pid
                if (readyQueueEPWithoutPreemption.get(i).getPID() < min.getPID()) {
                    min = readyQueueEPWithoutPreemption.get(i);
                    Collections.swap(readyQueueEPWithoutPreemption, 0, i);
                }
            }
        }
    }
}