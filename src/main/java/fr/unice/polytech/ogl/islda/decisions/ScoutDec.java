package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.ScoutAns;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Nicolas HORY
 * @version  03/03/15
 */
public class ScoutDec extends DecisionWithDirection {
    private static final String NOM_DEC = "scout";

    public ScoutDec(Direction direction) {
        super(NOM_DEC, direction);
    }

    @Override
    public Answer parseResults(String results) {
        ScoutAns answer = Utils.json.fromJson(results, ScoutAns.class);
        answer.setDirection(Direction.enumFor(parameters.getDirection()));

        return answer;
    }

    @Override
    public boolean isValid() {
        return parameters.getDirection() != null;
    }
}
