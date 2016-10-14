package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.MoveAns;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.parameters.DirectionParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas HORY
 * @version 14/03/15.
 */
public class MoveDecTest {
    private MoveDec moveDec;
    private String moveAns;

    @Before
    public void setUp() {
        moveDec = new MoveDec(Direction.NORTH);
        moveAns = "{ \"status\": \"OK\", \"cost\": 21 }";
    }

    /**
     * Test the validity of the decision without direction
     */
    @Test
    public void testIsValid() {
        assertTrue(moveDec.isValid());
        moveDec.setParameters(new DirectionParameters(null));
        assertFalse(moveDec.isValid());
    }

    @Test
    public void parseResults() {
        Answer answer = moveDec.parseResults(moveAns);
        Answer ans = new MoveAns("OK", 21);
        Assert.assertEquals(answer, ans);
    }

    @Test
    public void testEqualsAndHashcode() {
        Direction dir = Direction.EAST;
        MoveDec x = new MoveDec(dir);
        MoveDec y = new MoveDec(dir);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
