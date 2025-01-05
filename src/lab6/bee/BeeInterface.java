/*
 * Course: SWE2410
 * Fall 2024
 * Lab 6: Planting the Garden
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab6.bee;

/**
 * Bees interface
 */
public interface BeeInterface {

    /**
     * moving the bee
     */
    void move();

    /**
     * gaining energy
     * @param energyAmount amount of energy add
     */
    void gainEnergy(int energyAmount);

    /**
     * retrieves the x position of the bee
     * @return positionBeeX
     */
    double getXPosition();

    /**
     * retrieves the y position of the bee
     * @return positionBeeY
     */
    double getYPosition();

    /**
     * sets the y position value for the bee
     * @param yPosition new y position
     */
    void setYPosition(double yPosition);

    /**
     * sets the x position value for the bee
     * @param xPosition new x position
     */
    void setXPosition(double xPosition);

}
