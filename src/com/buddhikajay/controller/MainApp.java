package com.buddhikajay.controller;

import com.buddhikajay.SqliteDatabase;
import com.buddhikajay.model.TableTransaction;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Buddhika Jayawardhana on 01/03/2015.
 */
public class MainApp extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<TableTransaction> transactions = FXCollections.observableArrayList();
    /*
    Url of the sqlite database
     */
    final String DB_URL = "C://Users//Buddhika//Documents//Programming//IdeaProjects//VirtualCreditDesktop//database//test.sqlite";

    public MainApp(){
        //creates test dataset for transactionOverview controller
        transactions.add(new TableTransaction(1,"Buddhika", 100, "Lend", "2015", "Yes", "Ananm manam" ));//int id, String person, float amount, String type, String date, String resolved, String description
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Virtual Credit");
        try{
            //loads a Border pane.
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/buddhikajay/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        showTransactionOverview();
        showNewTransactionDialog();

    }
    /*
    loads the table and other componets and sets it on the center of borderpane.
     */
    public void showTransactionOverview(){
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/buddhikajay/view/TransactionOverview.fxml"));
            AnchorPane overviewPage = loader.load();
            rootLayout.setCenter(overviewPage);
            TransactionOverviewController controller = loader.getController();
            controller.setMainApp(this, new SqliteDatabase(DB_URL));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public void showNewTransactionDialog(){

        try {
            FXMLLoader loader = new FXMLLoader();//("/com/buddhikajay/view/NewTransactionDialog.fxml"));
            loader.setLocation(MainApp.class.getResource("/com/buddhikajay/view/NewTransactionDialog.fxml"));
            loader.setController(new NewTransactionDialogController());
            AnchorPane anchorPane = (AnchorPane)loader.load();
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setTitle("Add New Transaction");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(scene);
            stage.showAndWait();

            //set the controller
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ObservableList<TableTransaction> getTransactions() {
        return transactions;
    }
    Stage getPrimaryStage(){
        return this.primaryStage;
    }
    public static void main(String arga[]){
        Application.launch();
    }
}
