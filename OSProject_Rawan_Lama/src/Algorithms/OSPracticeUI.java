/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;


import Algorithms.Process;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static javafx.application.Application.launch;


/**
 * @author Main
 */

public class OSPracticeUI extends Application {
    public static ArrayList<Process> processList = new ArrayList<Process>();
    public static ArrayList<Process> readyQueue = new ArrayList<Process>();
    public static int enteredQ;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLs/MainInterface.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            GenerateFile.randomGenerate();
        } catch (IOException e) {
            System.out.println("Unsuccessful generation of random file");
        }

        if (readFile("Processes.txt")) {
            System.out.println("Done Reading File!");
        }
        Collections.sort(processList);
//            for(int i=0; i < processList.size(); i++){
//                System.out.println( processList.get(i) );
//            }

        launch(args);


    }

    public static boolean readFile(String fileName) {
        try {
            // open the file to read from it.
            File file = new File(fileName);
            if (!(file.exists())) {// if the file does not exists.
                System.out.println("file dosenot exists");
                return false;
            } else {
                Scanner y = new Scanner(file);
                String reader;
                while (y.hasNext()) {
                    reader = y.nextLine();
                    String[] values = reader.split(" ");
                    Process temp = new Process(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()), Integer.parseInt(values[2].trim()), Integer.parseInt(values[3].trim()), Integer.parseInt(values[4].trim()), Integer.parseInt(values[5].trim()));
                    processList.add(temp);
                }
                y.close();
            }
        } catch (IOException x) {
            System.out.println("There is something error in the file");
        }
        return true;
    }

}
