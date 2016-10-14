package fr.unice.polytech.ogl.islda.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.unice.polytech.ogl.islda.model.Resource;
import fr.unice.polytech.ogl.islda.parameters.TransformParamSerializer;
import fr.unice.polytech.ogl.islda.parameters.TransformParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Soumille
 * @version 26/02/15
 *
 * contains some useful methods and attributes
 *
 */
public class Utils {
    /**
     * Object use to create JSON file
     */
    public static Gson json = new Gson();

    /**
     * Private constructor to hide the implicit public one
     */
    private Utils() {
    }

    /**
     * return list of Resource from a list of resource names
     * @param stringResources
     * @return
     */
    public static List<Resource> listStringToListResource(List<String> stringResources) {
        List<Resource> listResources = new ArrayList<>();
        for (String resource : stringResources) {
            listResources.add(new Resource(resource, "", ""));
        }

        return listResources;
    }

    /**
     * return list of resource names from a list of Resource
     * @param listResources
     * @return
     */
    public static List<String> listResourceToListString(List<Resource> listResources) {
        if (listResources == null) {
            return new ArrayList<>();
        }

        List<String> stringResources = new ArrayList<>();
        for (Resource resource : listResources) {
            stringResources.add(resource.getResource());
        }

        return stringResources;
    }

    /**
     * create a specific object for the json parser
     */
    public static void init() {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(TransformParameters.class, new TransformParamSerializer());
        json = gson.create();
    }
}
