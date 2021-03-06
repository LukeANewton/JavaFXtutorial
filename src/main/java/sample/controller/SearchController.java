package sample.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import sample.model.Person;

import java.util.stream.Collectors;

public class SearchController {

    public static final int PAGE_ITEMS_COUNT = 10;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Pagination pagination;
    @FXML
    private Label searchLabel;

    private ObservableList<Person> masterData;

    public SearchController() {
        masterData = FXCollections.observableArrayList();
        masterData.add(new Person(5, "John", true));
        masterData.add(new Person(7, "Albert", true));
        masterData.add(new Person(11, "Monica", false));
    }

    @FXML
    private void initialize() {

        // search panel
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());
        searchButton.setStyle("-fx-background-color: #457ecd; -fx-text-fill: #ffffff;");

        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLabel.setText(newValue);
        });

        pagination.setPageFactory(SearchController.this::createPage);
    }

    private Node createPage(Integer pageIndex) {

        VBox dataContainer = new VBox();

        TableView<Person> tableView = new TableView<>(masterData);
        TableColumn id = new TableColumn("ID");
        TableColumn name = new TableColumn("NAME");
        TableColumn employed = new TableColumn("EMPLOYED");

        tableView.getColumns().addAll(id, name, employed);
        dataContainer.getChildren().add(tableView);

        return dataContainer;
    }

    private void loadData() {

        String searchText = searchField.getText();

        Task<ObservableList<Person>> task = new Task<>() {
            @Override
            protected ObservableList<Person> call() {
                updateMessage("Loading data");
                System.out.println(searchText);
                return FXCollections.observableArrayList(masterData
                        .stream()
                        .filter(value -> value.getName().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList()));
            }
        };

        task.setOnSucceeded(event -> {
            System.out.println(masterData);
            masterData = task.getValue();
            pagination.setVisible(true);
            pagination.setPageCount(masterData.size() / PAGE_ITEMS_COUNT);
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

}