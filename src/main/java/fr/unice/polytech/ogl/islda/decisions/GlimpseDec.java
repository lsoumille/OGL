package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.GlimpseAns;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.parameters.GlimpseParameters;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Nicolas HORY
 * @author Pascal Tung
 * @version 23/03/15.
 */
public class GlimpseDec extends Decision {
    private static final String NOM_DEC = "glimpse";
    private GlimpseParameters parameters;

    public GlimpseDec(Direction direction, int range) {
        super(NOM_DEC);
        parameters = new GlimpseParameters(direction.toString(), range);
    }

    @Override
    public Answer parseResults(String results) {
        GlimpseAns answer = Utils.json.fromJson(results, GlimpseAns.class);
        answer.setDirection(Direction.enumFor(parameters.getDirection()));

        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        GlimpseDec that = (GlimpseDec) o;

        return !(parameters != null ? !parameters.equals(that.parameters) : that.parameters != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isValid() {
        return parameters.getDirection() != null && parameters.getRange() >= 1 && 4 >= parameters.getRange();
    }

    public GlimpseParameters getParameters() {
        return parameters;
    }
}