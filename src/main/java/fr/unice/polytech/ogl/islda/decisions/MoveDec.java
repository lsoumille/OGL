package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.MoveAns;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class MoveDec extends DecisionWithDirection {
    private static final String NOM_DEC = "move_to";

    public MoveDec(Direction direction) {
        super(NOM_DEC, direction);
    }

    @Override
    public Answer parseResults(String results) {
        MoveAns answer = Utils.json.fromJson(results, MoveAns.class);
        answer.setDirection(Direction.enumFor(parameters.getDirection()));

        return answer;
    }

    @Override
    public boolean isValid() {
        return parameters.getDirection() != null;
    }
}
