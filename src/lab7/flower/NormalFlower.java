/*
 * Course: SWE2410
 * Fall 2024
 * Lab 7: The Garden in Action
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab7.flower;


import javafx.scene.control.Label;
import lab7.bee.BeeInterface;

/**
 * Dictates the state and behavior for a normal flower
 */
public class NormalFlower implements FlowerInterface {
    private static final int MAX_NECTAR = 100;
    private static final int RECHARGE_AMOUNT = 5;
    private int nectarLevel;
    private double x;
    private double y;

    private final Label energyLabel;

    /**
     * Creates a normal flower
     * @param x position on the x-axis
     * @param y position on the y-axis
     */
    public NormalFlower(double x, double y){
        nectarLevel = MAX_NECTAR;
        this.x = x;
        this.y = y;
        energyLabel = new Label();
    }
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
    @Override
    public void interact(BeeInterface beeInterface) {
        beeInterface.gainEnergy(Math.max(nectarLevel, 0));
        //shouldn't reset every time
        nectarLevel = 0;
    }

    @Override
    public void recharge() {
        if(nectarLevel < MAX_NECTAR) {
            nectarLevel += RECHARGE_AMOUNT;
        }
    }

    @Override
    public double getXPosition() {
        return x;
    }

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
        y = yPosition;
    }

    /**
     * sets the x value for the flower if needed
     *
     * @param xPosition new x position
     */
    @Override
    public void setXPosition(double xPosition) {
        x = xPosition;
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


}
