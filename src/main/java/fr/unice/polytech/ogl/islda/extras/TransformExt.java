package fr.unice.polytech.ogl.islda.extras;

import java.util.List;

/**
 * @author Pascal Tung
 */
public class TransformExt extends Extras {
    String kind;
    int production;

    public TransformExt(String kind, int production) {
        this.kind = kind;
        this.production = production;
    }

    @Override
    public List<?> getResources() {
        return null;
    }

    public String getKind() {
        return this.kind;
    }

    public int getProduction() {
        return this.production;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransformExt that = (TransformExt) o;

        return production == that.production && !(kind != null ? !kind.equals(that.kind) : that.kind != null);
    }

    @Override
    public int hashCode() {
        int result = kind != null ? kind.hashCode() : 0;
        result = 31 * result + production;
        return result;
    }
}
