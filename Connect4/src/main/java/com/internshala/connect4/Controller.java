package com.internshala.connect4;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    @FXML
    public GridPane rootGridPane;
    public Pane insertedDiscPane;
    @FXML
    public Label PlayerNameLable;
    @FXML
    public TextField playerOneTextField,playerTwoTextField;
    @FXML
    public Button setNamesButton;

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final double Circle_Dia = 80;
    private static final String discColour1 = "#24303E";
    private static final String discColour2 = "#4CAA88";
    private static String Player_One = "Player One";
    private static String Player_Two = "Player Two";
    private boolean isPlayerOneTurn = true;
    private final Disc[][] insertedDiscArray = new Disc[ROWS][COLUMNS];
    private boolean isAllowedToInsert = true;

    public void createPlayGround(){

        Shape rectangleWithHoles = CreateGameStructuralGrid();
        rootGridPane.add(rectangleWithHoles,0,1);

        List<Rectangle> rectangleList = createClickableColumns();

        for (Rectangle rectangle : rectangleList){
            rootGridPane.add(rectangle,0,1);
        }

        setNamesButton.setOnAction(actionEvent -> {
            Player_One = playerOneTextField.getText();
            Player_Two = playerTwoTextField.getText();
            PlayerNameLable.setText(Player_One);
        });
    }

    private Shape CreateGameStructuralGrid(){
        Shape rectanglrectangleWithHolese = new Rectangle((COLUMNS + 1) * Circle_Dia, (ROWS + 1) * Circle_Dia);

        for(int rows = 0; rows < ROWS; rows++) {
            for (int column = 0; column < COLUMNS; column++) {
                Circle circle = new Circle();
                circle.setRadius(Circle_Dia / 2);
                circle.setCenterY(Circle_Dia / 2);
                circle.setCenterX(Circle_Dia / 2);
                circle.setSmooth(true);

                circle.setTranslateX(column * (Circle_Dia+5) + Circle_Dia/4);
                circle.setTranslateY(rows * (Circle_Dia+5) + Circle_Dia/4);

                rectanglrectangleWithHolese = Shape.subtract(rectanglrectangleWithHolese, circle);
            }
        }


        rectanglrectangleWithHolese.setFill(Color.WHITE);
        return rectanglrectangleWithHolese;
    }

    private List<Rectangle> createClickableColumns(){

        List<Rectangle> rectangleList = new ArrayList<>();
        for (int col = 0; col < COLUMNS; col++){
            Rectangle rectangle = new Rectangle(Circle_Dia, (ROWS + 1) * Circle_Dia);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(col * (Circle_Dia+5) + Circle_Dia/4);


            rectangle.setOnMouseEntered(mouseEvent -> rectangle.setFill(Color.valueOf("#d3d3d326")));
            rectangle.setOnMouseExited(mouseEvent -> rectangle.setFill(Color.TRANSPARENT));

            final  int column = col;
            rectangle.setOnMouseClicked(mouseEvent -> {
                if (isAllowedToInsert) {
                    isAllowedToInsert = false;
                    insertDisc(new Disc(isPlayerOneTurn), column);
                }
            });

            rectangleList.add(rectangle);
        }
        return rectangleList;
    }

    private void insertDisc(Disc disc, int column){

        int row = ROWS - 1;
        while (row>=0){
            if (getDiscIfPresent(row,column) == null)
                break;

            row--;
        }

        if (row<0)
            return;

        insertedDiscArray[row][column] = disc;
        insertedDiscPane.getChildren().add(disc);
        int currentrow = row;
        disc.setTranslateX(column * (Circle_Dia + 5) + Circle_Dia / 4);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),disc);
        translateTransition.setToY(row * (Circle_Dia + 5) + Circle_Dia / 4);
        translateTransition.setOnFinished(actionEvent -> {
            isAllowedToInsert = true;
            if(gameEnded(currentrow,column)){
                gameOver();
                return;
            }
            isPlayerOneTurn = !isPlayerOneTurn;

            PlayerNameLable.setText(isPlayerOneTurn ?Player_One:Player_Two);
        });
        translateTransition.play();
    }

    private boolean gameEnded(int row, int column) {
        //Vertical Points
        List<Point2D> verticalPoints = IntStream.rangeClosed(row - 3,row + 3)
                .mapToObj(r -> new Point2D(r,column))
                .collect(Collectors.toList());

        //Horizontal Points
        List<Point2D> horizontalPoints = IntStream.rangeClosed(column - 3,column + 3)
                .mapToObj(col -> new Point2D(row,col))
                .collect(Collectors.toList());

        Point2D startPoint1 = new Point2D(row-3,column+3);
        List<Point2D> diagonal1Points = IntStream.rangeClosed(0,6)
                .mapToObj(i -> startPoint1.add(i,-i))
                .collect(Collectors.toList());

        Point2D startPoint2 = new Point2D(row-3,column-3);
        List<Point2D> diagonal2Points = IntStream.rangeClosed(0,6)
                .mapToObj(i -> startPoint2.add(i,i))
                .collect(Collectors.toList());

        return checkCombinations(verticalPoints) || checkCombinations(horizontalPoints) ||
               checkCombinations(diagonal1Points) || checkCombinations(diagonal2Points);
    }

    private boolean checkCombinations(List<Point2D> points) {

        int chain = 0;

        for (Point2D point : points){
            int rowIndexForArray = (int) point.getX();
            int columnIndexForArray = (int) point.getY();

            Disc disc = getDiscIfPresent(rowIndexForArray,columnIndexForArray);

            if (disc != null && disc.isPlayerOneMove == isPlayerOneTurn){
                chain++;
                if(chain == 4)
                    return true;
            }else
                chain=0;
        }
        return false;
    }

    private Disc getDiscIfPresent(int row,int column){

        if (row>=ROWS || row<0 || column>=COLUMNS || column<0)
            return null;
        return insertedDiscArray[row][column];
    }

    private void gameOver() {
        String winner = isPlayerOneTurn ? Player_One : Player_Two;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect Four");
        alert.setHeaderText("Winner is: " + winner);
        alert.setContentText("Want to play again?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No, Exit");
        alert.getButtonTypes().setAll(yesBtn,noBtn);

        Platform.runLater(() -> {

            Optional<ButtonType> btnClicked = alert.showAndWait();
            if (btnClicked.isPresent() && btnClicked.get() == yesBtn){
                resetGame();
            }else {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public void resetGame() {

        insertedDiscPane.getChildren().clear();

        for (Disc[] discs : insertedDiscArray) {
            Arrays.fill(discs, null);
        }

        isPlayerOneTurn  = true;
        PlayerNameLable.setText(Player_One);

        createPlayGround();
    }


    private static class Disc extends Circle{

        private final boolean isPlayerOneMove;
        public Disc(boolean isPlayerOneMove){
            this.isPlayerOneMove = isPlayerOneMove;
            setRadius(Circle_Dia/2);
            setFill(isPlayerOneMove ? Color.valueOf(discColour1):Color.valueOf(discColour2));
            setCenterX(Circle_Dia/2);
            setCenterY(Circle_Dia/2);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}