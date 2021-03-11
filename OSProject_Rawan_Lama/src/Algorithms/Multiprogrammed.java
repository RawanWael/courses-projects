package Algorithms;

import java.util.ArrayList;

import static Algorithms.OSPracticeUI.processList;

public class Multiprogrammed extends Thread {
    public static double P;
    public static ArrayList<GanttchartNode> ganttChartMultiprogrammed = new ArrayList<>();
    public static ArrayList<Process> processListMultiprogrammed = new ArrayList<>();
    public static ArrayList<Process> readyQueueMultiprogrammed = new ArrayList<>();

    @Override
    public void run() {
        if (processListMultiprogrammed.size() != 0) {
            processListMultiprogrammed.clear();
        }
        if (readyQueueMultiprogrammed.size() != 0) {
            processListMultiprogrammed.clear();
        }
        if (ganttChartMultiprogrammed.size() != 0) {
            ganttChartMultiprogrammed.clear();
        }
        int counterIndex = 0; //to keep track of the arriving processes
        int counter = 0;


        for (int i = 0; i < processList.size(); i++) {
            Process temp = new Process(processList.get(i).getPID(),
                    processList.get(i).getArrivalTime(),
                    processList.get(i).getCpuBurstFixed(),
                    processList.get(i).getRepeat(),
                    processList.get(i).getInterval(),
                    processList.get(i).getDeadline());
            processListMultiprogrammed.add(temp);
        }
        double time = processListMultiprogrammed.get(0).getArrivalTime();
        int i = 0;
        int size = 0;
        double duration;
        double newBurst;
        double percentage;
        for (int j = 0; j < processListMultiprogrammed.size(); j++) {
            newBurst = processListMultiprogrammed.get(j).getCpuBurstFixed() * (1 - P);
            processListMultiprogrammed.get(j).setCpuBurstVar(newBurst);
        }
        while (counter < processListMultiprogrammed.size()) {
            if (counterIndex < processListMultiprogrammed.size() - 1) {
                while (counterIndex < processListMultiprogrammed.size() - 1 && i < processListMultiprogrammed.size() && time >= processListMultiprogrammed.get(i).getArrivalTime()) {
                    if (Math.abs(time - processListMultiprogrammed.get(i).getArrivalTime()) <= 0.0001) {
                        processListMultiprogrammed.get(i).setStartTime((int) time);
                        readyQueueMultiprogrammed.add(processListMultiprogrammed.get(i));
                        counterIndex++;
                    }
                    i++;
                }
                i = 0;
                duration=0;
                percentage=0;
                size = readyQueueMultiprogrammed.size();
                if(readyQueueMultiprogrammed.size()!=0){
                    percentage=minCpuVar();
                    duration=percentage / ((1 - Math.pow(P, size)) / (double) size);
                }
                if(readyQueueMultiprogrammed.size()!=0 && duration+time <processListMultiprogrammed.get(counterIndex).getArrivalTime()){
                    time += duration;
                    String ids = "";
                    for (int k = 0; k < readyQueueMultiprogrammed.size(); k++) {
                        ids = ids + "P" + readyQueueMultiprogrammed.get(k).getPID() + " ";
                    }
                    GanttchartNode t = new GanttchartNode(ids, time - duration, time);
                    ganttChartMultiprogrammed.add(t);
                    for (int j = 0; j < readyQueueMultiprogrammed.size(); j++) {
                        readyQueueMultiprogrammed.get(j).setCpuBurstVar(readyQueueMultiprogrammed.get(j).getCpuBurstVar() - percentage);
                        String w = "" + readyQueueMultiprogrammed.get(j).getCpuBurstVar();
                        if (w.equals("0.0")) {
                            readyQueueMultiprogrammed.get(j).setFinishTime(time);
                            readyQueueMultiprogrammed.get(j).setTA(readyQueueMultiprogrammed.get(j).getFinishTime() - readyQueueMultiprogrammed.get(j).getArrivalTime());
                            readyQueueMultiprogrammed.get(j).setWait(readyQueueMultiprogrammed.get(j).getTA() - readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            readyQueueMultiprogrammed.get(j).setWTA(readyQueueMultiprogrammed.get(j).getTA() / readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            counter++;
                            readyQueueMultiprogrammed.remove(j);
                        }
                    }
                    time=processListMultiprogrammed.get(counterIndex).getArrivalTime();


                }
                else {


                    duration = processListMultiprogrammed.get(counterIndex).getArrivalTime() - time; //counterIndex representing the first process to be added next
                    time = time + duration;
                    percentage = ((1 - Math.pow(P, size)) / (double) size) * duration;
                    if(duration!=0) {
                        String ids = "";
                        for (int k = 0; k < readyQueueMultiprogrammed.size(); k++) {
                            ids = ids + "P" + readyQueueMultiprogrammed.get(k).getPID() + " ";
                        }
                        GanttchartNode t = new GanttchartNode(ids, time - duration, time);
                        ganttChartMultiprogrammed.add(t);
                    }
                    for (int j = 0; j < readyQueueMultiprogrammed.size(); j++) {
                        readyQueueMultiprogrammed.get(j).setCpuBurstVar(readyQueueMultiprogrammed.get(j).getCpuBurstVar() - percentage);
                        String w = "" + readyQueueMultiprogrammed.get(j).getCpuBurstVar();
                        if (w.equals("0.0")) {
                            readyQueueMultiprogrammed.get(j).setFinishTime(time);
                            readyQueueMultiprogrammed.get(j).setTA(readyQueueMultiprogrammed.get(j).getFinishTime() - readyQueueMultiprogrammed.get(j).getArrivalTime());
                            readyQueueMultiprogrammed.get(j).setWait(readyQueueMultiprogrammed.get(j).getTA() - readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            readyQueueMultiprogrammed.get(j).setWTA(readyQueueMultiprogrammed.get(j).getTA() / readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            counter++;
                            readyQueueMultiprogrammed.remove(j);
                        }

                    }
                }
            } else if (counterIndex == processListMultiprogrammed.size() - 1) { //last process
                processListMultiprogrammed.get(counterIndex).setStartTime((int) time);
                readyQueueMultiprogrammed.add(processListMultiprogrammed.get(counterIndex));
                size = readyQueueMultiprogrammed.size();
                if (size != 0) {
                    percentage = minCpuVar();
                    duration = percentage / ((1 - Math.pow(P, size)) / (double) size);
                    time += duration;
                    String ids = "";
                    for (int k = 0; k < readyQueueMultiprogrammed.size(); k++) {
                        ids = ids + "P" + readyQueueMultiprogrammed.get(k).getPID() + " ";
                    }
                    GanttchartNode t = new GanttchartNode(ids, time - duration, time);
                    ganttChartMultiprogrammed.add(t);

                    for (int j = 0; j < readyQueueMultiprogrammed.size(); j++) {
                        readyQueueMultiprogrammed.get(j).setCpuBurstVar(readyQueueMultiprogrammed.get(j).getCpuBurstVar() - percentage);
                        String w = "" + readyQueueMultiprogrammed.get(j).getCpuBurstVar();
                        if (w.equals("0.0")) {
                            readyQueueMultiprogrammed.get(j).setFinishTime(time);
                            readyQueueMultiprogrammed.get(j).setTA(readyQueueMultiprogrammed.get(j).getFinishTime() - readyQueueMultiprogrammed.get(j).getArrivalTime());
                            readyQueueMultiprogrammed.get(j).setWait(readyQueueMultiprogrammed.get(j).getTA() - readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            readyQueueMultiprogrammed.get(j).setWTA(readyQueueMultiprogrammed.get(j).getTA() / readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            counter++;
                            readyQueueMultiprogrammed.remove(j);
                            j--;
                        }
                    }

                }
                counterIndex++;

            } else { //All processes have arrived
                size = readyQueueMultiprogrammed.size();
                if (size != 0) {
                    percentage = minCpuVar();
                    duration = percentage / ((1 - Math.pow(P, size)) / (double) size);
                    time += duration;
                    String ids = "";
                    for (int k = 0; k < readyQueueMultiprogrammed.size(); k++) {
                        ids = ids + "P" + readyQueueMultiprogrammed.get(k).getPID() + " ";
                    }
                    GanttchartNode t = new GanttchartNode(ids, time - duration, time);
                    ganttChartMultiprogrammed.add(t);

                    for (int j = 0; j < readyQueueMultiprogrammed.size(); j++) {
                        readyQueueMultiprogrammed.get(j).setCpuBurstVar(readyQueueMultiprogrammed.get(j).getCpuBurstVar() - percentage);
                        String w = "" + readyQueueMultiprogrammed.get(j).getCpuBurstVar();
                        if (w.equals("0.0")) {
                            readyQueueMultiprogrammed.get(j).setFinishTime(time);
                            readyQueueMultiprogrammed.get(j).setTA(readyQueueMultiprogrammed.get(j).getFinishTime() - readyQueueMultiprogrammed.get(j).getArrivalTime());
                            readyQueueMultiprogrammed.get(j).setWait(readyQueueMultiprogrammed.get(j).getTA() - readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            readyQueueMultiprogrammed.get(j).setWTA(readyQueueMultiprogrammed.get(j).getTA() / readyQueueMultiprogrammed.get(j).getCpuBurstFixed());
                            counter++;
                            readyQueueMultiprogrammed.remove(j);
                            j--;
                        }
                    }
                }
            }

        }


    }

    public double minCpuVar() {
        double min = readyQueueMultiprogrammed.get(0).getCpuBurstVar();
        for (int i = 1; i < readyQueueMultiprogrammed.size(); i++) {
            if (readyQueueMultiprogrammed.get(i).getCpuBurstVar() <= min)
                min = readyQueueMultiprogrammed.get(i).getCpuBurstVar();
        }
        return min;
    }
}