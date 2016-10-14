package fr.unice.polytech.ogl.islda.model;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.map.Biome;
import fr.unice.polytech.ogl.islda.map.BiomeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas HORY
 * @version 02/04/15.
 */
public class ObjectiveTest {
    private Objective objective;

    @Before
    public void setUp() {
        objective = new Objective("WOOD", 2);
        objective.setResource("FISH");
    }

    @Test
    public void getAvailableBiomes() {
        List<Biome> biomes;
        biomes = objective.getAvailableBiomes();
        Biome lake = new Biome (BiomeEnum.LAKE, 30.0);
        Biome ocean = new Biome (BiomeEnum.OCEAN, 15.0);
        List<Biome> wantedBiomes = new ArrayList(Arrays.asList(ocean, lake));
        assertTrue(biomes.containsAll(wantedBiomes) && wantedBiomes.containsAll(biomes));
    }

    /**
     * Want 40 Plank and we have 10 Wood
     *  => we can finish the plank contract
     */
    @Test
    public void canFinishContract() {
        Objective objective = new Objective("PLANK", 40);
        objective.setLevel(2);

        Context context = new Context();
        Objective primaryObjective = new Objective("WOOD", 10);
        primaryObjective.setHarvestedAmount(40);
        context.setObjective(Arrays.asList(primaryObjective));

        assertTrue(objective.canFinishContract(context));
    }

    /**
     * Want 40 Plank and we have 1 Wood useless
     *  => we can transform
     */
    @Test
    public void canTransformUseless() {
        Objective objective = new Objective("PLANK", 40);
        objective.setLevel(2);

        Context context = new Context();
        Objective primaryObjective = new Objective("WOOD", 40);
        primaryObjective.setHarvestedAmount(40);
        context.setObjective(Arrays.asList(primaryObjective, objective));
        context.updatePrimaryRes();

        assertFalse(objective.canTransform(context));
        primaryObjective.setUseless(true);
        assertTrue(objective.canTransform(context));
    }

    /**
     * Want 40 Plank and we have too much wood
     *  => we can transform
     */
    @Test
    public void canTransformTooMuch() {
        Objective objective = new Objective("PLANK", 40);
        objective.setLevel(2);

        Context context = new Context();
        Objective primaryObjective = new Objective("WOOD", 10);
        primaryObjective.setHarvestedAmount(10);
        context.setObjective(Arrays.asList(primaryObjective, objective));
        context.updatePrimaryRes();

        assertFalse(objective.canTransform(context));
        primaryObjective.setHarvestedAmount(11);
        assertTrue(objective.canTransform(context));
    }

    @Test
    public void testEqualsAndHashcode() {
        Objective x = new Objective("WOOD", 10);
        Objective y = new Objective("WOOD", 10);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
