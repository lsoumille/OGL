package fr.unice.polytech.ogl.islda.model;

/**
 * @author Pascal Tung
 */
public class ResourceSec {
    String resource;
    double amount;

    public ResourceSec(String resource, double amount) {
        this.resource = resource;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceSec that = (ResourceSec) o;

        if (Double.compare(that.amount, amount) != 0) {
            return false;
        }
        return !(resource != null ? !resource.equals(that.resource) : that.resource != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = resource != null ? resource.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getResource() {
        return resource;
    }

    public double getAmount() {
        return amount;
    }
}
