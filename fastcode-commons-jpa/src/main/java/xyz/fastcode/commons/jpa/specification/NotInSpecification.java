package xyz.fastcode.commons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class NotInSpecification<T> extends SingleSpecification<T> {

    private final String propName;
    private final Collection fieldValue;

    public NotInSpecification(String propName, Collection fieldValue) {
        this.propName = propName;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.not(getFieldPath(root, query, propName).in(fieldValue));
    }
}