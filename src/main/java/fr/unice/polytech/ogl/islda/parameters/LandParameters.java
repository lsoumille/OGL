package fr.unice.polytech.ogl.islda.parameters;

/**
 * @author Lucas Soumille
 * @version 26/02/15
 */
public class LandParameters {
    private String creek;
    private int people;

    public LandParameters(String creek, int people) {
        this.creek = creek;
        this.people = people;
    }

    public String getCreek() {
        return creek;
    }

    public int getPeople() {
        return people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandParameters)) {
            return false;
        }

        LandParameters that = (LandParameters) o;

        return people == that.people && creek.equals(that.creek);
    }

    @Override
    public int hashCode() {
        int result = creek != null ? creek.hashCode() : 0;
        result = 31 * result + people;
        return result;
    }
}
