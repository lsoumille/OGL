package fr.unice.polytech.ogl.islda.map;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.extras.ExploreExt;
import fr.unice.polytech.ogl.islda.extras.GlimpseExt;
import fr.unice.polytech.ogl.islda.extras.ScoutExt;
import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;
import fr.unice.polytech.ogl.islda.utils.DecisionManager;
import fr.unice.polytech.ogl.islda.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pascal Tung
 */
public class MapperTest {
    Case currentCase;
    Mapper map;
    Point origin;

    @Before
    public void setUp() {
        currentCase = new Case();
        currentCase.addBiome(new Biome(BiomeEnum.BEACH, 100.0));
        map = new Mapper(currentCase);
        origin = new Point();
    }

    /**
     * Move with a CaseToGo NW
     */
    @Test
    public void testMoveWithCaseToGoNW() {
        Assert.assertEquals(origin, currentCase.getCoordinate());

        Case caseToGo = new Case();
        caseToGo.setX(-1);
        caseToGo.setY(4);
        Point coordCase = new Point(-1, 4);
        Assert.assertEquals(coordCase, caseToGo.getCoordinate());

        DecisionManager dm = new DecisionManager();
        map.move(caseToGo, dm);

        DecisionManager oracleDM = new DecisionManager();
        Assert.assertEquals(5, dm.count());
        Assert.assertEquals(oracleDM.getMoveJson(Direction.NORTH), dm.getFirstDecisionJson());
    }

    /**
     * Move with a CaseToGo SE
     */
    @Test
    public void testMoveWithCaseToGoSE() {
        Assert.assertEquals(origin, currentCase.getCoordinate());

        Case caseToGo = new Case();
        caseToGo.setX(1);
        caseToGo.setY(-4);
        Point coordCase = new Point(1, -4);
        Assert.assertEquals(coordCase, caseToGo.getCoordinate());

        DecisionManager dm = new DecisionManager();
        map.move(caseToGo, dm);

        DecisionManager oracleDM = new DecisionManager();
        Assert.assertEquals(5, dm.count());
        Assert.assertEquals(oracleDM.getMoveJson(Direction.SOUTH), dm.getFirstDecisionJson());
    }

    /**
     * Test with no cases around
     */
    @Test
    public void testMoveWithDirection() {
        Case oracleCase = new Case();
        oracleCase.setY(1);
        map.addCase(oracleCase);

        map.move(Direction.NORTH);

        Assert.assertEquals(oracleCase, map.getCurrentCase());
        Assert.assertEquals(currentCase, map.getCaseAround(Direction.SOUTH));
    }

    /**
     * Test with on an unknown case
     */
    @Test
    public void testMoveUnknownCase(){
        Mapper oracleMap = new Mapper(map.getCurrentCase());
        map.move(Direction.NORTH);
        Case oracleNorth = oracleMap.getCaseAround(Direction.NORTH);
        oracleNorth.setVisited();

        Assert.assertEquals(oracleMap.getCurrentCase(), map.getCaseAround(Direction.SOUTH));
        Assert.assertEquals(oracleNorth, map.getCurrentCase());
    }

    /**
     * Test with one case around
     */
    @Test
    public void testMoveWithCase() {
        Case nextCase = new Case();
        nextCase.setX(-1);
        map.addCase(nextCase);

        map.move(Direction.WEST);

        Assert.assertEquals(nextCase, map.getCurrentCase());
        Assert.assertEquals(currentCase, map.getCaseAround(Direction.EAST));
    }

    /**
     * Test with currentCase does not contain resources
     */
    @Test
    public void testInitCurrent() {
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(new Resource("WOOD", "HIGH", "EASY"));
        resourceList.add(new Resource("FUR", "LOW", "FAIR"));
        resourceList.add(new Resource("FLOWER", "MEDIUM", "HARSH"));
        List<Pois> poisList = new ArrayList<>(Arrays.asList((new Pois("CREEK", "CREEK_ID"))));
        ExploreExt exploreExt = new ExploreExt(resourceList, poisList);

        map.initCurrentCase(exploreExt);

        Assert.assertEquals(resourceList, map.getCurrentCase().getResources());
        Assert.assertEquals(poisList, map.getCurrentCase().getPois());
    }

    /**
     * Test with currentCase does contain resources
     */
    @Test
    public void testInitCurrentAlreadyContain() {
        List<Resource> resourceList1 = new ArrayList<>();
        resourceList1.add(new Resource("WOOD", "HIGH", "EASY"));
        resourceList1.add(new Resource("FLOWER", "MEDIUM", "HARSH"));
        currentCase.setResources(resourceList1);

        List<Resource> resourceList2 = new ArrayList<>();
        resourceList2.add(new Resource("WOOD", "HIGH", "EASY"));
        resourceList2.add(new Resource("FUR", "LOW", "FAIR"));
        resourceList2.add(new Resource("FLOWER", "MEDIUM", "HARSH"));
        List<Pois> poisList = new ArrayList<>(Arrays.asList((new Pois("CREEK", "CREEK_ID"))));
        ExploreExt exploreExt = new ExploreExt(resourceList2, poisList);

        map.initCurrentCase(exploreExt);

        Assert.assertNotEquals(resourceList1, resourceList2);
        Assert.assertEquals(resourceList1, map.getCurrentCase().getResources());
        Assert.assertEquals(poisList, map.getCurrentCase().getPois());
    }

    /**
     * Test without case around
     */
    @Test
    public void testInitCaseAround() {
        List<String> resourceList = new ArrayList<>();
        resourceList.add("WOOD");
        resourceList.add("FUR");
        resourceList.add("FLOWER");
        ScoutExt scoutExt = new ScoutExt(resourceList, 2);

        Case oracleCase = new Case();
        oracleCase.setResources(Utils.listStringToListResource(resourceList));
        oracleCase.setAltitude(2);
        oracleCase.setY(1);
        map.addCase(oracleCase);

        map.initCaseAround(Direction.NORTH, scoutExt);

        Assert.assertEquals(oracleCase, map.getCaseAround(Direction.NORTH));
        Assert.assertEquals(3, map.getCaseAround(Direction.NORTH).getResources().size());
    }

    /**
     * Test with case around already defined
     */
    @Test
    public void testInitCaseAroundExisted() {
        List<String> resourceList = new ArrayList<>();
        resourceList.add("WOOD");

        Case nextCase = new Case();
        nextCase.setResources(Utils.listStringToListResource(resourceList));
        nextCase.setAltitude(2);
        nextCase.setX(-1);
        map.addCase(nextCase);

        resourceList.add("FUR");
        resourceList.add("FLOWER");
        ScoutExt scoutExt = new ScoutExt(resourceList, 2);
        map.initCaseAround(Direction.WEST, scoutExt);

        Assert.assertEquals(nextCase, map.getCaseAround(Direction.WEST));
        Assert.assertEquals(1, map.getCaseAround(Direction.WEST).getResources().size());
    }

    @Test
    public void testInitBiome()
    {
        List<List<Object>> report = new ArrayList<>();
        List<Object> mangrove80 = new ArrayList<Object>(Arrays.asList("MANGROVE", 80.0));
        List<Object> beach20 = new ArrayList<Object>(Arrays.asList("BEACH", 20.0));
        List<Object> mangrove40 = new ArrayList<Object>(Arrays.asList("MANGROVE", 40.0));
        List<Object> tropicalR40 = new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 40.0));
        List<Object> tropicalR30 = new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 30.0));
        List<Object> tropicalS20 = new ArrayList<Object>(Arrays.asList("TROPICAL_SEASONAL_FOREST", 20.0));
        report.add(new ArrayList<Object>(Arrays.asList(mangrove80, beach20)));
        report.add(new ArrayList<Object>(Arrays.asList(mangrove40, tropicalR40, tropicalS20, tropicalR30)));
        report.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", "TROPICAL_SEASONAL_FOREST")));
        report.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST")));
        GlimpseExt glimpseExt = new GlimpseExt(4, report);

        map.initBiomes(Direction.EAST, glimpseExt);
        List<Double> rates = new ArrayList<>();
        for (Biome b: map.getCaseAround(Direction.EAST, 1).getBiomes()) {
            rates.add(b.getRate());
        }
        Assert.assertEquals(2, map.getCurrentCase().getBiomes().size());
        Assert.assertEquals(3, map.getCaseAround(Direction.EAST, 1).getBiomes().size());
        map.initBiomes(Direction.EAST, glimpseExt);

        List<Double> secondRates = new ArrayList<>();
        for (Biome b: map.getCaseAround(Direction.EAST, 1).getBiomes()) {
            secondRates.add(b.getRate());
        }
        Assert.assertEquals(rates, secondRates); // Checks that the rates are not changed after a second glimpse

        Assert.assertEquals(2, map.getCaseAround(Direction.EAST, 2).getBiomes().size());
        Assert.assertEquals(1, map.getCaseAround(Direction.EAST, 3).getBiomes().size());
        Assert.assertEquals(0, map.getCaseAround(Direction.EAST, 4).getBiomes().size());
    }

    @Test
    public void testBiomeInDirection () {
        map.getCaseAround(Direction.EAST).addBiome(new Biome(BiomeEnum.BEACH, null));

        boolean test = map.biomeInDirection(Direction.EAST, new Biome(BiomeEnum.BEACH, null), false);
        Assert.assertTrue(test);
        Assert.assertEquals(3, map.getEmptyBiomesDirections().size());
    }

    /**
     * check if a case is created when it doesn't exist
     */
    @Test
    public void testCreateNewCaseWithCoord(){
        currentCase.setX(origin.getX());
        currentCase.setY(origin.getY());

        Case oracle = new Case();
        oracle.setY(1);
        Assert.assertEquals(oracle, map.getCaseAround(Direction.NORTH, currentCase));
        oracle.setX(-1);
        oracle.setY(0);
        Assert.assertEquals(oracle, map.getCaseAround(Direction.WEST, currentCase));
    }

    @Test
    public void testEqualsAndHashcode() {
        Mapper x = new Mapper(currentCase);
        Mapper y = new Mapper(currentCase);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void getCaseInRange(){
        Mapper map = new Mapper(currentCase);
        Biome biome = new Biome(BiomeEnum.BEACH, 100.0);
        Case case1 = new Case();
        case1.setCoordinate(new Point(-1, -1));
        case1.addBiome(biome);
        Case case2 = new Case();
        case2.setCoordinate(new Point(0, 1));
        case2.addBiome(biome);
        Case caseOut = new Case();
        caseOut.setCoordinate(new Point(2,2));
        caseOut.addBiome(biome);
        map.addCase(case1);
        map.addCase(case2);
        map.addCase(caseOut);

        List<Case> casesAround = map.getCasesByRange(1);
        Assert.assertEquals(3, casesAround.size());
        casesAround = map.getCasesByRange(2);
        Assert.assertEquals(3, casesAround.size());
    }

}
