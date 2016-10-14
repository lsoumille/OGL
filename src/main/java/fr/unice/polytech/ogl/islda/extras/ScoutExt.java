package fr.unice.polytech.ogl.islda.extras;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Soumille
 * @version 06/03/15
 */
public class ScoutExt extends Extras {
    private List<String> resources;
    private int          altitude;
    private boolean unreachable = false;

    public ScoutExt(List<String> resources, int altitude) {
        this.resources = resources;
        this.altitude = altitude;
    }

    /**
     * Default contructor
     */
    public ScoutExt() {
        resources = new ArrayList<>();
    }

    @Override
    public List<String> getResources() {
        return resources;
    }

    public int getAltitude() {
        return altitude;
    }

    public boolean isUnreachable() {
        return unreachable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoutExt)) {
            return false;
        }

        ScoutExt scoutExt = (ScoutExt) o;

        return altitude == scoutExt.altitude && resources.equals(scoutExt.resources);
    }

    @Override
    public int hashCode() {
        int result = resources != null ? resources.hashCode() : 0;
        result = 31 * result + altitude;
        result = 31 * result + (unreachable ? 1 : 0);
        return result;
    }

    public void setUnreachable(){
        unreachable = true;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
