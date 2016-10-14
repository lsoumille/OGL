package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

/**
 * @author Lucas Martinez
 * @version 14/03/2015.
 */
public class StopAns extends Answer {
    public StopAns(String status, int cost) {
        super(status, cost);
    }

    /**
     * Is supposed to do nothing
     *
     * @param map The map to move the current case or initialize case
     */
    @Override
    public void init(Mapper map) {
    }

    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }

        StopAns stopAns = (StopAns) o;

        return getCost() == stopAns.getCost() && getStatus().equals(stopAns.getStatus());
    }
}
