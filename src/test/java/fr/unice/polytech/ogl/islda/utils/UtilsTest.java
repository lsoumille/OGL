package fr.unice.polytech.ogl.islda.utils;

import fr.unice.polytech.ogl.islda.model.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lucas SOUMILLE
 * @date : 18/04/15
 */
public class UtilsTest {

    Resource wood;
    Resource fish;

    @Before
    public void setUp(){
        wood = new Resource("WOOD", "","");
        fish = new Resource("FISH", "","");
    }

    @Test
    public void testListStringToListResource(){
        ArrayList<String> listStrRes = new ArrayList<String>();
        listStrRes.add("WOOD");
        listStrRes.add("FISH");

        List<Resource> listRes = Utils.listStringToListResource(listStrRes);
        Assert.assertEquals(listRes.getClass(), new ArrayList<Resource>().getClass());

        Assert.assertEquals(wood, listRes.get(0));
        Assert.assertEquals(fish, listRes.get(1));
    }

    @Test
    public void testListResourceToListString(){
        List<Resource> listRes  = new ArrayList<Resource>();
        listRes.add(wood);
        listRes.add(fish);

        List<String> listStrRes = Utils.listResourceToListString(null);
        Assert.assertEquals(new ArrayList<>().getClass(), listStrRes.getClass());

        listStrRes = Utils.listResourceToListString(listRes);
        Assert.assertEquals("WOOD", listStrRes.get(0));
        Assert.assertEquals("FISH", listStrRes.get(1));
    }
}
