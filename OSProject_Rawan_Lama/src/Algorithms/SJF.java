/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.ArrayList;

import static Algorithms.OSPracticeUI.processList;


import java.util.Collections;

public class SJF extends Thread {
    public static ArrayList<GanttchartNode> ganttChatList = new ArrayList<>();
    public static ArrayList<Process> processListSJF = new ArrayList<>();
    public static ArrayList<Process> readyQueueSJF = new ArrayList<>();

    @Override
    public void run() {
        if (processListSJF.size() != 0) {
            processListSJF.clear();
        }
        if (readyQueueSJF.size() != 0) {
            processListSJF.clear();
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
            processListSJF.add(temp);
        }

        boolean currentRunning = false; //used to indicate whether the CPU is working on a process or not
        boolean checkPriority = false; //we need to check the shortest remaining job in two cases:
        //1)having a new process entering the ready queue, 2)a process has finished
        //so checkPriority will act as a flag to whether enter the reordering of the ready queue steps or not, and will be set to true in the above two cases.
        int time = 0; //clock demonstration
        int i = 0;
        int counter = 0; //to count the processes that have finished executing
        boolean start = true;
        while (counter < processListSJF.size()) {   //as long as there not finished processes, times should keep increasing

            if (currentRunning && readyQueueSJF.size() != 0 && ((time - readyQueueSJF.get(0).getStartTime()) == readyQueueSJF.get(0).getCpuBurstFixed())) {  //If a running process has executed all its Bursts: we can store the finish
                readyQueueSJF.get(0).setFinishTime(time);
                GanttchartNode tempNode = new GanttchartNode(readyQueueSJF.get(0).getPID(), time - readyQueueSJF.get(0).getCpuBurstFixed(), time);
                ganttChatList.add(tempNode);
                counter++;
                readyQueueSJF.get(0).setTA((int) readyQueueSJF.get(0).getFinishTime() - readyQueueSJF.get(0).getArrivalTime());
                readyQueueSJF.get(0).setWait(readyQueueSJF.get(0).getTA() - readyQueueSJF.get(0).getCpuBurstFixed());
                readyQueueSJF.get(0).setWTA((double) readyQueueSJF.get(0).getTA() / readyQueueSJF.get(0).getCpuBurstFixed());
                readyQueueSJF.remove(0);
                checkPriority = true;   //need to check the shortest remaining job
                currentRunning = false; //the CPU can start working on another process
            }
            while (i < processListSJF.size() && time >= processListSJF.get(i).getArrivalTime()) { //check the arriving processes at a given time
                if (time == processListSJF.get(i).getArrivalTime()) {  //adding the process to the readyQueue
                    readyQueueSJF.add(processListSJF.get(i));
                }
                i++;

            }
            i = 0;
            if (readyQueueSJF.size() > 0 && (checkPriority || start)) {  //if there is a need to check the shortest remaining and the readyQueue is not empty, we perform the following:
                reorderingReadyQueue();
                if (readyQueueSJF.get(0).getStartTime() == -1)
                    readyQueueSJF.get(0).setStartTime(time);
                currentRunning = true;
                checkPriority = false;
                start = false;
            }

            time++;
        }


    }

    public static void reorderingReadyQueue() {
        Process min = readyQueueSJF.get(0);
        for (int i = 1; i < readyQueueSJF.size(); i++) {
            if (readyQueueSJF.get(i).getCpuBurstFixed() < min.getCpuBurstFixed()) {   //searching for the minimum remaining time
                min = readyQueueSJF.get(i);
                Collections.swap(readyQueueSJF, 0, i);
            }
            if (readyQueueSJF.get(i).getCpuBurstFixed() == min.getCpuBurstFixed()) { //if two processes with same remaining time, take the one with the smallest PID
                if (readyQueueSJF.get(i).getPID() < min.getPID()) {
                    min = readyQueueSJF.get(i);
                    Collections.swap(readyQueueSJF, 0, i);
                }
            }
        }
    }
}