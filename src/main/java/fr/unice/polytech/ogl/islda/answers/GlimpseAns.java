package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.MoveDec;
import fr.unice.polytech.ogl.islda.extras.GlimpseExt;
import fr.unice.polytech.ogl.islda.map.*;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;

import java.util.List;

/**
 * @author Nicolas HORY
 * @author Pascal Tung
 * @version 23/03/15
 */

public class GlimpseAns extends Answer {
    private Direction direction;
    private GlimpseExt extras;

    public GlimpseAns(String status, int cost, GlimpseExt extras) {
        super(status, cost);
        this.extras = extras;
    }

    @Override
    public void init(Mapper map) {
        map.initBiomes(direction, extras);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String nextDecision(DecisionManager decisionManager, Mapper map) {
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        takeDecisionWithRange1(decisionManager, map);
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        takeDecisionWithRange2(decisionManager, map);
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        takeDecisionWithRange34(decisionManager, map);
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        if(map.getLastDirection() == null){
            searchInCircle(decisionManager, map, new Biome(BiomeEnum.OCEAN, null));
            if (!decisionManager.isEmpty()) {
                return decisionManager.getFirstDecisionJson();
            }
        }

        checkOceanInRange(decisionManager, map, map.getLastDirection(), 3);
        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        List<Direction> nextDirections = Direction.getLeftRight(map.getLastDirection());
        for(Direction dir : nextDirections){
            if(checkOceanInRange(decisionManager, map, dir, 3))
                break;
        }

        if (!decisionManager.isEmpty()) {
            return decisionManager.getFirstDecisionJson();
        }

        for(Direction dir : nextDirections){
            if(checkOceanInRange(decisionManager, map, dir, 1))
                break;
        }

        return decisionManager.getFirstDecisionJson();
    }

    /**
     * return true if the case in the direction "dir" , at the range "range" does not contain ocean
     * If it returns true, add X (X = range) move in the direction "dir"
     * @param dm
     * @param map
     */
    public boolean checkOceanInRange(DecisionManager dm, Mapper map, Direction dir, int range){
        Biome oceanBiome = new Biome(BiomeEnum.OCEAN, null);
        Case nextCase = map.getCaseAround(dir, range);
        if(!nextCase.getBiomes().contains(oceanBiome) && !nextCase.isVisited() && !nextCase.isUnreachable()) {
            for(int i = 0 ; i < range ; ++i){
                dm.addDecisionToQueue(new MoveDec(dir));
            }
            return true;
        }
        return false;
    }

    /**
     * search in circle around the current case, start with the case at the range 3 and end with range 1
     * @param dm
     * @param map
     * @param oceanBiome
     */
    public void searchInCircle(DecisionManager dm, Mapper map, Biome oceanBiome) {
        for (int i = 3; i > 0 && dm.isEmpty(); i--) {
            for (Direction dir : Direction.values()) {
                Case caseAround = map.getCaseAround(dir, i);
                if (caseAround != null && !caseAround.isVisited() && !caseAround.isUnreachable()) {
                    List<Biome> biome = caseAround.getBiomes();
                    if (!biome.contains(oceanBiome)) {
                        dm.addDecisionToQueue(new MoveDec(dir));
                        dm.addDecisionToQueue(new MoveDec(dir));
                        dm.addDecisionToQueue(new MoveDec(dir));
                        return;
                    }
                }
            }
        }
    }

    /**
     * Take decision with only cases around 2&3 information
     * @param dm Decision manager
     * @param map Mapper
     * @return
     */
    public void takeDecisionWithRange34(DecisionManager dm, Mapper map) {
        for (int i = 3; i <= 4; i++) {
            for (Direction dir : Direction.values()) {
                Case caseWithObj = getCaseWithObj(map,
                        getContext().getObjective(),
                        dir,
                        i-1);
                if (caseWithObj != null && !caseWithObj.isUnreachable()) {
                    for (int j = i - 1; j >= 0; --j) {
                        dm.addDecisionToQueue(new MoveDec(dir));
                    }
                    return;
                }
            }
        }
    }

    /**
     * Take decision with only cases around information
     * @param dm Decision manager
     * @param map Mapper
     * @return
     */
    public void takeDecisionWithRange2(DecisionManager dm, Mapper map) {
        Direction mostValuableDirection = getMostValuableDirection(map,
                getContext().getObjective());
        if (mostValuableDirection != null) {
            dm.getMoveJson(mostValuableDirection);
        }
    }

    /**
     * Take decision with only currentCase information
     * @param dm Decision manager
     * @param map Mapper
     * @return
     */
    public void takeDecisionWithRange1(DecisionManager dm, Mapper map) {
        if (map.getCurrentCase().resourcesKnown())
            return;

        Case caseWithObj = getCaseWithObj(map,
                getContext().getObjective(),
                Direction.NORTH,
                0);
        if (caseWithObj != null) {
            dm.getExploreJson();
        }
    }

    /**
     * Direction with the best value considering the list of objectives and the last move direction
     * @param map The map
     * @param objectives List of objectives
     * @return mostValuableDirection
     */
    public Direction getMostValuableDirection(Mapper map, List<Objective> objectives) {
        Direction mostValuableDirection = null;
        Case mostValuableCase = null;
        for (Direction dir : Direction.values()) {
            Case caseWithObj = getCaseWithObj(map, objectives, dir, 1);
            if (caseWithObj != null) {
                if (mostValuableCase == null) {
                    mostValuableDirection = dir;
                    mostValuableCase = caseWithObj;
                } else {
                    List<Biome> biomesWO = map.getBiomesWithObj(objectives, caseWithObj);
                    List<Biome> biomesMV = map.getBiomesWithObj(objectives, mostValuableCase);

                    Double valueWO = Biome.getListBiomeValue(biomesWO);
                    Double valueMV = Biome.getListBiomeValue(biomesMV);

                    if (valueWO > valueMV) {
                        mostValuableDirection = dir;
                        mostValuableCase = caseWithObj;
                    }
                }
            }
        }

        return mostValuableDirection;
    }

    /**
     * Case which includes the objective
     *
     * @param map
     * @param objectives
     * @param direction
     * @param range
     * @return
     */
    public Case getCaseWithObj(Mapper map, List<Objective> objectives, Direction direction, int range) {
        Case caseToCheck = map.getCaseAround(direction, range);
        Case caseWithObj = null;

        List<Biome> biomes = caseToCheck.getBiomes();
        List<Biome> availableBiomes = Objective.getAvailableBiomes(objectives);
        for (Biome biomeInCase : biomes) {
            for (Biome availableBiome : availableBiomes) {
                if (biomeInCase.equals(availableBiome)) {
                    caseWithObj = caseToCheck;
                    break;
                }
            }
        }

        return caseWithObj;
    }

    /**
     * Moves to the case where the ocean biome has the lower percentage
     * @param dm
     * @param map
     */
    public void moveToCaseWithLowOcean(DecisionManager dm, Mapper map) {
        Biome oceanBiome = new Biome(BiomeEnum.OCEAN, null);
        Double lowRate = 100.0;
        Direction bestDir = null;
        for (Direction dir : Direction.values()) {
            Case nextCase = map.getCaseAround(dir);
            if (nextCase.getBiomes().contains(oceanBiome)
                && nextCase.getRateBiome(BiomeEnum.OCEAN) < lowRate
                && !nextCase.isUnreachable()){
                lowRate = map.getCaseAround(dir).getRateBiome(BiomeEnum.OCEAN);
                bestDir = dir;
            }
        }
        if (bestDir != null) {
            dm.addDecisionToQueue(new MoveDec(bestDir));
        }
    }

    /**
     * This function checks if we can fall out of the world if we keep going towards that direction
     * @return
     */
    public boolean isUnreachable(){
        if (extras.getResources().size() < extras.getAsked_range()) {
            return true;
        }
        return false;
    }

    @Override
    public GlimpseExt getExtras() {
        return extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GlimpseAns ans = (GlimpseAns) o;

        return !(extras != null ? !extras.equals(ans.extras) : ans.extras != null);
    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + (extras != null ? extras.hashCode() : 0);
        return result;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
