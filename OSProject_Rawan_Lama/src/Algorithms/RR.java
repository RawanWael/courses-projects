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

import static Algorithms.OSPracticeUI.enteredQ;
import static Algorithms.OSPracticeUI.processList;


//if one process arrives and the other finishes at the same time, the one that arrived is added first to the queue
public class RR extends Thread {
    public static ArrayList<GanttchartNode> ganttChatList = new ArrayList<>();
    public static ArrayList<Process> processListRR = new ArrayList<>();
    public static ArrayList<Process> readyQueueRR = new ArrayList<>();

    /**
     *
     */
    @Override
    public void run() {
        if (processListRR.size() != 0) {
            processListRR.clear();
        }
        if (readyQueueRR.size() != 0) {
            processListRR.clear();
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
            processListRR.add(temp);
        }

        int time = 0; //clock demonstration
        int i = 0;
        int counter = 0;
        boolean flag = false; //used to detect the first process to get to the CPU
        int qtime = 0; //to keep track of the quantum, goes from zero to entered_Quantum
        while (counter < processListRR.size()) {  //we will finish when all processes finish
            while (i < processListRR.size() && time >= processListRR.get(i).getArrivalTime()) {  //adding to ready queue
                //we need to loop through all processes in the processList and add those who have arrived to the ready queue,
                // we are using >=, because there is no need to loop through any process that has an arrival time greater than the current
                if (time == processListRR.get(i).getArrivalTime()) {
                    readyQueueRR.add(processListRR.get(i));
                    if (flag == false) {
                        readyQueueRR.get(0).setStartTime(time);
                        flag = true;
                    }
                }
                i++;
            }
            i = 0;
            if (readyQueueRR.size() != 0 && readyQueueRR.get(0).getCpuBurstVar() >= enteredQ) {
                if (qtime == enteredQ) { //if a quantum has passed, either:
                    if (readyQueueRR.get(0).getCpuBurstVar() - enteredQ == 0) {//1) A process finihes
                        GanttchartNode t = new GanttchartNode(readyQueueRR.get(0).getPID(), time - enteredQ, time);
                        ganttChatList.add(t);
                        readyQueueRR.get(0).setFinishTime(time);
                        readyQueueRR.get(0).setTA((int) readyQueueRR.get(0).getFinishTime() - readyQueueRR.get(0).getArrivalTime());
                        readyQueueRR.get(0).setWait(readyQueueRR.get(0).getTA() - readyQueueRR.get(0).getCpuBurstFixed());
                        readyQueueRR.get(0).setWTA((double) readyQueueRR.get(0).getTA() / readyQueueRR.get(0).getCpuBurstFixed());
                        readyQueueRR.remove(0);
                        counter++;
                        if (readyQueueRR.size() != 0)
                            qtime = 1;
                        else
                            qtime = 0;
                        if (readyQueueRR.size() != 0 && readyQueueRR.get(0).getStartTime() == -1)
                            readyQueueRR.get(0).setStartTime(time);
                    } else { //2)still needs more time quantum
                        GanttchartNode t = new GanttchartNode(readyQueueRR.get(0).getPID(), time - enteredQ, time);
                        ganttChatList.add(t);
                        readyQueueRR.get(0).setCpuBurstVar(readyQueueRR.get(0).getCpuBurstVar() - enteredQ);
                        Process temp = readyQueueRR.remove(0);

                        readyQueueRR.add(temp);
                        if (readyQueueRR.size() != 0 && readyQueueRR.get(0).getStartTime() == -1)
                            readyQueueRR.get(0).setStartTime(time);
                        if (readyQueueRR.size() != 0)
                            qtime = 1;
                        else
                            qtime = 0;
                    }
                } else { //if a time quantum has not elapsed yet, increment qtime
                    if (readyQueueRR.size() != 0 && readyQueueRR.get(0).getStartTime() == -1)  //for time gap
                        readyQueueRR.get(0).setStartTime(time);
                    qtime++;
                }
            } else if (readyQueueRR.size() != 0) {  //if a process will finish with time less than the quantum
                if (readyQueueRR.get(0).getCpuBurstVar() == qtime) {
                    GanttchartNode t = new GanttchartNode(readyQueueRR.get(0).getPID(), time - (int) readyQueueRR.get(0).getCpuBurstVar(), time);
                    ganttChatList.add(t);
                    readyQueueRR.get(0).setFinishTime(time);
                    readyQueueRR.get(0).setTA((int) readyQueueRR.get(0).getFinishTime() - readyQueueRR.get(0).getArrivalTime());
                    readyQueueRR.get(0).setWait(readyQueueRR.get(0).getTA() - readyQueueRR.get(0).getCpuBurstFixed());
                    readyQueueRR.get(0).setWTA((double) readyQueueRR.get(0).getTA() / readyQueueRR.get(0).getCpuBurstFixed());
                    readyQueueRR.remove(0);
                    counter++;
                    if (readyQueueRR.size() != 0)
                        qtime = 1;
                    else
                        qtime = 0;
                    if (readyQueueRR.size() != 0 && readyQueueRR.get(0).getStartTime() == -1)
                        readyQueueRR.get(0).setStartTime(time);
                } else {
                    qtime++;
                }
            }
            time++; //need to always keep track of time
        }
    }

}


