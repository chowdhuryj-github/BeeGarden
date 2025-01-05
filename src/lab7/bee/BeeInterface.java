/*
 * Course: SWE2410
 * Fall 2024
 * Lab 7: The Garden in Action
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab7.bee;

import lab7.flower.FlowerInterface;
import java.util.List;

/**
 * Bees interface
 */
public interface BeeInterface {


    /**
     * method for reducing energy
     * @param energyAmount energyAmount
     */
    void reduceEnergy(double energyAmount);

    /**
     * moving the bee
     */
    void move();

    /**
     * gaining energy
     * @param energyAmount amount of energy added
     */
    void gainEnergy(int energyAmount);

    /**
     * how it chooses a valid flower
     *
     * @param flowers collection of flowers
     */
    void chooseFlower(List<FlowerInterface> flowers);


    /**
     * method for choosing a flower
     * @return a flower interface
     */
    FlowerInterface getChosenFlower();


    /**
     * gets if target flower reached
     * @return boolean
     */
    boolean getFlowerReached();


    /**
     * requests the x position of the bee
     * @return x position
     */
    double getBeeXPosition();

    /**
     * sets the x position of the bee
     * @param xPosition x coordinate/position
     */
    void setBeeXPosition(double xPosition);

    /**
     * gets the y position of the bee
     * @return double y position
     */
    double getBeeYPosition();

    /**
     * sets the y position of the bee
     * @param yPosition yPosition
     */
    void setBeeYPosition(double yPosition);

    /**
     * method for returning the distance
     * @param beeX x position of bee
     * @param beeY y position of bee
     * @return the distance of the bee
     */
    double calcBeeDistance(double beeX, double beeY);

    /**
     * method for returning the initial energy level
     * @return energy level
     */
    double getEnergyLevel();

}
