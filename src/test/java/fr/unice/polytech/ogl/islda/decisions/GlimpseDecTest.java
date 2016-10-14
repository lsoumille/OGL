package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pascal Tung
 * @version 23/03/15
 */
public class GlimpseDecTest {
    /**
     * Deserialize JSON
     */
    @Test
    public void deserializeGlimpseDecision() {
        String decisionJSON = "{ \n" +
                "  \"action\": \"glimpse\", \n" +
                "  \"parameters\": { \"direction\": \"N\", \"range\": 2 }\n" +
                "}";
        GlimpseDec decisonFromJson = Utils.json.fromJson(decisionJSON, GlimpseDec.class);
        GlimpseDec decisonOracle = new GlimpseDec(Direction.NORTH, 2);

        Assert.assertEquals(decisonFromJson, decisonOracle);
    }

    /**
     * check if the function isValid works
     */
    @Test
    public void testIsValid(){
        GlimpseDec dec = new GlimpseDec(Direction.NORTH, 4);
        Assert.assertEquals(true, dec.isValid());

        dec = new GlimpseDec(Direction.NORTH,5);
        Assert.assertEquals(false, dec.isValid());
    }

    @Test
    public void testEqualsAndHashcode() {
        Direction dir = Direction.EAST;
        GlimpseDec x = new GlimpseDec(dir, 4);
        GlimpseDec y = new GlimpseDec(dir, 4);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
