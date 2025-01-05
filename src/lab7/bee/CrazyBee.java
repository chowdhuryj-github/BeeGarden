/*
 * Course: SWE2410
 * Fall 2024
 * Lab 7: The Garden in Action
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab7.bee;

import javafx.scene.layout.Pane;
import lab7.flower.FlowerInterface;

import java.util.List;
import java.util.Random;

/**
 * class for CrazyBee
 */
public class CrazyBee implements BeeInterface {

    private static final int HUNDRED = 100;
    private static final int TEN = 10;
    private static final int FIVE = 5;
    private static final int FIFTY = 50;

    private double energyLevel;
    private double positionX;
    private double positionY;

    private List<FlowerInterface> flowers;
    private final Random random = new Random();
    private FlowerInterface flower;
    private final Pane theGarden;

    private int direction = 1;

    /**
     * constructor for CrazyBee
     * @param x position x of bee
     * @param y position y of bee
     * @param pane the pane of the bee
     */
    public CrazyBee(double x, double y, Pane pane) {
        this.positionX = x;
        this.positionY = y;
        this.energyLevel = HUNDRED;
        theGarden = pane;
    }


    /**
     * method for reducing energy
     *
     * @param energyAmount energyAmount
     */
    @Override
    public void reduceEnergy(double energyAmount) {
        this.energyLevel = this.energyLevel - energyAmount;
    }

    /**
     * moving the bee
     */
    @Override
    public void move() {
        energyLevel -= FIVE;

        if(positionX + TEN > theGarden.getWidth() - FIFTY) {
            direction = 0;
        } else if(positionX - TEN < 0) {
            direction = 1;
        }

        // direction from bee to flower
        if(direction == 1) {
            positionX += TEN;
        } else {
            positionX -= TEN;
        }

        for(FlowerInterface flowerInterface : flowers) {
            flower = flowerInterface;
            if(calcBeeDistance(positionX, positionY) <= TEN) {
                flower.interact(this);
            }
        }
    }

    /**
     * gaining energy
     *
     * @param energyAmount amount of energy added
     */
    @Override
    public void gainEnergy(int energyAmount) {
        this.energyLevel = this.energyLevel + HUNDRED;
    }


    /**
     * requests the x position of the bee
     *
     * @return x position
     */
    @Override
    public double getBeeXPosition() {
        return this.positionX;
    }

    /**
     * sets the x position of the bee
     *
     * @param xPosition x coordinate/position
     */
    @Override
    public void setBeeXPosition(double xPosition) {
        this.positionX = xPosition;
    }

    /**
     * gets the y position of the bee
     *
     * @return double y position
     */
    @Override
    public double getBeeYPosition() {
        return this.positionY;
    }

    /**
     * sets the y position of the bee
     *
     * @param yPosition yPosition
     */
    @Override
    public void setBeeYPosition(double yPosition) {
        this.positionY = yPosition;
    }

    /**
     * method for returning the distance
     *
     * @param beeX x position of bee
     * @param beeY y position of bee
     * @return the distance
     */
    @Override
    public double calcBeeDistance(double beeX, double beeY) {
        double differenceXSquared = (beeX - flower.getXPosition()) * (beeX - flower.getXPosition());
        double differenceYSquared = (beeY - flower.getYPosition()) * (beeY - flower.getYPosition());
        double sumOfSquared = differenceXSquared + differenceYSquared;
        return Math.sqrt(sumOfSquared);
    }


    /**
     * method for returning the initial energy level
     *
     * @return energy level
     */
    @Override
    public double getEnergyLevel() {
        return this.energyLevel;
    }

    /**
     * how it chooses a valid flower
     *
     * @param flowers collection of flowers
     */
    @Override
    public void chooseFlower(List<FlowerInterface> flowers) {
        this.flowers = flowers;
    }

    /**
     * method for choosing a flower
     * @return a flower interface
     */
    @Override
    public FlowerInterface getChosenFlower() {
        return null;
    }

    /**
     * gets if target flower reached
     * @return boolean
     */
    @Override
    public boolean getFlowerReached() {
        return false;
    }

}
