package misc;

import java.util.ArrayList;
import java.util.List;

public class Comparison {

    private final List<ComparisonRule<? extends Comparable<?>>> comparisonRules;

    private final ComparisonRange range;

    public Comparison(ComparisonRange range) {
        this.range = range;
        comparisonRules = new ArrayList<>();
    }

    public void addRule(ComparisonRule<? extends Comparable<?>> rule) {

        comparisonRules.add(rule);
    }

    public List<ComparisonRule<? extends Comparable<?>>> getComparisonRules() {
        return new ArrayList<>(comparisonRules);
    }

    public ComparisonRange getRange() {
        return range;
    }
}
