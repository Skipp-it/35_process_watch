package com.codecool.processwatch.gui;

import com.codecool.processwatch.queries.QueryByInput;
import javafx.application.Application;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * The JavaFX application Window.
 */
public class FxMain extends Application {
    private static final String TITLE = "Process Watch";

    private App app;


    /**
     * Entrypoint for the javafx:run maven task.
     *
     * @param args an array of the command line parameters.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Build the application window and set up event handling.
     *
     *
     */



    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle(TITLE);

        ObservableList<ProcessView> displayList = observableArrayList();
        app = new App(displayList);
        // TODO: Factor out the repetitive code
        var tableView = new TableView<ProcessView>(displayList);
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        var pidColumn = new TableColumn<ProcessView, Long>("Process ID");
        pidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("pid"));
        var parentPidColumn = new TableColumn<ProcessView, Long>("Parent Process ID");
        parentPidColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, Long>("parentPid"));
        var userNameColumn = new TableColumn<ProcessView, String>("Owner");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("userName"));
        var processNameColumn = new TableColumn<ProcessView, String>("Name");
        processNameColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("processName"));
        var argsColumn = new TableColumn<ProcessView, String>("Arguments");
        argsColumn.setCellValueFactory(new PropertyValueFactory<ProcessView, String>("args"));
        tableView.getColumns().add(pidColumn);
        tableView.getColumns().add(parentPidColumn);
        tableView.getColumns().add(userNameColumn);
        tableView.getColumns().add(processNameColumn);
        tableView.getColumns().add(argsColumn);

        var refreshButton = new Button("Refresh");
        refreshButton.setOnAction(actionEvent -> {
            App newApp = new App(displayList);
            newApp.refresh();
        });

        var refreshBtnInfo = new ToggleButton("?");
        refreshBtnInfo.setOnAction(actionEvent -> {

            Label text = new Label("The Refresh Button reloads all active processes");
            text.setWrapText(true);


            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(text);

            Scene secondScene = new Scene(secondaryLayout, 300, 150);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Info Refresh Button");
            newWindow.setScene(secondScene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 400);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });

        var killBtnInfo = new Button("?");
        killBtnInfo.setOnAction(actionEvent->{

            Label text = new Label("The Kill Button kill's all selected processes");
            text.setWrapText(true);

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(text);

            Scene secondScene = new Scene(secondaryLayout, 300, 150);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Info Refresh Button");
            newWindow.setScene(secondScene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });

        TextField inputBox = new TextField();
        inputBox.setMaxWidth(180);

        var filterButton = new Button("Filter");
        filterButton.setOnAction(actionEvent -> {
            QueryByInput filteredQuery = new QueryByInput(inputBox.getText());
            app.setQuery(filteredQuery);
            app.refresh();
        });

        var filterBtnInfo = new Button("?");
        filterBtnInfo.setOnAction(actionEvent->{

            Label text = new Label("You can filter processes either by entering the parent ID or by owner name");
            text.setWrapText(true);

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(text);

            Scene secondScene = new Scene(secondaryLayout, 300, 150);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Info Refresh Button");
            newWindow.setScene(secondScene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 700);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });

        var killButton = new Button("Kill process");
        killButton.setOnAction(actionEvent -> {
            tableView.getSelectionModel().getSelectedItems().forEach(p -> {
                app.killProcess(p.getPid());
            });
        });

        FileInputStream inputStream = new FileInputStream("images/about.png");
        Image img = new Image(inputStream,20,20,false,false);
        ImageView imgView = new ImageView(img);
        imgView.setPreserveRatio(true);

        Button aboutBtn = new Button();
        aboutBtn.setGraphic(imgView);
        aboutBtn.setStyle("-fx-background-size: inherit");




        aboutBtn.setOnAction(actionEvent->{

            Label text = new Label("Application shows all processes from computer");
            text.setWrapText(true);


            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().addAll(text);

            Scene secondScene = new Scene(secondaryLayout, 300, 150);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("About application");
            newWindow.setScene(secondScene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);

            newWindow.show();
        });

        var box = new VBox();

        var label = new Label("Filter by owner name or Parent ID");

/**
 refreshButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
 refreshBtnInfo.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
 **/

        //Creating an Anchor Pane
        AnchorPane anchorPane = new AnchorPane();

        AnchorPane.setLeftAnchor(killButton, 10.0);
        AnchorPane.setTopAnchor(killButton,10.0);
        AnchorPane.setBottomAnchor(killButton,5.0);

        AnchorPane.setLeftAnchor(killBtnInfo, 90.0);
        AnchorPane.setTopAnchor(killBtnInfo,10.0);
        AnchorPane.setBottomAnchor(killBtnInfo,5.0);

        AnchorPane.setLeftAnchor(aboutBtn, 230.0);
        AnchorPane.setTopAnchor(aboutBtn,10.0);
        AnchorPane.setBottomAnchor(aboutBtn,5.0);

        AnchorPane.setLeftAnchor(refreshButton, 400.0);
        AnchorPane.setRightAnchor(refreshButton, 400.0);
        AnchorPane.setTopAnchor(refreshButton,10.0);
        AnchorPane.setBottomAnchor(refreshButton,5.0);

        refreshButton.setMinSize(0, 10);
        AnchorPane.setLeftAnchor(refreshButton, 400.0);
        AnchorPane.setRightAnchor(refreshBtnInfo, 400.0);
        AnchorPane.setTopAnchor(refreshBtnInfo,10.0);
        AnchorPane.setBottomAnchor(refreshBtnInfo,5.0);

        AnchorPane.setRightAnchor(filterButton, 194.0);
        AnchorPane.setTopAnchor(filterButton,10.0);
        AnchorPane.setBottomAnchor(filterButton,5.0);

        AnchorPane.setRightAnchor(inputBox, 34.0);
        AnchorPane.setTopAnchor(inputBox,10.0);
        AnchorPane.setBottomAnchor(inputBox,5.0);

        AnchorPane.setRightAnchor(filterBtnInfo, 10.0);
        AnchorPane.setTopAnchor(filterBtnInfo,10.0);
        AnchorPane.setBottomAnchor(filterBtnInfo,5.0);

        ObservableList list = anchorPane.getChildren();

        list.addAll(killButton, killBtnInfo, aboutBtn, refreshButton, refreshBtnInfo, filterButton, inputBox, filterBtnInfo);

        final Separator separator = new Separator();
        final Separator separator2 = new Separator();

        var elements = box.getChildren();

        elements.addAll(anchorPane);
        box.setSpacing(5);
        elements.addAll(separator);
        box.setSpacing(5);
        elements.addAll(tableView);
        box.setSpacing(5);
        elements.addAll(separator2);
        box.setSpacing(5);

        var scene = new Scene(box, 1024, 640, Color.AQUAMARINE);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
