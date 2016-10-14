package fr.unice.polytech.ogl.islda.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pascal Tung
 * @version 24/03/15
 */
public enum BiomeEnum {
    MANGROVE("WOOD", "FLOWER"),
    SNOW(),
    TROPICAL_RAIN_FOREST("WOOD", "SUGAR_CANE", "FRUITS"),
    TROPICAL_SEASONAL_FOREST("WOOD", "SUGAR_CANE", "FRUITS"),
    TAIGA("WOOD"),
    TEMPERATE_RAIN_FOREST("WOOD", "FUR"),
    TEMPERATE_DECIDUOUS_FOREST("WOOD"),
    GRASSLAND("FUR"),
    SHRUBLAND("FUR"),
    TUNDRA("FUR"),
    ALPINE("ORE", "FLOWER"),
    BEACH("QUARTZ"),
    SUB_TROPICAL_DESERT("ORE", "QUARTZ"),
    TEMPERATE_DESERT("ORE", "QUARTZ"),
    OCEAN("FISH"),
    LAKE("FISH"),
    GLACIER("FLOWER");

    private List<String> availableResources;

    private BiomeEnum(String... resources) {
        availableResources = new ArrayList<>(Arrays.asList(resources));
    }

    public List<String> getAvailableResources() {
        return availableResources;
    }
}
