package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pascal Tung
 * @version 16/03/15
 */
public class LandAnsTest {
    DecisionManager decisionManager;
    Objective objective;

    @Before
    public void setUp() {
        objective = new Objective("WOOD", 10);
        decisionManager = new DecisionManager();
    }

    @Test
    public void testInitLandAns() {
        Mapper map = new Mapper(new Case());
        LandAns answer = new LandAns("OK", 30);
        answer.setContext(new Context());
        answer.init(map);
        assertEquals(0, answer.getContext().getMaxCostAction());
        assertEquals(30, answer.getContext().getMinimumBudget());
    }

    @Test
    public void nextDecisionTest() {
        LandAns answer = new LandAns("OK", 30);
        String decision = answer.nextDecision(decisionManager, new Mapper(new Case()));

        assertEquals(decision, decisionManager.getGlimpseJson(Direction.NORTH, 4));
        assertEquals(5, decisionManager.count());
    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        LandAns x = new LandAns(status, 10);
        LandAns y = new LandAns(status, 10);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
    }
}
