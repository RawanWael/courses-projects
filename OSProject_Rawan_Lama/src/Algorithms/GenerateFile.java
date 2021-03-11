/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

/**
 * @author Main
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateFile {
    public static void randomGenerate() throws IOException {
        int randomSize = ThreadLocalRandom.current().nextInt(1, 10 + 1); //should be 100
        //Properties
        int arrivalTime;
        int cpuBurst;
        int repeat;
        int interval;
        int deadline;

        FileWriter file = new FileWriter("Processes.txt");

        for (int i = 0; i < randomSize; i++) {
            arrivalTime = ThreadLocalRandom.current().nextInt(0, 20 + 1);
            cpuBurst = ThreadLocalRandom.current().nextInt(1, 20 + 1);
            repeat = 1;
            interval = 0;
            deadline = 0;
            file.write(i + " " + arrivalTime + " " + cpuBurst + " " + repeat + " " + interval + " " + deadline + "\n");
        }

        file.close();
    }

}

