package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.ExploreAns;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class ExploreDec extends Decision {
    private static final String NOM_DEC = "explore";

    public ExploreDec() {
        super(NOM_DEC);
    }

    @Override
    public Answer parseResults(String results) {
        return Utils.json.fromJson(results, ExploreAns.class);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
