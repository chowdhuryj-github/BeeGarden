/*
 * Course: SWE2410
 * Fall 2024
 * Lab 6: Planting the Garden
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab6.bee;

/**
 * class for Normal Bee
 */
public class NormalBee implements BeeInterface {

    private static final int ENERGY_LEVEL = 100;
    private final int beeEnergyLevel;
    private final boolean isDead;
    private double positionBeeX;
    private double positionBeeY;

    /**
     * constructor for NormalBee
     * @param positionX positionX
     * @param positionY positionY
     */
    public NormalBee(double positionX, double positionY) {
        this.beeEnergyLevel = ENERGY_LEVEL;
        this.isDead = false;
        this.positionBeeX = positionX;
        this.positionBeeY = positionY;
    }

    @Override
    public void move() {

    }

    @Override
    public void gainEnergy(int energyAmount) {


    }

    /**
     * retrieve the x position of the bee
     * @return positionBeeX
     */
    @Override
    public double getXPosition() {
        return positionBeeX;
    }

    /**
     * retrieves the y position of the bee
     * @return positionBeeY
     */
    @Override
    public double getYPosition() {
        return positionBeeY;
    }


    /**
     * sets the y position value for the bee
     * @param positionY new y position
     */
    @Override
    public void setYPosition(double positionY) {
        positionBeeY = positionY;
    }

    /**
     * sets the x position value for the bee
     * @param positionX new x position
     */
    @Override
    public void setXPosition(double positionX) {
        positionBeeX = positionX;
    }




}
