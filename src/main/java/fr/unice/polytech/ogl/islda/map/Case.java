package fr.unice.polytech.ogl.islda.map;

import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lucas SOUMILLE
 * @version 13/03/15
 */
public class Case {
    private int altitude;
    private List<Resource> resources = new ArrayList<>();
    private List<Pois> pois;
    private List<Biome> biomes = new ArrayList<>();
    private boolean visited = false;
    private boolean unreachable = false;
    private Point coordinate;

    public Case() {
        this.resources = null;
        this.pois = new ArrayList<>();
        this.altitude = 0;
        this.coordinate = new Point();
    }

    public Case(List<Resource> resources,
                List<Pois> pois,
                int altitude) {
        this.resources = resources;
        this.pois = pois;
        this.altitude = altitude;
        this.coordinate = new Point();
    }

    public Case(Point coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns if the case is known
     * @return True if the case is already scout, explore or glimpse
     */
    public boolean isKnown() {
        return resources != null || (biomes != null && !biomes.isEmpty());
    }

    /**
     * Returns if the resources are known
     * @return True if the case is already scout, explore
     */
    public boolean resourcesKnown() {
        return resources != null;
    }

    /**
     * Add biome to the biome list
     * @param biome
     */
    public void addBiome(Biome biome){
        if (biomes == null) {
            biomes = new ArrayList<>();
        }

        for (Biome cBiome : biomes) {
            if (biome.equals(cBiome)) {
                if (cBiome.getRate() == null && biome.getRate() != null) {
                    cBiome.setRate(biome.getRate());
                }
                return;
            }
        }

        biomes.add(biome);
    }

    /**
     * Removes a resource of the resource list
     * @param resource Name of the resource
     */
    public void removeResource(String resource) {
        if (resources != null) {
            for (Iterator<Resource> it = resources.iterator(); it.hasNext(); ) {
                Resource res = it.next();
                if (resource.equals(res.getResource())) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Returns the rate of the biome in parameter
     * @param biomeEnum
     * @return
     */
    public Double getRateBiome(BiomeEnum biomeEnum) {
        for (Biome biome : biomes) {
            if (biome.getBiome().equals(biomeEnum)) {
                return (biome.getRate() != null) ? biome.getRate() : 0.0;
            }
        }
        return 0.0;
    }

    /**
     * Set visited true
     */
    public void setVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Case aCase = (Case) o;

        if (altitude != aCase.altitude) return false;
        if (visited != aCase.visited) return false;
        if (resources != null ? !resources.equals(aCase.resources) : aCase.resources != null) return false;
        if (pois != null ? !pois.equals(aCase.pois) : aCase.pois != null) return false;
        if (biomes != null ? !biomes.equals(aCase.biomes) : aCase.biomes != null) return false;
        return !(coordinate != null ? !coordinate.equals(aCase.coordinate) : aCase.coordinate != null);

    }

    @Override
    public int hashCode() {
        int result = altitude;
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        result = 31 * result + (pois != null ? pois.hashCode() : 0);
        result = 31 * result + (biomes != null ? biomes.hashCode() : 0);
        result = 31 * result + (visited ? 1 : 0);
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        return result;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public List<Biome> getBiomes() {
        return biomes;
    }

    public void setBiomes(List<Biome> biomes) {
        this.biomes = biomes;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Pois> getPois() {
        return pois;
    }

    public void setPois(List<Pois> pois) {
        this.pois = pois;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public int getX() {
        return coordinate.getX();
    }

    public void setX(int x) {
        coordinate.setX(x);
    }

    public int getY() {
        return coordinate.getY();
    }

    public void setY(int y) {
        coordinate.setY(y);
    }

    public boolean isUnreachable() {
        return unreachable;
    }

    public void setUnreachable(boolean unreachable) {
        this.unreachable = unreachable;
    }
}
