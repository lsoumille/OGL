package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.answers.ExploreAns;
import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pascal Tung
 * @version 16/03/15
 */
public class DecisionManagerTest {
    DecisionManager decisionManager;

    @Before
    public void setUp() {
        decisionManager = new DecisionManager();
    }

    /**
     * Test parse results with and without a last decision
     */
    @Test
    public void testParseResults() {
        String exploreAns = "{\"status\":\"OK\",\"cost\":39,\"extras\":{\"resources\":[{\"resource\":\"WOOD\",\"amount\":\"HIGH\",\"cond\":\"EASY\"}],\"pois\":[{\"kind\":\"CREEK\",\"id\":\"creek_identifier_2\"}]}}";
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new Resource("WOOD", "HIGH", "EASY"));
        List<Pois> poisList = new ArrayList<>();
        poisList.add(new Pois("CREEK", "creek_identifier_2"));
        ExploreExt oracleExt = new ExploreExt(resourceList, poisList);
        ExploreAns oracle = new ExploreAns("OK", 39, oracleExt);

        assertEquals(0, decisionManager.count());
        assertEquals(null, decisionManager.parseResults(""));
        decisionManager.getExploreJson();
        assertEquals(1, decisionManager.count());
        assertEquals(oracle, decisionManager.parseResults(exploreAns));
    }

    @Test
    public void testCleanQueue() {
        decisionManager.getExploreJson();
        assertEquals(1, decisionManager.count());
        decisionManager.clean();
        assertEquals(0, decisionManager.count());
    }

    @Test
    public void stopJsonTest() {
        String stopJson = "{\"action\":\"stop\"}";
        assertEquals(stopJson, decisionManager.getStopJson());
    }

    @Test
    public void emptyTest() {
        assertTrue(decisionManager.isEmpty());
        assertEquals(new StopDec().toJSON(), decisionManager.getFirstDecisionJson());
        decisionManager.addDecisionToQueue(new StopDec());
        decisionManager.getFirstDecisionJson();
        assertFalse(decisionManager.isEmpty());
    }
}
