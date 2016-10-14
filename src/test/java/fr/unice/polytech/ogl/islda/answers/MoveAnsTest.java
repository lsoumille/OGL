package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.map.*;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lucas Martinez
 * @version 14/03/2015.
 */
public class MoveAnsTest {
    Mapper map;
    MoveAns answer;
    DecisionManager decisionManager, decisionManager2;
    Case currentCase;
    Context context;
    Objective objective;
    List<Resource> listResources;

    @Before
    public void setUp() {
        currentCase = new Case();
        map = new Mapper(currentCase);
        context = new Context();
        objective = new Objective("WOOD", 10);
        context.setObjective(Arrays.asList(objective));
        listResources = new ArrayList<>(Arrays.asList(new Resource("WOOD", "", "")));
        answer = new MoveAns("OK", 30);
        answer.setDirection(Direction.NORTH);
        answer.setContext(context);
        decisionManager = new DecisionManager();
        decisionManager2 = new DecisionManager();

        Case WCase = new Case(new ArrayList<Resource>(), null, 20);
        WCase.setX(-1);
        Case SCase = new Case(listResources, null, 10);

        map.addCase(WCase);
        map.addCase(SCase);
    }

    @Test
    public void testInit(){
        Case oracleCase = new Case(listResources, null, 2);
        oracleCase.setY(1);
        map.addCase(oracleCase);

        answer.init(map);

        Assert.assertEquals(oracleCase , map.getCurrentCase());
    }

    /**
     * Explore decision if current case contains a biome that contains the objective
     */
    @Test
    public void testNextDecisionExplore() {
        //currentCase.setResources(listResources);
        currentCase.addBiome(new Biome(BiomeEnum.MANGROVE, 100.0));
        Mapper map = new Mapper(currentCase);
        String decision = answer.nextDecision(decisionManager, map);
        DecisionManager oracle = new DecisionManager();

        Assert.assertEquals(1, decisionManager.count());
        Assert.assertEquals(oracle.getExploreJson(), decision);
    }

    /**
     * Scout decisions of empty directions if current case doesn't contains objective
     */
    @Test
    public void testNextDecisionGlimpse() {
        Case NCase = new Case();
        NCase.addBiome(new Biome(BiomeEnum.GLACIER, 89.0));
        NCase.setY(1);

        Mapper map = new Mapper(currentCase);
        map.addCase(NCase);

        DecisionManager oracle = new DecisionManager();
        String oracleGlimpse = oracle.getGlimpseJson(Direction.WEST, 4);
        String decision = answer.nextDecision(decisionManager, map);

        Assert.assertEquals(oracleGlimpse, decision);
        Assert.assertEquals(2, decisionManager.count()); //only 2 because we know the N case, and the one we come from
    }


    /**
     * On bouge vers le nord dans une case ou il y a le bon biome, on explore
     * puis on move vers le nord dans une case uo il n'y a pas de biome, on glimpse
     */
    @Test
    public void testNextDecisionDoubleMove() {
        DecisionManager oracle = new DecisionManager();
        DecisionManager oracle2 = new DecisionManager();

        Case lastCase = new Case(new ArrayList<Resource>(), new ArrayList<Pois>(), 20);
        lastCase.setY(2);

        Case secondCase = new Case(null, new ArrayList<Pois>(), 1); //null necessary because of the condition "map.getCurrentCase().getResources() == null" in MoveAns
        secondCase.addBiome(new Biome(BiomeEnum.MANGROVE, 100.0));
        secondCase.setY(1);

        Case firstCase = new Case(new ArrayList<Resource>(), null, 0);

        String oracleExplore = oracle.getExploreJson();
        String oracleGlimpse = oracle2.getGlimpseJson(Direction.NORTH, 4);
        Mapper map = new Mapper(firstCase);
        map.addCase(secondCase);
        map.addCase(lastCase);

        Assert.assertEquals(firstCase, map.getCurrentCase());
        answer.init(map);
        String decision = answer.nextDecision(decisionManager, map);
        Assert.assertEquals(null, map.getCurrentCase().getResources());
        Assert.assertEquals(oracleExplore, decision);
        Assert.assertEquals(secondCase, map.getCurrentCase());
        decisionManager.clean(); // Ignore the first decision

        answer.init(map);
        String decision2 = answer.nextDecision(decisionManager, map);
        Assert.assertEquals(lastCase, map.getCurrentCase());
        Assert.assertEquals(3, decisionManager.count());
        Assert.assertEquals(oracleGlimpse, decision2);

    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        MoveAns x = new MoveAns(status, 10);
        MoveAns y = new MoveAns(status, 10);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

}
