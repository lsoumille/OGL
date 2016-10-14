package fr.unice.polytech.ogl.islda.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lucas SOUMILLE
 * @version 09/05/15
 */
public enum ResourceSecEnum {
    GLASS(false, 0.5, new ResourceSec("QUARTZ", 10), new ResourceSec("WOOD", 5)),
    INGOT(true, 1.0, new ResourceSec("ORE", 5), new ResourceSec("WOOD", 5)),
    PLANK(false, 0.1, new ResourceSec("WOOD", 0.25)),
    LEATHER(false, 1.2, new ResourceSec("FUR", 3)),
    RUM(true, 3.0, new ResourceSec("SUGAR_CANE", 10), new ResourceSec("FRUITS", 1));

    private List<ResourceSec> resources;
    private boolean isAloneActivity;
    private double costFactor;

    private ResourceSecEnum(boolean isAloneActivity, double costFactor, ResourceSec... resources) {
        this.isAloneActivity = isAloneActivity;
        this.costFactor = costFactor;
        this.resources = new ArrayList<>(Arrays.asList(resources));
    }

    public List<ResourceSec> getResForTransform() {
        return resources;
    }

    public double getCostFactor() {
        return costFactor;
    }

    /**
     * Return the primary resource with the amount to create the resource in param
     * @param obj
     * @return
     */
    public static List<ResourceSec> getPrimaryList(Objective obj) {
        for (ResourceSecEnum sec : ResourceSecEnum.values()) {
            if (sec.name().equals(obj.getResource())) {
                return sec.getResForTransform();
            }
        }

        return null;
    }
}