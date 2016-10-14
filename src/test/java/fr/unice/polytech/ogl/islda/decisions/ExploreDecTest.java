package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.ExploreAns;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas HORY
 * @version 13/03/15.
 */
public class ExploreDecTest {
    private ExploreDec exploreDec;
    private String stringAns;

    @Before
    public void setUp() {
        exploreDec = new ExploreDec();
        stringAns = "{\n" +
                "    \"status\": \"OK\", \"cost\": 39,\n" +
                "    \"extras\": {\n" +
                "        \"resources\": [\n" +
                "            {\"resource\": \"WOOD\", \"amount\": \"HIGH\", \"cond\": \"EASY\"},\n" +
                "            {\"resource\": \"FUR\", \"amount\": \"LOW\", \"cond\": \"FAIR\"},\n" +
                "            {\"resource\": \"FLOWER\", \"amount\": \"MEDIUM\", \"cond\": \"HARSH\"}\n" +
                "        ],\n" +
                "        \"pois\": [\n" +
                "            {\"kind\": \"CREEK\", \"id\": \"creek_identifier_2\"}\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }

    /**
     * Test the validity of the decision
     */
    @Test
    public void testIsValid() {
        assertTrue(exploreDec.isValid());
    }

    @Test
    public void parseResults() {
        Answer answer = exploreDec.parseResults(stringAns);

        ArrayList<Resource> ansRes = new ArrayList<Resource>();
        ansRes.add(new Resource("WOOD", "HIGH", "EASY"));
        ansRes.add(new Resource("FUR", "LOW", "FAIR"));
        ansRes.add(new Resource("FLOWER", "MEDIUM", "HARSH"));
        ArrayList<Pois> ansPois = new ArrayList<Pois>();
        ansPois.add(new Pois("CREEK", "creek_identifier_2"));
        ExploreAns ans = new ExploreAns("OK", 39, new ExploreExt(ansRes, ansPois));
        Assert.assertEquals(answer, ans);
    }

    @Test
    public void testEqualsAndHashcode() {
        ExploreDec x = new ExploreDec();
        ExploreDec y = new ExploreDec();
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
