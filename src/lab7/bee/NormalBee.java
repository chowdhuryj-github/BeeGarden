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
 * class for Normal Bee
 */
public class NormalBee implements BeeInterface {

    private static final int TEN = 10;
    private static final int FIVE = 5;
    private boolean flowerCaptured;
    private double energyLevel;
    private double positionX;
    private double positionY;
    private FlowerInterface flower;

    private final Random random = new Random();

    private Pane theGarden = new Pane();

    /**
     * Main constructor for a NormalBee
     * @param x x position/coordinate
     * @param y y position/coordinate
     * @param pane pane to place visual elements on
     * @param energyLevel the energy level of the bee
     */
    public NormalBee(double x, double y, double energyLevel, Pane pane) {
        this.positionX = x;
        this.positionY = y;
        this.energyLevel = energyLevel;
        this.flowerCaptured = false;
        this.theGarden = pane;
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
     * method for bee moving to flower
     */
    @Override
    public void move() {

        energyLevel -= FIVE;

        double euclideanDistance = calcBeeDistance(getBeeXPosition(), getBeeYPosition());
        double beeSpeed = TEN;

        // direction from bee to flower
        double directionX = flower.getXPosition() - getBeeXPosition();
        double directionY = flower.getYPosition() - getBeeYPosition();

        // bee's X and Y locations

        // getting a unit vector
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        if(magnitude > 0) {
            double unitX = directionX / magnitude;
            double unitY = directionY / magnitude;

            // move bee in direction of flower
            positionX += unitX * beeSpeed;
            positionY += unitY * beeSpeed;

            if(euclideanDistance <= TEN) {
                flowerCaptured = true;
                positionX = flower.getXPosition() - TEN;
                positionY = flower.getYPosition()- TEN;

                flower.interact(this);
            }
        }

    }

    /**
     * method for calculating distance
     * @param beeX beeX
     * @param beeY beeY
     * @return distance
     */
    public double calcBeeDistance(double beeX, double beeY) {
        double differenceXSquared = (beeX - flower.getXPosition()) * (beeX - flower.getXPosition());
        double differenceYSquared = (beeY - flower.getYPosition()) * (beeY - flower.getYPosition());
        double sumOfSquared = differenceXSquared + differenceYSquared;
        return Math.sqrt(sumOfSquared);
    }

    /**
     * method for when bee comes into contact with flower
     * not implemented
     * @param energyAmount amount of energy added
     */
    public void gainEnergy(int energyAmount) {
        this.energyLevel = this.energyLevel + energyAmount;
    }

    /**
     * how it chooses a valid flower
     *
     * @param flowers collection of flowers
     */
    public void chooseFlower(List<FlowerInterface> flowers) {

        int flowerListLength = flowers.size();
        int randomNumber = random.nextInt(flowerListLength);
        flower = flowers.get(randomNumber);
        flowerCaptured = false;

    }


    public FlowerInterface getChosenFlower() {
        return flower;
    }


    /**
     * this is a method for returning the energy level
     * implemented!
     * @return energy level
     */
    public double getEnergyLevel() {
        return this.energyLevel;
    }


    /**
     * gets if target flower reached
     * not implemented
     * @return boolean
     */
    @Override
    public boolean getFlowerReached() {
        return flowerCaptured;
    }


    /**
     * requests the x position of the bee
     * implemented!
     * @return x position
     */
    @Override
    public double getBeeXPosition() {
        return this.positionX;
    }

    /**
     * sets the x position of the bee
     * implemented!
     * @param xPosition x coordinate/position
     */
    @Override
    public void setBeeXPosition(double xPosition) {
        this.positionX = xPosition;
    }

    /**
     * used to return the y position of the bee
     * implemented!
     * @return y position
     */
    @Override
    public double getBeeYPosition() {
        return this.positionY;
    }

    /**
     * used to set the y position of the normal bee
     * implemented!
     * @param yPosition yPosition
     */
    @Override
    public void setBeeYPosition(double yPosition) {
        this.positionY = yPosition;
    }


}
