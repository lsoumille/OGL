package fr.unice.polytech.ogl.islda.map;

import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.model.Objective;
import fr.unice.polytech.ogl.islda.model.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nicolas HORY
 * @author Pascal Tung
 * @version  14/03/15
 */
public class CaseTest {
    private Case caseTest;
    private Direction wantedDirection;
    private Objective objective;
    private List<Resource> resources;
    private Mapper map;

    @Before
    public void setUp() {
        caseTest = new Case(null, null, 0);
        map = new Mapper(caseTest);
        wantedDirection = Direction.NORTH;
        objective = new Objective("WOOD", 10);

        resources = new ArrayList<>();
        resources.add(new Resource("WOOD", "HIGH", "FAIR"));
    }

    @Test
    public void testRemoveResource() {
        Case currentCase = new Case();
        currentCase.removeResource("WOOD");
        Assert.assertNull(currentCase.getResources());
        currentCase.setResources(new ArrayList<>(Arrays.asList(new Resource("WOOD", null, null))));
        currentCase.removeResource("FUR");
        Assert.assertEquals(1, currentCase.getResources().size());
        currentCase.removeResource("WOOD");
        Assert.assertEquals(0, currentCase.getResources().size());
    }

    /**
     * 0 case around
     * Cases around null
     */
    @Test
    public void getEmptyDirectionsCasesAroundNull() {
        List<Direction> directions = map.getEmptyResourcesDirections();
        Assert.assertTrue(directions.contains(Direction.NORTH));
        Assert.assertTrue(directions.contains(Direction.WEST));
        Assert.assertTrue(directions.contains(Direction.SOUTH));
        Assert.assertTrue(directions.contains(Direction.EAST));
    }

    /**
     * 1 case around
     * Case around north is initialized with resources
     */
    @Test
    public void getEmptyDirectionsCaseN() {
        Point coordNorth = map.getNextCoord(map.getCurrentCase(), Direction.NORTH);
        Case north = new Case(resources, null, 0);
        north.setX(coordNorth.getX());
        north.setY(coordNorth.getY());
        map.addCase(north);
        List<Direction> directions = map.getEmptyResourcesDirections();
        Assert.assertFalse(directions.contains(Direction.NORTH));
        Assert.assertTrue(directions.contains(Direction.WEST));
        Assert.assertTrue(directions.contains(Direction.SOUTH));
        Assert.assertTrue(directions.contains(Direction.EAST));
    }

    /**
     * 2 cases around
     * Case around south and east are initialized with resources
     */
    @Test
    public void getEmptyDirectionsCaseSE() {
        Point coordSouth = map.getNextCoord(map.getCurrentCase(), Direction.SOUTH);
        Point coordEast = map.getNextCoord(map.getCurrentCase(), Direction.EAST);
        Case south = new Case(resources, null, 0);
        south.setCoordinate(coordSouth);
        Case east = new Case(resources, null, 0);
        east.setCoordinate(coordEast);
        map.addCase(south);
        map.addCase(east);
        List<Direction> directions = map.getEmptyResourcesDirections();
        Assert.assertTrue(directions.contains(Direction.NORTH));
        Assert.assertTrue(directions.contains(Direction.WEST));
        Assert.assertFalse(directions.contains(Direction.SOUTH));
        Assert.assertFalse(directions.contains(Direction.EAST));
    }

    /**
     * 3 cases around
     * Case around north, south and west are initialized with resources
     */
    @Test
    public void getEmptyDirectionsCaseNSW() {
        Point coordSouth = map.getNextCoord(map.getCurrentCase(), Direction.SOUTH);
        Point coordWest = map.getNextCoord(map.getCurrentCase(), Direction.WEST);
        Point coordNorth = map.getNextCoord(map.getCurrentCase(), Direction.NORTH);
        Case north = new Case(resources, null, 0);
        north.setCoordinate(coordNorth);
        Case south = new Case(resources, null, 0);
        south.setCoordinate(coordSouth);
        Case west = new Case(resources, null, 0);
        west.setCoordinate(coordWest);
        map.addCase(north);
        map.addCase(south);
        map.addCase(west);
        List<Direction> directions = map.getEmptyResourcesDirections();
        Assert.assertFalse(directions.contains(Direction.NORTH));
        Assert.assertFalse(directions.contains(Direction.WEST));
        Assert.assertFalse(directions.contains(Direction.SOUTH));
        Assert.assertTrue(directions.contains(Direction.EAST));
    }

    /**
     * 4 cases around
     * Case around north, south, west and east are initialized with resources
     */
    @Test
    public void getEmptyDirectionsCaseNSWE() {
        Point coordSouth = map.getNextCoord(map.getCurrentCase(), Direction.SOUTH);
        Point coordWest = map.getNextCoord(map.getCurrentCase(), Direction.WEST);
        Point coordNorth = map.getNextCoord(map.getCurrentCase(), Direction.NORTH);
        Point coordEast = map.getNextCoord(map.getCurrentCase(), Direction.EAST);
        Case north = new Case(resources, null, 0);
        north.setCoordinate(coordNorth);
        Case south = new Case(resources, null, 0);
        south.setCoordinate(coordSouth);
        Case west = new Case(resources, null, 0);
        west.setCoordinate(coordWest);
        Case east = new Case(resources, null, 0);
        east.setCoordinate(coordEast);
        map.addCase(north);
        map.addCase(south);
        map.addCase(west);
        map.addCase(east);
        List<Direction> directions = map.getEmptyResourcesDirections();
        Assert.assertFalse(directions.contains(Direction.NORTH));
        Assert.assertFalse(directions.contains(Direction.WEST));
        Assert.assertFalse(directions.contains(Direction.SOUTH));
        Assert.assertFalse(directions.contains(Direction.EAST));
    }

    @Test
    public void addBiome() {
        Biome biomeToAdd = new Biome(BiomeEnum.BEACH, 30.0);
        caseTest.addBiome(biomeToAdd);
        ArrayList<Biome> wantedBiome = new ArrayList<>(Arrays.asList(new Biome(BiomeEnum.BEACH, 30.0)));
        Assert.assertEquals(caseTest.getBiomes().get(0).getRate(), wantedBiome.get(0).getRate());
    }

    /**
     * Check that the function getRateBiome reacts works correctly
     */
    @Test
    public void getRateBiome(){
        Biome biomeToAdd = new Biome(BiomeEnum.BEACH, 30.0);
        Biome biomeNull = new Biome(BiomeEnum.ALPINE, null);
        Biome biomeLake1 = new Biome(BiomeEnum.LAKE, 25.2);
        Biome biomeLake2 = new Biome(BiomeEnum.LAKE, 24.8);

        caseTest.addBiome(biomeToAdd);
        caseTest.addBiome(biomeNull);
        caseTest.addBiome(biomeLake1);

        Double wantedRateBeach = 30.0;
        Double wantedRateZero = 0.0;
        Double wantedRateFirstLake = 25.2;
        Double wantedRateBothLake = 50.0;

        Assert.assertEquals(caseTest.getRateBiome(BiomeEnum.BEACH), wantedRateBeach);
        Assert.assertEquals(caseTest.getRateBiome(BiomeEnum.OCEAN), wantedRateZero);
        Assert.assertEquals(caseTest.getRateBiome(BiomeEnum.ALPINE), wantedRateZero);
        Assert.assertEquals(caseTest.getRateBiome(BiomeEnum.LAKE), wantedRateFirstLake);

        caseTest.addBiome(biomeLake2);

        Assert.assertNotEquals(caseTest.getRateBiome(BiomeEnum.LAKE), wantedRateBothLake);

    }

    @Test
    public void testEqualsAndHashcode() {
        Point origin = new Point(0, 0);
        Case x = new Case(origin);
        Case y = new Case(origin);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
