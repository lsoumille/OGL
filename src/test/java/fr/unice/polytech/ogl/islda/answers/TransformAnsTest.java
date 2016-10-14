package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.decisions.TransformDec;
import fr.unice.polytech.ogl.islda.extras.TransformExt;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.parameters.TransformParameters;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Pascal Tung
 */
public class TransformAnsTest {
    @Test
    public void deserialize() {
        TransformDec dec = new TransformDec(new TransformParameters(new Objective("WOOD", 1)));
        TransformAns oracle = new TransformAns("OK", 12, new TransformExt("PLANK", 4));

        Answer ans = dec.parseResults("{\"status\":\"OK\", cost:12, \"extras\":{\"kind\":\"PLANK\", \"production\":4}}");
        assertEquals(oracle, ans);
    }

    @Test
    public void init() {
        String ressource = "PLANK";
        Context context = new Context();
        Objective objective = new Objective(ressource, 10);
        context.setObjective(Arrays.asList(objective));

        TransformExt extras = new TransformExt(ressource, 3);
        TransformAns answer = new TransformAns("OK", 12, extras);
        answer.setContext(context);
        answer.init(new Mapper(new Case()));
        Assert.assertEquals(3, context.getObjective(ressource).getHarvestedAmount());
    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        TransformExt transformExt = new TransformExt("", 10);
        TransformAns x = new TransformAns(status, 10, transformExt);
        TransformAns y = new TransformAns(status, 10, transformExt);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}

