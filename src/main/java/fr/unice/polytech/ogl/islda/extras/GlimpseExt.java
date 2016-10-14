package fr.unice.polytech.ogl.islda.extras;

import java.util.List;

/**
 * @author Nicolas HORY
 * @version 20/03/15.
 */
public class GlimpseExt extends Extras {
    private List<List<Object>> report;
    private int asked_range;

    public GlimpseExt(int range, List<List<Object>> report) {
        this.asked_range = range;
        this.report = report;
    }

    @Override
    public List<List<Object>> getResources() {
        return report;
    }

    public int getAsked_range() {
        return asked_range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GlimpseExt)) {
            return false;
        }

        GlimpseExt that = (GlimpseExt) o;

        return report.equals(that.report) && asked_range == that.asked_range;
    }

    @Override
    public int hashCode() {
        int result = report != null ? report.hashCode() : 0;
        result = 31 * result + asked_range;
        return result;
    }
}
