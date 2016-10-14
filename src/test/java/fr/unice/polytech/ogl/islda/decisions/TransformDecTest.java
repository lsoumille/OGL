package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.parameters.TransformParameters;
import fr.unice.polytech.ogl.islda.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pascal Tung
 */
public class TransformDecTest {
    @Test
    public void testJson() {
        TransformDec dec = new TransformDec(new TransformParameters(new Objective("WOOD", 20)));
        String oracleJSON = "{\"parameters\":{\"WOOD\":20},\"action\":\"transform\"}";
        Utils.init();
        assertEquals(oracleJSON, dec.toJSON());
    }

    @Test
     public void testEqualsAndHashcode() {
        String wood = "WOOD";
        Objective res = new Objective(wood, 100);
        TransformParameters transformParameters = new TransformParameters(res);
        TransformDec x = new TransformDec(transformParameters);
        TransformDec y = new TransformDec(transformParameters);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }


}
