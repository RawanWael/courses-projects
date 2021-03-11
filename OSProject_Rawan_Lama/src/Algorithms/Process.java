/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Main
 */

public class Process implements Comparable<Process> {
    private int PID;
    private double priority;
    private int arrivalTime;
    //private int burst;
    private int cpuBurstFixed;
    private double cpuBurstVar;
    private int IOBurst = 0;
    private int repeat;
    private int interval;
    private int deadline;
    private int startTime = -1;
    private double finishTime = 0;
    private double TA = 0;
    private double Wait = 0;
    private double WTA = 0;

    public Process(int pID, int arrivalTime, int cpuBurst, int repeat,
                   int interval, int deadline) {
        super();
        this.PID = pID;
        this.arrivalTime = arrivalTime;
        this.cpuBurstFixed = cpuBurst;
        this.cpuBurstVar = cpuBurst;
        this.repeat = repeat;
        this.interval = interval;
        this.deadline = deadline;
        this.priority = pID;
    }

    public int getPID() {

        return PID;
    }

    public void setPID(int pID) {

        PID = pID;
    }

    public int getArrivalTime() {

        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCpuBurstFixed() {
        return cpuBurstFixed;
    }

    public void setCpuBurstFixed(int cpuBurstFixed) {
        this.cpuBurstFixed = cpuBurstFixed;
    }

    public double getCpuBurstVar() {
        return cpuBurstVar;
    }

    public void setCpuBurstVar(double cpuBurstVar) {
        this.cpuBurstVar = cpuBurstVar;
    }

    public int getIOBurst() {
        return IOBurst;
    }

    public void setIOBurst(int iOBurst) {
        IOBurst = iOBurst;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public double getFinishTime() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.finishTime;
        return Double.parseDouble(df.format(temp));

    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getTA() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.TA;
        return Double.parseDouble(df.format(temp));
    }

    public void setTA(double TA) {
        this.TA = TA;
    }

    public double getWait() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.Wait;
        return Double.parseDouble(df.format(temp));
    }

    public void setWait(double Wait) {
        this.Wait = Wait;
    }

    public double getWTA() {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = this.WTA;
        return Double.parseDouble(df.format(temp));

    }

    public void setWTA(double WTA) {
        this.WTA = WTA;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Process p) {
        return (this.arrivalTime - p.arrivalTime);
    }

    @Override
    public String toString() {
        return "Process [PID=" + PID + ", arrivalTime=" + arrivalTime + ", cpuBurstFixed=" + cpuBurstFixed
                + ", cpuBurstVar=" + cpuBurstVar + ", IOBurst=" + IOBurst + ", repeat=" + repeat + ", interval=" + interval
                + ", deadline=" + deadline + ", startTime=" + startTime + ", finishTime=" + finishTime + ", TA=" + TA
                + ", Wait=" + Wait + ", WTA=" + WTA + "]";
    }


}
