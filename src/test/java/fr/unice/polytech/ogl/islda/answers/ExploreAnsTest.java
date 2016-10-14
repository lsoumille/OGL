package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.ExploitDec;
import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.model.Resource;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lucas Martinez
 * @version 14/03/2015.
 */
public class ExploreAnsTest {
    Context context;
    Mapper map;
    List<Resource> listResources = new ArrayList<>(Arrays.asList(new Resource("WOOD", null, null)));
    Objective objective;
    ExploreExt extrasWood = new ExploreExt(listResources, null);
    ExploreExt extrasNoWood = new ExploreExt(null, null);
    DecisionManager dm;

    @Before
    public void setUp() {
        map = new Mapper(new Case());
        objective = new Objective("WOOD", 10);
        dm = new DecisionManager();
        context = new Context();
        context.setBudget(100);
        List<Objective> allObjective = new ArrayList<>();
        allObjective.add(objective);
        context.setObjective(allObjective);
    }

    /**
     * Check if the robot exploit the resource when it's available
     */
    @Test
    public void testNextDecisionWithWood() {
        ExploreAns answer = new ExploreAns("OK", 30, extrasWood);
        answer.setContext(context);
        answer.init(map);
        String decision = answer.nextDecision(dm, map);
        DecisionManager oracle = new DecisionManager();

        assertEquals(dm.count(), 1);
        assertEquals(decision, oracle.getExploitJson("WOOD"));
    }

    /**
     * Next decision
     * Current case with resource
     */
    @Test
    public void NDcurrentCaseOk() {
        assertTrue(dm.isEmpty());
        ExploreAns ans = new ExploreAns("OK", 20, extrasWood);
        ans.setContext(context);

        assertFalse(map.getCurrentCase().isKnown());
        ans.init(map);
        assertTrue(map.getCurrentCase().isKnown());

        Case oracleCurrentCase = new Case();
        oracleCurrentCase.setResources(listResources);
        oracleCurrentCase.setPois(null);

        assertTrue(dm.isEmpty());
        assertEquals(oracleCurrentCase, map.getCurrentCase());

        String oracleExploit = new ExploitDec("WOOD").toJSON();
        String nextDecision = ans.nextDecision(dm, map);
        assertEquals(oracleExploit, nextDecision);
    }

    /**
     * Check if the queue contains four glimpses
     */
    @Test
    public void testNextDecision() {
        ExploreAns answer = new ExploreAns("OK", 30, extrasNoWood);
        answer.setContext(context);
        String decision = answer.nextDecision(dm, map);
        DecisionManager oracle = new DecisionManager();

        assertEquals(4, dm.count());
        assertEquals(oracle.getGlimpseJson(Direction.NORTH, 4), decision);
    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        ExploreExt exploreExt = new ExploreExt(listResources, null);
        ExploreAns x = new ExploreAns(status, 10, exploreExt);
        ExploreAns y = new ExploreAns(status, 10, exploreExt);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
