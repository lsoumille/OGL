package fr.unice.polytech.ogl.islda.model;

/**
 * @author Lucas Soumille
 * @version 01/03/15
 */
public class Resource {
    private String resource;
    private String amount;
    private String cond;

    public Resource(String resource, String amount, String cond) {
        this.resource = (resource != null) ? resource : "";
        this.amount = (amount != null) ? amount : "";
        this.cond = (cond != null) ? cond : "";
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }

        Resource resource1 = (Resource) o;

        if (!amount.equals(resource1.amount)) {
            return false;
        }
        if (!cond.equals(resource1.cond)) {
            return false;
        }
        if (!resource.equals(resource1.resource)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = resource != null ? resource.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (cond != null ? cond.hashCode() : 0);
        return result;
    }
}
