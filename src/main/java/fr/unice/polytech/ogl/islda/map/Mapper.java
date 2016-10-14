package fr.unice.polytech.ogl.islda.map;

import fr.unice.polytech.ogl.islda.decisions.MoveDec;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.extras.GlimpseExt;
import fr.unice.polytech.ogl.islda.extras.ScoutExt;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.model.Resource;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import fr.unice.polytech.ogl.islda.utils.Utils;

import java.util.*;

/**
 * @author Tung Pascal
 * @version 03/04/15
 */
public class Mapper {
    private Map<Point, Case> map;
    private Case currentCase;
    private Direction lastDirection;
    private static final int MAX_RANGE = 3;

    public Mapper(Case currentCase) {
        this.currentCase = currentCase;
        this.map = new LinkedHashMap<>();
        map.put(currentCase.getCoordinate(), currentCase);
    }

    public void addCase(Case newCase) {
        map.put(newCase.getCoordinate(), newCase);
    }

    /**
     * Move the current case
     *
     * @param direction The direction to move
     */
    public void move(Direction direction) {
        this.lastDirection = direction;
        Case nextCase = getCaseAround(direction);
        Point coord = getNextCoord(currentCase, direction);
        nextCase.setX(coord.getX());
        nextCase.setY(coord.getY());

        currentCase = nextCase;
        currentCase.setVisited();
    }

    /**
     * Move to the case to go
     *
     * @param caseToGo The case to go
     * @param dm Decision manager to take the move decision
     */
    public void move(Case caseToGo, DecisionManager dm) {
        int dx = caseToGo.getX() - currentCase.getX();
        int dy = caseToGo.getY() - currentCase.getY();


        for (int i = 0; i < Math.abs(dy); i++) {
            if (dy > 0) {
                dm.addDecisionToQueue(new MoveDec(Direction.NORTH));
            } else if (dy < 0) {
                dm.addDecisionToQueue(new MoveDec(Direction.SOUTH));
            }
        }

        for (int i = 0; i < Math.abs(dx); i++) {
            if (dx > 0) {
                dm.addDecisionToQueue(new MoveDec(Direction.EAST));
            } else if (dx < 0) {
                dm.addDecisionToQueue(new MoveDec(Direction.WEST));
            }
        }
    }

    /**
     * Put ExploreAns information in the current case
     *
     * @param extras Information of the ExploreAns
     */
    public void initCurrentCase(ExploreExt extras) {
        currentCase.setPois(extras.getPois());
        if (currentCase.getResources() == null ||
                (!currentCase.getResources().isEmpty() &&
                        currentCase.getResources().get(0).getAmount() == null)) {

            currentCase.setResources(extras.getResources());
        }
    }

    /**
     * return cases at the distance in param of the currentcase
     * @param range
     * @return
     */
    public List<Case> getCasesByRange(int range) {
        List<Case> casesInRange = new ArrayList<>();

        for(int x = currentCase.getX() - range ; x <= currentCase.getX() + range ; ++x){
            for(int y = currentCase.getY() - range ; y <= currentCase.getY() + range ; y += range){
                casesInRange.add(getCaseByCoord(new Point(x, y)));
            }
        }

        for(int y = currentCase.getY() - range ; y <= currentCase.getY() + range ; ++y){
            for(int x = currentCase.getX() - range ; x <= currentCase.getX() + range ; x += range){
                casesInRange.add(getCaseByCoord(new Point(x, y)));
            }
        }
        casesInRange = new ArrayList<>(new LinkedHashSet(casesInRange));
        List<Case> casesWithBiomes = new ArrayList<>();
        for(Case nextCase : casesInRange){
            if(! nextCase.getBiomes().isEmpty()){
                casesWithBiomes.add(nextCase);
            }
        }

        return casesWithBiomes;
    }

    /**
     * return a case with a good biome and which isn't visited yes around us
     * @param objectives
     * @return
     */
    public Case getCaseWithGoodBiomeNotVisited(List<Objective> objectives){
        List<Biome> biomesRes = Objective.getAvailableBiomes(objectives);
        for(int i = 1 ; i <= MAX_RANGE ; ++i) {
            List<Case> allCases = getCasesByRange(i);
            for (Case nextCase : allCases) {
                if (!nextCase.isVisited()) {
                    List<Biome> biomeInCase = nextCase.getBiomes();
                    for (Biome bCase : biomeInCase) {
                        for (Biome bRes : biomesRes) {
                            if (bCase.equals(bRes)) {
                                return nextCase;
                            }
                        }
                    }
                }
            }
        }
        return currentCase;
    }

    /**
     * Create cases around with ScoutAns information
     *
     * @param direction Direction of the ScoutDec
     * @param extras Information of the ScoutAns
     *
     */
    public void initCaseAround(Direction direction, ScoutExt extras) {
        int absoluteAltitude = currentCase.getAltitude()+extras.getAltitude();
        Case nextCase = getCaseAround(direction);
        Point coord = getNextCoord(currentCase, direction);
        nextCase.setX(coord.getX());
        nextCase.setY(coord.getY());
        nextCase.setAltitude(absoluteAltitude);

        if (nextCase.getResources() == null) {
            List<Resource> resources = Utils.listStringToListResource(extras.getResources());
            nextCase.setResources(resources);
        }

        if (extras.isUnreachable()) {
            nextCase.setUnreachable(true);
        }
    }

    /**
     * Add biome information with GlimpseAns information
     *
     * @param direction Direction of the GlimpseDec
     * @param extras Information of the GlimpseAns
     */
    public void initBiomes(Direction direction, GlimpseExt extras) {
        Case nextCase = currentCase;
        List<Biome> allBiomes;

        for (int i = 0; i <= extras.getAsked_range()-1; i++) {
            allBiomes = new ArrayList<>();
            if (i+1 > extras.getResources().size()) {
                nextCase.setUnreachable(true);
                continue;
            }
            List<Object> reports = extras.getResources().get(i);
            for (Object report : reports) {
                Biome biome = null;

                if (i < 2) {
                    List<Object> specificReport = (List<Object>) report;

                    if (specificReport.size() == 2) {
                        BiomeEnum biomeEnum = BiomeEnum.valueOf(specificReport.get(0).toString());
                        Double rate = (Double) specificReport.get(1);
                        biome = new Biome(biomeEnum, rate);
                    }
                } else {
                    BiomeEnum biomeEnum = BiomeEnum.valueOf(report.toString());
                    biome = new Biome(biomeEnum, null);
                }

                if (biome != null) {
                    allBiomes.add(biome);
                }
            }
            Biome biome = null;
            Biome biomeChecked = null;
            for (int k = 0; k < allBiomes.size(); k++) {
                biome = allBiomes.get(k);
                for (int j = k+1; j < allBiomes.size(); j++) {
                    biomeChecked = allBiomes.get(j);
                    if(biome.equals(biomeChecked)) {
                        if (biomeChecked.getRate() != null) {
                            allBiomes.get(k).setRate(biome.getRate() + biomeChecked.getRate());
                        }
                        allBiomes.remove(j);
                        j--;
                    }
                }
            }

            for (Biome b : allBiomes) {
                nextCase.addBiome(b);
            }

            nextCase = getCaseAround(direction, nextCase);
        }
    }

    public Case getCurrentCase() {
        return currentCase;
    }

    /**
     return true if the biome is in the direction cases and if its rate is 100% with unique == true
     return true if the biome is in the direction cases with unique == false
     */
    public boolean biomeInDirection(Direction dir, Biome biome, boolean unique){
        List<Biome> biomeInCurrent;
        for (int i = 3 ; i > 0 ; i--) {
            biomeInCurrent = getCaseAround(dir, i).getBiomes();
            if(biomeInCurrent.size() == 1 && biomeInCurrent.contains(biome) && unique){
                return true;
            }
            if(biomeInCurrent.contains(biome) && !unique) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the new coordinates
     * @param direction Direction of the new coordinates
     * @return Coordinates of the next case
     */
    public Point getNextCoord(Case current, Direction direction) {
        Point coord = new Point(current.getX(), current.getY());

        switch (direction) {
            case NORTH:
                coord.setY(coord.getY() + 1);
                break;
            case SOUTH:
                coord.setY(coord.getY() - 1);
                break;
            case WEST:
                coord.setX(coord.getX() - 1);
                break;
            case EAST:
                coord.setX(coord.getX() + 1);
                break;
            default:
                break;
        }

        return coord;
    }

    public Case getCaseAround(Direction dir) {
        return getCaseAround(dir, currentCase);
    }

    /**
     * get the case in the direction and create it if it doesn't exist
     * @param dir
     * @param currentCase
     * @return
     */
    public Case getCaseAround(Direction dir, Case currentCase){
        Point coordNextCase = getNextCoord(currentCase, dir);
        return getCaseByCoord(coordNextCase);
    }

    /**
     * Get the case around with direction and range
     * @param direction
     * @param range
     */
    public Case getCaseAround(Direction direction, int range) {
        if (range == 0) {
            return currentCase;
        }

        Case nextCase = currentCase;
        for (int i = 1; i <= range; ++i) {
            nextCase = getCaseByCoord(getNextCoord(nextCase, direction));
        }

        return nextCase;
    }

    /**
     * return a case which is not visited and doesn't contain ocean
     * @return
     */
    public Case getCaseVisitedBiomeEdge(){
        Stack<Case> allCase = new Stack<>();
        for (Map.Entry<Point, Case> nextCase : map.entrySet()){
            allCase.push(nextCase.getValue());
        }
        while(! allCase.isEmpty()){
            Case caseTmp = allCase.pop();
            for(Direction dir : Direction.values()){
                Case neighCase = getCaseAround(dir, caseTmp);
                if(!neighCase.isVisited() && !neighCase.getBiomes().isEmpty()
                        && !neighCase.getBiomes().contains(new Biome(BiomeEnum.OCEAN,null))){
                    return neighCase;
                }
            }
        }
        return currentCase;
    }

    /**
     * return the case corresponding at the coordinates
     * @param coord
     * @return
     */
    public Case getCaseByCoord(Point coord){
        Case nextCase = map.get(coord);
        if (nextCase == null) {
            nextCase = new Case();
            nextCase.setCoordinate(coord);
            map.put(nextCase.getCoordinate(), nextCase);
        }

        return map.get(coord);
    }

    /**
     * Returns a list of directions of the cases around contain an objective's biomee
     * @param objective
     * @return
     */
    public List<Direction> getDirectionWithGoodBiome(List<Objective> objective){
        List<Direction> directions = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Case caseAround = this.getCaseAround(dir,1);
            if (!caseAround.isVisited() && !caseAround.getBiomes().isEmpty() && !dir.equals(lastDirection)) {
                boolean loop = true;
                for (int i = 0; i < caseAround.getBiomes().size() && loop; i++) {
                    Biome biome = caseAround.getBiomes().get(i);
                    for (Objective obj : objective) {
                        if (obj.getAvailableBiomes().contains(biome)) {
                            directions.add(dir);
                            loop = false;
                        }
                    }
                }
            }
        }
        return directions;
    }

    /**
     * Returns the directions of the cases that has been defined yet
     *
     * @return List of direction which has listResources null
     */
    public List<Direction> getEmptyResourcesDirections() {
        List<Direction> emptyDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Case nextCase = getCaseAround(direction, currentCase);
            if (nextCase.getResources() == null) {
                emptyDirections.add(direction);
            }
        }
        return emptyDirections;
    }

    /**
     * Returns the directions of the cases that has been defined yet
     * @return
     */
    public List<Direction> getEmptyBiomesDirections() {
        List<Direction> emptyDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Case nextCase = getCaseAround(direction, currentCase);
            if (nextCase.getBiomes().isEmpty()) {
                emptyDirections.add(direction);
            }
        }

        return emptyDirections;
    }
    /**
     * Get a list of Biome which possibly contain an objective
     *
     * @param objectives The list of objective sought
     * @return
     */
    public List<Biome> getBiomesWithObj(List<Objective> objectives, Case caseTest) {
        List<Biome> biomesWithObj = new ArrayList<>();
        for (Objective obj : objectives) {
            biomesWithObj.addAll(obj.getAvailableBiomes());
        }
        List<Biome> currentBiomes = new ArrayList<>(caseTest.getBiomes());
        if(currentBiomes != null){
            currentBiomes.retainAll(biomesWithObj);
        }
        return currentBiomes;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mapper mapper = (Mapper) o;

        if (currentCase != null ? !currentCase.equals(mapper.currentCase) : mapper.currentCase != null) return false;
        if (lastDirection != mapper.lastDirection) return false;
        if (map != null ? !map.equals(mapper.map) : mapper.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = map != null ? map.hashCode() : 0;
        result = 31 * result + (currentCase != null ? currentCase.hashCode() : 0);
        result = 31 * result + (lastDirection != null ? lastDirection.hashCode() : 0);
        return result;
    }
}
