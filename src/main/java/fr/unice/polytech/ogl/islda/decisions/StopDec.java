package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.StopAns;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Lucas Soumille
 * @version 23/02/15
 */
public class StopDec extends Decision {
    private static final String NOM_DEC = "stop";

    public StopDec(){
        super(NOM_DEC);
    }

    @Override
    public Answer parseResults(String results) {
        return Utils.json.fromJson(results, StopAns.class);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
