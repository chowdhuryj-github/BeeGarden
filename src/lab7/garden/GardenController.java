/*
 * Course: SWE2410
 * Fall 2024
 * Lab 7: The Garden in Action
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab7.garden;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lab7.bee.BeeInterface;
import lab7.bee.CrazyBee;
import lab7.bee.NormalBee;
import lab7.flower.DrainFlower;
import lab7.flower.FlowerInterface;
import lab7.flower.NormalFlower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller for the garden
 */
public class GardenController {

    private static final double FIFTY = 50.0;
    private static final double THREE_HUNDRED = 300.0;
    private static final double TWO_HUNDRED = 200.0;
    private static final double ONE_HUNDRED = 100.0;
    private static final double TEN = 10.0;
    private static final double POINT_FIVE = 0.5;
    private static final double ZERO_POINT_ZERO_ONE = 0.01;

    /**
     * pane for the bee image
     */
    private Pane beeImageBox;


    @FXML
    private Button addBee;

    @FXML
    private Button subtractBee;

    @FXML
    private Button addFLower;

    @FXML
    private Button subtractFlower;

    @FXML
    private Button subtractDrainFlower;

    @FXML
    private Button addDrainFlower;

    @FXML
    private Button subtractDiffBee;

    @FXML
    private Button addDiffBee;

    @FXML
    private ImageView flowerLegend;

    @FXML
    private ImageView beeLegend;

    @FXML
    private ImageView crazyBeeLegend;

    @FXML
    private ImageView drainFlowerLegend;
    // box containing bee, and it's label; NOT a good domain name!




    //Pane the flower images are displayed on
    private Pane flowerImageBox;


    //contains all flowers
    private final List<FlowerInterface> flowers = new ArrayList<>();
    private final List<BeeInterface> bees = new ArrayList<>();
    private final List<Pane> normalFlowerImagePanes = new ArrayList<>();
    private final List<Pane> drainFlowerImagePanes = new ArrayList<>();
    private final List<Pane> crazyBeeImagesPanes = new ArrayList<>();
    private final List<Pane> beeImagesPanes = new ArrayList<>();
    private final List<BeeInterface> crazyBees = new ArrayList<>();
    private final List<BeeInterface> normalBees = new ArrayList<>();


    private final Random rand = new Random();

    private final Image flowerImage = new Image("file:src/lab7/images/flower-1.jpg");
    private final Image drainFlowerImage = new Image("file:src/lab7/images/coneflower.jpg");
    private final Image normalBeeImage = new Image("file:src/lab7/images/bee-1.jpg");
    private final Image crazyBeeImage = new Image("file:src/lab7/images/bee-2.jpg");
    private final ImageView flowerImageView = new ImageView(flowerImage);

    private final ProgressBar beeProgressBar = new ProgressBar();
    private final ImageView beeImageView = new ImageView(normalBeeImage);


    @FXML
    private Pane theGarden;

    /**
     * sets up the application
     */
    @FXML
    public void initialize() {

        // ensuring that the key presses don't interfere with the button
        addDiffBee.setFocusTraversable(false);
        addBee.setFocusTraversable(false);
        addFLower.setFocusTraversable(false);
        addDrainFlower.setFocusTraversable(false);
        subtractBee.setFocusTraversable(false);
        subtractDiffBee.setFocusTraversable(false);
        subtractFlower.setFocusTraversable(false);
        subtractDrainFlower.setFocusTraversable(false);

        //used to create legend and set it
        setLegend();

        // loading up the bee on the JavaFX and then preserving the ratio and setting a width
        beeProgressBar.setProgress(1);
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

        // executed after scene is loaded but before any methods
        // for fun, set up a gradient background; could probably do in SceneBuilder as well
        // note the label has a Z index of 2, so it is drawn above the panel,
        // otherwise it may be displayed "under" the panel and not be visible
        theGarden.setStyle("-fx-background-color: linear-gradient(to bottom right, " +
                "derive(forestgreen, 20%), derive(forestgreen, -40%));");

        // load image from a file; the file needs to be in the top folder of the project
        ImageView flowerImage = new ImageView(new Image("file:src/lab7/images/flower-1.jpg"));

        // setting the width and preserve ratio of the flower image
        flowerImage.setPreserveRatio(true);
        flowerImage.setFitWidth(FIFTY);

        // you might make this an attribute of another
        // class, so you can update it
        flowerImageView.setFitWidth(FIFTY);
        flowerImageView.setPreserveRatio(true);

        // related to flowerLabel and beeLabel
        Label flowerLabel = new Label();
        beeLabel.setText("Some Bee");
        beeLabel.setStyle("-fx-text-fill: blue;");
        flowerLabel.setText("Some Flower");
        flowerLabel.setStyle("-fx-text-fill: yellow;");

        flowerImageBox = new VBox();

        NormalBee initialBee = new NormalBee(ONE_HUNDRED, TWO_HUNDRED, ONE_HUNDRED, theGarden);
        bees.add(initialBee);
        normalBees.add(initialBee);
        displayBee(initialBee);

        NormalFlower initialFlower = new NormalFlower(THREE_HUNDRED, THREE_HUNDRED);
        flowers.add(initialFlower);

        displayFlower(initialFlower);

        theGarden.setFocusTraversable(true); // ensure garden pane will receive key presses

    }

    /**
     * method used to set the images of the legend
     */
    private void setLegend() {
        beeLegend.setImage(normalBeeImage);
        flowerLegend.setImage(flowerImage);
        drainFlowerLegend.setImage(drainFlowerImage);
        crazyBeeLegend.setImage(crazyBeeImage);
    }


    /**
     * displays the bee at the beeXLocation & beeYLocation
     * and ensuring that the bees doesn't leave the garden
     */
    private void displayBee(BeeInterface bee) {
        int index = -1;
        Pane beeImageToRemove = null;

        // Remove the old VBox representing this bee from the pane
        if (bees.contains(bee) && normalBees.indexOf(bee) < beeImagesPanes.size()
                && bee instanceof NormalBee) {
            index = normalBees.indexOf(bee);

            // Get the corresponding VBox for this bee
            beeImageToRemove = beeImagesPanes.get(index);

            // Remove the old image of the bee from the pane
            theGarden.getChildren().remove(beeImageToRemove);
            beeImagesPanes.remove(beeImageToRemove); // Remove it from the list to update later

        } else if(bees.contains(bee) && crazyBees.indexOf(bee) < crazyBeeImagesPanes.size() &&
                bee instanceof CrazyBee) {
            index = crazyBees.indexOf(bee);
            beeImageToRemove = crazyBeeImagesPanes.get(index);
            theGarden.getChildren().remove(beeImageToRemove);
            crazyBeeImagesPanes.remove(beeImageToRemove);
        }

        VBox vBeeBox = new VBox();
        VBox vCrazyBox = new VBox();

        if (bee.getBeeXPosition() < 0) {
            bee.setBeeXPosition(0);
        } else if (theGarden.getWidth() > 0 && bee.getBeeXPosition() > theGarden.getWidth() - TEN) {
            // note: getWidth() is 0 when first load the scene,
            // so don't relocate the bee in that case
            bee.setBeeXPosition(theGarden.getWidth() - TEN);
        }
        if (bee.getBeeYPosition() < 0) {
            bee.setBeeYPosition(0);
        } else if (theGarden.getHeight() > 0 && bee.getBeeYPosition() >
                theGarden.getHeight() - TEN) {
            bee.setBeeYPosition(theGarden.getHeight() - TEN);
        }

        ImageView imageView = new ImageView();
        Label beeLabel = new Label("Bee");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(FIFTY);
        progressBar.setProgress((bee.getEnergyLevel() - POINT_FIVE) * ZERO_POINT_ZERO_ONE);


        Label crazyBeeLabel = new Label("Crazy Bee");


        //sets  bounds and coordinates
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(FIFTY);
        imageView.setX(bee.getBeeXPosition());
        imageView.setY(bee.getBeeYPosition());
        progressBar.setPrefHeight(TEN);

        //checks for what kind of flower it is
        if(bee instanceof NormalBee) {
            imageView.setImage(normalBeeImage);
            vBeeBox.getChildren().addAll(imageView, beeLabel, progressBar);
            vBeeBox.setLayoutX(bee.getBeeXPosition());
            vBeeBox.setLayoutY(bee.getBeeYPosition());

            if(index > 0) {
                beeImagesPanes.add(index, vBeeBox);
            } else {
                beeImagesPanes.add(vBeeBox);
            }

            theGarden.getChildren().addAll(vBeeBox);


        } else if (bee instanceof CrazyBee) {

            imageView.setImage(crazyBeeImage);
            vCrazyBox.getChildren().addAll(imageView, crazyBeeLabel, progressBar);
            vCrazyBox.setLayoutX(bee.getBeeXPosition());
            vCrazyBox.setLayoutY(bee.getBeeYPosition());

            if(index > 0) {
                crazyBeeImagesPanes.add(index, vCrazyBox);
            } else {
                crazyBeeImagesPanes.add(vCrazyBox);
            }

            theGarden.getChildren().addAll(vCrazyBox);

        }

        if(progressBar.getProgress() <= 0) {
            theGarden.getChildren().remove(vBeeBox);
        }

        if(progressBar.getProgress() <= 0) {
            theGarden.getChildren().remove(vCrazyBox);
        }

    }


    /**
     * Handles the javafx to display a new flower onto theGarden Pane
     * Modified by: galvanm
     * @param flower flower to display, NormalFlower or DrainFlower
     */
    private void displayFlower(FlowerInterface flower) {

        VBox vBox = new VBox();
        Pane pane = new Pane();
        Label flowerLabel = new Label();
        Label flowerEnergy = new Label();

        flowerEnergy.setText(String.valueOf(flower.getNectar()));
        if(flower.getNectar() < ONE_HUNDRED) {
            flowerEnergy.setStyle("-fx-text-fill: red;");
        } else {
            flowerEnergy.setStyle("-fx-text-fill: yellow;");
        }
        flowerLabel.setStyle("-fx-text-fill: yellow;");


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


        //sets  bounds and coordinates
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(FIFTY);
        imageView.setX(flower.getXPosition());
        imageView.setY(flower.getYPosition());

        vBox.setLayoutX(flower.getXPosition());
        vBox.setLayoutY(flower.getYPosition());

        pane.setPrefSize(FIFTY, FIFTY);

        //checks for what kind of flower it is
        if(flower instanceof NormalFlower) {
            imageView.setImage(flowerImage);

            flowerLabel.setText("Normal Flower");

            vBox.getChildren().addAll(imageView, flowerLabel,
                    flower.getEnergyLabel());

            normalFlowerImagePanes.add(vBox);

        } else {
            imageView.setImage(drainFlowerImage);

            flowerLabel.setText("Drain Flower");

            vBox.getChildren().addAll(imageView, flowerLabel, flower.getEnergyLabel());

            //pane.getChildren().add(vBox);
            drainFlowerImagePanes.add(vBox);
        }
        theGarden.getChildren().addAll(vBox);
    }

    /**
     * Action for when the right arrow key is pressed
     * @param keyEvent the key pressed
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        /*
         * For this method I was thinking that most of the actual movement can be fit into the
         * bee's move() method. Things like euclideanDistance can be moved in and
         * reference towards the bee's chosenFlower. For the small section that snaps the
         * bee to the flower, just has to update flowerReached.
         */
        if (keyEvent.getCode() == KeyCode.RIGHT) {

            for(BeeInterface bee: bees) {

                //checks if the bee's target flower has been removed or not
                if(!flowers.contains(bee.getChosenFlower())) {
                    bee.chooseFlower(flowers);
                }


                bee.move();

                if (bee.getFlowerReached()) {
                    bee.chooseFlower(flowers);
                }

                displayBee(bee);
            }

            for (FlowerInterface flower : flowers) {
                flower.recharge();
                displayFlower(flower);
            }

        }
    }

    /**
     * method for adding a bee
     * @param actionEvent actionEvent
     */
    public void onAddBee(MouseEvent actionEvent) {
        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] coordinates = checkCollision(x, y);
        x = coordinates[0];
        y = coordinates[1];

        if (actionEvent.getButton() == MouseButton.PRIMARY){
            NormalBee beeToAdd = new NormalBee(x, y, ONE_HUNDRED, theGarden);
            bees.add(beeToAdd);
            normalBees.add(beeToAdd);
            displayBee(bees.get(bees.size() - 1));

        }

    }


    /**
     * method for removing a bee
     * @param actionEvent actionEvent
     */
    public void onSubractBee(MouseEvent actionEvent) {

        if (actionEvent.getButton() == MouseButton.PRIMARY) {
            if(!bees.isEmpty()) {
                Pane beeImageToRemove = beeImagesPanes.get(beeImagesPanes.size() - 1);
                BeeInterface beeToRemove = bees.get(bees.size() - 1);

                beeImagesPanes.remove(beeImageToRemove);
                theGarden.getChildren().remove(beeImageToRemove);
                normalBees.remove(beeToRemove);
                bees.remove(beeToRemove);
            }
        }
    }

    /**
     * method for adding a crazy bee
     * @param mouseEvent mouseEvent
     */
    public void addDiffBee(MouseEvent mouseEvent) {

        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] coordinates = checkCollision(x, y);
        x = coordinates[0];
        y = coordinates[1];

        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            CrazyBee beeToAdd = new CrazyBee(x, y, theGarden);
            bees.add(beeToAdd);
            crazyBees.add(beeToAdd);
            displayBee(bees.get(bees.size() - 1));
        }

    }

    /**
     * method for removing a crazy bee
     * @param mouseEvent mouseEvent
     */
    public void subtractDiffBee(MouseEvent mouseEvent) {

        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            BeeInterface beeToRemove = null;
            boolean found = false;

            //checks the flowers to make sure that the most recent instance of a NormalFlower
            //is being removed, if found save the result and allow method to continue
            for(int i = bees.size() - 1; i >= 0; i--) {
                if(bees.get(i) instanceof CrazyBee && !found) {
                    beeToRemove = bees.get(i);
                    found = true;
                }
            }
            if(!bees.isEmpty() && found) {
                Pane beeImageToRemove = crazyBeeImagesPanes
                        .get(crazyBeeImagesPanes.size() - 1);

                crazyBeeImagesPanes.remove(beeImageToRemove);
                theGarden.getChildren().remove(beeImageToRemove);

                bees.remove(beeToRemove);
                crazyBees.remove(beeToRemove);
            }
        }

    }

    /**
     * Adds a flower to the theGarden pane. Also adds a NormalFlower object
     * into the flowers ArrayList.
     * Created by: galvanm
     * @param actionEvent MouseClicked
     */
    public void onAddFlower(MouseEvent actionEvent) {


        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] newCoordinates = checkCollision(x, y);

        x = newCoordinates[0];
        y = newCoordinates[1];
        //check if an image is present at the new coordinates, if not pick a new position

        if (actionEvent.getButton() == MouseButton.PRIMARY){
            NormalFlower newflower = new NormalFlower(x, y);
            flowers.add(newflower);
            displayFlower(flowers.get(flowers.size() - 1));
        }
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
                double beeX = i.getBeeXPosition();
                double beeY = i.getBeeYPosition();

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
     * Removes a NormalFlower image from theGarden pane. Also
     * removes a NormalFlower from the flowers ArrayList
     * Created by: galvanm
     * @param actionEvent MouseClicked
     */
    public void onSubtractFlower(MouseEvent actionEvent) {
        if(actionEvent.getButton() == MouseButton.PRIMARY) {
            FlowerInterface flowerToRemove = null;
            boolean found = false;
            //checks the flowers to make sure that the most recent instance of a NormalFlower
            //is being removed, if found save the result and allow method to continue
            for(int i = flowers.size() - 1; i >= 0; i--) {
                if(flowers.get(i) instanceof NormalFlower && !found) {
                    flowerToRemove = flowers.get(i);
                    found = true;
                }
            }

            if(!flowers.isEmpty() && found) {
                Pane flowerImageToRemove = normalFlowerImagePanes
                        .get(normalFlowerImagePanes.size() - 1);


                normalFlowerImagePanes.remove(flowerImageToRemove);
                theGarden.getChildren().remove(flowerImageToRemove);

                flowers.remove(flowerToRemove);
            }
        }
    }

    /**
     * Adds a DrainFlower to theGarden Pane. Also adds a DrainFlower
     * to the flowers ArrayList
     * Created by: galvanm
     * @param mouseEvent Mouse clicked
     */
    public void addDrainFlower(MouseEvent mouseEvent) {
        double x = rand.nextDouble(theGarden.getWidth() - FIFTY);
        double y = rand.nextDouble(theGarden.getHeight() - FIFTY);

        double[] newCoordinates = checkCollision(x, y);

        x = newCoordinates[0];
        y = newCoordinates[1];
        //check if an image is present at the new coordinates, if not pick a new position

        if (mouseEvent.getButton() == MouseButton.PRIMARY){

            flowers.add(new DrainFlower(x, y));
            displayFlower(flowers.get(flowers.size() - 1));
        }
    }

    /**
     * Removes a DrainFlower from theGarden Pane, also removes a
     * DrainFlower from the flowers Arraylist
     * Created by: galvanm
     * @param mouseEvent MouseClicked
     */
    public void subtractDrainFlower(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            FlowerInterface flowerToRemove = null;
            boolean found = false;
            //checks the flowers to make sure that the most recent instance of a NormalFlower
            //is being removed, if found save the result and allow method to continue
            for(int i = flowers.size() - 1; i >= 0; i--) {
                if(flowers.get(i) instanceof DrainFlower && !found) {
                    flowerToRemove = flowers.get(i);
                    found = true;
                }
            }
            if(!flowers.isEmpty() && found) {
                Pane flowerImageToRemove = drainFlowerImagePanes
                        .get(drainFlowerImagePanes.size() - 1);

                drainFlowerImagePanes.remove(flowerImageToRemove);
                theGarden.getChildren().remove(flowerImageToRemove);

                flowers.remove(flowerToRemove);
            }
        }
    }
}
