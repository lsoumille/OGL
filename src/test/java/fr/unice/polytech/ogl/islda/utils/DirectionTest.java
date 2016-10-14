package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.map.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Lucas SOUMILLE
 * @version 08/05/15
 */
public class DirectionTest {
    /**
     * Get the enum object from the string
     */
    @Test
    public void testEnumFor() {
        assertEquals(null, Direction.enumFor("nord"));
        assertEquals(Direction.NORTH, Direction.enumFor("N"));
        assertEquals(Direction.WEST, Direction.enumFor("W"));
        assertEquals(Direction.SOUTH, Direction.enumFor("S"));
        assertEquals(Direction.EAST, Direction.enumFor("E"));
    }


    /**
     * check if the next direction is the good one
     */
    @Test
    public void testNext(){
        Direction east = Direction.EAST;
        Direction oracle = Direction.NORTH;

        assertEquals(oracle, Direction.next(east));
    }

    /**
     * check if the opposite direction is the good one
     */
    @Test
    public void testOpposite(){
        Direction north = Direction.NORTH;
        Direction oracle = Direction.SOUTH;

        assertEquals(oracle, Direction.getOpposite(north));
    }

    @Test
    public void testLeftRight(){
        Direction north = Direction.NORTH;
        Direction oracleW = Direction.WEST;
        Direction oracleE = Direction.EAST;

        assertEquals(oracleW, Direction.getLeftRight(north).get(0));
        assertEquals(oracleE, Direction.getLeftRight(north).get(1));
    }
}
