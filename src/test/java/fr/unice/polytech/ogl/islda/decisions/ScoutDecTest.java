package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.ScoutAns;
import fr.unice.polytech.ogl.islda.extras.ScoutExt;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.parameters.DirectionParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Nicolas HORY
 * @version 13/03/15.
 */
public class ScoutDecTest {
    private ScoutDec scoutDec;
    private String scoutAns;

    @Before
    public void setUp() {
        scoutDec = new ScoutDec(Direction.NORTH);
        scoutAns = "{\n" +
                "    \"status\": \"OK\", \"cost\": 8,\n" +
                "    \"extras\": {\n" +
                "        \"resources\": [\"WOOD\", \"FUR\", \"FLOWER\"], \"altitude\": -23\n" +
                "    }\n" +
                "}";
    }

    /**
     * Test the validity of the decision without direction
     */
    @Test
    public void testIsValid() {
        assertTrue(scoutDec.isValid());
        scoutDec.setParameters(new DirectionParameters(null));
        assertFalse(scoutDec.isValid());
    }

    @Test
    public void parseResults() {
        Answer answer = scoutDec.parseResults(scoutAns);
        ArrayList<String> resources = new ArrayList<String>();
        resources.add("WOOD");
        resources.add("FUR");
        resources.add("FLOWER");
        ScoutAns ans = new ScoutAns("OK", 8, new ScoutExt(resources, -23));
        assertEquals(answer, ans);
    }

    @Test
    public void testEqualsAndHashcode() {
        Direction dir = Direction.EAST;
        ScoutDec x = new ScoutDec(dir);
        ScoutDec y = new ScoutDec(dir);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
