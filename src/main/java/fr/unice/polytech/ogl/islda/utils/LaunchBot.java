package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.answers.Answer;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.parameters.LandParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lucas Soumille
 * @author Pascal Tung
 * @version 14/03/15
 */
public class LaunchBot {
    private Context context;
    private DecisionManager decisionManager;
    private Mapper map;

    public LaunchBot(Context context) {
        this.context = context;
        this.context.updatePrimaryRes();
        List<Pois> listPois = new ArrayList<>(Arrays.asList(new Pois("CREEK", context.getCreek())));
        Case currentCase = new Case();
        currentCase.setPois(listPois);
        currentCase.setVisited();
        map = new Mapper(currentCase);
        decisionManager = new DecisionManager();
    }

    /**
     * Method that determines what decision to take
     *
     * @param answer Answer of the previous decision
     * @return JSON of the new decision
     */
    public String takeDecision(Answer answer) {
        if (answer == null) {
            if (!context.isValid() && context.noMoreContract()) {
                decisionManager.clean();
                return decisionManager.getStopJson();
            }

            return decisionManager.getLandJson(new LandParameters(context.getCreek(), 1));
        }

        if (context.getBudget() <= context.getMinimumBudget() || context.noMoreContract()) {
            decisionManager.clean();
            return decisionManager.getStopJson();
        }

        return answer.nextDecision(decisionManager, map);
    }

    /**
     * Handle results by the decision manager
     *
     * @param results The response JSON of the island engine
     * @return The Answer which is the result of the response JSON
     */
    public Answer handleResults(String results) {
        Answer result = decisionManager.parseResults(results);
        result.setContext(context);
        context.updateBudget(result);
        result.init(map);

        return result;
    }
}
