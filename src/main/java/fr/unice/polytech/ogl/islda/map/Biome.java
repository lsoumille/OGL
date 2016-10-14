package fr.unice.polytech.ogl.islda.map;

import java.util.List;

/**
 * @author Pascal Tung
 * @version 24/03/15
 */
public class Biome {
    private BiomeEnum biomeName;
    private Double rate;

    public Biome(BiomeEnum biome, Double rate) {
        this.biomeName = biome;
        this.rate = rate;
    }

    /**
     * Get the value of a list of biome object
     *
     * @param biomes List of biome
     * @return The value of the biomes
     */
    public static Double getListBiomeValue(List<Biome> biomes) {
        Double value = 0.0;
        for (Biome biome : biomes) {
            if (biome.getRate() != null) {
                value += biome.getRate();
            }
        }

        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Biome biome1 = (Biome) o;

        return biomeName == biome1.biomeName;
    }

    @Override
    public int hashCode() {
        int result = biomeName != null ? biomeName.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    public Double getRate() {
        return rate;
    }

    public BiomeEnum getBiome() {
        return biomeName;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
