package models;

import java.util.ArrayList;
import java.util.List;

public class Comparison {

    private final List<ComparisonRule> comparisonRules;

    private final ComparisonRange range;

    public static Comparison of(ComparisonRange range) {

        return new Comparison(range);
    }

    private Comparison(ComparisonRange range) {
        this.range = range;
        comparisonRules = new ArrayList<>();
    }

    public void addRule(ComparisonRule rule) {

        comparisonRules.add(rule);
    }

    public List<ComparisonRule> getComparisonRules() {
        return new ArrayList<>(comparisonRules);
    }

    public ComparisonRange getRange() {
        return range;
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "comparisonRules=" + comparisonRules +
                ", range=" + range +
                '}';
    }
}
