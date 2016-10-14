package fr.unice.polytech.ogl.islda.model;

import fr.unice.polytech.ogl.islda.answers.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pascal Tung
 * @version 23/02/15
 */
public class Context {
    private String creek;
    private int    budget;
    private int minimumBudget = 0;
    private int maxCostAction = 0;
    private int men;
    private List<Objective> objective = new ArrayList<>();

    /**
     * Update budget and minimum budget to quit
     * @param result The result of the last decision
     */
    public void updateBudget(Answer result) {
        int factor = 2;

        decreaseBudget(result.getCost());
        if (getMaxCostAction() < result.getCost()) {
            int landCost = getMinimumBudget()-factor*getMaxCostAction();

            setMaxCostAction(result.getCost());
            setMinimumBudget(landCost+2*getMaxCostAction());
        }
    }

    /**
     * add the necessary primary resource to create secondary resource
     */
    public void updatePrimaryRes() {
        List<Objective> newObjectives = new ArrayList<>(objective);
        for (Objective obj : objective) {
            List<ResourceSec> primaryList = ResourceSecEnum.getPrimaryList(obj);
            if (primaryList != null) {
                obj.setLevel(2);
                for (ResourceSec primary : primaryList) {
                    int indexPrim = -1;
                    for (int i = 0; i < newObjectives.size(); ++i) {
                        if (newObjectives.get(i).getResource().equals(primary.getResource())) {
                            indexPrim = i;
                            break;
                        }
                    }

                    int newAmount = (int) Math.ceil(primary.getAmount() * obj.getAmount());
                    if (indexPrim != -1) {
                        newAmount += newObjectives.get(indexPrim).getAmount();
                        newObjectives.get(indexPrim).setAmount(newAmount);
                    } else {
                        Objective newObj = new Objective(primary.getResource(), newAmount);
                        newObj.setUseless(true);
                        newObjectives.add(newObj);
                    }
                }
            }
        }

        objective = newObjectives;
    }

    /**
     * Get objectives by level of resource
     * @param level The level of the resources
     * @return List of objectives
     */
    public List<Objective> getObjectivesByLevel(int level) {
        List<Objective> objectives = new ArrayList<>();

        for (Objective obj : objective) {
            if (obj.getLevel() == level) {
                objectives.add(obj);
            }
        }

        return objectives;
    }

    /**
     * Tests the validity of the object
     *
     * @return if it's a exploitable context, return true else false
     */
    public boolean isValid() {
        return !(creek == null || "".equals(creek)) && budget > 0 && men > 0 && objective != null;
    }

    /**
     * Increase objective amount and remove it if the contract has been completed
     * @param resource Objective's resource to increase
     * @param amount Value to increase
     */
    public void increaseObjective(String resource, int amount) {
        Objective obj = getObjective(resource);
        if (obj != null) {
            obj.increaseHarvest(amount);

            if (obj.getHarvestedAmount() >= obj.getAmount()) {
                if (!obj.isUseless()) {
                    objective.remove(objective.indexOf(obj));
                }

                if (obj.getLevel() > 1) {
                    for (ResourceSec priRes : ResourceSecEnum.getPrimaryList(obj)) {
                        decreaseObjective(
                            priRes.getResource(),
                            (int) Math.round(obj.getAmount() * priRes.getAmount())
                        );
                    }
                }
            }
        }
    }

    /**
     * Decrease objective amount and remove it if the contract has been completed
     * @param resource Objective's resource to decrease
     * @param amount Value to decrease
     */
    public void decreaseObjective(String resource, int amount) {
        Objective obj = getObjective(resource);
        if (obj != null) {
            obj.decreaseAmount(amount);
            if (obj.getAmount() <= 0) {
                objective.remove(objective.indexOf(obj));
            }
        }
    }

    public Objective getObjective(String resource) {
        Objective objec = null;
        for (Objective obj : objective) {
            if (obj.getResource().equals(resource)) {
                objec = obj;
            }
        }

        return objec;
    }

    public boolean noMoreContract() {
        return objective == null || objective.isEmpty();
    }

    /**
     * Decrease the available budget
     *
     * @param cost The cost of the last action
     */
    public void decreaseBudget(int cost) {
        budget -= cost;
    }

    public String getCreek() {
        return creek;
    }
    public int getBudget() {
        return budget;
    }

    public void setCreek(String creek) {
        this.creek = creek;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getMen() {
        return men;
    }

    public List<Objective> getObjective() {
        return objective;
    }

    public void setObjective(List<Objective> objective) {
        this.objective = objective;
    }

    public int getMinimumBudget() {
        return minimumBudget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Context)) {
            return false;
        }

        Context context = (Context) o;

        return budget == context.budget
                && men == context.men
                && minimumBudget == context.minimumBudget
                && creek.equals(context.creek)
                && objective.equals(context.objective);

    }

    public void setMen(int men) {
        this.men = men;
    }

    public void setMinimumBudget(int minimumBudget) {

        this.minimumBudget = minimumBudget;
    }

    public int getMaxCostAction() {
        return maxCostAction;
    }

    public void setMaxCostAction(int maxCostAction) {
        this.maxCostAction = maxCostAction;
    }

    @Override
    public int hashCode() {

        int result = creek != null ? creek.hashCode() : 0;
        result = 31 * result + budget;
        result = 31 * result + minimumBudget;
        result = 31 * result + men;
        result = 31 * result + (objective != null ? objective.hashCode() : 0);
        return result;
    }
}

