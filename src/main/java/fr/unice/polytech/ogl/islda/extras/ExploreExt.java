package fr.unice.polytech.ogl.islda.extras;

import fr.unice.polytech.ogl.islda.model.Pois;
import fr.unice.polytech.ogl.islda.model.Resource;

import java.util.List;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class ExploreExt extends Extras {
    private List<Resource> resources;
    private List<Pois> pois;

    public ExploreExt(List<Resource> resources, List<Pois> pois) {
        this.resources = resources;
        this.pois = pois;
    }

    @Override
    public List<Resource> getResources() {
        return resources;
    }

    public List<Pois> getPois() {
        return pois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExploreExt)) {
            return false;
        }

        ExploreExt that = (ExploreExt) o;

        return pois.equals(that.pois) && resources.equals(that.resources);
    }

    @Override
    public int hashCode() {
        int result = resources != null ? resources.hashCode() : 0;
        result = 31 * result + (pois != null ? pois.hashCode() : 0);
        return result;
    }
}
