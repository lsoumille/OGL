package fr.unice.polytech.ogl.islda.model;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class Pois {
    private String kind;
    private String id;

    public Pois(String kind, String id) {
        this.kind = kind;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pois)) {
            return false;
        }

        Pois pois = (Pois) o;

        return id.equals(pois.id) && kind.equals(pois.kind);
    }

    @Override
    public int hashCode() {
        int result = kind != null ? kind.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
