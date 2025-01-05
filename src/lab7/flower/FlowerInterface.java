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
 * interface for the Flowers in the project
 */
public interface FlowerInterface {
    /**
     * handles when the flower interacts with a bee
     * @param beeInterface bee to interact with
     */
    void interact(BeeInterface beeInterface);

    /**
     * handles for when the flower isn't interacting with anything
     * and an action event on the right arrow key occurs
     */
    void recharge();

    /**
     * returns the x position of the flower on the pane
     * @return double x position
     */
    double getXPosition();

    /**
     * returns the y position of the flower on the pane
     * @return double y position
     */
    double getYPosition();

    /**
     * sets the y value for the flower if needed
     * @param yPosition new y position
     */
    void setYPosition(double yPosition);

    /**
     * sets the x value for the flower if needed
     * @param xPosition new x position
     */
    void setXPosition(double xPosition);

    /**
     * gets the current nectar level of the flower
     * @return nectar level as int
     */
    int getNectar();

    /**
     * gets the nectar level of the flower to display
     * @return Label containing the nectar of flower
     */
    Label getEnergyLabel();
}
