package fr.unice.polytech.ogl.islda.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lucas Martinez
 * @version 09/03/2015.
 */
public class ContextTest {
    private Context context;

    @Before
    public void setUp() {
        Objective objective = new Objective("FISH", 90);
        Objective secondObjective = new Objective("QUARTZ", 100);
        context = new Context();
        int defaultBudget = 500;
        context.setBudget(defaultBudget);
        List<Objective> allObjectives = new ArrayList<>();
        allObjectives.add(objective);
        allObjectives.add(secondObjective);
        context.setObjective(allObjectives);
    }

    @Test
    public void decreaseBudget(){
        context.decreaseBudget(5);
        assertEquals(495, context.getBudget());
    }

    @Test
    public void deletePrimaryObjective() {
        assertEquals(1, context.getObjective("QUARTZ").getLevel());
        assertEquals(100, context.getObjective("QUARTZ").getAmount());
        assertEquals(0, context.getObjective("QUARTZ").getHarvestedAmount());
        context.increaseObjective("QUARTZ", 99);
        assertNotNull(context.getObjective("QUARTZ"));
        assertEquals(100, context.getObjective("QUARTZ").getAmount());
        assertEquals(99, context.getObjective("QUARTZ").getHarvestedAmount());
        context.increaseObjective("QUARTZ", 20);
        assertNull(context.getObjective("QUARTZ"));
    }

    @Test
    public void deleteSecObjective() {
        List<Objective> newObjectives = context.getObjective();
        newObjectives.add(new Objective("GLASS", 15));
        context.setObjective(newObjectives);
        context.updatePrimaryRes();

        assertEquals(1, context.getObjective("QUARTZ").getLevel());
        assertEquals(250, context.getObjective("QUARTZ").getAmount());
        assertEquals(0, context.getObjective("QUARTZ").getHarvestedAmount());
        assertEquals(2, context.getObjective("GLASS").getLevel());
        assertEquals(15, context.getObjective("GLASS").getAmount());
        assertEquals(0, context.getObjective("GLASS").getHarvestedAmount());

        context.increaseObjective("GLASS", 2);
        assertNotNull(context.getObjective("QUARTZ"));
        assertEquals(15, context.getObjective("GLASS").getAmount());
        assertEquals(2, context.getObjective("GLASS").getHarvestedAmount());
        assertEquals(250, context.getObjective("QUARTZ").getAmount());
        assertEquals(0, context.getObjective("QUARTZ").getHarvestedAmount());

        context.increaseObjective("GLASS", 14);
        assertNull(context.getObjective("GLASS"));
        assertEquals(100, context.getObjective("QUARTZ").getAmount());
        assertEquals(0, context.getObjective("QUARTZ").getHarvestedAmount());
    }

    @Test
    public void increaseObjective() {
        String resource = "FISH";
        int amount = 40;
        String secondResource = "QUARTZ";
        int secondAmount = 20;
        context.increaseObjective(resource, amount);
        assertEquals(40, context.getObjective(resource).getHarvestedAmount());
        context.increaseObjective(secondResource, secondAmount);
        assertEquals(20, context.getObjective(secondResource).getHarvestedAmount());
    }

    /**
     * check if the resource is deleted when the contract is done
     */
    @Test
    public void testDeleteObjectiveDone(){
        String resource = "FISH";
        int amount = 90;
        context.increaseObjective(resource, amount);

        assertEquals(null, context.getObjective(resource));
    }

    /**
     * check if the amount of a primary res increase
     */
    @Test
    public void testUpdateObjective(){
        List<Objective> currentObj = new ArrayList<>();
        Objective plank = new Objective("PLANK", 8);
        Objective wood = new Objective("WOOD", 10);
        currentObj.add(plank);
        currentObj.add(wood);
        context.setObjective(currentObj);

        context.updatePrimaryRes();
        assertEquals(12, wood.getAmount());
        assertEquals(8, plank.getAmount());
        assertEquals(2, context.getObjective().size());
    }

    /**
     * check if a new objective is add when it's necessary to create secondary res
     */
    @Test
    public void testAddObjective(){
        List<Objective> currentObj = context.getObjective();
        Objective plank = new Objective("PLANK", 2);
        currentObj.add(plank);
        Objective wood = new Objective("WOOD", 10);

        assertEquals(3, context.getObjective().size());
        assertNull(context.getObjective("WOOD"));

        context.updatePrimaryRes();
        assertEquals(4, context.getObjective().size());
        assertEquals(wood, context.getObjective("WOOD"));
    }

    /**
     * Check the updatePrimaryRes with multiple secondary objectives
     */
    @Test
    public void testUpdateMultiObjective(){
        List<Objective> currentObj = new ArrayList<>();
        Objective glass = new Objective("GLASS", 10);
        Objective plank = new Objective("PLANK", 12);
        currentObj.add(glass);
        currentObj.add(plank);
        context.setObjective(currentObj);

        context.updatePrimaryRes();
        assertEquals(10, glass.getAmount());
        assertEquals(100, context.getObjective("QUARTZ").getAmount());
        assertEquals(53, context.getObjective("WOOD").getAmount());
        assertEquals(12, plank.getAmount());
        assertEquals(4, context.getObjective().size());
    }
}
