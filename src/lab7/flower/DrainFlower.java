/*
 * Course: SWE2410
 * Fall 2024
 * Lab 7: The Garden in Action
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/23/24
 */
package lab7.flower;
import javafx.scene.control.Label;
import lab7.bee.BeeInterface;

import java.util.Random;

/**
 * DrainFlower class. Flowers with less predictable behavior
 */
public class DrainFlower implements FlowerInterface {
    private static final int MAX_NECTAR = 100;
    private static final int FIFTEEN = 15;
    private static final int RECHARGE_AMOUNT = 5;
    private final Random rand = new Random();
    private final Label energyLabel;
    private int nectarLevel;

    private double x;
    private double y;

    /**
     * Constructor for DrainFlower
     * @param x x coordinate
     * @param y y coordinate
     */
    public DrainFlower(double x, double y) {
        nectarLevel = MAX_NECTAR;
        this.x = x;
        this.y = y;
        energyLabel = new Label();
    }
    /**
     * handles when the flower interacts with a bee
     *
     * @param beeInterface bee to interact with
     */
    @Override
    public void interact(BeeInterface beeInterface) {
        if(nectarLevel < MAX_NECTAR) {
            int energy = rand.nextInt(FIFTEEN);
            beeInterface.gainEnergy(-energy);
            nectarLevel += energy % MAX_NECTAR;
        } else {
            beeInterface.gainEnergy(nectarLevel);
            nectarLevel = 0;
        }
    }

    /**
     * handles for when the flower isn't interacting
     */
    @Override
    public void recharge() {
        if(nectarLevel < MAX_NECTAR) {
            nectarLevel += RECHARGE_AMOUNT % MAX_NECTAR;
        }

    }

    /**
     * returns the x position of the flower on the pane
     *
     * @return double x position
     */
    @Override
    public double getXPosition() {
        return x;
    }

    /**
     * returns the y position of the flower on the pane
     *
     * @return double y position
     */
    @Override
    public double getYPosition() {
        return y;
    }

    /**
     * sets the y value for the flower if needed
     *
     * @param yPosition new y position
     */
    @Override
    public void setYPosition(double yPosition) {
        this.y = yPosition;
    }

    /**
     * sets the x value for the flower if needed
     *
     * @param xPosition new x position
     */
    @Override
    public void setXPosition(double xPosition) {
        this.x = xPosition;
    }

    /**
     * gets the current nectar level of the flower
     *
     * @return nectar level as int
     */
    @Override
    public int getNectar() {
        return nectarLevel;
    }

    /**
     * gets the nectar level of the flower to display
     *
     * @return Label containing the nectar of flower
     */
    @Override
    public Label getEnergyLabel() {
        energyLabel.setText(String.valueOf(nectarLevel));
        if(nectarLevel < MAX_NECTAR) {
            energyLabel.setStyle("-fx-text-fill: red;");
        } else {
            energyLabel.setStyle("-fx-text-fill: yellow;");
        }
        return energyLabel;
    }
}
