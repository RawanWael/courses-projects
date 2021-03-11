/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

/**
 * @author Main
 */

import java.util.ArrayList;

import static Algorithms.OSPracticeUI.processList;

import java.util.Collections;


public class EPWithPreemption extends Thread {
    //if two processes have the same pid after aging, arrival time will be considered
    public static ArrayList<GanttchartNode> ganttChatList = new ArrayList<>();
    public static ArrayList<Process> processListEPWithPreemption = new ArrayList<>();
    public static ArrayList<Process> readyQueueEPWithPreemption = new ArrayList<>();

    @Override
    public void run() {
        if (processListEPWithPreemption.size() != 0) {
            processListEPWithPreemption.clear();
        }
        if (readyQueueEPWithPreemption.size() != 0) {
            processListEPWithPreemption.clear();
        }
        if (ganttChatList.size() != 0) {
            ganttChatList.clear();
        }
        int ganttCounter = 0;
        for (int i = 0; i < processList.size(); i++) {
            Process temp = new Process(processList.get(i).getPID(),
                    processList.get(i).getArrivalTime(),
                    processList.get(i).getCpuBurstFixed(),
                    processList.get(i).getRepeat(),
                    processList.get(i).getInterval(),
                    processList.get(i).getDeadline());
            processListEPWithPreemption.add(temp);
        }
        int counter = 0;
        int i = 0;
        int time = 0;
        //boolean flag=false; //to set the start time of the first arrived process
        while (counter < processListEPWithPreemption.size()) {
            while (i < processListEPWithPreemption.size() && time >= processListEPWithPreemption.get(i).getArrivalTime()) { //check the arriving processes at a given time
                if (time == processListEPWithPreemption.get(i).getArrivalTime()) {  //adding the process to the readyQueue
                    readyQueueEPWithPreemption.add(processListEPWithPreemption.get(i));
                }
                i++;
            }
            i = 0;
            if (readyQueueEPWithPreemption.size() > 0) {
                for (int k = 0; k < processListEPWithPreemption.size(); k++) {
                    if (processListEPWithPreemption.get(k).getArrivalTime() == time && processListEPWithPreemption.get(k).getCpuBurstVar() != 0) {
                        if (ganttCounter != 0) {
                            GanttchartNode t = new GanttchartNode(readyQueueEPWithPreemption.get(0).getPID(), time - ganttCounter, time);
                            ganttChatList.add(t);
                            ganttCounter = 0;
                            break;   //for time complexity
                        }

                    }
                }

            }
            //reordering,aging start time if time==-1, check finish time (CPUVAR)

            if (readyQueueEPWithPreemption.size() != 0) {
                Process temp = readyQueueEPWithPreemption.get(0);
                //Aging();
                reorderingReadyQueue();
                Aging();
                //A preemption needed becasue aging change the situation
                for (int k = 0; k < processListEPWithPreemption.size(); k++) {
                    if (temp.getPID() != readyQueueEPWithPreemption.get(0).getPID() && processListEPWithPreemption.get(k).getCpuBurstVar() != 0) {
                        if (ganttCounter != 0) {
                            GanttchartNode t = new GanttchartNode(temp.getPID(), time - ganttCounter, time);
                            ganttChatList.add(t);
                            ganttCounter = 0;
                            break;   //for time complexity
                        }

                    }
                }

                if (readyQueueEPWithPreemption.get(0).getStartTime() == -1) {
                    readyQueueEPWithPreemption.get(0).setStartTime(time);
                }
            }
            time++;
            if (readyQueueEPWithPreemption.size() != 0) {
                readyQueueEPWithPreemption.get(0).setCpuBurstVar(readyQueueEPWithPreemption.get(0).getCpuBurstVar() - 1);
                ganttCounter++;
                if (readyQueueEPWithPreemption.get(0).getCpuBurstVar() == 0) {
                    readyQueueEPWithPreemption.get(0).setFinishTime(time);
                    counter++;
                    readyQueueEPWithPreemption.get(0).setTA((int) readyQueueEPWithPreemption.get(0).getFinishTime() - readyQueueEPWithPreemption.get(0).getArrivalTime());
                    readyQueueEPWithPreemption.get(0).setWait(readyQueueEPWithPreemption.get(0).getTA() - readyQueueEPWithPreemption.get(0).getCpuBurstFixed());
                    readyQueueEPWithPreemption.get(0).setWTA((double) readyQueueEPWithPreemption.get(0).getTA() / readyQueueEPWithPreemption.get(0).getCpuBurstFixed());
                    if (ganttCounter != 0) {
                        GanttchartNode t = new GanttchartNode(readyQueueEPWithPreemption.get(0).getPID(), time - ganttCounter, time);
                        ganttChatList.add(t);
                        ganttCounter = 0;
                        readyQueueEPWithPreemption.remove(0);
                    }
                }
            }
        }

    }

    public static void Aging() {
        //aging is not implemented for the process that has been executing on the CPU
        for (int i = 1; i < readyQueueEPWithPreemption.size(); i++) {
            readyQueueEPWithPreemption.get(i).setPriority(readyQueueEPWithPreemption.get(i).getPriority() - 0.25);
        }
    }

    public static void reorderingReadyQueue() {
        Process min = readyQueueEPWithPreemption.get(0);
        for (int i = 1; i < readyQueueEPWithPreemption.size(); i++) {
            if (readyQueueEPWithPreemption.get(i).getPriority() < min.getPriority()) {   //searching for the minimum priority
                min = readyQueueEPWithPreemption.get(i);
                Collections.swap(readyQueueEPWithPreemption, 0, i);
            }
            if (readyQueueEPWithPreemption.get(i).getPriority() == min.getPriority()) { //if two processes with same priority, take the one with the lower pid
                if (readyQueueEPWithPreemption.get(i).getPID() < min.getPID()) {
                    min = readyQueueEPWithPreemption.get(i);
                    Collections.swap(readyQueueEPWithPreemption, 0, i);
                }
            }
        }
    }
}