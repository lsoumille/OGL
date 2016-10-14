package fr.unice.polytech.ogl.islda.parameters;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class DirectionParameters {
    private String direction;

    public DirectionParameters(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectionParameters)) {
            return false;
        }

        DirectionParameters that = (DirectionParameters) o;

        return direction.equals(that.direction);
    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
