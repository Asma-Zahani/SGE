package com.sge.view;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainView {
    private BorderPane root;
    private TabPane tabPane;

    public MainView() {
        tabPane = new TabPane();
        root = new BorderPane(tabPane);
    }

    public void addTab(String title, Parent content) {
        Tab tab = new Tab(title, content);
        tab.setClosable(false);
        tabPane.getTabs().add(tab);
    }

    public Parent getRoot() {
        return root;
    }
}