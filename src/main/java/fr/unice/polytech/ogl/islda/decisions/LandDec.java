package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.LandAns;
import fr.unice.polytech.ogl.islda.parameters.LandParameters;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Lucas Soumille
 * @version 26/02/15
 */
public class LandDec extends Decision {
    private static final String NOM_DEC = "land";

    private LandParameters parameters;

    public LandDec(LandParameters parameters) {
        super(NOM_DEC);
        this.parameters = parameters;
    }

    public LandParameters getParameters() {
        return parameters;
    }

    public void setParameters(LandParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Answer parseResults(String results) {
        return Utils.json.fromJson(results, LandAns.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandDec)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        LandDec landDec = (LandDec) o;

        return parameters.equals(landDec.parameters);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isValid() {
        return parameters.getCreek() != null && parameters.getPeople() > 0;
    }
}
