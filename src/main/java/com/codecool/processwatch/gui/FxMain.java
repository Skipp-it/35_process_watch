package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessWatchApp;
import com.codecool.processwatch.queries.QueryByInput;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.stream.Stream;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.sort;

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
     * @param primaryStage a stage created by the JavaFX runtime.
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        ObservableList<ProcessView> displayList = observableArrayList();
        app = new App(displayList);
        // TODO: Factor out the repetitive code
        var tableView = new TableView<ProcessView>(displayList);
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

        TextField inputBox = new TextField();
        inputBox.setMaxWidth(150);

        var filterButton = new Button("Filter");
        filterButton.setOnAction(actionEvent -> {
            QueryByInput filteredQuery = new QueryByInput(inputBox.getText());
            app.setQuery(filteredQuery);
            app.refresh();
        });

        var killButton = new Button("Kill process");
        killButton.setOnAction(actionEvent -> {
            tableView.getSelectionModel().getSelectedItems().forEach(p -> {
                ProcessHandle.of(p.getPid()).ifPresent(s -> s.destroy());
            });
        });


        var box = new VBox();
        var label = new Label("Filter by owner name or Parent ID");

        var elements = box.getChildren();
        elements.addAll(refreshButton, label, inputBox, filterButton,
                tableView, killButton);

        var scene = new Scene(box, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();

        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }
}
