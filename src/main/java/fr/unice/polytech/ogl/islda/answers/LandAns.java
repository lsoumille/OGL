package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.GlimpseDec;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

/**
 * @author Lucas Martinez
 * @version 14/03/2015.
 */
public class LandAns extends Answer {

    public LandAns(String status, int cost) {
        super(status, cost);
    }

    /**
     * Init the minimum budget to quit (approximatly the cost of a land : piazza #102)
     * @param map The map to move the current case or initialize case
     */
    @Override
    public void init(Mapper map) {
        getContext().setMinimumBudget(getCost()+2*getContext().getMaxCostAction());
    }

    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        decisionManager.addDecisionToQueue(new GlimpseDec(Direction.NORTH, 4));
        decisionManager.addDecisionToQueue(new GlimpseDec(Direction.WEST, 4));
        decisionManager.addDecisionToQueue(new GlimpseDec(Direction.SOUTH, 4));
        decisionManager.addDecisionToQueue(new GlimpseDec(Direction.EAST, 4));

        return decisionManager.getFirstDecisionJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }

        LandAns landAns = (LandAns) o;

        return getCost() == landAns.getCost() && getStatus().equals(landAns.getStatus());
    }
}
