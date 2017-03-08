package xyz.fastcode.commons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotSpecification<T> extends SingleSpecification<T> {

    private final String fieldName;
    private final Object fieldValue;

    public NotSpecification(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return fieldValue == null
                ? builder.isNotNull(getFieldPath(root, query, fieldName))
                : builder.notEqual(getFieldPath(root, query, fieldName), fieldValue);
    }
}