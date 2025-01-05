/*
 * Course: SWE2410
 * Fall 2024
 * Lab 6: Planting the Garden
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab6.flower;

import lab6.bee.BeeInterface;

/**
 * Dictates the state and behavior for a normal flower
 */
public class NormalFlower implements FlowerInterface {
    private static final int MAX_NECTAR = 100;
    private int nectarLevel;
    private double x;
    private double y;


    /**
     * Creates a normal flower
     * @param x position on the x-axis
     * @param y position on the y-axis
     */
    public NormalFlower(double x, double y){
        nectarLevel = MAX_NECTAR;
        this.x = x;
        this.y = y;
    }


    /**
     * allow the bee to gain energy upon interaction
     * @param beeInterface bee to interact with
     */
    @Override
    public void interact(BeeInterface beeInterface) {
        beeInterface.gainEnergy(Math.max(nectarLevel, 0));
    }


    /**
     * allow the flower to recharge
     */
    @Override
    public void recharge() {
        if(nectarLevel < MAX_NECTAR) {
            nectarLevel++;
        }
    }

    /**
     * gets the x position of the flower
     * @return the x position of the flower
     */
    @Override
    public double getXPosition() {
        return x;
    }


    /**
     * gets the y position of the flower
     * @return the y position of the flower
     */
    @Override
    public double getYPosition() {
        return y;
    }


    /**
     * sets the y value for the flower if needed
     * @param yPosition new y position
     */
    @Override
    public void setYPosition(double yPosition) {
        y = yPosition;
    }


    /**
     * sets the x value for the flower if needed
     * @param xPosition new x position
     */
    @Override
    public void setXPosition(double xPosition) {
        x = xPosition;
    }


}
