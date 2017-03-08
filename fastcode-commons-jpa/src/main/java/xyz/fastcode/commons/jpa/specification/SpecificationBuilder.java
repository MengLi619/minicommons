package xyz.fastcode.commons.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class SpecificationBuilder<T> {

    @FunctionalInterface
    interface Operator<T> {
        Specification<T> operate(String property);
    }

    public static <T> Operator<T> equalTo(Object value) {
        return property -> new EqualSpecification<T>(property, value);
    }

    public static <T> Operator<T> notEqualTo(Object value) {
        return property -> new NotSpecification<>(property, value);
    }

    public static <T> Operator<T> like(Object value) {
        return property -> new LikeSpecification<>(property, value);
    }

    public static <T> Operator<T> greaterThan(Comparable value) {
        return property -> new FromSpecification<>(property, value, false);
    }

    public static <T> Operator<T> greaterThanOrEqualTo(Comparable value) {
        return property -> new FromSpecification<>(property, value, true);
    }

    public static <T> Operator<T> lessThan(Comparable value) {
        return property -> new ToSpecification<>(property, value, false);
    }

    public static <T> Operator<T> lessThanOrEqualTo(Comparable value) {
        return property -> new ToSpecification<>(property, value, true);
    }

    public static <T> Operator<T> in(Collection values) {
        return property -> new InSpecification<>(property, values);
    }

    public static <T> Operator<T> notIn(Collection values) {
        return property -> new NotInSpecification<>(property, values);
    }

    private final Specification<T> specification;

    public SpecificationBuilder() {
        this(Specifications.and());
    }

    public SpecificationBuilder(Specification<T> specification) {
        this.specification = specification;
    }

    public SpecificationBuilder<T> and(String property, Operator<T> operator) {

        return new SpecificationBuilder<>(
                Specifications.and(specification, operator.operate(property)));
    }

    public SpecificationBuilder<T> or(String property, Operator<T> operator) {

        return new SpecificationBuilder<>(
                Specifications.or(specification, operator.operate(property)));
    }

    public SpecificationBuilder<T> and(Specification<T> spec) {
        return new SpecificationBuilder<T>(Specifications.and(specification, spec));
    }

    public SpecificationBuilder<T> or(Specification<T> spec) {
        return new SpecificationBuilder<T>(Specifications.or(specification, spec));
    }

    public Specification<T> build() {
        return specification;
    }
}