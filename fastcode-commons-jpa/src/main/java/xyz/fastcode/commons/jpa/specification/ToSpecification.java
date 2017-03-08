package xyz.fastcode.commons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ToSpecification<T> extends SingleSpecification<T> {

    private final String fieldName;
    private final Comparable fieldValue;
    private final boolean include;

    public ToSpecification(String fieldName, Comparable fieldValue, boolean include) {

        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.include = include;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return include ? builder.lessThanOrEqualTo(getFieldPath(root, query, fieldName), this.fieldValue)
                : builder.lessThan(getFieldPath(root, query, fieldName), this.fieldValue);
    }
}