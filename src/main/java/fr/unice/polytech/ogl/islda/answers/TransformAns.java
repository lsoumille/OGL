package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.extras.TransformExt;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

/**
 * @author Lucas Martinez
 * @version 09/05/15
 */
public class TransformAns extends Answer {
    private TransformExt extras;

    public TransformAns(String status, int cost, TransformExt extras) {
        super(status, cost);
        this.extras = extras;
    }

    @Override
    /**
     * Increases secondary objective with extras response
     */
    public void init(Mapper map) {
        getContext().increaseObjective(extras.getKind(), extras.getProduction());
    }

    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        return decisionManager.getFirstDecisionJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransformAns ans = (TransformAns) o;

        return !(extras != null ? !extras.equals(ans.extras) : ans.extras != null);

    }

    @Override
    public int hashCode() {
        return extras != null ? extras.hashCode() : 0;
    }
}
