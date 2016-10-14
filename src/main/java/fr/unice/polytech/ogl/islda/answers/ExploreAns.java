package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.ExploitDec;
import fr.unice.polytech.ogl.islda.decisions.GlimpseDec;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.map.Mapper;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import fr.unice.polytech.ogl.islda.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class ExploreAns extends Answer{
    private ExploreExt extras;

    public ExploreAns(String status, int cost, ExploreExt extras) {
        super(status, cost);
        this.extras = extras;
    }

    @Override
    public void init(Mapper map) {
        map.initCurrentCase(extras);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        List<Objective> objectives = new ArrayList<>(getContext().getObjective());
        for (Iterator<Objective> it = objectives.iterator(); it.hasNext();) {
            Objective obj = it.next();
            if (obj.getHarvestedAmount() >= obj.getAmount()) {
                it.remove();
            }
        }

        List<String> listResources = Utils.listResourceToListString(map.getCurrentCase().getResources());
        List<String> listObjective = Objective.match(objectives, listResources);

        for (String objective : listObjective) {
            decisionManager.addDecisionToQueue(new ExploitDec(objective));
        }
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        List<Direction> dirs = map.getDirectionWithGoodBiome(getContext().getObjective());
        if(! dirs.isEmpty()){
            map.move(map.getCaseAround(dirs.get(0),1), decisionManager);
        }

        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        dirs = map.getEmptyBiomesDirections();
        if(!dirs.isEmpty()){
            for(Direction dir : dirs){
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
    public ExploreExt getExtras() {
        return extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExploreAns)) {
            return false;
        }

        ExploreAns that = (ExploreAns) o;

        return extras.equals(that.extras);
    }

    @Override
    public int hashCode() {
        return extras != null ? extras.hashCode() : 0;
    }
}
