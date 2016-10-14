package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.extras.Extras;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

/**
 * @author Lucas Soumille
 * @version 27/02/15
 */
public abstract class Answer {
    private String status;
    private int cost;
    private Context context;

    public Answer(String status, int cost) {
        this.status = status;
        this.cost = cost;
    }

    /**
     * Things to do before taking a decision
     *
     * @param map The map to move the current case or initialize case
     */
    public abstract void init(Mapper map);

    /**
     * Returns the most wise decision depending on the previous answer
     * @param decisionManager
     * @param map
     * @return
     */
    public abstract String nextDecision(DecisionManager decisionManager, Mapper map);

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Extras getExtras() {
        return null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
