package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import static Algorithms.Multiprogrammed.processListMultiprogrammed;
import static Algorithms.Multiprogrammed.ganttChartMultiprogrammed;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Algorithms.GanttchartNode;
import Algorithms.Process;


import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MultiprogrammedController implements Initializable {
    private ObservableList<Process> dataList;

    @FXML
    private TableView<Process> tvMultiprogrammed;

    @FXML
    private TableColumn<Process, Integer> colProcessID;

    @FXML
    private TableColumn<Process, Integer> colArrivalTime;

    @FXML
    private TableColumn<Process, Integer> colCpuBurst;

    @FXML
    private TableColumn<Process, Integer> colStartTime;

    @FXML
    private TableColumn<Process, Integer> colFinishTime;

    @FXML
    private TableColumn<Process, Integer> colTA;

    @FXML
    private TableColumn<Process, Double> colWait;

    @FXML
    private TableColumn<Process, Double> colWTA;

    @FXML
    private HBox ganttChartBOX;


    @FXML
    private HBox timeAxisBOX;


    @FXML
    private HBox slid;

    @FXML
    private Label labelAvgBurst;

    @FXML
    private Label labelAvgTA;

    @FXML
    private Label labelAvgWait;

    @FXML
    private Label labelAvgWTA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTable(processListMultiprogrammed);
        calculateAverages(processListMultiprogrammed);
        showGanttChart(ganttChartMultiprogrammed);

    }

    public void showTable(ArrayList temp) {
        colProcessID.setCellValueFactory(new PropertyValueFactory<Process, Integer>("PID"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("ArrivalTime"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("StartTime"));
        colCpuBurst.setCellValueFactory(new PropertyValueFactory<Process, Integer>("CpuBurstFixed"));
        colFinishTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("FinishTime"));
        colTA.setCellValueFactory(new PropertyValueFactory<Process, Integer>("TA"));
        colWait.setCellValueFactory(new PropertyValueFactory<Process, Double>("Wait"));
        colWTA.setCellValueFactory(new PropertyValueFactory<Process, Double>("WTA"));
        dataList = FXCollections.observableArrayList(temp);
        tvMultiprogrammed.setItems(dataList);
    }

    public void showGanttChart(ArrayList<GanttchartNode> temp) {
        //calculate duration
        double duration = temp.get(temp.size() - 1).getEnd() - temp.get(0).getStart();
        double mulNum = 8000 + duration * (20);
        ganttChartBOX.setPrefWidth(mulNum);
        timeAxisBOX.setPrefWidth(mulNum);
        ganttChartBOX.setMaxWidth(mulNum);
        timeAxisBOX.setMaxWidth(mulNum);
        int height = 100;
        int count = 0;
        boolean flag = false;


        for (int i = 0; i < temp.size(); i++) {
            if (i == 0 || flag || temp.get(i).getStart() - temp.get(i - 1).getEnd() <= 0.1) {

                double width = (temp.get(i).getEnd() - temp.get(i).getStart()) * mulNum / (double) duration; //where 770 is the total width of the used HBox
                Rectangle r = new Rectangle();
                r.setHeight(height);
                r.setWidth(width);
                if (count % 2 == 0)
                    r.setFill(Color.web("#e6d5b8"));
                else
                    r.setFill(Color.web("#99a8b2"));
                Label p = new Label(temp.get(i).getIdMultiprogrammed());
                StackPane adding = new StackPane();
                adding.getChildren().addAll(r, p);
                ganttChartBOX.getChildren().add(adding);
                if (i == 0) {
                    Label time1 = new Label("" + temp.get(i).getStart());
                    time1.setPrefWidth(60); //
                    time1.setPrefHeight(25);
                    time1.setAlignment(Pos.BOTTOM_LEFT);
                    Label time2 = new Label("" + temp.get(i).getEnd());
                    time2.setPrefWidth(width - 60); //
                    time2.setPrefHeight(25);
                    time2.setAlignment(Pos.BOTTOM_RIGHT);
                    timeAxisBOX.getChildren().addAll(time1, time2);
                } else {
                    Label time = new Label("" + temp.get(i).getEnd());
                    time.setPrefWidth(width);
                    time.setPrefHeight(25);
                    time.setAlignment(Pos.BOTTOM_RIGHT);
                    timeAxisBOX.getChildren().add(time);

                }
                count++;
                flag = false;

            } else {
                double width = (temp.get(i).getStart() - temp.get(i - 1).getEnd()) * mulNum / (double) duration;
                Rectangle r = new Rectangle();
                r.setHeight(height);
                r.setWidth(width);
                r.setFill(Color.TRANSPARENT);
                StackPane adding = new StackPane();
                adding.getChildren().add(r);
                ganttChartBOX.getChildren().add(adding);
                Label time = new Label("" + temp.get(i).getStart());
                time.setPrefWidth(width);
                time.setPrefHeight(25);
                time.setAlignment(Pos.BOTTOM_RIGHT);
                timeAxisBOX.getChildren().add(time);
                i--;
                flag = true;

            }

        }
    }

    public void calculateAverages(ArrayList<Process> temp) {
        double avgTA = 0;
        double avgWait = 0;
        double avgWTA = 0;
        double avgBurst = 0;

        for (int i = 0; i < temp.size(); i++) {
            avgTA += temp.get(i).getTA();
            avgWait += temp.get(i).getWait();
            avgWTA += temp.get(i).getWTA();
            avgBurst += temp.get(i).getCpuBurstFixed();
        }

        avgTA = avgTA / temp.size();
        avgWait = avgWait / temp.size();
        avgWTA = avgWTA / temp.size();
        avgBurst = avgBurst / temp.size();

        //Get code of two digits
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        labelAvgTA.setText("" + Double.parseDouble(df.format(avgTA)));
        labelAvgWait.setText("" + Double.parseDouble(df.format(avgWait)));
        labelAvgWTA.setText("" + Double.parseDouble(df.format(avgWTA)));
        labelAvgBurst.setText("" + Double.parseDouble(df.format(avgBurst)));
    }


}
