package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.answers.TransformAns;
import fr.unice.polytech.ogl.islda.parameters.TransformParameters;
import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Pascal Tung
 */
public class TransformDec extends Decision {
    private static final String NOM_DEC = "transform";
    private TransformParameters parameters;

    public TransformDec(TransformParameters parameters){
        super(NOM_DEC);
        this.parameters = parameters;
    }

    public TransformParameters getParameters() {
        return this.parameters;
    }

    @Override
    public Answer parseResults(String results) {
        return Utils.json.fromJson(results, TransformAns.class);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
