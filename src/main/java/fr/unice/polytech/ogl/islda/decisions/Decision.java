package fr.unice.polytech.ogl.islda.decisions;

import fr.unice.polytech.ogl.islda.utils.Utils;

/**
 * @author Lucas Soumille
 * @version 23/02/15
 */
public abstract class Decision implements DecisionInterface {
    protected String action;

    public Decision(String action){
        this.action = action;
    }

    public abstract boolean isValid();

    @Override
    public String toJSON(){
        return Utils.json.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Decision)) {
            return false;
        }

        Decision decision = (Decision) o;

        return action.equals(decision.action);
    }

    @Override
    public int hashCode() {
        return action != null ? action.hashCode() : 0;
    }
}
