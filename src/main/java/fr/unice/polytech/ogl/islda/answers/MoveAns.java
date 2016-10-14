package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.ExploreDec;
import fr.unice.polytech.ogl.islda.decisions.GlimpseDec;
import fr.unice.polytech.ogl.islda.map.Case;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import fr.unice.polytech.ogl.islda.utils.Utils;

import java.util.List;

/**
 * @author Lucas Martinez
 * @version 14/03/2015.
 */
public class MoveAns extends Answer {
    private Direction direction;

    public MoveAns(String status, int cost) {
        super(status, cost);
    }

    @Override
    public void init(Mapper map) {
        map.move(direction);
    }

    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        if(map.getCurrentCase().getResources() == null &&
                !map.getBiomesWithObj(getContext().getObjective(), map.getCurrentCase()).isEmpty()){
            decisionManager.addDecisionToQueue(new ExploreDec());
        }

        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        map.move(map.getCaseWithGoodBiomeNotVisited(getContext().getObjective()), decisionManager);

        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        for(Direction dir : map.getEmptyBiomesDirections()){
            if(!dir.equals(Direction.getOpposite(direction))){
                decisionManager.addDecisionToQueue(new GlimpseDec(dir,4));
            }
        }

        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        map.move(map.getCaseVisitedBiomeEdge(), decisionManager);

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

        MoveAns moveAns = (MoveAns) o;

        return getCost() == moveAns.getCost() && getStatus().equals(moveAns.getStatus());
    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
