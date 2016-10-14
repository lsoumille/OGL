package fr.unice.polytech.ogl.islda.parameters;

import fr.unice.polytech.ogl.islda.model.Objective;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Martinez
 * @version 09/05/15
 */
public class TransformParameters {
    private List<Objective> resources;

    /**
     * Constructor when we need only one resource to transform (ex : fur => leather)
     */
    public TransformParameters(Objective res) {
        resources = new ArrayList<>();
        resources.add(res);
    }

    /**
     * Constructor when we need two or more resources to transform (ex : quartz + wood => glass)
     */
    public TransformParameters(List<Objective> res) {
        this.resources = res;
    }

    public List<Objective> getResources() {
        return resources;
    }
}
