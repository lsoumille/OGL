package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.extras.ScoutExt;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

/**
 * @author Lucas SOUMILLE
 * @version 06/03/15
 */
public class ScoutAns extends Answer {
    private ScoutExt extras;
    private Direction direction;

    public ScoutAns(String status, int cost, ScoutExt extras) {
        super(status, cost);
        this.extras = extras;
    }

    @Override
    public void init(Mapper map) {
        map.initCaseAround(direction, extras);
    }

    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        return decisionManager.getStopJson();
    }

    @Override
    public ScoutExt getExtras() {
        return extras;
    }

    public void setExtras(ScoutExt extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoutAns)) {
            return false;
        }

        ScoutAns scoutAns = (ScoutAns) o;

        return extras.equals(scoutAns.extras);
    }

    @Override
    public int hashCode() {
        int result = extras != null ? extras.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
