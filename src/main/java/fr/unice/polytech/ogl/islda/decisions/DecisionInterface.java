package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.answers.Answer;

/**
 * @author Pascal Tung
 * @version 08/03/15
 */
public interface DecisionInterface {
    public String toJSON();

    public Answer parseResults(String results);
}
