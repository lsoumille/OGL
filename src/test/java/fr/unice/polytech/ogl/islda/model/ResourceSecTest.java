package fr.unice.polytech.ogl.islda.model;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author : Lucas SOUMILLE
 * @date : 09/05/15
 */
public class ResourceSecTest {

    @Test
    public void testEnumSecRes(){
        ResourceSecEnum obj = ResourceSecEnum.GLASS;
    }

    @Test
    public void testEqualsAndHashcode() {
        ResourceSec x = new ResourceSec("WOOD", 10);
        ResourceSec y = new ResourceSec("WOOD", 10);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
