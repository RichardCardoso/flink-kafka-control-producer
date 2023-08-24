package models;

import java.io.Serializable;
import java.util.Objects;

public class ComparisonRule implements Serializable {

    private final static String INVALID_UNARY_MSG_SUFFIX = " is not a valid unary comparator";
    private final static String INVALID_BINARY_MSG_SUFFIX = " is not a valid binary comparator";
    private static final String NULL_VALUES_ERROR = "Null value is not allowed!";
    private static final String NULL_REF_VALUE_ERROR = "Reference value must be non null!";

    private final Comparator comparator;
    private final Double value1;
    private final Double value2;
    private final ComparisonType comparisonType;

    public static ComparisonRule of(Comparator unaryComparator, double value1) {

        return new ComparisonRule(unaryComparator, value1);
    }

    public static ComparisonRule of(Comparator binaryComparator, double value1, double value2) {

        return new ComparisonRule(binaryComparator, value1, value2);
    }

    private ComparisonRule(Comparator unaryComparator, Double value1) {

        validateUnaryComparator(unaryComparator);
        validateUnaryValues(value1);
        this.comparator = unaryComparator;
        this.value1 = value1;
        this.value2 = null;
        this.comparisonType = ComparisonType.UNARY;
    }

    private ComparisonRule(Comparator binaryComparator, Double value1, Double value2) {

        validateBinaryComparator(binaryComparator);
        validateBinaryValues(value1, value2);
        this.comparator = binaryComparator;
        this.value1 = value1;
        this.value2 = value2;
        this.comparisonType = ComparisonType.BINARY;
    }

    public boolean matches(Double reference) {

        if (Objects.isNull(reference)) {
            throw new RuntimeException(NULL_REF_VALUE_ERROR);
        }

        switch (comparisonType){
            case UNARY:
                return unaryMatch(reference);
            case BINARY:
                return binaryMatch(reference);
            default:
                throw new RuntimeException("Invalid comparison parameters");
        }
    }

    private boolean unaryMatch(Double reference) {

        if (value1 == null || reference == null) {
            return false; // You can decide how to handle null values
        }

        switch (comparator) {
            case SMALLER_THAN:
                return value1.compareTo(reference) < 0;
            case SMALLER_OR_EQUAL_TO:
                return value1.compareTo(reference) <= 0;
            case GREATER_THAN:
                return value1.compareTo(reference) > 0;
            case GREATER_OR_EQUAL_TO:
                return value1.compareTo(reference) >= 0;
            case EQUAL:
                return value1.compareTo(reference) == 0;
            case NOT_EQUAL:
                return value1.compareTo(reference) != 0;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + comparator);
        }
    }

    private boolean binaryMatch(Double reference) {

        if (value1 == null || value2 == null || reference == null) {
            return false; // If any input is null, return false
        }

        switch (comparator) {
            case BETWEEN:
                return value1.compareTo(reference) >= 0 && value2.compareTo(reference) <= 0;
            case NOT_BETWEEN:
                return value1.compareTo(reference) < 0 || value2.compareTo(reference) > 0;
            default:
                throw new IllegalArgumentException("Unsupported BinaryComparator: " + comparator);
        }
    }

    private static void validateUnaryComparator(Comparator comparator) {

        switch (comparator) {

            case BETWEEN:
            case NOT_BETWEEN:
                throw new RuntimeException(comparator + INVALID_UNARY_MSG_SUFFIX);
        }
    }

    private static void validateBinaryComparator(Comparator comparator) {

        try {
            validateUnaryComparator(comparator);
        } catch (Exception ex) {
            if (!ex.getMessage().endsWith(INVALID_UNARY_MSG_SUFFIX)) {
                throw ex;
            }
            return;
        }
        throw new RuntimeException(comparator + INVALID_BINARY_MSG_SUFFIX);
    }

    private static void validateUnaryValues(Object value1) {

        if (Objects.isNull(value1)) {
            throw new RuntimeException(NULL_VALUES_ERROR);
        }
    }

    private void validateBinaryValues(Double value1, Double value2) {

        validateUnaryValues(value1);
        validateUnaryValues(value2);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ComparisonRule [comparisonType=").append(comparisonType);

        switch (comparisonType) {
            case UNARY:
                stringBuilder.append(", comparator=").append(comparator);
                stringBuilder.append(", value1=").append(value1);
                break;
            case BINARY:
                stringBuilder.append(", comparator=").append(comparator);
                stringBuilder.append(", value1=").append(value1);
                stringBuilder.append(", value2=").append(value2);
                break;
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private enum ComparisonType {

        UNARY,
        BINARY
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Double getValue1() {
        return value1;
    }

    public Double getValue2() {
        return value2;
    }

    public ComparisonType getComparisonType() {
        return comparisonType;
    }
}
