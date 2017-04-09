package xyz.dreamcoder.minicommons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FromSpecification<T> extends SingleSpecification<T> {

    private final String propName;
    private final Comparable propValue;
    private final boolean include;

    public FromSpecification(String propName, Comparable propValue, boolean include) {
        this.propName = propName;
        this.propValue = propValue;
        this.include = include;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return include ? builder.greaterThanOrEqualTo(getFieldPath(root, query, propName), propValue)
                : builder.greaterThan(getFieldPath(root, query, propName), propValue);
    }
}
