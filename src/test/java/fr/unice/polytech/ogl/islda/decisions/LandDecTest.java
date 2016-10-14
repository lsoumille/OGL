package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.LandAns;
import fr.unice.polytech.ogl.islda.parameters.LandParameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nicolas HORY
 * @version 14/03/15.
 */
public class LandDecTest {
    private LandDec landDec;
    private String landAns;

    @Before
    public void setUp() {
        landDec = new LandDec(new LandParameters(null, 10));
        landAns = "{\n" +
                "    \"status\": \"OK\", \"cost\": 12\n" +
                "}";
    }

    @Test
    public void parseResults() {
        Answer answer = landDec.parseResults(landAns);
        Answer ans = new LandAns("OK", 12);
        Assert.assertEquals(answer, ans);
    }

    @Test
    public void testIsValid(){
        LandDec dec = new LandDec(new LandParameters("port",5));
        Assert.assertEquals(true, dec.isValid());

        dec = new LandDec(new LandParameters(null,5));
        Assert.assertEquals(false, dec.isValid());

        dec = new LandDec(new LandParameters("port",-1));
        Assert.assertEquals(false, dec.isValid());
    }

    @Test
    public void testEqualsAndHashcode() {
        LandParameters landParameters = new LandParameters("CREEK", 10);
        LandDec x = new LandDec(landParameters);
        LandDec y = new LandDec(landParameters);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
