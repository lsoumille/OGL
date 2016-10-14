package fr.unice.polytech.ogl.islda.parameters;

/**
 * @author Pascal Tung
 * @version 23/03/15
 */
public class GlimpseParameters extends DirectionParameters {
    private int range;

    public GlimpseParameters(String direction, int range) {
        super(direction);
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        GlimpseParameters that = (GlimpseParameters) o;

        return range == that.range;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + range;
        return result;
    }

    public int getRange() {
        return range;
    }
}
