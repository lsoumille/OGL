package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.map.Direction;
import fr.unice.polytech.ogl.islda.parameters.DirectionParameters;

/**
 * @author Lucas MARTINEZ
 * @version 06/03/15
 */
public abstract class DecisionWithDirection extends Decision {
    protected DirectionParameters parameters;

    public DecisionWithDirection(String action, Direction direction) {
        super(action);
        this.parameters = new DirectionParameters(direction.toString());
    }

    public DirectionParameters getParameters() {
        return parameters;
    }

    public void setParameters(DirectionParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecisionWithDirection)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        DecisionWithDirection that = (DecisionWithDirection) o;

        return parameters.equals(that.parameters);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
