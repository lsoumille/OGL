package fr.unice.polytech.ogl.islda.map;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pascal Tung
 * @version 04/04/15
 */
public class BiomeTest {
    @Test
    public void testGetListValue() {
        List<Biome> biomes = new ArrayList<>();
        Assert.assertEquals(new Double(0.0), Biome.getListBiomeValue(biomes));
        biomes.add(new Biome(BiomeEnum.OCEAN, 2.0));
        Assert.assertEquals(new Double(2.0), Biome.getListBiomeValue(biomes));
        biomes.add(new Biome(BiomeEnum.TROPICAL_RAIN_FOREST, 18.0));
        Assert.assertEquals(new Double(20.0), Biome.getListBiomeValue(biomes));
        biomes.add(new Biome(BiomeEnum.SHRUBLAND, null));
        Assert.assertEquals(new Double(20.0), Biome.getListBiomeValue(biomes));
    }

    @Test
    public void testEqualsAndHashcode() {
        Biome x = new Biome(BiomeEnum.ALPINE, 10.0);
        Biome y = new Biome(BiomeEnum.ALPINE, 10.0);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
