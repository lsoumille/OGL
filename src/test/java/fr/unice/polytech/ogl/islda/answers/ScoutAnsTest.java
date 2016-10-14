package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.extras.ScoutExt;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.map.Point;
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
 * @author Pascal Tung
 * @version 14/03/15
 */
public class ScoutAnsTest {
    ScoutAns answer;
    ScoutAns answerWithNewAltitude;
    DecisionManager decisionManager;
    Case currentCase;
    Case goodCase;
    Objective objective;

    @Before
    public void setUp() {
        List<Resource> listResources = new ArrayList<>(Arrays.asList(new Resource("WOOD", "", "")));
        List<String> listStrResources = new ArrayList<>(Arrays.asList("WOOD"));

        answerWithNewAltitude = new ScoutAns("OK", 30, new ScoutExt(listStrResources,30));
        answerWithNewAltitude.setDirection(Direction.NORTH);
        objective = new Objective("WOOD", 10);
        decisionManager = new DecisionManager();

        currentCase = new Case();
        currentCase.setResources(listResources);

        goodCase = new Case(listResources, new ArrayList<Pois>(), 20);
        goodCase.setY(1);

        Context context = new Context();
        context.setObjective(new ArrayList<>(Arrays.asList(objective)));
        answer = new ScoutAns("OK", 30, new ScoutExt(listStrResources, 20));
        answer.setContext(context);
        answer.setDirection(Direction.NORTH);
    }

    /**
     * Test the coordinates of a new scouted case
     */
    @Test
    public void testCoordinatesNewCase() {
        Point origin = new Point();
        Mapper map = new Mapper(currentCase);
        Assert.assertEquals(origin, currentCase.getCoordinate());
        answer.setDirection(Direction.SOUTH);
        answer.init(map);

        Assert.assertTrue(map.getCaseAround(Direction.SOUTH).isKnown());

        Point oraclePSouth = new Point(0, -1);
        Assert.assertEquals(oraclePSouth, map.getCaseAround(Direction.SOUTH).getCoordinate());
    }

    @Test
    public void testNextDecisionUnreachable() {
        Mapper map = new Mapper(currentCase);
        ScoutExt extUnreach = new ScoutExt();
        extUnreach.setUnreachable();
        String decision = answer.nextDecision(decisionManager, map);

        Assert.assertEquals(decisionManager.count(), 1);
        Assert.assertEquals(decision, decisionManager.getMoveJson(Direction.SOUTH));
    }

    @Test
    public void testNextDecisionMoveInCaseWithObjective(){
        Mapper map = new Mapper(currentCase);
        Case badCase = new Case();
        String decision = answer.nextDecision(decisionManager, map);

        Assert.assertEquals(decision, decisionManager.getMoveJson(Direction.NORTH));
    }

    @Test
    public void testNextDecisionDefaultMove(){
        Case caseHighAlt = new Case();
        caseHighAlt.setAltitude(10);
        caseHighAlt.setY(1);
        Case caseLowAlt = new Case();
        caseLowAlt.setAltitude(1);
        caseLowAlt.setY(-1);
        Mapper map = new Mapper(currentCase);
        map.addCase(caseHighAlt);
        map.addCase(caseLowAlt);
        String decision = answer.nextDecision(decisionManager, map);

        Assert.assertEquals(decision, decisionManager.getMoveJson(Direction.SOUTH));
    }

    @Test
    public void testInitCaseAround() {
        Mapper map = new Mapper(currentCase);
        answer.init(map);
        Case testCase = map.getCaseAround(Direction.NORTH);
        Assert.assertEquals(goodCase, testCase);

        answerWithNewAltitude.init(map);
        Case test2Case = map.getCaseAround(Direction.NORTH);
        Assert.assertEquals(testCase,test2Case);
    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        List<String> listStrResources = new ArrayList<>(Arrays.asList("WOOD"));
        ScoutExt scoutExt = new ScoutExt(listStrResources, 10);
        ScoutAns x = new ScoutAns(status, 10, scoutExt);
        ScoutAns y = new ScoutAns(status, 10, scoutExt);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
