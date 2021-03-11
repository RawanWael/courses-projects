package Controllers;

import Algorithms.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Algorithms.OSPracticeUI.enteredQ;
import static Algorithms.Multiprogrammed.P;

public class MainInterfaceController implements Initializable {

    @FXML
    private Button btnMultiprogrammed;

    @FXML
    private Button btnFCFS;

    @FXML
    private Button btnSJF;

    @FXML
    private Button btnSRTF;

    @FXML
    private Button btnEPWithPreemption;

    @FXML
    private Button btnEPWithoutPreemption;

    @FXML
    private Button btnRR;

    @FXML
    private TextField tfEnteredQ;

    @FXML
    private Label labelGenerate;

    @FXML
    private TextField tfIOPercentage;

    @FXML
    void clickEPWithPreemption(ActionEvent event) throws IOException {
        //code for chaning stage
        EPWithPreemption threadEPWithPreemption = new EPWithPreemption();
        threadEPWithPreemption.start();
        while (threadEPWithPreemption.isAlive()) ;
        final Stage EPWithPreemptionStage = new Stage();
        EPWithPreemptionStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        EPWithPreemptionStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/EPWithPreemption.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        EPWithPreemptionStage.setScene(scene11);
        EPWithPreemptionStage.show();

    }

    @FXML
    void clickEPWithoutPreemption(ActionEvent event) throws IOException {
        EPWithoutPreemption threadEPWithoutPreemption = new EPWithoutPreemption();
        threadEPWithoutPreemption.start();
        while (threadEPWithoutPreemption.isAlive()) ;
        final Stage EPWithoutPreemptionStage = new Stage();
        EPWithoutPreemptionStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        EPWithoutPreemptionStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/EPWithoutPreemption.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        EPWithoutPreemptionStage.setScene(scene11);
        EPWithoutPreemptionStage.show();
    }

    @FXML
    void clickFCFS(ActionEvent event) throws IOException {
        //code for chaning stage
        FCFS threadFCFS = new FCFS();
        threadFCFS.start();
        while (threadFCFS.isAlive()) ;
        final Stage FCFSStage = new Stage();
        FCFSStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        FCFSStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/FCFS.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        FCFSStage.setScene(scene11);
        FCFSStage.show();
    }

    @FXML
    void clickMultiprogrammed(ActionEvent event) throws IOException {
        //code for chaning stage
        try {
            P = Double.parseDouble(tfIOPercentage.getText().trim());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid IO percentage!");
            alert.showAndWait();
        }
        Multiprogrammed threadMultiprogrammed = new Multiprogrammed();
        threadMultiprogrammed.start();
        while (threadMultiprogrammed.isAlive()) ;
        final Stage MultiprogrammedStage = new Stage();
        MultiprogrammedStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        MultiprogrammedStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/Multiprogrammed.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        MultiprogrammedStage.setScene(scene11);
        MultiprogrammedStage.show();
    }

    @FXML
    void clickRR(ActionEvent event) throws IOException {
        //code for chaning stage
        try {
            enteredQ = Integer.parseInt(tfEnteredQ.getText().trim());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid time quantum!");
            alert.showAndWait();
        }
        RR threadRR = new RR();
        threadRR.start();
        while (threadRR.isAlive()) ;
        final Stage RRStage = new Stage();
        RRStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        RRStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/RR.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        RRStage.setScene(scene11);
        RRStage.show();
    }

    @FXML
    void clickSJF(ActionEvent event) throws IOException {
        SJF threadSJF = new SJF();
        threadSJF.start();
        while (threadSJF.isAlive()) ;
        final Stage SJFStage = new Stage();
        SJFStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        SJFStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/SJF.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        SJFStage.setScene(scene11);
        SJFStage.show();

    }

    @FXML
    void clickSRTF(ActionEvent event) throws IOException {
        //code for chaning stage
        SRTF threadSRTF = new SRTF();
        threadSRTF.start();
        while (threadSRTF.isAlive()) ;
        final Stage SRTFStage = new Stage();
        SRTFStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        SRTFStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/SRTF.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        SRTFStage.setScene(scene11);
        SRTFStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
