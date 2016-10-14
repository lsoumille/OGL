package fr.unice.polytech.ogl.islda.model;

import fr.unice.polytech.ogl.islda.map.Biome;
import fr.unice.polytech.ogl.islda.map.BiomeEnum;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Pascal Tung
 * @version 23/02/15
 */
public class Objective {
    private String resource;
    private int    amount;
    private int    harvestedAmount = 0;
    private int level = 1;
    private boolean useless = false;

    public Objective(String resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    /**
     * Retain the Objective which are in listResources
     * @param objectives List of objectives
     * @param listResources List of string resources
     * @return The original objectives minus those are not in the resources list
     */
    public static List<String> match(List<Objective> objectives, List<String> listResources) {
        List<String> objectivesStr = new ArrayList<>();
        for (Objective obj : objectives) {
            objectivesStr.add(obj.getResource());
        }
        objectivesStr = new ArrayList<>(new LinkedHashSet(objectivesStr));

        objectivesStr.retainAll(listResources);
        return objectivesStr;
    }

    /**
     * Gets the list of biomes corresponding to the list of objectives
     * @param objectives List of objective
     * @return availableBiomes
     */
    public static List<Biome> getAvailableBiomes(List<Objective> objectives) {
        List<Biome> availableBiomes = new ArrayList<>();

        for (Objective obj : objectives) {
            availableBiomes.addAll(obj.getAvailableBiomes());
        }

        return availableBiomes;
    }

    /**
     * Gets the list of biomes corresponding to the current objective
     * @return availableBiomes
     */
    public List<Biome> getAvailableBiomes() {
        List<Biome> availableBiomes = new ArrayList<>();

        for (BiomeEnum biomeEnum : BiomeEnum.values()) {
            if (biomeEnum.getAvailableResources().contains(resource)) {
                availableBiomes.add(new Biome(biomeEnum, null));
            }
        }

        return availableBiomes;
    }

    /**
     * Check if we can finish the current objective with the harvested resources
     * @param context The context with the list of objectives
     * @return true if we can finish the contract
     */
    public boolean canFinishContract(Context context) {
        if (level == 1 && harvestedAmount >= amount) {
            return true;
        }
        if (level == 2) {
            boolean toTransform = true;
            for (ResourceSec priRes : ResourceSecEnum.getPrimaryList(this)) {
                Objective priObj = context.getObjective(priRes.getResource());
                if (priObj != null) {
                    int wantedValue = amount-harvestedAmount;
                    if (priObj.getHarvestedAmount() < (wantedValue*priRes.getAmount())) {
                        toTransform = false;
                    }
                }
            }
            return toTransform;
        }

        return false;
    }

    /**
     * Check if the primary resources associated are useless or if we have enough to finish the primary contract
     * @param context The context with the list of objectives
     * @return true if we can transform
     */
    public boolean canTransform(Context context) {
        if (level == 2) {
            boolean toTransform = true;
            for (ResourceSec priRes : ResourceSecEnum.getPrimaryList(this)) {
                Objective priObj = context.getObjective(priRes.getResource());
                if (priObj != null) {
                    int wantedValue = amount-harvestedAmount-1;
                    if (!priObj.useless && priObj.getHarvestedAmount() < (priObj.getAmount()-(wantedValue*priRes.getAmount()))) {
                        toTransform = false;
                    } else if (priObj.useless && priObj.getHarvestedAmount() < priRes.getAmount()) {
                        toTransform = false;
                    }
                }
            }
            return toTransform;
        }

        return false;
    }

    /**
     * Increase the harvested amount of the objective
     *
     * @param value Value to increase
     */
    public void increaseHarvest(int value) {
        harvestedAmount += value;
    }

    public void decreaseHarvest(int amount) {
        harvestedAmount -= amount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUseless() {
        return useless;
    }

    public void setUseless(boolean useless) {
        this.useless = useless;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
    }

    public int getHarvestedAmount() {
        return harvestedAmount;
    }

    public void setHarvestedAmount(int amount) {
        this.harvestedAmount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Objective)) {
            return false;
        }

        Objective objective = (Objective) o;

        return resource.equals(objective.resource);

    }

    @Override
    public int hashCode() {
        int result = resource != null ? resource.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }
}
