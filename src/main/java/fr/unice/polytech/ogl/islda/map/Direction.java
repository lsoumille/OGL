package fr.unice.polytech.ogl.islda.map;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas HORY
 * @version 03/03/15
 */
public enum Direction {
    NORTH("N"),
    WEST("W"),
    SOUTH("S"),
    EAST("E");

    private String name;

    /**
     * @param nameDir
     */
    private Direction (String nameDir) {
        this.name = nameDir;
    }
    
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the next direction in the enum
     * @param dir
     * @return
     */
    public static Direction next(Direction dir) {
        int dirInEnum = (dir.ordinal() + 1)%4;
        return Direction.values()[dirInEnum];
    }

    /**
     * returns the right and left directions
     * @param dir
     * @return
     */
    public static List<Direction> getLeftRight(Direction dir){
        List<Direction> dirs = new ArrayList<>();
        dirs.add(next(dir));
        dirs.add(next(next(next(dir))));
        return dirs;
    }

    /**
     * Get the direction value for the string argument
     *
     * @param strDirection Name of the direction
     * @return Direction element which match with the string parameter or null if not found
     */
    public static Direction enumFor(String strDirection) {
        for (Direction direction : Direction.values()) {
            if (direction.toString().equals(strDirection)) {
                return direction;
            }
        }

        return null;
    }

    /**
     * Get the opposite direction
     *
     * @return The opposite direction of the direction element
     */
    public static Direction getOpposite(Direction direction) {
        int index = direction.ordinal();
        return Direction.values()[(index+2)%4];
    }
}
