package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.answers.LandAns;
import fr.unice.polytech.ogl.islda.answers.MoveAns;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.parameters.LandParameters;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Lucas SOUMILLE
 * @version 18/04/15
 */
public class LaunchBotTest {
    Context context;
    LaunchBot bot;
    DecisionManager decisionManager;

    @Before
    public void setUp(){
        context = new Context();
        bot = new LaunchBot(context);
        decisionManager = new DecisionManager();
    }

    /**
     * Test of handle results method maxCost < answerCost
     */
    @Test
    public void testHandleResults() {
        LandAns oracleAns = new LandAns("OK", 12);
        Objective objective = new Objective("WOOD", 2);
        context.setCreek("port");
        context.setMen(2);
        context.setObjective(new ArrayList<>(Arrays.asList(objective)));
        bot.takeDecision(null);
        assertEquals(oracleAns, bot.handleResults("{\"status\":\"OK\",\"cost\":12}"));
        assertFalse(context.noMoreContract());
        assertEquals(-12, context.getBudget());
        assertEquals(12, context.getMaxCostAction());
        assertEquals(36, context.getMinimumBudget());
    }

    /**
     * Test of handle results method maxCost > answerCost
     */
    @Test
    public void testHandleResultsMaxCost() {
        LandAns oracleAns = new LandAns("OK", 12);
        Objective objective = new Objective("WOOD", 2);
        context.setCreek("port");
        context.setMen(2);
        context.setMaxCostAction(20);
        context.setObjective(new ArrayList<>(Arrays.asList(objective)));
        bot.takeDecision(null);
        assertEquals(oracleAns, bot.handleResults("{\"status\":\"OK\",\"cost\":12}"));
        assertEquals(-12, context.getBudget());
        assertEquals(20, context.getMaxCostAction());
        assertEquals(52, context.getMinimumBudget());
    }

    @Test
    public void testTakeDecisionNullAns(){
        String decision = bot.takeDecision(null);
        assertEquals(decisionManager.getStopJson(), decision);

        context.setCreek("depart");
        context.setBudget(1000);
        context.setMen(60);
        List<Objective> objectives = new ArrayList<>();
        objectives.add(new Objective("WOOD", 2));
        context.setObjective(objectives);
        decision = bot.takeDecision(null);
        assertEquals(decision, decisionManager.getLandJson(new LandParameters("depart", 10)));
    }

    @Test
    public void testTakeDecisionEnergy(){
        context.setBudget(50);
        String decision = bot.takeDecision(new MoveAns("OK",5));
        assertEquals(decisionManager.getStopJson(), decision);


        List<Objective> objectives = new ArrayList<>();
        objectives.add(new Objective("WOOD", 2));
        context.setObjective(objectives);
        decision = bot.takeDecision(new MoveAns("OK",50));
        assertEquals(decisionManager.getStopJson(), decision);
    }
}
