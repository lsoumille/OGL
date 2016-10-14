package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.StopAns;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas HORY
 * @version 13/03/15.
 */
public class StopDecTest {
    private StopDec stopDec;
    private String stopAns;

    @Before
    public void setUp() {
        stopDec = new StopDec();
        stopAns = "{\n" +
                "    \"status\": \"OK\", \"cost\": 35\n" +
                "}";
    }

    /**
     * Test the validity of the decision
     */
    @Test
    public void testIsValid() {
        assertTrue(stopDec.isValid());
    }

    @Test
    public void parseResults() {
        Answer answer = stopDec.parseResults(stopAns);
        Answer ans = new StopAns("OK", 35);
        Assert.assertEquals(answer, ans);
    }

    @Test
    public void testEqualsAndHashcode() {
        String wood = "WOOD";
        StopDec x = new StopDec();
        StopDec y = new StopDec();
        ExploitDec z = new ExploitDec(wood);
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
