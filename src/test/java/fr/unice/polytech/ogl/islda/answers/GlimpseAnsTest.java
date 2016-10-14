package fr.unice.polytech.ogl.islda.answers;

import fr.unice.polytech.ogl.islda.decisions.MoveDec;
import fr.unice.polytech.ogl.islda.decisions.StopDec;
import fr.unice.polytech.ogl.islda.extras.GlimpseExt;
import fr.unice.polytech.ogl.islda.map.*;
import fr.unice.polytech.ogl.islda.model.Context;
import fr.unice.polytech.ogl.islda.model.Objective;
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
 * @version 23/03/15
 */
public class GlimpseAnsTest {
    Mapper map;
    List<Object> range1;
    List<Object> range2;
    List<Object> range3;
    List<Object> range4;
    List<List<Object>> report;
    GlimpseAns answer;
    Case currentCase;
    Objective objective;
    DecisionManager decisionManager;
    Context context;

    @Before
    public void setUp() {
        currentCase = new Case();
        map = new Mapper(currentCase);
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("MANGROVE", 80.0)));
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 20.0)));

        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("MANGROVE", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_SEASONAL_FOREST", 20.0)));

        range3 = new ArrayList<>();
        range3.add("TROPICAL_RAIN_FOREST");
        range3.add("TROPICAL_SEASONAL_FOREST");

        range4 = new ArrayList<>();
        range4.add("TROPICAL_RAIN_FOREST");

        report = new ArrayList<>();

        objective = new Objective("WOOD", 10);
        decisionManager = new DecisionManager();


        context = new Context();
        context.setObjective(Arrays.asList(objective));
    }

    /**
     * Test the coordinates of a new glimpsed cases
     */
    @Test
    public void testCoordinatesNewCase() {
        // Create case
        Mapper map = new Mapper(currentCase);
        // Create glimpse
        Direction direction = Direction.WEST;
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        answer = new GlimpseAns("OK", 20, new GlimpseExt(4, report));
        answer.setDirection(direction);
        answer.init(map);

        Point origin = new Point();
        Assert.assertEquals(origin, currentCase.getCoordinate());
        Point oracleP1West = new Point(-1, 0);
        Assert.assertEquals(oracleP1West, map.getCaseAround(direction, 1).getCoordinate());
        Point oracleP2West = new Point(-2, 0);
        Assert.assertEquals(oracleP2West, map.getCaseAround(direction, 2).getCoordinate());
        Point oracleP3West = new Point(-3, 0);
        Assert.assertEquals(oracleP3West, map.getCaseAround(direction, 3).getCoordinate());
    }

    /**
     * Test good biome in current case
     */
    @Test
    public void testNextDecision1() {
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        answer = new GlimpseAns("OK", 22, new GlimpseExt(4, report));
        answer.setContext(context);

        Mapper map = new Mapper(currentCase);

        Assert.assertTrue(decisionManager.isEmpty());
        answer.setDirection(Direction.NORTH);
        answer.init(map);
        answer.nextDecision(decisionManager, map);

        DecisionManager oracleDM = new DecisionManager();
        Assert.assertEquals(oracleDM.getExploreJson(), decisionManager.getFirstDecisionJson());
        Assert.assertEquals(1, decisionManager.count());
    }

    /**
     * Do nothing if the biomes doesn't contain the resources
     */
    @Test
    public void testRangeWithoutGoodBiome(){
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        range3 = new ArrayList<>();
        range3.add("BEACH");
        range4 = new ArrayList<>();
        range4.add("BEACH");
        report = new ArrayList<>();
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        answer = new GlimpseAns("OK", 1, new GlimpseExt(1,report));
        answer.setDirection(Direction.NORTH);
        answer.setContext(context);
        answer.init(map);
        answer.takeDecisionWithRange34(decisionManager, map);

        Assert.assertEquals(0, decisionManager.count());
    }

    /**
     * Multi move ont the direction with the good biome
     * else nothing
     */
    @Test
    public void testRange34(){
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        answer = new GlimpseAns("OK", 1, new GlimpseExt(4, report));
        answer.setDirection(Direction.NORTH);
        answer.setContext(context);
        answer.init(map);
        answer.takeDecisionWithRange34(decisionManager, map);

        DecisionManager oracleDM = new DecisionManager();
        Assert.assertEquals(oracleDM.getMoveJson(Direction.NORTH), decisionManager.getFirstDecisionJson());
        Assert.assertEquals(3, decisionManager.count());
    }

    /**
     * Move on the direction which contains the good biome
     * else nothing
     */
    @Test
    public void testRange2(){
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));

        report.add(range1);
        report.add(range2);

        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        answer = new GlimpseAns("OK", 1, new GlimpseExt(1,report));
        answer.setDirection(Direction.NORTH);
        answer.setContext(context);
        answer.takeDecisionWithRange2(decisionManager, map);

        Assert.assertEquals(decisionManager.getMoveJson(Direction.NORTH), decisionManager.getFirstDecisionJson());
        Assert.assertEquals(1, decisionManager.count());

        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        report = new ArrayList<>();
        report.add(range1);
        report.add(range2);
        decisionManager = new DecisionManager();
        answer.takeDecisionWithRange2(decisionManager, map);

        Assert.assertEquals(0, decisionManager.count());
    }

    /**
     * Explore when the case contains the good Biome
     * Else do nothing
     */
    @Test
    public void testRange1() {
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        report.add(range1);
        answer = new GlimpseAns("OK", 1, new GlimpseExt(1,report));
        answer.setDirection(Direction.NORTH);
        answer.setContext(context);
        answer.takeDecisionWithRange1(decisionManager, map);

        Assert.assertEquals(decisionManager.getExploreJson(), decisionManager.getFirstDecisionJson());
        Assert.assertEquals(1, decisionManager.count());

        decisionManager = new DecisionManager();
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 100.0)));
        report = new ArrayList<>();
        report.add(range1);

        answer.takeDecisionWithRange1(decisionManager, map);
        Assert.assertEquals(0, decisionManager.count());
    }

    /**
     * Check the creation of the map with a glimpse ans
     */
    @Test
    public void testInitGlimpse(){
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        answer = new GlimpseAns("OK", 1, new GlimpseExt(4,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        Biome mangrove = new Biome(BiomeEnum.MANGROVE, 80.0);
        Biome beach = new Biome(BiomeEnum.BEACH, 20.0);
        List<Biome> oracle = new ArrayList<>();
        oracle.add(mangrove);
        oracle.add(beach);
        Assert.assertEquals(oracle,currentCase.getBiomes());

        Biome rain = new Biome(BiomeEnum.TROPICAL_RAIN_FOREST, 40.0);
        Biome seosonal = new Biome(BiomeEnum.TROPICAL_SEASONAL_FOREST, 20.0);
        oracle = new ArrayList<>();
        oracle.add(mangrove);
        oracle.add(rain);
        oracle.add(seosonal);
        Assert.assertEquals(oracle, map.getCaseAround(Direction.NORTH).getBiomes());

        oracle = new ArrayList<>();
        oracle.add(rain);
        oracle.add(seosonal);
        Assert.assertEquals(oracle, map.getCaseAround(Direction.NORTH, map.getCaseAround(Direction.NORTH)).getBiomes());

        oracle = new ArrayList<>();
        oracle.add(rain);
        Assert.assertEquals(oracle, map.getCaseAround(Direction.NORTH, map.getCaseAround(Direction.NORTH, map.getCaseAround(Direction.NORTH))).getBiomes());
    }

    /*
    check if the robot move in the direction with the better biome
     */
    @Test
    public void getMostValuableDirection(){
        Mapper map = new Mapper(currentCase);
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        answer = new GlimpseAns("OK", 12, new GlimpseExt(4, report));
        Biome biome50Wood = new Biome(BiomeEnum.TROPICAL_RAIN_FOREST, 50.0);
        Biome biomeMang = new Biome(BiomeEnum.MANGROVE, 50.0);
        Biome biomeLake = new Biome(BiomeEnum.LAKE, 100.0);
        Biome biome100Wood = new Biome(BiomeEnum.TROPICAL_SEASONAL_FOREST, 100.0);

        List<Resource> listResources = new ArrayList<>(Arrays.asList(new Resource("WOOD", "", "")));
        currentCase = new Case(listResources, null, 1);
        currentCase.addBiome(biome100Wood);
        Case caseW = new Case();
        Case caseE = new Case();
        Case caseS = new Case();
        Case caseN = new Case();
        caseW.addBiome(biome100Wood);
        caseE.addBiome(biomeLake);
        caseS.addBiome(biome50Wood);
        caseS.addBiome(biomeMang);
        caseN.addBiome(biome50Wood);

        caseW.setX(-1);
        caseE.setX(1);
        caseS.setY(-1);
        caseN.setY(1);

        map.addCase(caseW);
        map.addCase(caseE);
        map.addCase(caseS);
        map.addCase(caseN);

        Direction result = answer.getMostValuableDirection(map, new ArrayList<>(Arrays.asList(objective)));
        Assert.assertEquals(Direction.WEST, result);
    }

    /*
    check if the method casewwithobj return a case with the better Biome
     */
    @Test
    public void getCaseWithObj(){
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);
        answer = new GlimpseAns("OK", 12, new GlimpseExt(4, report));

        Biome biomeWood = new Biome(BiomeEnum.TROPICAL_RAIN_FOREST, 50.0);
        Biome biomeMang = new Biome(BiomeEnum.MANGROVE, 50.0);
        Biome biomeLake = new Biome(BiomeEnum.LAKE, 100.0);

        List<Resource> listResources = new ArrayList<>(Arrays.asList(new Resource("WOOD", "", "")));
        currentCase = new Case(listResources, null, 1);
        currentCase.addBiome(biomeLake);
        Case range2Case = new Case();
        range2Case.setX(1);
        range2Case.addBiome(biomeWood);
        map.addCase(range2Case);

        Case range3Case = new Case();
        range3Case.setX(2);
        map.addCase(range3Case);

        Case range4Case = new Case();
        range4Case.setX(3);
        map.addCase(range4Case);

        range4Case.addBiome(biomeLake);

        List<Objective> objectives = new ArrayList<>();
        objectives.add(objective);

        Case result = answer.getCaseWithObj(map, objectives, Direction.EAST, 1);
        Assert.assertEquals(result, range2Case);
        result = answer.getCaseWithObj(map,objectives,Direction.EAST,2);
        Assert.assertNotEquals(result, range3Case);
        result = answer.getCaseWithObj(map,objectives,Direction.EAST,3);
        Assert.assertNotEquals(result, range4Case);

        range2Case.addBiome(biomeMang);
        result = answer.getCaseWithObj(map, objectives, Direction.EAST, 1);
        Assert.assertEquals(result, range2Case);
    }

    /**
     * Init case around with range 1
     * with a new case
     */
    @Test
    public void initBiomeAround1WithoutCaseAround() {
        List<List<Object>> report = new ArrayList<>();
        report.add(range1);

        GlimpseAns answer = new GlimpseAns("OK", 12, new GlimpseExt(1, report));
        answer.setDirection(Direction.NORTH);

        answer.init(map);

        List<Biome> biomesOracle = new ArrayList<>();
        biomesOracle.add(new Biome(BiomeEnum.MANGROVE, 80.0));
        biomesOracle.add(new Biome(BiomeEnum.BEACH, 20.0));
        Case currentCaseOracle = new Case();
        currentCaseOracle.setBiomes(biomesOracle);

        Assert.assertEquals(currentCase, currentCaseOracle);
    }

    /**
     * Init case around with range 4
     * with a new case
     */
    @Test
    public void initBiomeAround4WithoutCaseAround() {
        List<List<Object>> report = new ArrayList<>();
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);

        GlimpseAns answer = new GlimpseAns("OK", 12, new GlimpseExt(4, report));
        answer.setDirection(Direction.NORTH);

        answer.init(map);

        List<Biome> biomes1Oracle = new ArrayList<>();
        biomes1Oracle.add(new Biome(BiomeEnum.MANGROVE, 80.0));
        biomes1Oracle.add(new Biome(BiomeEnum.BEACH, 20.0));
        Case currentCaseOracle = new Case();
        currentCaseOracle.setBiomes(biomes1Oracle);

        List<Biome> biomes2Oracle = new ArrayList<>();
        biomes2Oracle.add(new Biome(BiomeEnum.MANGROVE, 40.0));
        biomes2Oracle.add(new Biome(BiomeEnum.TROPICAL_RAIN_FOREST, 20.0));
        biomes2Oracle.add(new Biome(BiomeEnum.TROPICAL_SEASONAL_FOREST, 20.0));
        Case next1CaseOracle = new Case(new Point(0, 1));
        map.addCase(next1CaseOracle);
        next1CaseOracle.setBiomes(biomes2Oracle);

        Assert.assertEquals(map.getCaseAround(Direction.NORTH), next1CaseOracle);
    }

    /**
     * Deserialize JSON with range 1
     */
    @Test
    public void deserializeGlimpseAnswer1() {
        String answer1JSON = "{ \"status\": \"OK\", \"cost\": 12,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 1,\n" +
                "    \"report\": [\n" +
                "      [[\"MANGROVE\", 80.0], [\"BEACH\", 20.0]]\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        GlimpseAns answerFromJson = Utils.json.fromJson(answer1JSON, GlimpseAns.class);

        List<List<Object>> report = new ArrayList<>();
        report.add(range1);

        GlimpseAns answerOracle = new GlimpseAns("OK", 12, new GlimpseExt(1, report));

        Assert.assertEquals(answerFromJson, answerOracle);
    }

    /**
     * Deserialize JSON with range 4
     */
    @Test
    public void deserializeGlimpseAnswer4() {
        String answer4JSON = "{ \"status\": \"OK\", \"cost\": 12,\n" +
                "  \"extras\": {\n" +
                "    \"asked_range\": 4,\n" +
                "    \"report\": [\n" +
                "      [[\"MANGROVE\", 80.0], [\"BEACH\", 20.0]],\n" +
                "      [[\"MANGROVE\", 40.0], [\"TROPICAL_RAIN_FOREST\", 40.0], [\"TROPICAL_SEASONAL_FOREST\", 20.0]],\n" +
                "      [\"TROPICAL_RAIN_FOREST\", \"TROPICAL_SEASONAL_FOREST\"],\n" +
                "      [\"TROPICAL_RAIN_FOREST\"]\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        GlimpseAns answerFromJson = Utils.json.fromJson(answer4JSON, GlimpseAns.class);

        List<List<Object>> report = new ArrayList<>();
        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);

        GlimpseAns answerOracle = new GlimpseAns("OK", 12, new GlimpseExt(4, report));

        Assert.assertEquals(answerFromJson, answerOracle);
    }

    @Test
    public void testSearchInCircleFullOcean() {
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("OCEAN", 80.0)));
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 20.0)));

        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("OCEAN", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_SEASONAL_FOREST", 20.0)));

        range3 = new ArrayList<>();
        range3.add("OCEAN");
        range3.add("TROPICAL_SEASONAL_FOREST");

        range4 = new ArrayList<>();
        range4.add("OCEAN");

        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);

        List<List<Object>> report2 = new ArrayList<>();
        report2.add(range1);
        report2.add(range2);
        report2.add(range3);
        report2.add(range4);

        List<List<Object>> report3 = new ArrayList<>();
        report3.add(range1);
        report3.add(range2);
        report3.add(range3);
        report3.add(range4);

        List<List<Object>> report4 = new ArrayList<>();
        report4.add(range1);
        report4.add(range2);
        report4.add(range3);
        report4.add(range4);

        answer = new GlimpseAns("OK", 1, new GlimpseExt(4,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        GlimpseAns answer2 = new GlimpseAns("OK", 1, new GlimpseExt(4,report2));
        answer2.setDirection(Direction.WEST);
        answer2.init(map);

        GlimpseAns answer3 = new GlimpseAns("OK", 1, new GlimpseExt(4,report3));
        answer3.setDirection(Direction.EAST);
        answer3.init(map);

        GlimpseAns answer4 = new GlimpseAns("OK", 1, new GlimpseExt(4,report4));

        answer4.setDirection(Direction.SOUTH);
        answer4.init(map);

        answer4.searchInCircle(decisionManager, map, new Biome(BiomeEnum.OCEAN, null));

        Assert.assertEquals(true, decisionManager.isEmpty());
    }

    /**
     * Test next decision after glimpse if the current case is in
     * a good biome and already explore
     */
    @Test
    public void testGlimpseCurrentCaseKnown() {
        currentCase = new Case();
        currentCase.setResources(new ArrayList<Resource>());
        Mapper map = new Mapper(currentCase);
        report.add(range1);
        answer = new GlimpseAns("OK", 20, new GlimpseExt(1, report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);
        answer.setContext(context);

        answer.nextDecision(decisionManager, map);

        DecisionManager oracleDM = new DecisionManager();
        Assert.assertNotEquals(oracleDM.getExploreJson(), decisionManager.getFirstDecisionJson());
    }

    @Test
    public void testSearchInCircleThreeOcean() {
        currentCase = new Case();
        Mapper map = new Mapper(currentCase);
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("OCEAN", 80.0)));
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 20.0)));

        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("OCEAN", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_SEASONAL_FOREST", 20.0)));

        range3 = new ArrayList<>();
        range3.add("OCEAN");
        range3.add("TROPICAL_SEASONAL_FOREST");

        range4 = new ArrayList<>();
        range4.add("OCEAN");

        List<List<Object>> reportWithoutOcean = new ArrayList<>();

        List<Object> range4WithoutOcean = new ArrayList<>();
        range4WithoutOcean.add("TROPICAL_RAIN_FOREST");

        reportWithoutOcean.add(range1);
        reportWithoutOcean.add(range2);
        reportWithoutOcean.add(range3);
        reportWithoutOcean.add(range4WithoutOcean);

        report.add(range1);
        report.add(range2);
        report.add(range3);
        report.add(range4);


        answer = new GlimpseAns("OK", 1, new GlimpseExt(4,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        GlimpseAns answer2 = new GlimpseAns("OK", 1, new GlimpseExt(4,report));
        answer2.setDirection(Direction.WEST);
        answer2.init(map);

        GlimpseAns answer3 = new GlimpseAns("OK", 1, new GlimpseExt(4,report));
        answer3.setDirection(Direction.EAST);
        answer3.init(map);

        GlimpseAns answer4 = new GlimpseAns("OK", 1, new GlimpseExt(4,reportWithoutOcean));
        answer4.setDirection(Direction.SOUTH);
        answer4.init(map);

        answer4.searchInCircle(decisionManager, map, new Biome(BiomeEnum.OCEAN, null));
        Assert.assertEquals(3, decisionManager.count()); // Makes sure 3 moves have been added

        MoveDec moveSouth = new MoveDec(Direction.SOUTH);
        String moveDec = moveSouth.toJSON();
        String firstDec = decisionManager.getFirstDecisionJson();

        Assert.assertEquals(moveDec, firstDec); // Checks that the first decision is a move to south, where there is no ocean
    }

    /**
     * check if the robot choose the direction with the lower rate of ocean
     */
    @Test
    public void testMoveCaseLowOcean(){
        Mapper map = new Mapper(currentCase);
        Case north = new Case();
        north.setY(1);
        map.addCase(north);
        north.addBiome(new Biome(BiomeEnum.OCEAN,80.0));
        Case south = new Case();
        south.setY(-1);
        map.addCase(south);
        south.addBiome(new Biome(BiomeEnum.OCEAN, 70.0));
        answer = new GlimpseAns("OK", 10, new GlimpseExt(4,report));
        answer.moveToCaseWithLowOcean(decisionManager,map);

        MoveDec oracle = new MoveDec(Direction.SOUTH);
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());
    }

    /**
     * Checks if the function returns true when it should (certain cases doesn't contain ocean)
     * Checks if it adds the correct number of move
     */
    @Test
    public void testCheckOceanInRange(){
        Mapper map = new Mapper(currentCase);
        Case north1 = new Case();
        Case north2 = new Case();
        Case north3 = new Case();
        north1.setY(1);
        north2.setY(2);
        north3.setY(3);

        map.addCase(north1);
        map.addCase(north2);
        map.addCase(north3);

        north1.addBiome(new Biome(BiomeEnum.OCEAN, 2.5));
        north2.addBiome(new Biome(BiomeEnum.OCEAN, 80.0));
        north3.addBiome(new Biome(BiomeEnum.OCEAN, 100.0));


        answer = new GlimpseAns("OK", 10, new GlimpseExt(4,report));
        boolean boolRange1 = answer.checkOceanInRange(decisionManager, map, Direction.NORTH, 1);
        Assert.assertEquals(false, boolRange1);
        Assert.assertEquals(0, decisionManager.count());

        boolean boolRange2 = answer.checkOceanInRange(decisionManager, map, Direction.NORTH, 2);
        Assert.assertEquals(false, boolRange2);
        Assert.assertEquals(0, decisionManager.count());

        boolean boolRange3 = answer.checkOceanInRange(decisionManager, map, Direction.NORTH, 3);
        Assert.assertEquals(false, boolRange3);


        Case south1 = new Case();
        Case south2 = new Case();
        Case south3 = new Case();
        south1.setY(-1);
        south2.setY(-2);
        south3.setY(-3);
        map.addCase(south1);
        map.addCase(south2);
        map.addCase(south3);
        south1.addBiome(new Biome(BiomeEnum.BEACH, 100.0));
        south2.addBiome(new Biome(BiomeEnum.BEACH, 100.0));
        south3.addBiome(new Biome(BiomeEnum.BEACH, 100.0));

        MoveDec oracle = new MoveDec(Direction.SOUTH);

        boolean boolBeach1 = answer.checkOceanInRange(decisionManager, map, Direction.SOUTH, 1);
        Assert.assertEquals(true, boolBeach1);
        Assert.assertEquals(1, decisionManager.count());
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

        decisionManager.clean();
        boolean boolBeach2 = answer.checkOceanInRange(decisionManager, map, Direction.SOUTH, 2);
        Assert.assertEquals(true, boolBeach2);
        Assert.assertEquals(2, decisionManager.count());
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

        decisionManager.clean();
        boolean boolBeach3 = answer.checkOceanInRange(decisionManager, map, Direction.SOUTH, 3);
        Assert.assertEquals(true, boolBeach3);
        Assert.assertEquals(3, decisionManager.count());
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

        decisionManager.clean();
        currentCase.setBiomes(Arrays.asList((new Biome(BiomeEnum.OCEAN, 100.0))));
        boolean boolRange0 = answer.checkOceanInRange(decisionManager, map, Direction.SOUTH, 0);
        Assert.assertEquals(false, boolRange0);
        Assert.assertEquals(0, decisionManager.count()); //vérif que ça n'a pas ajouté de move

        decisionManager.clean();
        currentCase.setBiomes(Arrays.asList((new Biome(BiomeEnum.BEACH, 100.0))));
        boolRange0 = answer.checkOceanInRange(decisionManager, map, Direction.SOUTH, 0);
        Assert.assertEquals(true, boolRange0);
        Assert.assertEquals(0, decisionManager.count()); //vérif que ça n'a pas ajouté de move
    }

    @Test
    public void testUnreachable(){
        Mapper map = new Mapper(currentCase);
        range1 = new ArrayList<>();
        range1.add(new ArrayList<Object>(Arrays.asList("OCEAN", 80.0)));
        range1.add(new ArrayList<Object>(Arrays.asList("BEACH", 20.0)));

        range2 = new ArrayList<>();
        range2.add(new ArrayList<Object>(Arrays.asList("OCEAN", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_RAIN_FOREST", 40.0)));
        range2.add(new ArrayList<Object>(Arrays.asList("TROPICAL_SEASONAL_FOREST", 20.0)));

        range3 = new ArrayList<>();
        range3.add("OCEAN");
        range3.add("TROPICAL_SEASONAL_FOREST");

        report.add(range1);
        report.add(range2);
        report.add(range3);

        answer = new GlimpseAns("OK", 10, new GlimpseExt(4,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        Assert.assertFalse(map.getCaseAround(Direction.NORTH, 2).isUnreachable());
        Assert.assertTrue(map.getCaseAround(Direction.NORTH, 3).isUnreachable());

        Assert.assertEquals(3, answer.getExtras().getResources().size());
        Assert.assertEquals(true, answer.isUnreachable());
        StopDec oracle = new StopDec();
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

        report.remove(report.size() - 1);
        answer = new GlimpseAns("OK", 10, new GlimpseExt(4,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        Assert.assertEquals(2, answer.getExtras().getResources().size());
        Assert.assertEquals(true, answer.isUnreachable());
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

        answer = new GlimpseAns("OK", 10, new GlimpseExt(3,report));
        answer.setDirection(Direction.NORTH);
        answer.init(map);

        Assert.assertEquals(2, answer.getExtras().getResources().size());
        Assert.assertEquals(true, answer.isUnreachable());
        Assert.assertEquals(oracle.toJSON(), decisionManager.getFirstDecisionJson());

    }

    @Test
    public void testEqualsAndHashcode() {
        String status = "status";
        GlimpseExt glimpseExt = new GlimpseExt(10, null);
        GlimpseAns x = new GlimpseAns(status, 10, glimpseExt);
        GlimpseAns y = new GlimpseAns(status, 10, glimpseExt);
        StopDec z = new StopDec();
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertFalse(x.equals(z));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }
}
