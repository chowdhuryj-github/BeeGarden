/*
 * Course: SWE2410
 * Fall 2024
 * Lab 6: Planting the Garden
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab6.garden;


import javafx.scene.control.ProgressBar;
import lab6.bee.BeeInterface;
import lab6.bee.NormalBee;
import lab6.flower.FlowerInterface;
import lab6.flower.NormalFlower;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller for the garden
 */
public class GardenController {



    // defining the constants
    private static final double FIFTY = 50.0;
    private static final double TWO_HUNDRED = 200.0;
    private static final double ONE_HUNDRED = 100.0;
    private static final double TEN = 10.0;
    private static final double POINT_FIVE = 0.05;
    private static final double ONE = 1;

    private final ProgressBar beeProgressBar = new ProgressBar();

    /**
     * button for adding a bee
     */
    @FXML
    private Button addBee;

    /**
     * button for removing a bee
     */
    @FXML
    private Button subtractBee;

    /**
     * button for adding a flower
     */
    @FXML
    private Button addFLower;

    /**
     * button for removing a flower
     */
    @FXML
    private Button subtractFlower;


    // all JavaFX ImageViews
    @FXML
    private ImageView beeLegend;

    @FXML
    private ImageView flowerLegend;




    // Pane for Bees and its location
    private Pane beeImageBox;
    private double beeXLocation;
    private double beeYLocation;



    // Pane for Flowers and its location
    private Pane flowerImageBox;
    private double flowerXLocation;
    private double flowerYLocation;

    // measuring distance travelled by the Bee
    private final double distanceTravelled = 0;

    // list of flowers, bees, and panes
    private final List<BeeInterface> bees = new ArrayList<>();
    private final List<Pane> beeImagesPanes = new ArrayList<>();
    private final List<FlowerInterface> flowers = new ArrayList<>();
    private final List<Pane> flowerImagePanes = new ArrayList<>();

    // for generating random number
    private final Random rand = new Random();

    // for checking if flower has been taken over
    private boolean flowerCaptured = false;

    // list of Images
    private final Image flowerImage = new Image("file:src/lab6/images/flower-1.jpg");
    private final Image normalBeeImage = new Image("file:src/lab6/images/bee-1.jpg");

    // list of ImageViews
    private final ImageView beeImageView = new ImageView(normalBeeImage);
    private final ImageView flowerImageView = new ImageView(flowerImage);




    @FXML
    private Pane theGarden;



    /**
     * sets up the application
     */
    @FXML
    public void initialize() {

        addBee.setFocusTraversable(false);
        addFLower.setFocusTraversable(false);
        subtractBee.setFocusTraversable(false);
        subtractFlower.setFocusTraversable(false);

        // used to create the legend and set it
        setLegend();

        /*
         executed after scene is loaded but before any methods
         for fun, set up a gradient background; could probably do in SceneBuilder as well
         note the label has a Z index of 2, so it is drawn above the panel,
         otherwise it may be displayed "under" the panel and not be visible
         */
        theGarden.setStyle("-fx-background-color: linear-gradient(to bottom right, " +
                "derive(forestgreen, 20%), derive(forestgreen, -40%));");

        // loading up the bee on the JavaFX and then preserving the ratio and setting a width
        beeProgressBar.setProgress(ONE);
        beeProgressBar.setPrefHeight(TEN);
        beeImageView.setPreserveRatio(true);
        beeImageView.setFitWidth(FIFTY);

        // creating a label for the bee, and then setting the text and color
        Label beeLabel = new Label();
        beeLabel.setText("Bee 1");
        beeLabel.setStyle("-fx-text-fill: blue;");

        // creating a VBox which include the image of the bee and label
        beeImageBox = new VBox();
        beeImageBox.getChildren().addAll(beeImageView, beeLabel, beeProgressBar);

        // loading up the flower on the JavaFX, and then preserving the ratio and width
        flowerImageView.setPreserveRatio(true);
        flowerImageView.setFitWidth(FIFTY);

        // creating a label for the flower, and then setting the text and color
        Label flowerLabel = new Label();
        flowerLabel.setText("Flower 1");
        flowerLabel.setStyle("-fx-text-fill: yellow;");

        // creating a VBox for the flower, and then adding the image and the label
        flowerImageBox = new VBox();
        flowerImageBox.getChildren().addAll(flowerImageView, flowerLabel);

        // here, we set the X and Y locations of the Bee
        beeImageBox.setLayoutX(setBeeX());
        beeImageBox.setLayoutY(setBeeY());
        flowerImageBox.setLayoutX(setFlowerX());
        flowerImageBox.setLayoutY(setFlowerY());

        // here, we add the bee Pane to the Garden Pane
        theGarden.getChildren().addAll(beeImageBox, flowerImageBox);

        // here, we allow the garden pane to be able to receive key presses
        theGarden.setFocusTraversable(true);

    }

    /**
     * Action for when the right arrow key is pressed
     * @param keyEvent the key pressed
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        double beeSpeed = TEN;

        if (keyEvent.getCode() == KeyCode.RIGHT && !flowerCaptured) {

            double currentHealth = beeProgressBar.getProgress();
            beeProgressBar.setProgress(currentHealth - POINT_FIVE);

            if(beeProgressBar.getProgress() <= 0) {
                theGarden.getChildren().remove(beeImageBox);
            }

            // direction from bee to flower
            double directionX = getFlowerX() - getBeeX();
            double directionY = getFlowerY() - getBeeY();

            // getting a unit vector
            double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);


            if(magnitude > 0) {
                double unitX = directionX / magnitude;
                double unitY = directionY / magnitude;

                // move bee in direction of flower
                beeXLocation += unitX * beeSpeed;
                beeYLocation += unitY * beeSpeed;

                // update the position of the bee
                beeImageBox.setLayoutX(beeXLocation);
                beeImageBox.setLayoutY(beeYLocation);

                double euclideanDistance = calcDistance(getBeeX(), getBeeY(), getFlowerX(),
                        getFlowerY());


                if(euclideanDistance - distanceTravelled <= TEN) {
                    beeXLocation = getFlowerX() - TEN;
                    beeYLocation = getFlowerY() - TEN;
                    flowerCaptured = true;

                    beeImageBox.setLayoutX(beeXLocation);
                    beeImageBox.setLayoutY(beeYLocation);

                }
            }
        }
    }


    /**
     * method for calculating distance
     * @param beeX beeX
     * @param beeY beeY
     * @param flowerX flowerX
     * @param flowerY flowerY
     * @return distance
     */
    public double calcDistance(double beeX, double beeY, double flowerX, double flowerY) {
        double differenceXSquared = (beeX - flowerX) * (beeX - flowerX);
        double differenceYSquared = (beeY - flowerY) * (beeY - flowerY);
        double sumOfSquared = differenceXSquared + differenceYSquared;
        return Math.sqrt(sumOfSquared);
    }



    /**
     * method used to set the images of the legend
     */
    private void setLegend() {
        beeLegend.setImage(normalBeeImage);
        flowerLegend.setImage(flowerImage);
    }


    /**
     * setter method for x location of flower
     * @return x location of flower
     */
    private double setFlowerX() {
        flowerXLocation = TWO_HUNDRED;
        return flowerXLocation;
    }

    /**
     * setter method for y location of flower
     * @return y location of flower
     */
    private double setFlowerY() {
        flowerYLocation = TWO_HUNDRED;
        return flowerYLocation;
    }

    /**
     * getter method for x location of flower
     * @return x location of flower
     */
    private double getFlowerX() {
        return flowerXLocation;
    }

    /**
     * getter method for y location of flower
     */
    private double getFlowerY() {
        return flowerYLocation;
    }

    /**
     * setter method for x position of bee
     */
    private double setBeeX() {
        beeXLocation = ONE_HUNDRED;
        return beeXLocation;
    }

    /**
     * setter method for y position of bee
     * @return y location of bee
     */
    private double setBeeY() {
        beeYLocation = ONE_HUNDRED;
        return beeYLocation;
    }

    /**
     * getter method for x location of bee
     * @return x location of bee
     */
    private double getBeeX() {
        return beeXLocation;
    }

    /**
     * getter method for y location of bee
     * @return y location of bee
     */
    private double getBeeY() {
        return beeYLocation;
    }

    /**
     * loops through the flowers to check for collisions.
     * Created by: galvanm
     * @param x x coordinate
     * @param y y coordinate
     * @return an array containing the new x and y coordinates
     */
    private double[] checkCollision(double x, double y) {


        boolean collision = true;

        while (collision) {
            collision = false;

            for(FlowerInterface i : flowers) {
                double flowerX = i.getXPosition();
                double flowerY = i.getYPosition();

                double distance = calcDistance(x, y, flowerX, flowerY);

                if (distance <= FIFTY) {
                    x = rand.nextDouble(theGarden.getWidth() - FIFTY);
                    y = rand.nextDouble(theGarden.getHeight() - FIFTY);
                    collision = true;
                    break;
                }
            }
            for(BeeInterface i : bees) {
                double beeX = i.getXPosition();
                double beeY = i.getYPosition();

                double distance = calcDistance(x, y, beeX, beeY);

                if (distance <= FIFTY) {
                    x = rand.nextDouble(theGarden.getWidth() - FIFTY);
                    y = rand.nextDouble(theGarden.getHeight() - FIFTY);
                    collision = true;
                    break;
                }
            }

        }

        return new double[] {x, y};
    }


    /**
     * displays the bee at the beeXLocation & beeYLocation
     * and ensuring that the bees doesn't leave the garden
     */
    private void displayBee(BeeInterface bee) {

        VBox vBox = new VBox();

        if (bee.getXPosition() < 0) {
            bee.setXPosition(0);
        } else if (theGarden.getWidth() > 0 && bee.getXPosition() > theGarden.getWidth() - TEN) {
            // note: getWidth() is 0 when first load the scene,
            // so don't relocate the bee in that case
            bee.setXPosition(theGarden.getWidth() - TEN);
        }
        if (bee.getYPosition() < 0) {
            bee.setYPosition(0);
        } else if (theGarden.getHeight() > 0 && bee.getYPosition() >
                theGarden.getHeight() - TEN) {
            bee.setYPosition(theGarden.getHeight() - TEN);
        }

        ImageView imageView = new ImageView();
        Label beeLabel = new Label("Bee");
        ProgressBar progressBar = new ProgressBar(1);

        //sets  bounds and coordinates
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(FIFTY);
        imageView.setX(bee.getXPosition());
        imageView.setY(bee.getYPosition());
        progressBar.setPrefHeight(TEN);

        //checks for what kind of flower it is
        if(bee instanceof NormalBee) {
            imageView.setImage(normalBeeImage);
            vBox.getChildren().addAll(imageView, beeLabel, progressBar);
            vBox.setLayoutX(bee.getXPosition());
            vBox.setLayoutY(bee.getYPosition());
        }

        theGarden.getChildren().addAll(vBox);
        beeImagesPanes.add(vBox);
    }


    /**
     * adding the bee
     * @param mouseClick mouseClick
     */
    public void onAddBee(MouseEvent mouseClick) {

        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] coordinates = checkCollision(x, y);

        if (mouseClick.getButton() == MouseButton.PRIMARY){
            bees.add(new NormalBee(coordinates[0], coordinates[1]));
            displayBee(bees.get(bees.size() - 1));
        }

    }

    /**
     * removing the bee
     * @param mouseEvent mouseClick
     */
    public void onSubtractBee(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if(!bees.isEmpty()) {
                Pane beeImageToRemove = beeImagesPanes.get(beeImagesPanes.size() - 1);
                BeeInterface beeToRemove = bees.get(bees.size() - 1);

                beeImagesPanes.remove(beeImageToRemove);
                theGarden.getChildren().remove(beeImageToRemove);

                bees.remove(beeToRemove);
            }
        }
    }


    /**
     * this is a method for displaying a flower
     * @param flower flower
     */
    private void displayFlower(FlowerInterface flower) {

        VBox vBox = new VBox();
        Pane pane = new Pane();

        if (flower.getXPosition() < 0) {
            flower.setXPosition(0);
        } else if (theGarden.getWidth() > 0 && flower.getXPosition() > theGarden.getWidth() - TEN) {
            // note: getWidth() is 0 when first load the scene,
            // so don't relocate the bee in that case
            flower.setXPosition(theGarden.getWidth() - TEN);
        }
        if (flower.getYPosition() < 0) {
            flower.setYPosition(0);
        } else if (theGarden.getHeight() > 0 && flower.getYPosition() >
                theGarden.getHeight() - TEN) {
            flower.setYPosition(theGarden.getHeight() - TEN);
        }

        ImageView imageView = new ImageView();
        Label flowerLabel = new Label("Flower");

        //sets  bounds and coordinates
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(FIFTY);
        imageView.setX(flower.getXPosition());
        imageView.setY(flower.getYPosition());

        //checks for what kind of flower it is
        if(flower instanceof NormalFlower) {
            imageView.setImage(flowerImage);
            vBox.getChildren().addAll(imageView, flowerLabel);
            vBox.setLayoutX(flower.getXPosition());
            vBox.setLayoutY(flower.getYPosition());
        }
        pane.getChildren().add(vBox);
        theGarden.getChildren().addAll(pane);
        flowerImagePanes.add(pane);

    }

    /**
     * method for adding a flower
     * @param actionEvent actionEvent
     */
    public void onAddFlower(MouseEvent actionEvent) {

        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] coordinates = checkCollision(x, y);

        if (actionEvent.getButton() == MouseButton.PRIMARY){
            flowers.add(new NormalFlower(coordinates[0], coordinates[1]));
            displayFlower(flowers.get(flowers.size() - 1));
        }
    }

    /**
     * subtracting a flower from the application
     * @param actionEvent actionEvent
     */
    public void onSubtractFlower(MouseEvent actionEvent) {
        if (actionEvent.getButton() == MouseButton.PRIMARY) {
            if(!flowers.isEmpty()) {
                Pane flowerImageToRemove = flowerImagePanes.get(flowerImagePanes.size() - 1);
                FlowerInterface flowerToRemove = flowers.get(flowers.size() - 1);

                flowerImagePanes.remove(flowerImageToRemove);
                theGarden.getChildren().remove(flowerImageToRemove);

                flowers.remove(flowerToRemove);
            }
        }
    }



}
