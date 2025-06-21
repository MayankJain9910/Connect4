package com.internshala.connect4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Controller controller;

    public static void main(String[] args) {
        launch();
    }

    public MenuBar createMenubar(){
        Menu fileMenu = new Menu("File");

        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(actionEvent -> controller.resetGame());

        MenuItem resetGame = new MenuItem("Reset Game");
        resetGame.setOnAction(actionEvent -> controller.resetGame());

        SeparatorMenuItem separator1 = new SeparatorMenuItem();

        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction(actionEvent -> exitGame());

        fileMenu.getItems().addAll(newGame,resetGame,separator1,exitGame);

        Menu helpMenu = new Menu("Help");

        MenuItem aboutGame = new MenuItem("About Connect4");
        aboutGame.setOnAction(actionEvent -> aboutGame());

        SeparatorMenuItem separator2 = new SeparatorMenuItem();

        MenuItem aboutMe = new MenuItem("About Developer");
        aboutMe.setOnAction(actionEvent -> aboutMe());

        helpMenu.getItems().addAll(aboutGame,separator2,aboutMe);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,helpMenu);
        return menuBar;

    }

    private void aboutMe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Developer");
        alert.setHeaderText("Mayank Jain ");
        alert.setContentText("""
                I love to play with code and develop new Games/Programs.
                In my free time i play with my friends and learn new things.
                """);
        alert.show();
    }

    private void aboutGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect4 Game");
        alert.setHeaderText("How to play?");
        alert.setContentText("""
                Connect Four is a two-player connection game in which
                the players first choose a color and then take turns
                dropping colored discs from the top into a seven-column,
                six-row vertically suspended grid. The pieces fall straight
                down, occupying the next available space within the column.
                The objective of the game is to be the first to form a horizontal,
                vertical, or diagonal line of four of one's own discs.
                Connect Four is a solved game. The first player can
                always win by playing the right moves.
                """);
        alert.show();
    }

    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("game.fxml"));
        GridPane rootGridPane = loader.load();

        controller = loader.getController();
        controller.createPlayGround();

        MenuBar menuBar = createMenubar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        Pane menupane = (Pane) rootGridPane.getChildren().getFirst();
        menupane.getChildren().add(menuBar);

        Scene scene = new Scene(rootGridPane);
        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}